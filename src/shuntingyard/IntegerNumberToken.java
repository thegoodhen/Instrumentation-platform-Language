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
public class IntegerNumberToken extends NumberToken {

	public IntegerNumberToken(String s) {
		super(s);
	}

	public IntegerNumberToken() {
		super("0");
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		theStack.push(this);
		int val = (int) this.getValue();
		byte lowerByte = (byte) (val & 0xFF);
		byte upperByte = (byte) ((val >> 8) & 0xFF);
		c.getByteCode().push(new NumericByteCodeToken(upperByte));
		c.getByteCode().push(new NumericByteCodeToken(lowerByte));
	}

	@Override
	public int getNumberOfBytes() {
		return 2;
	}

	@Override
	public boolean isImplicitlyCastableTo(NumberToken nt) {
		if (nt instanceof IntegerNumberToken || nt instanceof FloatNumberToken) {
			return true;
		}
		return false;
	}

	@Override
	public int compileCastTo(NumberToken nt, int stackPos, Compiler c) throws RuntimeException {
	    int returnValue=0;
		if (isImplicitlyCastableTo(nt)) {
			if (nt instanceof FloatNumberToken) {
				if(CompilableToken.compileNumber(stackPos, c))
				{
				    returnValue = 2;
				}
				else
				{
				    returnValue = 1;
				}
				c.getByteCode().push(new CastIntegerToFloatByteCodeToken());
			}
		}
		else
		{
			throw new RuntimeException("Cannot cast integer to + "+nt.getClass().getSimpleName());
		}
		return returnValue;
	}

	@Override
	public String getShortName() {
		return "int";
	}
}
