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
@Deprecated
public class AssignmentOperatorTokenFactory extends TokenFactory {


	@Override
	public String getRegex() {
		return "=";
	}

	@Override
	@Deprecated
	public Token generateInstance(String tokenString) {
		return new AssignmentOperatorToken(tokenString);
	}

}
