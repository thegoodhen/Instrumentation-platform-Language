/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 *TODO: change name to ExpressionToken?
 * @author thegoodhen
 */
public abstract class CompilableToken extends Token {

	int argumentCount = 0;

	public CompilableToken(String s) {
		super(s);
	}

	public int getArgumentCount() {
		return this.argumentCount;
	}


	/**
	 * Add 1 or two bytes representing the given number to the specified compilers ByteCode.
	 * If the number given is less than 128, greather than or equal to -128 (e.g. can be represented by a byte), only this single
	 * byte is placed on top of the ByteCode stack of the compiler c. In other cases, the integer
	 * is split into two bytes, and those are placed on the top of the ByteCode stack, MSB first.
	 * @param number the number to place on the Compiler stack
	 * @param c the compiler, stack of which should be used
	 * @return whether 2 bytes were placed on the stack (true) or just one (false);
	 */
	public static boolean compileNumber(int number, Compiler c) {

		boolean returnVal=false;
		byte lowerByte = (byte) (number & 0xFF);
		byte upperByte = (byte) ((number >> 8) & 0xFF);
		if (number > Byte.MAX_VALUE || number<Byte.MIN_VALUE) {
			c.getByteCode().push(new NumericByteCodeToken(upperByte));
			returnVal=true;
		}
		c.getByteCode().push(new NumericByteCodeToken(lowerByte));
		return returnVal;
	}



/**
 * Compile the command to cast nth byte on the stack to integer. The topmost byte is number 0.
 * @param index
 * @param c 
 */
	public static void compileByteToIntCast(int index, Compiler c)
	{
			compileNumber(index,c);
			c.getByteCode().push(new CastByteToIntegerByteCodeToken());
	}

	/**
	 * Compile this token into a corresponding sequence of final code
	 * Tokens, passing those to the given compiler,  popping the required arguments from stack,
	 * returning the symbolic result on stack. 
	 * 
	 * Simply put, the compilation to the final bytecode is handled, while
	 * the operation, which will be carried in runtime once the compiled bytecode sequence is 
	 * executed, is simulated on symbolic level.
	 * 
	 * 
	 * The result (Object pushed to the stack) is a Token,
	 * describing the symbolic return value of the token, corresponding to
	 * the actual return value if computed in runtime. For instance, if this
	 * class is extended by a built-in function, it should pop n arguments from the
	 * stack, where n is the number of arguments to the function, and should
	 * place such token on the stack, that it represents the return type of
	 * the function (such as byteNumberToken). 
	 * The generic contract for this function is that it MUST ensure that
	 * the amount of elements popped from the stack does not exceed the
	 * number indicated by getArgumentCount(), and it must not be lower either.
	 * 
	 * At the same time, the symbolic value pushed on the working stack
	 * must correspond to the actual value pushed on the virtual machine stack
	 * in runtime and same goes for agruments.
	 * 
	 * The stack is a sort of simulation of the runtime VM stack. It is
	 * intended to detect malformed commands (such as passing expression,
	 * result of which is int, into a function which expects byte) in compile time,
	 * rather than runtime.
	 *
	 * @see getArgumentCount()
	 * @param theStack
	 * @param c the compiler to give the resulting bytecode to
	 */
	public abstract void compile(LinkedList<Token> theStack, Compiler c) throws CompilerException;
}
