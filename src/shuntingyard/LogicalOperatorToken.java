/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public abstract class LogicalOperatorToken extends BooleanReturningOperatorToken{

	public LogicalOperatorToken(String s) {
		super(s);
	}


	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		//first we get the 2 operands off our working stack
		Token o1=null;
		Token o2=null;
		
		if (theStack.size() >= 2) {
			o1 = theStack.pop();
			o2 = theStack.pop();
		}
		else
		{
			System.err.println("Not enough arguments to an operator: \""+this.getTokenString()+"\"");
		}
		if (!(o1 instanceof ByteNumberToken && o2 instanceof ByteNumberToken)) {
			System.err.println("Expected 2 booleans (bytes) for a logical operator!; Operator" + this.getTokenString() + " cannot be applied to arguments of type" + o1.getClass().getSimpleName() + " and " + o2.getClass().getSimpleName() + "!");
		}
		
		pushSelf(theStack,c);
	}
	
}
