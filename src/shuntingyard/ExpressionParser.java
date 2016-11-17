/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class ExpressionParser extends AbstractParser{

	@Deprecated
	public static String[] regularExpressions;

	@Deprecated
	private TreeMap<Integer, Token> availableTokenMap;

	public ExpressionParser() {
		availableTokenMap = new TreeMap<>();

		/*
		 regularExpressions = new String[7];
		 regularExpressions[Token.NUMBER] = "[0-9][0-9]*";
		 regularExpressions[Token.LEFTBRACKET] = "\\(";
		 regularExpressions[Token.RIGHTBRACKET] = "\\)";
		 regularExpressions[Token.ARGUMENTSEPARATOR] = ",";
		 regularExpressions[Token.FUNCTIONCALL] = "([A-Za-z][A-Za-z0-9]*)\\(";
		 regularExpressions[Token.VARIABLE] = "([A-Za-z][A-Za-z0-9]*)";
		 regularExpressions[Token.OPERATOR] = "[\\+-/*\\\\/]";
		
		 */
	}

	@Deprecated
	private int lastTokenID = -1;

	@Deprecated
	public void addToken(Token t) {
		availableTokenMap.put(t.getID(), t);
	}

	@Deprecated
	public TreeMap<Integer, Token> getAvailableTokenMap() {
		return this.availableTokenMap;
	}

	public ArrayList<Token> Tokenize(String s) {
		s=stripSpaces(s);
		return super.Tokenize(s);
	}

	/**
	 * crunches the next token, advancing the global variable currentIndex
	 * and setting the global variable lastTokenNumber
	 *
	 * @param s
	 * @return the string of which the current token consists
	 */
	/*
	@Deprecated
	private String crunchToken(String s) {

		for (Integer i : availableTokenMap.keySet()) {
			Token t = availableTokenMap.get(i);
			String regex = t.getRegex();
			int test = (getTokenEnd(s, regex, currentIndex));
			if (test != -1) {
				String returnString = s.substring(currentIndex, test);
				currentIndex = test;
				lastTokenID = i;
				return returnString;
			}
		}
		return null;
	}
	*/

	/**
	 * This method uses Shunting yard to convert an ArrayList of Tokens,
	 * ordered in an infix notation, into an ArrayList of Tokens, ordered in
	 * reverse polish notation (RPN). Note that not all of the Tokens,
	 * present in the input, will also be present in the output. The generic
	 * contract for this method allows it to discard Tokens, that are
	 * superfluous in terms of RPN, that is such tokens, that don't corrupt
	 * the inambiguity of the resulting RPN when removed.
	 *
	 * @param infixTokenList
	 * @return
	 */
	public ArrayList<Token> getRPN(ArrayList<Token> infixTokenList) {
		ArrayList<Token> returnQueue = new ArrayList<>();
		LinkedList<Token> theStack = new LinkedList<>();
		for (Token t : infixTokenList) {
			if (t instanceof NumberToken || t instanceof VariableToken|| t instanceof StringLiteralToken) {//TODO: add common interface for literals
				returnQueue.add(t);
			} else if (t instanceof FunctionToken) {
				theStack.push(t);
			} else if (t instanceof ArgumentSeparatorToken) {
				while (!(theStack.peek() instanceof LeftBracketToken)) {
					if (theStack.peek() != null) {
						returnQueue.add(theStack.pop());
					} else {
						System.err.println("Misplaced argument separator or mussing left parenthesis '(' in input");
					}
				}
			} else if (t instanceof OperatorToken) {
				OperatorToken o1 = (OperatorToken) t;
				while (theStack.peek() instanceof OperatorToken) {
					OperatorToken o2 = (OperatorToken) theStack.peek();
					if ((o1.getAssociativity() == OperatorToken.ASSOCIATIVITY_LEFT && o1.getPriority() <= o2.getPriority()) || (o1.getAssociativity() == OperatorToken.ASSOCIATIVITY_RIGHT && o1.getPriority() < o2.getPriority())) {
						returnQueue.add(theStack.pop());
					} else {
						break;
					}
				}
				theStack.push(o1);

			} else if (t instanceof LeftBracketToken) {
				theStack.push(t);
			} else if (t instanceof RightBracketToken) {

				while (!(theStack.peek() instanceof LeftBracketToken)) {
					if (theStack.peek() != null) {
						returnQueue.add(theStack.pop());
					} else {
						System.err.println("Missing right parenthesis ')' in input");
					}
				}
				theStack.pop();//discard the LEFTBRACKET
				if (theStack.peek() instanceof FunctionToken) {

					returnQueue.add(theStack.pop());
				}
			}
		}
		while (theStack.peek() != null) {
			Token t2 = theStack.pop();
			if (t2 instanceof LeftBracketToken || t2 instanceof RightBracketToken) {
				System.err.println("Unexpected token! Maybe unclosed parenthesis?");
			} else {
				returnQueue.add(t2);
			}
		}
		return returnQueue;
	}

	/**
	 * parses an expression, given as an ArrayList of tokens, ordered in
	 * reverse polish notation
	 *
	 * @param tokenList
	 * @return
	 */
	public NumberToken parse(ArrayList<Token> tokenList) {
		LinkedList<Token> theStack = new LinkedList<>();
		for (Token t : tokenList) {
			if (t instanceof NumberToken || t instanceof VariableToken) {
				theStack.push(t);
			} else if (t instanceof ComputableToken) {
				((ComputableToken) t).compute(theStack);
			}
		}
		return (NumberToken) theStack.pop();
	}

	/**
	 * Creates an Expression object from a String, representing this
	 * expression; the string can contain function calls, variables and all
	 * other tokens, added to this parser, described by their respective
	 * regular expressions
	 *
	 * @param s the String to construct the Expression from
	 * @return an initialized Expression object
	 */
	public Expression createExpression(String s) {
		ArrayList<Token> theList = this.Tokenize(s);
		theList = this.getRPN(theList);
		return new Expression(theList);
	}

	/**
	 * This method will see if the String s has a regex at a given index, if
	 * so, returns the index of the end of the expression, otherwise returns
	 * -1
	 *
	 * @param s the String to search for the regex in
	 * @param regex the regex to search for in the string
	 * @param index where in the String should the search begin.
	 * @return end index of the token, -1 if token not present at the
	 * beginning of the string
	 */
	@Deprecated
	private int getTokenEnd(String s, String regex, int index) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		if (m.find(index)) {
			if (m.start() == index) {
				if (m.groupCount() > 0) {
					return m.end(1);
				}
				return m.end();
			}
		}
		return -1;
	}

	@Deprecated
	public boolean startsWithRegex(String s, String regex) {
		return s.matches(regex + ".*");
	}

	public String stripSpaces(String s) {
		return s.replaceAll("\\s+", "");
	}

}
//Number:
//([0-9]*).*
//Variable:
//([A-Za-z][A-Za-z0-9]*).*
