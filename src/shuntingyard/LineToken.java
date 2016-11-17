package shuntingyard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thegoodhen
 */
public abstract class LineToken extends Token{
	public static final int IF=0;
	public static final int ELSE=1;
	public static final int ENDIF=2;
	public static final int GLOBAL_VARIABLE_DECLARATION=3;
	public static final int VARIABLE_ASSIGNMENT=4;
	public LineToken(String s)
	{
		super(s);
	}
	public void prepare(Compiler c)
	{

	}

	public void precompile(Compiler c)
	{

	}
	public void compile(Compiler c)
	{

	}
}
