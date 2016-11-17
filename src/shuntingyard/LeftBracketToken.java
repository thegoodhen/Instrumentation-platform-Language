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
public class LeftBracketToken extends Token {

	public LeftBracketToken(String s) {
		super(s);
	}

	@Override
	public String getRegex()
	{
		return  "\\(";
	}

	@Override
	public int getID() {
		return Token.LEFTBRACKET;
	}

}
