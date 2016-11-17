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
public class AllocByteOnHeapByteCodeToken extends ByteCodeToken implements IRunnableToken{

	/**
	 * Pop 1 or 2 bytes from vm stack, based on the "extended" property of this Token.
	 * Then allocate number of bytes, corresponding to the byte/bytes on the virtual machines Heap.
	 * @param vm 
	 */
	@Override
	public void run(VirtualMachine vm) {
		int bytesToAllocCount=vm.popNumberFromStack(this.isExtended());



		for(int i=0;i<bytesToAllocCount;i++)
		{
			vm.getHeap().add(new NumericByteCodeToken((byte)0));
		}
	}

	@Override
	public byte getBaseCode() {
		return 6;
	}
	
}
