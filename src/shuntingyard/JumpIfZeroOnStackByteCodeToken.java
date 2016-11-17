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
public class JumpIfZeroOnStackByteCodeToken extends AbstractJumpByteCodeToken implements IRunnableToken {

	private int type = VariableToken.BYTE;

	public JumpIfZeroOnStackByteCodeToken(JumpTargetByteCodeToken jtbct) {
		super(jtbct);
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void run(VirtualMachine vm) {
		int jumpTarget = vm.popNumberFromStack(this.isExtended());
		int comparisonVal = 0;
		if (type == VariableToken.BYTE) {
			comparisonVal = vm.popByteFromStack();
		} else if (type == VariableToken.INT) {
			comparisonVal = vm.popIntFromStack();
		}
		if (comparisonVal == 0) {
			vm.relativeJump(jumpTarget);
		}
	}

	@Override
	public byte getBaseCode() {
		return 102;
	}

}
