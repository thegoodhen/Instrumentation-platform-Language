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
public class EndIfLineToken extends LineToken{
	IfLineToken ilt;
	private JumpTargetByteCodeToken jtbct;

	public EndIfLineToken(String s) {
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
		return "^ENDIF$";
	}


	public void prepare(Compiler c) throws CompilerException {
		this.jtbct=new JumpTargetByteCodeToken();
		if(this.ilt==null)
		{
			throw new CompilerException("Found ENDIF with no preceding IF!");
		}
	}

	public void compile(Compiler c)
	{
		c.getByteCode().push(this.getJumpTarget());
	}

	public void setIf(IfLineToken ilt)
	{
		this.ilt=ilt;
	}
	
}
