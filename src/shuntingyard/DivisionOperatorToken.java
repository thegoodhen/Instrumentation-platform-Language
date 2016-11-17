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
public class DivisionOperatorToken extends OperatorToken{

	public DivisionOperatorToken(String s) {
		super(s);
	}
	
	@Override
	public int getPriority()
	{
		return 20;
	}

	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		return a/b;
	}

	@Override
	public byte getBaseCode() {
		return 49;
	}
}
