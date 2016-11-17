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
public class StackByteUserFunctionToken extends AbstractBuiltInFunctionToken{

	public StackByteUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new ByteNumberToken("0"));
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

		
		byte theByte=((NumericByteCodeToken)(vm.getStack().get(array+index))).getValue();
		vm.pushByteOnStack(theByte);
	}

	@Override
	public byte getBaseCode() {
		return (byte)136;
	}
	
}
