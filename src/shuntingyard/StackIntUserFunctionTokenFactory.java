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
public class StackIntUserFunctionTokenFactory extends TokenFactory {


	@Override
	public String getRegex() {
		return "si";//for short
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new StackIntUserFunctionToken(tokenString);
	}



}
