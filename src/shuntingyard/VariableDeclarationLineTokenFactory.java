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
public class VariableDeclarationLineTokenFactory extends TokenFactory {


	@Override
	public String getRegex() {
		NumberTokenFactory ntf=new NumberTokenFactory();
		return "^"+ntf.getTypeRegex()+"\\s+([A-Za-z][A-Za-z0-9]*);";
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new VariableDeclarationLineToken(tokenString);
	}

}
