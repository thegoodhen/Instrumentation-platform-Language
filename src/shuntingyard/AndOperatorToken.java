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
public class AndOperatorToken extends LogicalOperatorToken {

	public AndOperatorToken(String s) {
		super(s);
	}

	@Override
	public int getPriority()
	{
		return -200;
	}

	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		if (Math.abs(a-1)<0.0000001 && Math.abs(b-1)<0.0000001) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public byte getBaseCode() {
		return 68;
	}

}
