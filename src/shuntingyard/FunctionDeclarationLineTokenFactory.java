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
public class FunctionDeclarationLineTokenFactory extends TokenFactory {

	@Override
	public String getRegex() {
		return "^(void|byte|int)\\s+([A-Za-z][A-Za-z_0-9]*)\\((.*)\\)";//TODO: ...the hell? we... we aren't supporting floats? look into this!!
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new FunctionDeclarationLineToken(tokenString);
	}

}
