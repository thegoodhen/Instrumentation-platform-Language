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
public class PopByteFromStackByteCodeToken extends ByteCodeToken{

	@Override
	public void run(VirtualMachine vm) {
		vm.popByteFromStack();
	}
	
	public byte getBaseCode()
	{
		return 121;
	}

	public byte getCode()
	{
		return this.getBaseCode();
	}
}
