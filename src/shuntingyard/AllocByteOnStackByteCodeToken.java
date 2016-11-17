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
public class AllocByteOnStackByteCodeToken extends ByteCodeToken implements IRunnableToken{

	/**
	 * Pop 1 or 2 bytes from vm stack, based on the "extended" property of this Token.
	 * Then allocate number of bytes, corresponding to the byte/bytes on the virtual machines Stack.
	 * @param vm 
	 */
	@Override
	public void run(VirtualMachine vm) {
		int allocBytesCount=vm.popNumberFromStack(this.isExtended());
		for(int i=0;i<allocBytesCount;i++)
		{
			vm.pushByteOnStack((byte)-3);
		}
	}

	@Override
	public byte getBaseCode() {
		return 4;
	}

	
}
