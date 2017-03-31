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
public class ByteNumberToken extends NumberToken {

	public ByteNumberToken(String s) {
		super(s);
	}

	public ByteNumberToken() {
		super("0");
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		theStack.push(this);
		c.getByteCode().push(new NumericByteCodeToken((byte) (this.getValue())));
	}

	@Override
	public int getNumberOfBytes() {
		return 1;
	}

	@Override
	public boolean isImplicitlyCastableTo(NumberToken nt) {
		return true;
	}

	@Override
	public int compileCastTo(NumberToken nt, int stackPos, Compiler c) throws RuntimeException {
	    int addedBytes=0;
		if (!isImplicitlyCastableTo(nt)) {
			throw new RuntimeException();
		} else if (nt instanceof IntegerNumberToken) {

			if(compileNumber(stackPos, c))
			{
			    addedBytes=2;
			}
			else
			{
			    addedBytes=1;
			}
			c.getByteCode().push(new CastByteToIntegerByteCodeToken());
		} else if (nt instanceof FloatNumberToken) {

			//compileNumber(stackPos, c);
			if(compileNumber(stackPos, c))
			{
			    addedBytes+=2;
			}
			else
			{
			    addedBytes+=1;
			}
			c.getByteCode().push(new CastByteToIntegerByteCodeToken());
			if(compileNumber(stackPos+1, c))
			{
			    addedBytes+=2;
			}
			else
			{
			    addedBytes+=1;
			}
			//compileNumber(stackPos+1, c);
			c.getByteCode().push(new CastIntegerToFloatByteCodeToken());
		}
		return addedBytes;
	}

	@Override
	public String getShortName() {
		return "byte";
	}

}
