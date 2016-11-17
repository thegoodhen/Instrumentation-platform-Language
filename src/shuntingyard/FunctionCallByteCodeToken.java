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
public class FunctionCallByteCodeToken extends AbstractJumpByteCodeToken implements IRunnableToken{

	public FunctionCallByteCodeToken(JumpTargetByteCodeToken jtbct) {
		super(jtbct);
	}

	@Override
	public void run(VirtualMachine vm) {
		int jumpTarget = vm.popNumberFromStack(this.isExtended());
		vm.pushIntOnStack(vm.getProgramCounter());
		vm.pushIntOnStack(vm.getBasePointer());
		vm.relativeJump(jumpTarget);
		vm.setBasePointer(vm.getStackPointer());
	}

	@Override
	public byte getBaseCode() {
		return 104;
	}
	
}
