/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

/**
 * This class has been deprecated and is subject to removal.
 * @author thegoodhen
 */
@Deprecated
public class AssignmentOperatorToken extends OperatorToken{

	public AssignmentOperatorToken(String s) {
		super(s);
	}

	@Override
	public int getPriority()
	{
		return -100;//maybe lower
	}
	
	@Override
	public float computeBinaryOperatorFromNumbers(float a, float b) {
		return a;
	}

	public int getAssociativity() {
		return ASSOCIATIVITY_RIGHT;
	}

	@Override
	public int getArgumentCount() {
		return 2;
	}

	public void compile(Compiler c)
	{

	}

	@Override
	public byte getBaseCode() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
