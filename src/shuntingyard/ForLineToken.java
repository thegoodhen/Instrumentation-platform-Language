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
public class ForLineToken extends LineToken {

	private String expression1String;
	private String expression2String;
	private String expression3String;
	private NextLineToken nextToken = null;
	private VariableAssignmentLineToken ex1;
	private Expression ex2;
	private VariableAssignmentLineToken ex3;
	private JumpTargetByteCodeToken jtbct;

	public ForLineToken(String s) {
		super(s);
	}

	@Override
	public int getID() {
		return LineToken.IF;
	}

	public NextLineToken getNext() {
		return this.nextToken;
	}

	public JumpTargetByteCodeToken getJumpTarget() {
		return this.jtbct;
	}

	public void prepare(Compiler c) {

		Pattern pattern = Pattern.compile(this.getRegex());
		Matcher matcher = pattern.matcher(this.getTokenString());
		this.jtbct = new JumpTargetByteCodeToken();
		if (matcher.matches()) {
			expression1String = matcher.group(1);
			expression2String = matcher.group(2);
			expression3String = matcher.group(3);
		}

		int lineNumber = c.getLines().indexOf(this);

		int depth = 0; //if we encounter another "for" beginning, we add 1. For each "next" we subtract 1. This will let us know, which "NEXT" keyword belongs to this FOR.
		for (int i = lineNumber; i < c.getLines().size(); i++) {
			Token t = c.getLines().get(i);//TODO: this might be inefficient!
			if (t instanceof ForLineToken) {
				depth++;
			} else if (t instanceof NextLineToken) {
				depth--;
				if (depth == 0) {
					((NextLineToken) t).setFor(this);
					this.nextToken = (NextLineToken) t;
					break;
				}
			}

		}
		if (nextToken == null) {
			System.err.println("Attempt to find a matching NEXT failed; Reached end of code while parsing.");
		}
	}

	public void precompile(Compiler c) {
		//ex1 = c.getExpressionParser().createExpression(expression1String);
		VariableAssignmentLineTokenFactory valtf=new VariableAssignmentLineTokenFactory();
		ex1=(VariableAssignmentLineToken)valtf.create(expression1String+";", 0);
		if(ex1==null)
		{
			System.err.println("Error! First expression in FOR statement is supposed to be a variable assignment!");
		}

		ex2 = c.getExpressionParser().createExpression(expression2String);
		//ex3 = c.getExpressionParser().createExpression(expression3String);
		ex3=(VariableAssignmentLineToken)valtf.create(expression3String+";", 0);
		if(ex3==null)
		{
			System.err.println("Error! Last expression in FOR statement is supposed to be a variable assignment!");
		}
	}

	public void compile(Compiler c) {
		ex1.prepare(c);
		ex1.precompile(c);
		ex1.compile(c);
		c.getByteCode().push(this.getJumpTarget());
		Token t2 = ex2.compile(c);
		JumpTargetByteCodeToken nextJumpTarget = this.nextToken.getJumpTarget();
		JumpIfZeroOnStackByteCodeToken nextJump = new JumpIfZeroOnStackByteCodeToken(nextJumpTarget);
		if(t2 instanceof IntegerNumberToken)
		{
			nextJump.setType(VariableToken.INT);
		}
		//nextJump.setJumpTarget(nextJumpTarget);
		c.getByteCode().push(nextJump);


	}

	protected VariableAssignmentLineToken getExpression3()
	{
		return this.ex3;
	}

	@Override
	public String getRegex() {//TODO: I have to do something about this; this object doesn't have a 
		//reference to the Factory creating it; the Factory needs to know the Regex. This leads to 
		//code duplication, since sometimes (like in this case) we have to know the Regex 
		//both in the Factory AND in the Token. The getRegex() method isn't static, so if we wanted to remotely call it from the Factory, we'd need to instantiate the Token in the factory; this could be done in the constructor of the said Factory.

		return "^FOR\\s+(.*)\\s*;\\s*(.*)\\s*;\\s*(.*)\\s*;$";
		//return "^(.*?=.*?);";
	}

}
