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
public class StopByteCodeToken extends ByteCodeToken implements IRunnableToken{

	@Override
	public void run(VirtualMachine vm) {
		vm.stop();
	}

	@Override
	public byte getBaseCode() {
		return 122;
	}

	@Override
	public byte getCode()
	{
		return this.getBaseCode();
	}
	
}
