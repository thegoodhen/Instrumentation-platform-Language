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
	public void compileCastTo(NumberToken nt, int stackPos, Compiler c) throws RuntimeException {
		if (!isImplicitlyCastableTo(nt)) {
			throw new RuntimeException();
		} else if (nt instanceof IntegerNumberToken) {
			compileNumber(stackPos, c);
			c.getByteCode().push(new CastByteToIntegerByteCodeToken());
		} else if (nt instanceof FloatNumberToken) {
			compileNumber(stackPos, c);
			c.getByteCode().push(new CastByteToIntegerByteCodeToken());
			compileNumber(stackPos+1, c);
			c.getByteCode().push(new CastIntegerToFloatByteCodeToken());
		}
	}

	@Override
	public String getShortName() {
		return "byte";
	}

}
