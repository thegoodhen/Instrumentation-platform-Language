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
@Deprecated
public class PushByteVariableOnStackByteCodeToken extends ByteCodeToken implements IRunnableToken{
	//boolean extended;
	public PushByteVariableOnStackByteCodeToken(boolean extended)
	{
		this.setExtended(extended);
		//this.extended=extended;
	}

	@Override
	public void run(VirtualMachine vm) {
		int variableID=vm.popNumberFromStack(this.isExtended());
		vm.pushByteOnStack(vm.getHeap().get(variableID).getValue());
	}

	@Override
	public byte getBaseCode() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
