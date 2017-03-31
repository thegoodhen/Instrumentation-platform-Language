package shuntingyard;

import java.util.ArrayList;
import java.util.TreeMap;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thegoodhen
 */
public abstract class DelegatingFactory extends TokenFactory{

	private ArrayList<TokenFactory> subFactoriesList=new ArrayList<>();

	public void addSubFactory(TokenFactory tf)
	{
		this.subFactoriesList.add(tf);
		this.subFactoriesList.sort(TokenFactory.getFactoryComparator());
	}

	protected ArrayList<TokenFactory> getSubFactoriesList()
	{
		return this.subFactoriesList;
	}

	public Token generateInstance(String tokenString)
	{
		return null;
	}

	@Override
	    public Token create(String tokenString, int position) throws CompilerException {
		Token returnToken=null;
		for(TokenFactory tf:subFactoriesList)
		{
			returnToken=tf.create(tokenString, position);
			if(returnToken!=null)
			{
				this.setRegexEnd(tf.getRegexEnd());
				return returnToken;
			}
		}
		
		return generateInstance(tokenString);
	}
	
	@Override
	public String getRegex() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");

		sb.append(this.getSubFactoriesList().get(0).getRegex());
		for (int i = 1; i < this.getSubFactoriesList().size(); i++) {
			TokenFactory tf = this.getSubFactoriesList().get(i);
			sb.append("|").append(tf.getRegex());
		}
		sb.append(")");
		return sb.toString();
	}
}
