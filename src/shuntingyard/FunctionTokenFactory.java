/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class FunctionTokenFactory extends DelegatingFactory{
	public FunctionTokenFactory()
	{
		this.addSubFactory(new PrintNumberUserFunctionTokenFactory());
		this.addSubFactory(new PrintTextUserFunctionTokenFactory());
		this.addSubFactory(new AllocBytesOnStackUserFunctionTokenFactory());
		this.addSubFactory(new StackByteUserFunctionTokenFactory());
		this.addSubFactory(new SetStackByteUserFunctionTokenFactory());
		this.addSubFactory(new AllocIntsOnStackUserFunctionTokenFactory());
		this.addSubFactory(new StackIntUserFunctionTokenFactory());
		this.addSubFactory(new SetStackIntUserFunctionTokenFactory());
	}

	@Override
	public String getRegex() {
		return "([A-Za-z][A-Za-z0-9]*)\\(";
	}

	@Override
	public Token generateInstance(String tokenString) {
		return new FunctionToken(tokenString);
	}

	/**
	 * Override to make sure the parenthesis, needed to correctly identify the Token,
	 * doesn't become part of the token string itself.
	 * @param m
	 * @return 
	 */
	@Override
	public int calculateRegexMatchEnd(Matcher m) {
		if (m.groupCount() > 0) {
			this.setRegexEnd(m.end(1));
		}
		return this.getRegexEnd();
	}

	@Override
		public int getNiceness()
		{
			return -1;
		}

		

	@Override
	public Token create(String tokenString, int position) throws CompilerException
	{
		Pattern p=Pattern.compile("([A-Za-z][A-Za-z0-9]*)\\(",0);
		//Pattern p=Pattern.compile("(.*?)\\s*\\r?\\n");
		Matcher m=p.matcher(tokenString);
		
		if(m.find(position) && m.start()==position)
		{
			//String slepice=m.group(1).trim();
			Token t=super.create(m.group(1).trim(), 0);
			this.setRegexEnd(m.end(1));
			return t;
		}
		else
		{
			//System.err.println("Did not even find a generic function token!");
		}
		return null;
	}
}
