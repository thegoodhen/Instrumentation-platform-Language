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
public class NumericByteCodeToken extends Token implements IRunnableToken{

	private byte num;

	public NumericByteCodeToken(byte b) {
		this.num = b;
	}

	@Override
	public int getID() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getRegex() {
		return "";
	}

	@Override
	public String toString()
	{
		return Integer.toString(num);
	}

	public byte getValue()
	{
		return num;
	}

	@Override
	public void setExtended(boolean ex)
	{
		//do nothing
	}

	
	@Override
	public boolean isExtended()
	{
		return false;
	}




	@Override
	public void run(VirtualMachine vm) {
		vm.pushByteOnStack(num);
		//vm.getStack().push(this);
	}

	public void setValue(byte val) {
		this.num=val;
	}

	@Override
	public byte getBaseCode() {
		return this.num;
	}

	@Override
	public byte getCode() {
		return this.num;
	}

}
