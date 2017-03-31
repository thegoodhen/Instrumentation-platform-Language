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
public class ElseLineToken extends LineToken{

	private IfLineToken ilt;
	private JumpTargetByteCodeToken jtbct;


	public ElseLineToken(String s) {
		super(s);
	}

	public JumpTargetByteCodeToken getJumpTarget()
	{
		return this.jtbct;
	}

	@Override
	public int getID() {
		return LineToken.ELSE;
	}

	@Override
	public String getRegex() {
		return "^ELSE$";
	}
	
	public void setIf(IfLineToken ilt)
	{
		this.ilt=ilt;
	}

	@Override
	public void prepare(Compiler c)throws CompilerException {
		this.jtbct=new JumpTargetByteCodeToken();
		if(this.ilt==null)
		{
			throw new CompilerException("Found ELSE with no preceding IF!");
		}
	}


	public void compile(Compiler c)
	{
		JumpTargetByteCodeToken endIfJumpTarget=this.ilt.getEndIf().getJumpTarget();
		UnconditionalJumpByteCodeToken jumpToEndIf=new UnconditionalJumpByteCodeToken(endIfJumpTarget);
		//jumpToEndIf.setJumpTarget(endIfJumpTarget);
		c.getByteCode().push(jumpToEndIf);
		c.getByteCode().push(this.getJumpTarget());
	}
}
