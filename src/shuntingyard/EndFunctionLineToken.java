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
public class EndFunctionLineToken extends LineToken{
	private FunctionDeclarationLineToken fdlt=null;
	private JumpTargetByteCodeToken jtbct;

	public EndFunctionLineToken(String s) {
		super(s);
	}
	
	public JumpTargetByteCodeToken getJumpTarget()
	{
		return this.jtbct;
	}

	@Override
	public int getID() {
		return 124;
	}

	@Override
	public String getRegex() {
		return "^ENDFUNCTION$";
	}


	public void prepare(Compiler c) {
		//this.jtbct=new JumpTargetByteCodeToken();
		if(this.fdlt==null)
		{
			System.err.println("Found ENDFUNCTION with no preceding function declaration!");
		}
		 c.setCurrentFunction(null);
	}


	public void precompile(Compiler c)
	{
		c.setCurrentFunction(null);
	}

	public void compile(Compiler c)//TODO: stub
	{
		c.setCurrentFunction(null);
		//TODO: maybe push RETURN here? 
		//c.getByteCode().push(this.getJumpTarget());
	}

	public void setFunctionDeclaration(FunctionDeclarationLineToken fdlt)
	{
		this.fdlt=fdlt;
	}
	
}
