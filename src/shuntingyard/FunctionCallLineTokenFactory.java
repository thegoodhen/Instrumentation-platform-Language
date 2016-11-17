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
public class FunctionCallLineTokenFactory extends TokenFactory {


	@Override
	public String getRegex() {
		return "^([A-Za-z_][A-Za-z0-9_.]*\\s*\\(.*\\));";//Added the dot; this is specifically for my super awesome project of the platform for stuff, but its not really a problem, I hope
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new FunctionCallLineToken(tokenString);
	}

}
