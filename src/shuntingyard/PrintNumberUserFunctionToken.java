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
public class PrintNumberUserFunctionToken extends AbstractBuiltInFunctionToken {

	public PrintNumberUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new ByteNumberToken("0"));//TODO: introduce void
		VariableToken param1 = new VariableToken("number");
		param1.setType(new FloatNumberToken());
		this.addArgument(param1);
	}

	@Override
	public void run(VirtualMachine vm) {
		float numToPrint = vm.popFloatFromStack();
		if ((Math.abs(numToPrint - (int) numToPrint)) < 0.0000001)//isn't a fraction then
		{
			System.out.println((int)numToPrint);
		} else {

			System.out.println(numToPrint);
		}
		vm.pushByteOnStack((byte) 0);//TODO: remove when we introduce void
	}

	@Override
	public byte getBaseCode() {
		return (byte)128;
	}


}
