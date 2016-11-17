/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 * Addition - a+b;
 * @author thegoodhen
 */
public class AdditionOperatorToken extends OperatorToken{

	AdditionOperatorToken(String tokenString) {
		super(tokenString);
	}
	
	@Override
	public int getPriority()
	{
		return 10;
	}

	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		return a+b;
	}

	@Override
	public byte getBaseCode() {
		return 40;
	}

}