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
public class EndFunctionLineToken extends LineToken implements IRunnableToken{
	private FunctionDeclarationLineToken fdlt=null;
	private JumpTargetByteCodeToken jtbct;
	private boolean extendedArgumentCount = false;//whether the number of arguments of the function this endfunction statement belong to is over 255 (more than 1 byte)

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
	    c.getByteCode().push(this);
		c.setCurrentFunction(null);
		//TODO: maybe push RETURN here? 
		//c.getByteCode().push(this.getJumpTarget());
	}

	public void setFunctionDeclaration(FunctionDeclarationLineToken fdlt)
	{
		this.fdlt=fdlt;
	}

    @Override
    public void run(VirtualMachine vm) {
	//do nothing
    }

    @Override
    public boolean isExtended() {
	return this.extendedArgumentCount;
    }

    @Override
    public void setExtended(boolean ex) {
	this.extendedArgumentCount=ex;
    }

    @Override
    public byte getBaseCode() {
	return 120;//the baseCode of NoOperationByteCodeToken
    }

    @Override
    public byte getCode() {
	return this.getBaseCode();
    }
	
}
