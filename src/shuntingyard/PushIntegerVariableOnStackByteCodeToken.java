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
@Deprecated
/**
 * replaced by VariableToken
 */
public class PushIntegerVariableOnStackByteCodeToken extends ByteCodeToken{
	boolean extended;
	public PushIntegerVariableOnStackByteCodeToken(boolean extended)
	{
		this.extended=extended;
	}

	@Override
	public void run(VirtualMachine vm) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte getBaseCode() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
