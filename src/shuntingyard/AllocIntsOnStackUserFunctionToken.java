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
public class AllocIntsOnStackUserFunctionToken extends AbstractBuiltInFunctionToken{


	/**
	 * Pop int (2 bytes) from vm stack, then allocate number of bytes, times 2, corresponding to the int on the virtual machines stack.
	 * @param vm 
	 */
	public AllocIntsOnStackUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new IntegerNumberToken("0"));//TODO: introduce void
		VariableToken param1=new VariableToken("intsToAlloc");
		param1.setType(new IntegerNumberToken());
		this.addArgument(param1);
	}

	@Override
	public void run(VirtualMachine vm) {
		int intsToAlloc=vm.popIntFromStack()*2;
		int pos=vm.getStackPointer();
		vm.setStackPointer(pos+intsToAlloc);
		vm.pushIntOnStack(pos);
	}

	@Override
	public byte getBaseCode() {
		return (byte)131;
	}
	
}
