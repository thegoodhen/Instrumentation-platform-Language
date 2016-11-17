/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

/**
 * Allocate and set String stored on top of the stack on a defined location on
 * the Heap. Expected state: The very top of the stack (2 bytes or 1 byte,
 * depending on the extended property) is the address on heap to stored the
 * string to. Once these bytes are popped, what follows is the string to set.
 * Now the top of the stack (result of the peek) is the first character of the
 * String. Last character popped from the stack will be \0, which signifies the
 * end of the String. Once this last character is popped, no other modifications
 * to the VM stack are made.
 *
 * @author thegoodhen
 */
public class AllocStringOnHeapByteCodeToken extends ByteCodeToken implements IRunnableToken {

	@Override
	public void run(VirtualMachine vm) {
		int allocAddr = vm.popNumberFromStack(this.isExtended());

		byte currentByte = -1;
		int index = 0;
		while (currentByte != 0) {
			currentByte = vm.popByteFromStack();
			vm.getHeap().set(allocAddr + index, new NumericByteCodeToken(currentByte));
			index++;
		}
	}

	@Override
	public byte getBaseCode() {
		return 8;
	}

}
