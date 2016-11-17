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
public class IfLineToken extends LineToken {

	private String expressionString;
	private ElseLineToken elseToken = null;
	private EndIfLineToken endToken = null;
	private Expression ex;

	public IfLineToken(String s) {
		super(s);
	}

	@Override
	public int getID() {
		return LineToken.IF;
	}

	public EndIfLineToken getEndIf() {
		return this.endToken;
	}

	public void prepare(Compiler c) {

		Pattern pattern = Pattern.compile(this.getRegex());
		Matcher matcher = pattern.matcher(this.getTokenString());
		if (matcher.matches()) {
			expressionString = matcher.group(1);
		}

		int lineNumber = c.getLines().indexOf(this);

		int depth = 0; //if we encounter another "if" beginning, we add 1. For each "end if" we subtract 1. This will let us know, which "else" and "ENDIF" keyword belongs to this IF.
		for (int i = lineNumber; i < c.getLines().size(); i++) {
			Token t = c.getLines().get(i);//TODO: this might be inefficient!
			if (t instanceof IfLineToken) {
				depth++;
			} else if (t instanceof ElseLineToken) {
				if (depth == 1) {
					((ElseLineToken) t).setIf(this);
					this.elseToken = (ElseLineToken) t;
				}
			} else if (t instanceof EndIfLineToken) {
				depth--;
				if (depth == 0) {
					((EndIfLineToken) t).setIf(this);
					this.endToken = (EndIfLineToken) t;
					break;
				}
			}

		}
		if (endToken == null) {
			System.err.println("Attempt to find a matching ENDIF failed; Reached end of code while parsing.");
		}
	}

	public void precompile(Compiler c) {
		ex = c.getExpressionParser().createExpression(expressionString);
	}

	public void compile(Compiler c) {
		Token t = ex.compile(c);



		//TODO: Slight refactor
		if (elseToken != null) {
			JumpTargetByteCodeToken elseJumpTarget = this.elseToken.getJumpTarget();
			JumpIfZeroOnStackByteCodeToken elseJump = new JumpIfZeroOnStackByteCodeToken(elseJumpTarget);
			//elseJump.setJumpTarget(elseJumpTarget);
			c.getByteCode().push(elseJump);
			if (t instanceof IntegerNumberToken) {
				elseJump.setType(VariableToken.INT);//TODO: handle float
			}
		}
		else
		{
			JumpTargetByteCodeToken endIfJumpTarget = this.endToken.getJumpTarget();
			JumpIfZeroOnStackByteCodeToken endJump = new JumpIfZeroOnStackByteCodeToken(endIfJumpTarget);
			//endJump.setJumpTarget(endIfJumpTarget);
			c.getByteCode().push(endJump);
		}

	}

	@Override
	public String getRegex() {//TODO: I have to do something about this; this object doesn't have a 
		//reference to the Factory creating it; the Factory needs to know the Regex. This leads to 
		//code duplication, since sometimes (like in this case) we have to know the Regex 
		//both in the Factory AND in the Token. The getRegex() method isn't static, so if we wanted to remotely call it from the Factory, we'd need to instantiate the Token in the factory; this could be done in the constructor of the said Factory.

		return "^IF\\s*(.*)$";
		//return "^(.*?=.*?);";
	}

}
