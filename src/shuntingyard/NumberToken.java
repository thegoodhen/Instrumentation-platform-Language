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
public abstract class NumberToken extends CompilableToken{//TODO: make abstract? Maybe not... Unsure here

	float value=0;


	public NumberToken(String s)
	{
		super(s);
		value=Float.parseFloat(s);
	}
	
	public float getValue()
	{
		return this.value;
	}

	@Override
	public String getRegex()
	{
		return  "[0-9][0-9]*";
	}
	@Override
	public int getID() {
		return Token.NUMBER;
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		theStack.push(this);

		c.getByteCode();
	}
	
	public abstract String getShortName();

	public abstract int getNumberOfBytes();
	public abstract boolean isImplicitlyCastableTo(NumberToken nt);
	public abstract int compileCastTo(NumberToken nt, int stackPos, Compiler c) throws RuntimeException;
	
}
