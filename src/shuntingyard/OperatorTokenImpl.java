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
public class OperatorTokenImpl extends OperatorToken{

	public OperatorTokenImpl(String s) {
		super(s);
	}

	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte getBaseCode() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
