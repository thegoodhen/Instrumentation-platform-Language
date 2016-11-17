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
public class StackIntUserFunctionToken extends AbstractBuiltInFunctionToken{

	public StackIntUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new IntegerNumberToken("0"));
		VariableToken param1=new VariableToken("array");
		VariableToken param2=new VariableToken("index");
		param1.setType(new IntegerNumberToken());
		param2.setType(new IntegerNumberToken());
		this.addArgument(param1);
		this.addArgument(param2);
	}

	@Override
	public void run(VirtualMachine vm) {
		int index=vm.popIntFromStack();
		int array=vm.popIntFromStack();

		
		byte msb=((NumericByteCodeToken)(vm.getStack().get(array+index*2))).getValue();
		byte lsb=((NumericByteCodeToken)(vm.getStack().get((array+index*2)+1))).getValue();
		int theInt=HelpByteMethods.constructInt(msb, lsb);
		vm.pushIntOnStack(theInt);
	}

	@Override
	public byte getBaseCode() {
		return (byte)137;
	}
	
}
