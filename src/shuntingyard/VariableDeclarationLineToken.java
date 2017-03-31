/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class VariableDeclarationLineToken extends LineToken {

    private VariableToken vt;

    public VariableDeclarationLineToken(String s) {
	super(s);
    }

    @Override
    public int getID() {
	return LineToken.GLOBAL_VARIABLE_DECLARATION;
    }

    @Override
    public String getRegex() {
	//return "IF\\s+\\(.*\\)$";
	NumberTokenFactory ntf = new NumberTokenFactory();
	return "^" + ntf.getTypeRegex() + "\\s+([A-Za-z][A-Za-z0-9]*);";
    }

    @Override
    public void prepare(Compiler c) throws CompilerException {

	Pattern pattern = Pattern.compile(this.getRegex());
	Matcher matcher = pattern.matcher(this.getTokenString());
	if (matcher.matches()) {
	    VariableTokenFactory vtf = new VariableTokenFactory();
	    //TODO: Handle this with a factory pattern! edit: wait, what? why?
	    vt = (VariableToken) vtf.create(matcher.group(2), 0);

	    NumberTokenFactory ntf = new NumberTokenFactory();

	    vt.setType(ntf.generatePlaceHolder(matcher.group(1)));

	    /*
	     if (matcher.group(1).equals("byte")) {
	     vt.setType(new ByteNumberToken());
	     } else if (matcher.group(1).equals("int")) {
	     vt.setType(new IntegerNumberToken());
	     }
	     */
	    if (c.getCurrentFunction() == null)//global variable declaration
	    {

		if (c.getGlobalVariable(vt.getTokenString()) == null) {
		    c.addGlobalVariable(vt);
		} else {
		    throw new CompilerException("Cannot redeclare a previously declared variable " + vt.getTokenString());
		}
	    } else {//local variable or function parameter
		if (!c.getCurrentFunction().processingParameters) { //local variable
		    if (c.getCurrentFunction().getLocalVariable(vt.getTokenString()) != null) {
			throw new CompilerException("Cannot redeclare a previously declared local variable " + vt.getTokenString());
		    } else if (c.getCurrentFunction().getArgumentMap().get(vt.getTokenString()) != null) {
			throw new CompilerException("Error: local variable declaration shadows a parameter.");
		    } else {
			c.getCurrentFunction().addLocalVariable(vt);
			if (c.getGlobalVariable(vt.getTokenString()) != null) {
			    c.issueWaring("Warning: local variable declaration hides a field!");
			}
		    }
		} else //function parameter
		{
		    if (c.getCurrentFunction().getArgumentMap().get(vt.getTokenString()) != null) {
			throw new CompilerException("Error: duplicate parameter!");
		    } else {
			c.getCurrentFunction().addArgument(vt);
			if (c.getGlobalVariable(vt.getTokenString()) != null) {
			    c.issueWaring("Warning: function parameter hides a field!");
			}
		    }
		}
	    }
	}
    }

}
