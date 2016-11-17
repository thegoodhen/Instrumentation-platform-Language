/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public class Expression {
	ArrayList<Token> tokenList;
	private Token returnValue;
	public Expression(ArrayList<Token> tokenList)
	{
		this.tokenList=tokenList;
	}

	public ArrayList<Token> getTokenList()
	{
		return this.tokenList;
	}

	/**
	 * Compile the expression. This will modify the data in the given Compiler. It will also determine
	 * the return value of this expression.
	 * @param c
	 * @return 
	 */
	public Token compile(Compiler c)
	{

		LinkedList<Token> theStack = new LinkedList<>();
		for (Token t : tokenList) {
			if (t instanceof CompilableToken) {
				((CompilableToken) t).compile(theStack,c);
			}
		}
		this.returnValue=theStack.pop();
		if(theStack.size()>0)
		{
			System.err.println("Error: Too many arguments supplied to one or more functions or unary operators in the expression!");
		}
		return  this.returnValue;
	}
}
