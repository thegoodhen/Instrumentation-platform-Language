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
public class NoOperationByteCodeToken extends ByteCodeToken implements IRunnableToken{

	@Override
	public void run(VirtualMachine vm) {
		//no op! :3
	}

	@Override
	public byte getBaseCode() {
		return 120;
	}
	public byte getCode()
	{
		return this.getBaseCode();
	}

	
}
