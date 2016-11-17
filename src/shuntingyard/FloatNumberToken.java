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
public class FloatNumberToken extends NumberToken {

	public FloatNumberToken(String s) {
		super(s);
	}

	public FloatNumberToken()
	{
		super("0");
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		theStack.push(this);
		float val = this.getValue();
		byte[] floatBytes=HelpByteMethods.getFloatBytes(val);
		for(int i=0;i<floatBytes.length;i++)
		{
			c.getByteCode().push(new NumericByteCodeToken(floatBytes[i]));
		}
	}

	@Override
	public int getNumberOfBytes() {
		return 4;
	}

	@Override
	public boolean isImplicitlyCastableTo(NumberToken nt) {
		if(nt instanceof FloatNumberToken)
		{
			return true;
		}
		return false;
	}

	@Override
	public void compileCastTo(NumberToken nt, int stackPos, Compiler c) throws RuntimeException {
		if(!isImplicitlyCastableTo(nt))
		{
			throw new RuntimeException("Cast error, lossy conversion would result.");
		}
	}


	@Override
	public String getShortName() {
		return "float";
	}

}
