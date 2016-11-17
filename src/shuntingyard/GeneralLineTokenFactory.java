package shuntingyard;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thegoodhen
 */
public class GeneralLineTokenFactory extends DelegatingFactory {

	HashMap<Integer, TokenFactory> subFactoryMap;

	public GeneralLineTokenFactory() {
		this.addSubFactory(new IfLineTokenFactory());
		this.addSubFactory(new ElseLineTokenFactory());
		this.addSubFactory(new EndIfLineTokenFactory());
		this.addSubFactory(new VariableDeclarationLineTokenFactory());
		this.addSubFactory(new VariableAssignmentLineTokenFactory());
		this.addSubFactory(new FunctionDeclarationLineTokenFactory());
		this.addSubFactory(new EndFunctionLineTokenFactory());
		this.addSubFactory(new ForLineTokenFactory());
		this.addSubFactory(new NextLineTokenFactory());
		this.addSubFactory(new FunctionCallLineTokenFactory());
		this.addSubFactory(new ReturnLineTokenFactory());

	}

	@Override
	public Token create(String tokenString, int position)
	{
		Pattern p=Pattern.compile("(.*?)\\s*\\r?\\n");
		Matcher m=p.matcher(tokenString);
		if(m.find(position))
		{
			//String slepice=m.group(1).trim();
			Token t=super.create(m.group(1).trim(), 0);
			this.setRegexEnd(m.end());
			return t;
		}
		else
		{
			System.err.println("Did not even find a generic line token!");
		}
		return null;
	}

	 
	 





}
