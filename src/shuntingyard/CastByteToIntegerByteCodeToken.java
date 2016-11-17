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
public class CastByteToIntegerByteCodeToken extends ByteCodeToken implements IRunnableToken{

	/**
	 * Pop 1 or 2 bytes from stack, then use this number to determine which byte on stack
	 * to change to int.
	 * 
	 * Whether 1 or 2 bytes will be popped from the stack depends on the "extended" property of this
	 * token. This byte or these bytes signify the position of the int on stack to cast to float.
	 * 
	 * The position is relative to the top of the stack (top being the side "pop" would take from)
	 * and is zero-based. it is expressed in the number of bytes. That is, if we want to cast the
	 * topmost byte to int (e.g. the byte that has just been pushed to the top of the stack), the
	 * position of the said byte is 0. If we wanted to do this to the second last byte
	 * pushed to the stack, the number would be 1:
	 * 
	 * byte1 byte0
	 * 1	 0
	 * 
	 * As suggested, this method makes calls to the supplied VirtualMachine object
	 * and modifies it's stack.
	 * 
	 * @param vm 
	 */
	@Override
	public void run(VirtualMachine vm) {
		int stackPos=vm.popNumberFromStack(this.isExtended());
		vm.getStack().add(vm.getStackPointer()-stackPos-1,new NumericByteCodeToken((byte)0));
		vm.setStackPointer(vm.getStackPointer()+1);
	}

	@Override
	public byte getBaseCode() {
		return 0;
	}
	
}
