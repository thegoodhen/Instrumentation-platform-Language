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
public interface IRunnableToken {
	public void run(VirtualMachine vm);
	public boolean isExtended();
	public void setExtended(boolean ex);
	public byte getBaseCode();
	public byte getCode();
}
