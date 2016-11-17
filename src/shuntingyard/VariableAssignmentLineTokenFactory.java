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
public class VariableAssignmentLineTokenFactory extends TokenFactory {

	@Override
	public String getRegex() {
		//pointless overhead in creating a new instance caused by Java not allowing abstract
		//methods to be static
		return new VariableTokenFactory().getRegex() + "\\s*=\\s*(.+);";
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new VariableAssignmentLineToken(tokenString);
	}

}
