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
public class SetStackIntUserFunctionToken extends AbstractBuiltInFunctionToken{

	public SetStackIntUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new ByteNumberToken("0"));
		VariableToken param1=new VariableToken("array");
		VariableToken param2=new VariableToken("index");
		VariableToken param3=new VariableToken("value");
		param1.setType(new IntegerNumberToken());
		param2.setType(new IntegerNumberToken());
		param3.setType(new IntegerNumberToken());
		this.addArgument(param1);
		this.addArgument(param2);
		this.addArgument(param3);
	}

	@Override
	public void run(VirtualMachine vm) {
		int val=vm.popIntFromStack();
		int index=vm.popIntFromStack();
		int array=vm.popIntFromStack();
		
		byte msb=HelpByteMethods.getUpperByte(val);
		byte lsb=HelpByteMethods.getLowerByte(val);
		((NumericByteCodeToken)(vm.getStack().get(array+index*2))).setValue(msb);
		((NumericByteCodeToken)(vm.getStack().get(array+index*2+1))).setValue(lsb);
		vm.pushByteOnStack((byte)0);
	}

	@Override
	public byte getBaseCode() {
		return (byte)134;
	}
	
}
