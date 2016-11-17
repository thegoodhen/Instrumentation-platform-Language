/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

/**
 * Token which separates arguments in function calls and fuction declarations.
 * @author thegoodhen
 */
public class ArgumentSeparatorToken extends Token{

	ArgumentSeparatorToken(String tokenString) {
		super(tokenString);
	}

	@Override
	public String getRegex()
	{
		return  ",";
	}

	@Override
	public int getID() {
		return Token.ARGUMENTSEPARATOR;
	}
	
}
