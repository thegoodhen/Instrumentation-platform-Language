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
public class CastIntegerToFloatByteCodeToken extends ByteCodeToken implements IRunnableToken{

	/**
	 * Pop 1 or 2 bytes from stack, then use this number to determine which integer on stack
	 * to change to float.
	 * 
	 * Whether 1 or 2 bytes will be popped from the stack depends on the "extended" property of this
	 * token. This byte or these bytes signify the position of the int on stack to cast to float.
	 * 
	 * The position is relative to the top of the stack (top being the side "pop" would take from)
	 * and is zero-based. it is expressed in the number of bytes. That is, if we want to cast the
	 * topmost int to float (e.g. the int that has just been pushed to the top of the stack), the
	 * position of the said int is 1. If we wanted to do this to the second last int 
	 * pushed to the stack, the number would be 3:
	 * 
	 * MSB2 LSB2 MSB1 LSB1
	 * 3	2    1    0
	 * 
	 * As suggested, this method makes calls to the supplied VirtualMachine object
	 * and modifies it's stack.
	 * 
	 * @param vm 
	 */
	@Override
	public void run(VirtualMachine vm) {
		int stackPos=vm.popNumberFromStack(this.isExtended());
		byte[] intBytes=new byte[2];
		int currentSp=vm.getStackPointer();
		for(int i=0;i<2;i++)//2=number of bytes in INT
		{
			intBytes[i]=((NumericByteCodeToken)(vm.getStack().remove(currentSp-stackPos-1))).getValue();
		}
		int theInt=HelpByteMethods.constructInt(intBytes[0],intBytes[1]);
		float theFloat=theInt;
		byte [] floatBytes=new byte[4];
		floatBytes=HelpByteMethods.getFloatBytes(theFloat);
		for(int i=floatBytes.length-1;i>=0;i--)
		{
			vm.getStack().add(currentSp-stackPos-1, new NumericByteCodeToken(floatBytes[i]));
		}
		

		//vm.getStack().add(vm.getStackPointer()-stackPos,new NumericByteCodeToken((byte)0));
		vm.setStackPointer(vm.getStackPointer()+2);
	}

	@Override
	public byte getBaseCode() {
		return 2;
	}
	
}
