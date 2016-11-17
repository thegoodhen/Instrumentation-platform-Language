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
public class PrintTextUserFunctionToken extends AbstractBuiltInFunctionToken{

	public PrintTextUserFunctionToken(String tokenString) {
		super(tokenString);
		this.setReturnType(new ByteNumberToken("0"));//TODO: introduce void
		VariableToken param1=new VariableToken("text");
		param1.setType(new IntegerNumberToken());
		this.addArgument(param1);
	}

	@Override
	public void run(VirtualMachine vm) {
		int stringAddress=vm.popIntFromStack();
		StringBuilder sb=new StringBuilder();
		byte currentCharVal=-1;
		int index=0;

		while(currentCharVal!=0)
		{
			currentCharVal=(byte)(vm.getHeap().get(stringAddress+index).getValue());
			char currentChar=(char)currentCharVal;
			sb.append(currentChar);
			index++;
			currentCharVal=(byte)(vm.getHeap().get(stringAddress+index).getValue());
		}
		System.out.println(sb.toString());
		vm.pushByteOnStack((byte)0);//TODO: remove when we introduce void
	}

	@Override
	public byte getBaseCode() {
		return (byte)129;
	}
	
}
