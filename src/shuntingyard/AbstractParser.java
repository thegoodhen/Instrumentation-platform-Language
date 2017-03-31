/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;

/**
 * Abstraction for classes that use a factory to generate a stream of Tokens
 * from a String.
 *
 * @author thegoodhen
 */
public class AbstractParser {

	private TokenFactory tf;
	private int currentIndex = 0;
	private int tokenNumber = 0;

	public AbstractParser() {
		tf = new GeneralTokenFactory();
	}

	/**
	 * Set the factory for this parser.
	 * @param tf the factory to set
	 */
	public void setFactory(TokenFactory tf) {
		this.tf = tf;
	}

	/**
	 * Let user know that an error happened during parsing.
	 * @param tokenNumber the index of the Token the error occured on.
	 */
	public void parsingError(int tokenNumber) throws CompilerException{

	    throw new CompilerException("Unknown token: "+tokenNumber+" Parser exiting!");
	}

	/**
	 * Turn a string representing an expression written in infix notation
	 * with function calls into an ArrayList of Token objects. This method
	 * makes calls to the set tokenFactory. Specifically, it asks the
	 * factory, whether it can create token given a starting position in the
	 * given String, and then collects information about where the said
	 * Token ends in the text, and advances the starting position 
	 * correspondingly, before making calls to the Factory again.
	 * Function returns on error (when the Factory is unable to create Token
	 * on the given position) or when the whole given String is processed.
	 * The ordering of tokens stays
	 * consistent with the ordering imposed by the given String.
	 *
	 * @param s the String to generate an arrayList of tokens from
	 * @return ArrayList of Tokens, generated from the input String
     * @throws shuntingyard.CompilerException on unknown token
	 */
	public ArrayList<Token> Tokenize(String s) throws CompilerException{
		//System.out.println(startsWithRegex(s,"[+-/*//><=]"));
		//System.out.println(getTokenEnd(s,"[+-/*//><=]",0));
		//System.out.println(getTokenEnd(s,"([A-Za-z][A-Za-z0-9]*).*",0));
//([A-Za-z][A-Za-z0-9]*).*
		//s = stripSpaces(s);
		currentIndex = 0;
		tokenNumber=0;
		ArrayList<Token> returnList = new ArrayList<>();

		while (currentIndex < s.length()) {
			//String tokenString = crunchToken(s);
			Token t = tf.create(s, currentIndex);
			if (t == null) {
				parsingError(tokenNumber);
				break;
			}
			currentIndex = tf.getRegexEnd();
			//Token t = tf.create(lastTokenID, tokenString);
			returnList.add(t);
			tokenNumber++;
			//System.out.println(t.getTokenString());
			//System.out.println(crunchToken(s));
		}
		return returnList;
	}

}
