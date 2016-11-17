/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A common abstraction for jump Tokens, such as unconditional jump, various conditional jumps and other
 * bytecode commands, execution of which results in changing the program counter of the virual machine.
 * The user program is compiled in multiple stages. One of the compilation stages results in JumpByteCodeTokens being assigned a Jump target object. This jump target is of type JumpTargetByteToken, which is a special placeholder type. The reason behind this is, that other ByteCode tokens are subject to change
 * due to compiler optimizations and other reasons. Once the exact compiled code form is estabilished and it is decided that no further alterations will be undergone, all temporary JumpTargetByteCodeTokens are removed and the "realJumpTarget" variable is set to the first Token after the original position of the respective JumpTargetByteCodeToken, which is not of type JumpTargetByteCodeToken (That is, if the JumpTargetByteCodeToken was followed by another JumpTargetByteCodeToken in the original code, the target of the
 * JumpByteCodeToken will be set to the Token right after the second JumpTargetByteCodeToken. The described behavior is
 * handled by the Compiler class and happens at compile time.
 * @author thegoodhen
 */
public abstract class AbstractJumpByteCodeToken extends ByteCodeToken {

	private JumpTargetByteCodeToken jtbct;
	private int jumpTargetByteCount = 0;
	private int jumpNumber = 0;//the number that tells us how far we're gonna jump.
	private Token realJumpTarget;//the final code will not have Jump targets, so we will be deleting them; this variable holds the Token placed right after the jump target
	private int prevPos;
	//we used the intermiddiate jump target, since everything else is a possible subject to change
	//(due to compiler optimizations and more). If we linked to the exact token beforehand, it might
	//get deleted and the link would become invalid.
	private int prevJumpNumber;
	private boolean extended;

	public AbstractJumpByteCodeToken(JumpTargetByteCodeToken jtbct) {
		this.jtbct = jtbct;
	}

	/**
	 * Checks where the jump target (of type JumpTargetByteCodeToken) is in the code relatively to this jump
	 * and updates this info in the bytes stored right before the jump
	 * token, creating it if it doesn't exist yet.
	 * The info has form of one or two bytes (NumericByteCodeTokens) stored in the List passed to 
	 * the method. The list passed into the method is subject to modification by the method.
	 *
	 * @param program
	 * @return
	 */
	public boolean updateTargetInfo(List<Token> program) {
		int thisPos = program.indexOf(this);//find itself in the given program

		if (jumpTargetByteCount == 0)//never updated before
		{
			int jumpTargetPos = program.indexOf(this.jtbct);// find the jump target in the given program
			this.jumpNumber = jumpTargetPos - thisPos;//calculate the length of the relative jump
			int i=1;

			//skip every JumpTargetByteCodeToken that might follow (as we don't want the target to be a JumpTargetByteCodeToken - those will be removed by Compiler in the next step!
			while(program.get(jumpTargetPos+i)instanceof JumpTargetByteCodeToken)
			{
				i++;
			}
			this.realJumpTarget = program.get(jumpTargetPos+i);//set the actual jump target to the token following the JumpTarget token
			//program.remove(jumpTargetPos);//remove the dedicated jump target token
			/*if (jumpTargetPos < thisPos)//if we removed something before this token, it shifted around by the one removed element! We need to compensate for that! 
			{
				thisPos -= 1;
			}
			*/
			jumpTargetByteCount = compileJumpNumber(jumpNumber, thisPos, program);
			prevJumpNumber = jumpNumber;
			return true;
		} else {

			int jumpTargetPos = program.indexOf(this.realJumpTarget);// find the jump target in the given program
			this.jumpNumber = jumpTargetPos - thisPos;//calculate the length of the relative jump
			if (prevJumpNumber != jumpNumber) {
				for (int i = 0; i < jumpTargetByteCount; i++) {
					program.remove(thisPos - 1);
					thisPos -= 1;
				}
				jumpTargetByteCount = compileJumpNumber(jumpNumber, thisPos, program);
				prevJumpNumber = jumpNumber;
				return true;
			}
		}
		if (jumpTargetByteCount == 2) {
			extended = true;
		} else {
			extended = false;
		}

		return false;
	}


	/*
	private byte getUpperByte(int value) {
		return (byte) (value >> 8);
	}
	*/

	/**
	 * Places one or two bytes, containing the number given, in the
	 * ArrayList as NumericByteCodeTokens, right before the index given.
	 *
	 * @param jumpNumber the number, defining how far the jump target is
	 * @param index the position, before which the compiled bytes should go
	 * in the program (should be passed the position of this
	 * JumpByteCodeToken in the program!)
	 * @param program
	 * @return whether 1 or 2 bytes were placed (returns int, equal to 1 or
	 * 2).
	 */
	public int compileJumpNumber(int jumpNumber, int index, List<Token> program) {

		jumpNumber--;
		int returnNumber = 0;
		if (jumpNumber > 255) {
			program.add(index, new NumericByteCodeToken(HelpByteMethods.getUpperByte(jumpNumber)));
			returnNumber++;
		}
		program.add(index, new NumericByteCodeToken(HelpByteMethods.getLowerByte(jumpNumber)));
		returnNumber++;
		return returnNumber;
	}

	public void setJumpTarget(JumpTargetByteCodeToken jtbct) {
		this.jtbct = jtbct;
	}
}
