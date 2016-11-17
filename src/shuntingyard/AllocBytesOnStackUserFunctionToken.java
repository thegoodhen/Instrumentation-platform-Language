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
public class AllocBytesOnStackUserFunctionToken extends AbstractBuiltInFunctionToken{

	public AllocBytesOnStackUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new IntegerNumberToken("0"));//TODO: introduce void
		VariableToken param1=new VariableToken("bytesToAlloc");
		param1.setType(new IntegerNumberToken());
		this.addArgument(param1);
	}

	/**
	 * Pop int (2 bytes) from vm stack, then allocate number of bytes, corresponding to the int on the virtual machines stack.
	 * @param vm 
	 */
	@Override
	public void run(VirtualMachine vm) {
		int bytesToAlloc=vm.popIntFromStack();
		int pos=vm.getStackPointer();
		vm.setStackPointer(pos+bytesToAlloc);
		vm.pushIntOnStack(pos);
	}

	@Override
	public byte getBaseCode() {
		return (byte)130;
	}
	
}
