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
public class JumpTargetByteCodeToken extends ByteCodeToken{

	@Override
	public void run(VirtualMachine vm) {
		//do nothing, this will not be in the final code!

	}

	@Override
	public byte getBaseCode() {
		return -1; //not a valid code
	}
	
}
