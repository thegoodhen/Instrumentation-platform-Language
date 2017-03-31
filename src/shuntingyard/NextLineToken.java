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
public class NextLineToken extends LineToken{
	ForLineToken flt;
	private JumpTargetByteCodeToken jtbct;

	public NextLineToken(String s) {
		super(s);
	}
	
	public JumpTargetByteCodeToken getJumpTarget()
	{
		return this.jtbct;
	}

	@Override
	public int getID() {
		return LineToken.ENDIF;
	}

	@Override
	public String getRegex() {
		return "^NEXT$";
	}


	public void prepare(Compiler c) throws CompilerException {
		this.jtbct=new JumpTargetByteCodeToken();
		if(this.flt==null)
		{
			throw new CompilerException("Found NEXT with no preceding FOR!");
		}
	}

	public void compile(Compiler c) throws CompilerException
	{
		this.flt.getExpression3().prepare(c);
		this.flt.getExpression3().precompile(c);
		this.flt.getExpression3().compile(c);
		UnconditionalJumpByteCodeToken jumpToFor=new UnconditionalJumpByteCodeToken(flt.getJumpTarget());
		c.getByteCode().push(jumpToFor);
		c.getByteCode().push(this.getJumpTarget());
	}

	public void setFor(ForLineToken flt)
	{
		this.flt=flt;
	}
	
}
