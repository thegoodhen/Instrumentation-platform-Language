/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public class VirtualMachine {
	//TODO: decide on the actual list type for stack and heap
	private ArrayList<Token> programStack=new ArrayList<>();
	private ArrayList<NumericByteCodeToken> programHeap=new ArrayList<>();
	private int programCounter;
	private ArrayList<Token> program;
	private boolean isStopped=false;
	/**
	 * The variable determining the position on stack, where the function begins-the position, after which local variables are stored.
	 * Specifically, the content on stack is going to be following: ARG0 ARG1 ARG2 ARG3 PC1, PC2, BP1<-, BP2, LOCAL_VAR0, LOCAL_VAR1...
	 * where ARG[num] is an argument passed to the function or a part of it, PC1, PC2 form the program counter state when the function call happened (int stored as 2 bytes, MSB first), BP1, BP2 form the basepointer state when the function call happened (again, 2 bytes forming an int) and VAR[num] belong to the local variables of the function in question. The arrow symbol "<-" signifies the element on stack the basePointer variable points to, e.g. the element, position of which in the stack is represented by the basePointer variable.
	 * BP_1 is the base pointer state when the function call happened
	 */
	private int stackPointer=0;
	private int basePointer=0;
	//local function variables are stored. Specificall

	public void stop()
	{
		this.isStopped=true;
	}

	public ArrayList<Token> getStack()
	{
		return this.programStack;
	}

	public ArrayList<NumericByteCodeToken> getHeap()
		{
			return this.programHeap;
		}

	public byte popByteFromStack() throws RuntimeException
	{
		NumericByteCodeToken nbct;
		stackPointer--;
		nbct=(NumericByteCodeToken)this.programStack.get(stackPointer);
		return nbct.getValue();
	}

	public int popIntFromStack() throws RuntimeException
	{
		byte lsb=popByteFromStack();
		byte msb=popByteFromStack();

		return HelpByteMethods.constructInt(msb, lsb);
	}

	public void pushByteOnStack(byte b)
	{
		while(stackPointer>=programStack.size())
		{
			programStack.add(new NumericByteCodeToken((byte)0));
		}
		this.programStack.set(stackPointer, new NumericByteCodeToken(b));
		stackPointer++;
		//this.programStack.push(new NumericByteCodeToken(b));
	}
	public void pushIntOnStack(int i)
	{
		byte msb=HelpByteMethods.getUpperByte(i);
		byte lsb=HelpByteMethods.getLowerByte(i);
		pushByteOnStack(msb);
		pushByteOnStack(lsb);
		//this.programStack.push(new NumericByteCodeToken(msb));
		//this.programStack.push(new NumericByteCodeToken(lsb));
	}

	public void pushFloatOnStack(Float f)
	{
		byte[] floatBytes=HelpByteMethods.getFloatBytes(f);
		for(byte b:floatBytes)
		{
			pushByteOnStack(b);
		}
	}

	public float popFloatFromStack()
	{
		byte[] floatBytes=new byte[4];
		for(int i=floatBytes.length-1;i>=0;i--)
		{
			floatBytes[i]=popByteFromStack();
		}
		return HelpByteMethods.constructFloat(floatBytes);
	}

	public void pushNumberOnStack(int number, boolean extended)
	{
		if(extended)
		{
			pushIntOnStack(number);
		}
		else
		{
			pushByteOnStack((byte)number);
		}
	}

	public int popNumberFromStack(boolean extended)
	{
		if(extended)
		{
			return this.popIntFromStack();
		}
		else
		{
			return this.popByteFromStack();
		}
	}

	public int getProgramCounter()
	{
		return this.programCounter;
	}

	public int getBasePointer()
	{
		return this.basePointer;
	}
	public void setBasePointer(int basepointer)
	{
		this.basePointer=basepointer;
	}

	public int getStackPointer()
	{
		return this.stackPointer;
	}

	public void setStackPointer(int stackPointer)
	{
		this.stackPointer=stackPointer;
	}

	public void setProgramCounter(int programCounter)
	{
		this.programCounter=programCounter;
	}

	public void relativeJump(int howMuch)
	{
		this.programCounter+=howMuch;// -1, cause of the way we are iterating over the elements in runProgram. We jump to one before where we want to jump, and the progCounter icrements itself in runProgram.
	}

	public void setProgram(ArrayList<Token> program)
	{
		this.program=program;
	}

	public void runProgram()
	{
		programStack.add(new NumericByteCodeToken((byte)0));
		//System.out.println("Running a program!");
		this.isStopped=false;
		programCounter=0;
		while(!isStopped)
		{
			Token t=program.get(programCounter);
			if(t instanceof IRunnableToken)
			{
				((IRunnableToken)(t)).run(this);
			}
			programCounter++;
		}
		//System.out.println("Program stopped.");
	}
	
}
