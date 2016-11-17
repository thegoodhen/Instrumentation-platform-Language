package shuntingyard;


import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Delegating factory designed to create Tokens for expressions, to be used by
 * expression parsers.
 *
 * @author thegoodhen
 */
public class GeneralTokenFactory extends DelegatingFactory{
	HashMap<Integer,TokenFactory> subFactoryMap;
	public GeneralTokenFactory()
	{


		

		this.addSubFactory(new NumberTokenFactory());
		this.addSubFactory(new LeftBracketFactory());
		this.addSubFactory(new RightBracketFactory());
		this.addSubFactory(new ArgumentSeparatorTokenFactory());
		this.addSubFactory(new FunctionTokenFactory());
		this.addSubFactory(new VariableTokenFactory());
		this.addSubFactory(new OperatorTokenFactory());
		this.addSubFactory(new StringLiteralTokenFactory());
	}
	


	
}
