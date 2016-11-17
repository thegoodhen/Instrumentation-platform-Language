package shuntingyard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thegoodhen
 */
public abstract class Token {

	@Deprecated
	public static final int NUMBER = 0;
	@Deprecated
	public static final int LEFTBRACKET = 1;
	@Deprecated
	public static final int RIGHTBRACKET = 2;
	@Deprecated
	public static final int ARGUMENTSEPARATOR = 3;
	@Deprecated
	public static final int FUNCTIONCALL = 4;
	@Deprecated
	public static final int VARIABLE = 5;
	@Deprecated
	public static final int OPERATOR = 6;

	@Deprecated
	public abstract int getID();
	public String tokenString = "";

	public Token() {

	}

	@Deprecated
	public abstract String getRegex();

	public String getTokenString() {
		return tokenString;
	}

	public Token(String tokenString) {
		this.tokenString = tokenString;
	}
}
