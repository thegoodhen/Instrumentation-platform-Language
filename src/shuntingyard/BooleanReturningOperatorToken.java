/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public abstract class BooleanReturningOperatorToken extends OperatorToken{

	public BooleanReturningOperatorToken(String s) {
		super(s);
	}
	
	protected void pushSelf(LinkedList<Token> theStack, Compiler c)
	{
		
		theStack.push(new ByteNumberToken());
		c.getByteCode().push(this);
	}

	@Override

	public void run(VirtualMachine vm)//TODO: refactor to avoid code duplication
	{
		switch(this.getOperandType().getNumberOfBytes())
		{

			//unhandled exceptions below, but those couldn't be recovered from anyway and would be unexpected!
			//Todo: bit of a mess with the magic numbers, fix the switch case.
			case 1://byte
				//NumericByteCodeToken o2=(NumericByteCodeToken) vm.getStack().pop();
				byte o2=vm.popByteFromStack();
				//NumericByteCodeToken o1=(NumericByteCodeToken) vm.getStack().pop();
				byte o1=vm.popByteFromStack();
				byte ret=(byte) computeBinaryOperatorFromNumbers(o1,o2);
				vm.pushByteOnStack(ret);
				//vm.getStack().push(new NumericByteCodeToken(ret));
				break;
			case 2://int
				//NumericByteCodeToken o2lsb=(NumericByteCodeToken) vm.getStack().pop();
				byte o2lsb=vm.popByteFromStack();
				//NumericByteCodeToken o2msb=(NumericByteCodeToken) vm.getStack().pop();
				byte o2msb=vm.popByteFromStack();
				int o2int=HelpByteMethods.constructInt(o2msb, o2lsb);
				//NumericByteCodeToken o1lsb=(NumericByteCodeToken) vm.getStack().pop();
				byte o1lsb=vm.popByteFromStack();
				//NumericByteCodeToken o1msb=(NumericByteCodeToken) vm.getStack().pop();
				byte o1msb=vm.popByteFromStack();
				int o1int=HelpByteMethods.constructInt(o1msb, o1lsb);
				byte ret2=(byte) computeBinaryOperatorFromNumbers(o1int,o2int);
				vm.pushByteOnStack(ret2);
				break;
			case 4://float
				float o2f=vm.popFloatFromStack();
				float o1f=vm.popFloatFromStack();
				byte ret3=(byte) computeBinaryOperatorFromNumbers(o1f,o2f);
				vm.pushByteOnStack(ret3);
				break;
		}
	}

}
