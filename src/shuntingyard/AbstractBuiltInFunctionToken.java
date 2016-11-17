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
public abstract class AbstractBuiltInFunctionToken extends FunctionToken implements IRunnableToken{

	/**
	 * Abstraction class for builtin functions. That is, for functions that do not have their
	 * definition written in the user program, but instead are built in into the language
	 * specification  itself. Those functions have their special code in the resulting compiled call,
	 * different from user function calls.
	 * @param tokenString 
	 */
	public AbstractBuiltInFunctionToken(String tokenString) {
		super(tokenString);
	}

	@Override
	public boolean isExtended()
		{
			return false;
		}

	@Override
	public void setExtended(boolean ex)
	{
		//do nothing
	}

	@Override 
	public byte getCode()
	{
		return this.getBaseCode();
	}


	protected void doLookup(Compiler c)
	{
		//do nothing?
	}

	@Override
	protected void compileCall(Compiler c)
	{
		c.getByteCode().push(this);
	}


	
}
