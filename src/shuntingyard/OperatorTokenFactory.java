/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.HashMap;

/**
 *
 * @author thegoodhen
 */
public class OperatorTokenFactory extends DelegatingFactory{
	HashMap<String,TokenFactory> subOperatorFactoryMap;

	public OperatorTokenFactory()
	{
		this.addSubFactory(new AdditionOperatorTokenFactory());
		this.addSubFactory(new SubtractionOperatorTokenFactory());
		this.addSubFactory(new MultiplicationOperatorTokenFactory());
		this.addSubFactory(new DivisionOperatorTokenFactory());
		this.addSubFactory(new EqualsOperatorTokenFactory());
		this.addSubFactory(new LessThanOperatorTokenFactory());
		this.addSubFactory(new LessThanEqualsOperatorTokenFactory());
		this.addSubFactory(new GreaterThanOperatorTokenFactory());
		this.addSubFactory(new GreaterThanEqualsOperatorTokenFactory());
		this.addSubFactory(new OrOperatorTokenFactory());
		this.addSubFactory(new AndOperatorTokenFactory());
	}
	
	
}
