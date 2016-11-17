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
public class UnconditionalJumpByteCodeToken extends AbstractJumpByteCodeToken implements IRunnableToken {

	public UnconditionalJumpByteCodeToken(JumpTargetByteCodeToken jtbct) {
		super(jtbct);
	}

	@Override
	public void run(VirtualMachine vm) {
		int jumpTarget = vm.popNumberFromStack(this.isExtended());
		vm.relativeJump(jumpTarget);
	}

	@Override
	public byte getBaseCode() {
		return 100;
	}

}
