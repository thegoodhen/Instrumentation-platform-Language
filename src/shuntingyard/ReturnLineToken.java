/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class ReturnLineToken extends LineToken implements IRunnableToken {

	private String expressionString;
	private FunctionDeclarationLineToken fdlt;
	private Expression ex;
	private boolean extendedArgumentCount = false;//whether the number of arguments of the function this return statement belong to is over 255 (more than 1 byte)

	public ReturnLineToken(String s) {
		super(s);
	}

	@Override
	public int getID() {
		return LineToken.IF;
	}

	public FunctionDeclarationLineToken getFunctionDeclaration() {
		return this.fdlt;
	}

	public void setFunctionDeclaration(FunctionDeclarationLineToken fdlt) {
		this.fdlt = fdlt;
	}

	public void prepare(Compiler c) throws CompilerException {

		Pattern pattern = Pattern.compile(this.getRegex());
		Matcher matcher = pattern.matcher(this.getTokenString());
		if (matcher.matches()) {
			expressionString = matcher.group(1);
		}

		if (this.fdlt == null) {
			throw new CompilerException("Error: return statement outside function is invalid.");
		}
	}

	public void precompile(Compiler c) throws CompilerException{
		ex = c.getExpressionParser().createExpression(expressionString);
	}

	public void compile(Compiler c) throws CompilerException {
		Token t = ex.compile(c);
		if (t instanceof NumberToken) {
			//TODO: centralize the casting
			//TODO: handle floats
			if (t instanceof IntegerNumberToken) {
				if (this.fdlt.getFunction().getReturnType() instanceof ByteNumberToken)//expression is int, should return byte
				{
					throw new CompilerException("Incompatible return values: actual and formal return types differ, and no implicit conversion exists!");
				}
			} else if (t instanceof ByteNumberToken)//expression is a byte, should return int
			{
				if (this.fdlt.getFunction().getReturnType() instanceof IntegerNumberToken) {
					CompilableToken.compileByteToIntCast(0, c);
				}
			}
		} else {
			throw new CompilerException("Invalid return type.");
		}
		this.extendedArgumentCount = CompilableToken.compileNumber(this.fdlt.getFunction().getArgumentByteCount(), c);
		c.getByteCode().push(this);

	}

	@Override
	public String getRegex() {//TODO: I have to do something about this; this object doesn't have a 
		//reference to the Factory creating it; the Factory needs to know the Regex. This leads to 
		//code duplication, since sometimes (like in this case) we have to know the Regex 
		//both in the Factory AND in the Token. The getRegex() method isn't static, so if we wanted to remotely call it from the Factory, we'd need to instantiate the Token in the factory; this could be done in the constructor of the said Factory.

		return "RETURN\\s+(.*)\\s*;";
		//return "^IF\\s+(.*)$";
		//return "^(.*?=.*?);";
	}

	@Override
	public void run(VirtualMachine vm) {
		int functionArgumentsCount = vm.popNumberFromStack(extendedArgumentCount);
		int returnValue = 0;

		if (this.fdlt.getFunction().getReturnType() instanceof ByteNumberToken) {
			returnValue = vm.popByteFromStack();
		} else if (this.fdlt.getFunction().getReturnType() instanceof IntegerNumberToken) {
			returnValue = vm.popIntFromStack();
		}
		//TODO: make more elegant, handle floats too

		//following is inefficient and points towards the need to reimplement the way stack is handled, using ArrayList, instead of LinkedList!
		//dispose of local variables
		/*
		 for(int i=0;i<(vm.getStack().size()-vm.getBasePointer());i++)
		 {
		 vm.getStack().pop();
		 }
		 */
		vm.setStackPointer(vm.getBasePointer());
		byte bpLsb = vm.popByteFromStack();//base pointer stored on stack-least significant byte
		byte bpMsb = vm.popByteFromStack();//base pointer stored on stack-most significant byte
		byte pcLsb = vm.popByteFromStack();//program counter stored on stack-least significant byte
		byte pcMsb = vm.popByteFromStack();//program counter stored on stack-most significant byte
		int newBasePointer = HelpByteMethods.constructInt(bpMsb, bpLsb);
		int newProgramCounter = HelpByteMethods.constructInt(pcMsb, pcLsb);
		vm.setProgramCounter(newProgramCounter);
		vm.setBasePointer(newBasePointer);

		//eat the function arguments
		/*
		 for (int i = 0; i < functionArgumentsCount; i++) {
		 vm.popByteFromStack();//again, inefficient!
		 }
		 */

		vm.setStackPointer(vm.getStackPointer()-functionArgumentsCount);


		if (this.fdlt.getFunction().getReturnType()instanceof ByteNumberToken) {
			vm.pushByteOnStack((byte) returnValue);
		} else if (this.fdlt.getFunction().getReturnType() instanceof IntegerNumberToken) {
			vm.pushIntOnStack(returnValue);
		}

	}

	@Override
	public boolean isExtended() {
		return this.extendedArgumentCount;
	}

	@Override
	public void setExtended(boolean ex) {
		this.extendedArgumentCount=ex;
	}

	@Override
	public byte getBaseCode() {

		if (this.fdlt.getFunction().getReturnType()instanceof ByteNumberToken) {
			return 106;
		}

		if (this.fdlt.getFunction().getReturnType()instanceof IntegerNumberToken) {
			return 108;
		}
		if (this.fdlt.getFunction().getReturnType()instanceof FloatNumberToken) {
			return 110;
		}
		return -1;
	}

	@Override
	public byte getCode() {
		if (this.isExtended())
		{
			return (byte) (this.getBaseCode()+1);
		}
		return this.getBaseCode();
	}

}
