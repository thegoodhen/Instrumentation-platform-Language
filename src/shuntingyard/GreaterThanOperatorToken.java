/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

/**
 *
 * @author thegoodhen
 */
public class GreaterThanOperatorToken extends BooleanReturningOperatorToken {

	public GreaterThanOperatorToken(String s) {
		super(s);
	}

	@Override
	public int getPriority()
	{
		return -100;
	}

	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		if (a>b) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public byte getBaseCode() {
		return 52;
	}

}
