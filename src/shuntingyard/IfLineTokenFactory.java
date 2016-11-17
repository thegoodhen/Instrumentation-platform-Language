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
public class IfLineTokenFactory extends TokenFactory {

	public Token create(int tokenID, String tokenString) {
			return new IfLineToken(tokenString);
	}

	@Override
	public String getRegex() {
		return "^IF\\s*(.*)$";
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new IfLineToken(tokenString);
	}

}
