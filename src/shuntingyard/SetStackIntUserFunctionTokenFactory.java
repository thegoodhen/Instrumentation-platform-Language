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
public class SetStackIntUserFunctionTokenFactory extends TokenFactory {


	@Override
	public String getRegex() {
		return "setSi";//for short
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new SetStackIntUserFunctionToken(tokenString);
	}



}
