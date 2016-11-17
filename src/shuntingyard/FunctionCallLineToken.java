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
public class FunctionCallLineToken extends LineToken {

	private String expressionString;
	private Expression ex;

	public FunctionCallLineToken(String s) {
		super(s);
	}

	@Override
	public int getID() {
		return LineToken.IF;
	}

	public void prepare(Compiler c) {
	}

	public void precompile(Compiler c) {

		Pattern pattern = Pattern.compile(this.getRegex());
		Matcher matcher = pattern.matcher(this.getTokenString());
		if (matcher.matches()) {
			expressionString = matcher.group(1);
		}

		ex = c.getExpressionParser().createExpression(expressionString);
	}

	public void compile(Compiler c) {
		Token t = ex.compile(c);
		//c.getByteCode().push(this);

		//we have called a function on a line. The return value of the function is
		//therefor not used. Thus we must dispose of it properly. This is taken
		//care of in the following code:

		if(t instanceof NumberToken)
		{
			for(int i=0;i<((NumberToken)t).getNumberOfBytes();i++)
			{
				c.getByteCode().push(new PopByteFromStackByteCodeToken());
			}
		}
	}

	@Override
	public String getRegex() {//TODO: I have to do something about this; this object doesn't have a 
		//reference to the Factory creating it; the Factory needs to know the Regex. This leads to 
		//code duplication, since sometimes (like in this case) we have to know the Regex 
		//both in the Factory AND in the Token. The getRegex() method isn't static, so if we wanted to remotely call it from the Factory, we'd need to instantiate the Token in the factory; this could be done in the constructor of the said Factory.

		return "^([A-Za-z][A-Za-z0-9_.]*\\s*\\(.*\\));";
		//return "^([A-Za-z][A-Za-z0-9]*)\\s*\\(.*\\);";
		//return "^IF\\s+(.*)$";
		//return "^(.*?=.*?);";
	}

}
