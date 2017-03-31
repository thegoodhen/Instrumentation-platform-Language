/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public abstract class OperatorToken extends CompilableToken implements IRunnableToken{

	public final static int ASSOCIATIVITY_LEFT = 0;
	public final static int ASSOCIATIVITY_RIGHT = 1;
	private NumberToken operandType = new ByteNumberToken();

	public OperatorToken(String s) {
		super(s);
	}

	@Override
	public byte getCode()
	{
		byte baseCode=this.getBaseCode();
		if (this.operandType instanceof IntegerNumberToken)
		{
			baseCode+=1;
		}
		if (this.operandType instanceof FloatNumberToken)
		{
			baseCode+=2;
		}
		return baseCode;
	}

	@Override
	public void setExtended(boolean ex)
	{
		//do nothing
	}

	
	@Override
	public boolean isExtended()
	{
		return false;
	}

	@Override
	public int getID() {
		return Token.OPERATOR;
	}

	@Override //TODO: make this static, ditch the override, make the regex subject to change
	public String getRegex() {
		return "(\\+|-|==|=|\\/|\\*)";
		//return  "[\\+-/*\\\\/]";
	}
	

	public NumberToken getOperandType()
	{
		return this.operandType;
	}

	public int getPriority() {
		return -1;
	}

	public int getAssociativity() {
		return ASSOCIATIVITY_LEFT;
	}

	@Override
	public int getArgumentCount() {
		return 2;
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) throws CompilerException {
		//first we get the 2 operands off our working stack
		Token o1=null;
		Token o2=null;
		
		if (theStack.size() >= 2) {
			o1 = theStack.pop();
			o2 = theStack.pop();
		}
		else
		{
			throw new CompilerException("Not enough arguments to an operator: \""+this.getTokenString()+"\"");
		}
		//Then we make sure those are actually numbers; if not, this is not RPN!
		if (!(o1 instanceof NumberToken && o2 instanceof NumberToken)) {
			throw new CompilerException("Expected numeric arguments or variables for an operator; Operator" + this.getTokenString() + " cannot be applied to arguemnts of type" + o1.getClass().getSimpleName() + " and " + o2.getClass().getSimpleName() + "!");
		}
		
		

		if(o1.getClass().equals(o2.getClass()))
		{
			this.operandType=(NumberToken)o1;
		}
		else if(((NumberToken)o1).isImplicitlyCastableTo((NumberToken)o2))
		{
			this.operandType=(NumberToken)o2;
			((NumberToken)o1).compileCastTo((NumberToken)o2, ((NumberToken)(o1)).getNumberOfBytes()-1, c);
		} else if(((NumberToken)o2).isImplicitlyCastableTo((NumberToken)o1))
		{
			this.operandType=(NumberToken)o1;
			((NumberToken)o2).compileCastTo((NumberToken)o1, ((NumberToken)(o1)).getNumberOfBytes()+((NumberToken)(o2)).getNumberOfBytes()-1, c);
		}
		else
		{
			c.issueError("Cannot apply operator "+this.getTokenString()+" to arguments of type "+((NumberToken)(o2)).getShortName()+" and "+((NumberToken)o1).getShortName()+"!");
		}


		/*
		else if (o1 instanceof ByteNumberToken && o2 instanceof ByteNumberToken) {
			this.operandType = VariableToken.BYTE;
		} else if (o1 instanceof IntegerNumberToken && o2 instanceof IntegerNumberToken) {
			this.operandType = VariableToken.INT;
		} else if (o1 instanceof ByteNumberToken && o2 instanceof IntegerNumberToken) {
			this.operandType = VariableToken.INT;
			compileNumber(0, c);
			c.getByteCode().push(new CastToIntegerByteCodeToken());
		} else if (o1 instanceof IntegerNumberToken && o2 instanceof ByteNumberToken) {
			this.operandType = VariableToken.INT;
			compileNumber(2, c);
			c.getByteCode().push(new CastToIntegerByteCodeToken());
		}
		*/


		/*
		if (this.operandType == VariableToken.BYTE) {
			theStack.push(new ByteNumberToken("0"));
		} else if (this.operandType == VariableToken.INT) {
			theStack.push(new IntegerNumberToken("0"));
		}
			*/
		pushSelf(theStack,c);
	}

	protected void pushSelf(LinkedList<Token> theStack, Compiler c)
	{
		
		theStack.push(this.operandType);
		c.getByteCode().push(this);
	}

	//@Override
	public NumberToken compute(LinkedList<Token> theStack) throws CompilerException {
		if (theStack.size() < getArgumentCount()) {
			throw new CompilerException("Not enough arguments on stack!");
		}
		Token t1 = theStack.pop();
		Token t2 = theStack.pop();
		if (t1 instanceof NumberToken && t2 instanceof NumberToken)//TODO: support variables
		{
			int newVal = (int)computeBinaryOperatorFromNumbers(((NumberToken) t1).getValue(), ((NumberToken) t2).getValue());
			NumberTokenFactory ntf = new NumberTokenFactory();
			NumberToken t3 = (NumberToken) ntf.create(Integer.toString(newVal), 0);//new NumberToken(Integer.toString(newVal));

			theStack.push(t3);
			return t3;
		}
		throw new CompilerException("Cannot compute operator, expected numeric arguments");
	}

	public abstract float computeBinaryOperatorFromNumbers(float a, float b);



	@Override
	public void run(VirtualMachine vm)
	{
		switch(this.operandType.getNumberOfBytes())
		{

			//unhandled exceptions below, but those couldn't be recovered from anyway and would be unexpected!
			//Todo: bit of a mess with the magic numbers, fix the switch case.
			case 1://byte
				//NumericByteCodeToken o2=(NumericByteCodeToken) vm.getStack().pop();
				byte o2=vm.popByteFromStack();
				//NumericByteCodeToken o1=(NumericByteCodeToken) vm.getStack().pop();
				byte o1=vm.popByteFromStack();
				byte ret=(byte) computeBinaryOperatorFromNumbers(o1,o2);
				vm.pushByteOnStack(ret);
				//vm.getStack().push(new NumericByteCodeToken(ret));
				break;
			case 2://int
				//NumericByteCodeToken o2lsb=(NumericByteCodeToken) vm.getStack().pop();
				byte o2lsb=vm.popByteFromStack();
				//NumericByteCodeToken o2msb=(NumericByteCodeToken) vm.getStack().pop();
				byte o2msb=vm.popByteFromStack();
				int o2int=HelpByteMethods.constructInt(o2msb, o2lsb);
				//NumericByteCodeToken o1lsb=(NumericByteCodeToken) vm.getStack().pop();
				byte o1lsb=vm.popByteFromStack();
				//NumericByteCodeToken o1msb=(NumericByteCodeToken) vm.getStack().pop();
				byte o1msb=vm.popByteFromStack();
				int o1int=HelpByteMethods.constructInt(o1msb, o1lsb);
				int ret2=(int) (computeBinaryOperatorFromNumbers(o1int,o2int));
				byte retMsb=HelpByteMethods.getUpperByte(ret2);
				byte retLsb=HelpByteMethods.getLowerByte(ret2);
				//vm.getStack().push(new NumericByteCodeToken(retMsb));
				vm.pushByteOnStack(retMsb);
				//vm.getStack().push(new NumericByteCodeToken(retLsb));
				vm.pushByteOnStack(retLsb);
				break;
			case 4://float
				float o2f=vm.popFloatFromStack();
				float o1f=vm.popFloatFromStack();
				float result=computeBinaryOperatorFromNumbers(o1f,o2f);
				vm.pushFloatOnStack(result);
				break;
		}
	}

}
