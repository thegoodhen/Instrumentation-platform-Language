/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 * This was a utility class in the early stages of developement. It is now deprecated.
 * @author thegoodhen
 */
@Deprecated
public abstract class ComputableToken extends Token{

int argumentCount=0;

public ComputableToken(String s)
{
	super(s);
}
	public int getArgumentCount()
	{
		return this.argumentCount;
	}

	/**
	 * Compute the value of this token, popping the required arguments from stack, returning
	 * the result on stack. Return this result as a side effect.
	 * Please note that this is a class designed to be extended by operators/functions or
	 * other tokens that perform calculations. It's not to be used for variables or literals.
	 * The generic contract for this function is that it MUST ensure that 
	 * the amount of elements popped from the stack does not
	 * exceed the number indicated by 
	 * @see getArgumentCount()
	 * @param theStack
	 * @return the Token returned to the Stack
	 */
	public abstract NumberToken compute(LinkedList<Token> theStack);
	
}
