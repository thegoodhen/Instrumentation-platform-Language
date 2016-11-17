/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

/**
 * Abstraction for Tokens that are present in the compiled code, but not in the original code.
 *TODO: This should probably be changed.
 * @author thegoodhen
 */
public abstract class ByteCodeToken extends Token implements IRunnableToken{

	private boolean extended;
	@Override
	public int getID() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getRegex() {
		return "";
	}

	public void setExtended(boolean extended)
	{
		this.extended=extended;
	}

	public boolean isExtended()//TODO: rename to isExtended?
	{
		return this.extended;
	}

	@Override
	public byte getCode()
	{
		if(isExtended())
		{
			return (byte) (this.getBaseCode()+1);
		}
		return this.getBaseCode();
	}

	
}
