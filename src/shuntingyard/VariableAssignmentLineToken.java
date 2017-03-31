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
public class VariableAssignmentLineToken extends LineToken implements IRunnableToken {

	String variableString;
	String expressionString;
	Expression ex;
	private VariableToken targetVariable;
	private boolean extended;

	public VariableAssignmentLineToken(String s) {
		super(s);
	}

	@Override
	public int getID() {
		return LineToken.VARIABLE_ASSIGNMENT;
	}

	@Override
	public String getRegex() {
		return "([A-Za-z][A-Za-z0-9]*)\\s*=\\s*(.+);";
		//return "([A-Za-z][A-Za-z0-9]*)\\s*=\\s*.+;";//TODO: use Variable.getRegex();
		//return "^(.*?=.*?);";
	}

	public void prepare(Compiler c) throws CompilerException {

		Pattern pattern = Pattern.compile(this.getRegex());
		Matcher matcher = pattern.matcher(this.getTokenString());
		if (matcher.matches()) {
			variableString = matcher.group(1);
			//TODO: move this, we might not know the global variable yet...
			if (c.getCurrentFunction() != null) {
				targetVariable = c.getCurrentFunction().getLocalVariable(variableString);
			}
			if (targetVariable == null) {
				targetVariable = c.getGlobalVariable(variableString);
			}
			if (targetVariable == null) {
				throw new CompilerException("Attempt to assign to a variable " + variableString + ", which is unknown in the current scope!");
			}
			expressionString = matcher.group(2);
			if (expressionString.equals(variableString)) {
				throw new CompilerException("Expression assigns a variable to itself.");
			}
		}
	}

	public void precompile(Compiler c) throws CompilerException {
		ex = c.getExpressionParser().createExpression(expressionString);
	}

	public void compile(Compiler c) throws CompilerException{
		NumberToken t = (NumberToken) ex.compile(c);//TODO: check whether this really can't throw an invalidCastException, I think it shouldn't though.
		if (t.isImplicitlyCastableTo(targetVariable.getType())) {
			t.compileCastTo(targetVariable.getType(), t.getNumberOfBytes() - 1, c);
		} else {
			throw new CompilerException("Invalid assignment. Cannot assign an expression, result of which is " + t.getShortName() + " into " + targetVariable.getType().getShortName() + "; this might lead to a lossy conversion.");
		}
		/*
		 if (t instanceof IntegerNumberToken && targetVariable.getType() instanceof ByteNumberToken) {
		 System.err.println("Invalid assignment. Cannot assign an expression, result of which is an Integer, into a byte; this might lead to a lossy conversion.");
		 } else if (t instanceof ByteNumberToken && targetVariable.getType() instanceof IntegerNumberToken) {
		 //we need to cast the byte to int
		 //TODO: centralize this
		 //TODO: handle floats
		 //CompilableToken.compileNumber(0,c);
		 CompilableToken.compileByteToIntCast(0, c);
		 }
		 */
		this.setExtended(CompilableToken.compileNumber(this.targetVariable.getVariableID(), c));
		c.getByteCode().push(this);

	}

	@Override
	public void run(VirtualMachine vm) {
		int variableID = vm.popNumberFromStack(this.isExtended());//TODO: somehow handle setExtended... So far we aren't supporting more than 256 bytes big heap!
		float assignedValue = 0;
		switch (this.targetVariable.getContext()) {
			case VariableToken.GLOBAL:
				switch (this.targetVariable.getType().getNumberOfBytes())//TODO: this is hacky, change this! implementing float support and I don't wanna change too much code at once, so I need to get to a working state as soon as possible
				//we should instead have dedicated VariableTokens for each type, such as IntegerVariableToken or ByteVariableToken to avoid the following mess
				{
					case 1://byte
						assignedValue = vm.popByteFromStack();
						vm.getHeap().set(variableID, new NumericByteCodeToken((byte) assignedValue));
						break;
					case 2://int
						assignedValue = vm.popIntFromStack();
						byte msb = HelpByteMethods.getUpperByte((int) assignedValue);
						byte lsb = HelpByteMethods.getLowerByte((int) assignedValue);
						vm.getHeap().set(variableID, new NumericByteCodeToken(msb));
						vm.getHeap().set(variableID + 1, new NumericByteCodeToken(lsb));
						break;
					//TODO: handle float!
					case 4://float
						assignedValue=vm.popFloatFromStack();
						byte[] floatBytes = HelpByteMethods.getFloatBytes(assignedValue);
						for (int i = 0; i < floatBytes.length; i++) {

							vm.getHeap().set(variableID + i, new NumericByteCodeToken(floatBytes[i]));
						}
						break;
				}
				break;
			//TODO: handle argument!
			case VariableToken.LOCAL:
			case VariableToken.PARAM:

				switch (this.targetVariable.getType().getNumberOfBytes())//again, hacky
				{
					case 1://byte
						assignedValue = vm.popByteFromStack();
						vm.getStack().set(vm.getBasePointer() + variableID , new NumericByteCodeToken((byte) assignedValue));
						break;
					case 2://int
						assignedValue = vm.popIntFromStack();
						byte msb = HelpByteMethods.getUpperByte((int) assignedValue);
						byte lsb = HelpByteMethods.getLowerByte((int) assignedValue);
						vm.getStack().set(vm.getBasePointer() + variableID , new NumericByteCodeToken(msb));
						vm.getStack().set(vm.getBasePointer() + variableID + 1, new NumericByteCodeToken(lsb));
						break;
					//TODO: handle float!

					case 4://float

						byte[] floatBytes = HelpByteMethods.getFloatBytes(assignedValue);
						for (int i = 0; i < floatBytes.length; i++) {

							vm.getStack().set(variableID + 1 + i, new NumericByteCodeToken(floatBytes[i]));
						}

						break;
				}
				break;
		}
		//System.out.println("Assigned value "+assignedValue+" to "+this.targetVariable.getTokenString()+"!");

	}






	@Override
	public boolean isExtended() {
		return this.extended;
	}

	@Override
	public void setExtended(boolean ex) {
		this.extended=ex;
	}


	@Override
	public byte getBaseCode() {
		byte baseCode = 22;
		if (this.targetVariable.getType() instanceof IntegerNumberToken) {
			baseCode += 2;
		}
		if (this.targetVariable.getType() instanceof FloatNumberToken) {
			baseCode += 4;
		}
		if(this.targetVariable.getContext()==VariableToken.GLOBAL)
		{
			baseCode+=6;
		}
		return baseCode;
	}

	@Override
	public byte getCode() {
		if(extended)
		{
			return (byte) (this.getBaseCode()+1);
		}
		return this.getBaseCode();
	}
}
