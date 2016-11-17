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
public class VariableToken extends CompilableToken implements IRunnableToken {

	public static final int UNKNOWN = -1;
	public static final int BYTE = 0;
	public static final int INT = 1;//TODO: add float
	public static final int VOID = 2;//TODO: add float

	public static final int GLOBAL = 0;
	public static final int LOCAL = 1;
	public static final int PARAM = 2;

	int variableID;
	private NumberToken type;
	private int context;
	private boolean extended;

	public VariableToken(String tokenString) {
		super(tokenString);
		this.type = null;
		this.context = UNKNOWN;
	}

	public void setGlobal() {
		this.context = GLOBAL;
	}

	public void setLocal() {
		this.context = LOCAL;
	}

	public void setParam() {
		this.context = PARAM;
	}

	public int getContext() {
		return this.context;
	}

	public NumberToken getType() {
		return this.type;
	}

	public void setType(NumberToken type) {
		this.type = type;
	}

	public int getVariableID() {
		return this.variableID;
	}

	public void setVariableID(int variableID) {
		this.variableID = variableID;
	}

	@Override
	public int getID() {
		return Token.VARIABLE;
	}

	@Override
	public String getRegex() {
		return "([A-Za-z][A-Za-z0-9]*)";
	}

	//TODO: put the getNumberOfBytes into an interface.
	public int getNumberOfBytes() {
		return this.type.getNumberOfBytes();
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		//figure out if this variable even exists

		VariableToken vt;
		vt = c.getGlobalVariable(this.getTokenString());
		if (vt == null) {
			vt = c.getCurrentFunction().getLocalVariable(this.getTokenString());
		}
		if (vt == null) {
			vt = c.getCurrentFunction().getParameter(this.getTokenString());
		}

		if (vt == null) {
			System.err.println("The variable \"" + this.getTokenString() + "\" cannot be used in an expression, as it is not a known variable in the current context!");
		} else {
			NumberTokenFactory ntf = new NumberTokenFactory();

//			NumberToken nt = (NumberToken) ntf.create(Integer.toString(vt.getVariableID()), 0);
			//nt.compile(theStack, c);//when we compile the variable, we add the ID of the variable on the bytecode stack of the compiler c. We use NumberTokenFactory to decide, whether the ID is actually 1 byte long or 2 bytes long and then we add those bytes to the compiler bytecode stack.
			//theStack.pop();//well... the previous call had the side effect of placing the "NumberToken" on the working stack "theStack", which is not what we want, so we're removing it from the working stack here!
			extended=CompilableToken.compileNumber(vt.getVariableID(), c);

			//boolean extended = false;
			/*
			if (nt instanceof ByteNumberToken) {
				extended = false;
			} else {
				extended = true;
			}
			*/

			this.type = vt.getType();
			this.context = vt.getContext();

			c.getByteCode().push(this);

			//TODO: Following code should have been here for ages, it seems suspicious
			// (impossible)
			//that everything even worked without it
			theStack.push(this.type);
			/*
			 if (this.getType() == BYTE) {
			 c.getByteCode().push(new PushByteVariableOnStackByteCodeToken(extended));
			 theStack.push(new ByteNumberToken("0"));
			 } else if (this.getType() == INT) {
			 c.getByteCode().push(new PushIntegerVariableOnStackByteCodeToken(extended));
			 theStack.push(new IntegerNumberToken("0"));
			 } else {
			 System.err.println("Cannot compile a variable reference; unknown variable type for variable \"" + this.getTokenString() + ".\"");

			 }
			 */
		}

	}

	@Override
	public void run(VirtualMachine vm) {
		variableID = vm.popNumberFromStack(this.extended);
		if (this.context == GLOBAL) {
			if (this.type instanceof ByteNumberToken) {
				vm.pushByteOnStack(vm.getHeap().get(variableID).getValue());
			} else if (this.type instanceof IntegerNumberToken) {
				byte msb = vm.getHeap().get(variableID).getValue();
				byte lsb = vm.getHeap().get(variableID + 1).getValue();
				vm.pushIntOnStack(HelpByteMethods.constructInt(msb, lsb));
			} else if (this.type instanceof FloatNumberToken) {
				byte[] floatBytes = new byte[4];
				for (int i = 0; i < 4; i++) {
					floatBytes[i] = vm.getHeap().get(variableID + i).getValue();
				}
				Float f = HelpByteMethods.constructFloat(floatBytes);
				vm.pushFloatOnStack(f);

			}
		} else {//if (this.context == LOCAL) {

			if (this.type instanceof ByteNumberToken) {
				vm.pushByteOnStack(((NumericByteCodeToken) (vm.getStack().get(variableID + vm.getBasePointer() ))).getValue());
			} else if (this.type instanceof IntegerNumberToken) {
				byte msb = ((NumericByteCodeToken) (vm.getStack().get(variableID + vm.getBasePointer() ))).getValue();
				byte lsb = ((NumericByteCodeToken) (vm.getStack().get(variableID + vm.getBasePointer() + 1))).getValue();

				//byte msb = vm.getHeap().get(variableID).getValue();
				//byte lsb = vm.getHeap().get(variableID + 1).getValue();
				vm.pushIntOnStack(HelpByteMethods.constructInt(msb, lsb));
			} else if (this.type instanceof FloatNumberToken) {

				byte[] floatBytes = new byte[4];
				for (int i = 0; i < 4; i++) {
					floatBytes[i] = ((NumericByteCodeToken) (vm.getStack().get(variableID + vm.getBasePointer() + i))).getValue();
				}
				Float f = HelpByteMethods.constructFloat(floatBytes);
				vm.pushFloatOnStack(f);
			}
		} /*else if (this.context == PARAM) {

			if (this.type instanceof ByteNumberToken) {
				vm.pushByteOnStack(((NumericByteCodeToken) (vm.getStack().get(-variableID + vm.getBasePointer() - 3))).getValue());
			} else if (this.type instanceof IntegerNumberToken) {
				byte msb = ((NumericByteCodeToken) (vm.getStack().get(-variableID + vm.getBasePointer() - 3))).getValue();
				byte lsb = ((NumericByteCodeToken) (vm.getStack().get(-variableID + vm.getBasePointer() - 2))).getValue();

				//byte msb = vm.getHeap().get(variableID).getValue();
				//byte lsb = vm.getHeap().get(variableID + 1).getValue();
				vm.pushIntOnStack(HelpByteMethods.constructInt(msb, lsb));
			}

		}*/
	}

	@Override
	public boolean isExtended() {
		return extended;
	}

	@Override
	public void setExtended(boolean ex) {
		this.extended = ex;
	}

	@Override
	public byte getBaseCode() {
		byte baseCode = 10;
		if (this.type instanceof IntegerNumberToken) {
			baseCode += 2;
		}
		if (this.type instanceof FloatNumberToken) {
			baseCode += 4;
		}
		if(this.context==GLOBAL)
		{
			baseCode+=6;
		}
		return baseCode;
	}

	@Override
	public byte getCode() {
		if(extended)
		{
			return (byte) (this.getBaseCode()+1);
		}
		return this.getBaseCode();
	}

}
