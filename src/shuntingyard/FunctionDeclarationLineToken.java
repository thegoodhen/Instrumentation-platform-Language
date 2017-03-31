/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thegoodhen
 */
public class FunctionDeclarationLineToken extends LineToken {

    String name = "";
    String argumentsString = "";
    EndFunctionLineToken endToken = null;
    FunctionToken ft = null;
    JumpTargetByteCodeToken jtbct;

    FunctionDeclarationLineToken(String tokenString) {
	super(tokenString);
    }

    @Override
    public String getRegex() {
	return "^(void|byte|int)\\s+([A-Za-z][A-Za-z_0-9]*)\\((.*)\\)";
    }

    public JumpTargetByteCodeToken getJumpTarget() {
	return this.jtbct;
    }

    public FunctionToken getFunction() {
	return this.ft;
    }

    public void prepare(Compiler c) throws CompilerException {
	//TODO: handle the case when nothing is returned

	jtbct = new JumpTargetByteCodeToken();
	Pattern pattern = Pattern.compile(this.getRegex());
	Matcher matcher = pattern.matcher(this.getTokenString());
	if (matcher.matches()) {
	    String typeString = matcher.group(1);
	    this.ft = new FunctionToken(matcher.group(2));
	    ft.setFunctionDeclaration(this);//TODO: maybe something different		
	    NumberTokenFactory ntf = new NumberTokenFactory();
	    this.ft.setReturnType(ntf.generatePlaceHolder(typeString));
	    c.addFunction(ft);
	    c.setCurrentFunction(ft);

	    this.argumentsString = matcher.group(3);
	    String[] splitArgs = argumentsString.split(",\\s*");

	    VariableDeclarationLineTokenFactory gvdltf = new VariableDeclarationLineTokenFactory();

	    ft.processingParameters = true;//we use this to let the VariableDeclarationLineToken know that it should add the declarations as parameters. Bit hacky, admittedly.
	    for (String s : splitArgs) {
		VariableDeclarationLineToken gvdlt = (VariableDeclarationLineToken) gvdltf.create(s + ";", 0);//expecting the semicolon

		if (gvdlt == null) {
		    if (!s.isEmpty() || splitArgs.length > 1) {
			throw new CompilerException("Error: Invalid function argument: \"" + s + "\" !");
		    }
		} else {
		    gvdlt.prepare(c);
		    //this.ft.getArgumentList().add(gvdlt);
		}
	    }
	    ft.processingParameters = false;
	}

//And now we try matching the ENDFUNCTION
	int lineNumber = c.getLines().indexOf(this);

	for (int i = lineNumber + 1; i < c.getLines().size(); i++) {
	    Token t = c.getLines().get(i);
	    if (t instanceof FunctionDeclarationLineToken) {
		throw new CompilerException("Error! Function declaration nesting is not supported; expected ENDFUNCTION before new function declaration!");

	    } else if (t instanceof EndFunctionLineToken) {
		this.endToken = (EndFunctionLineToken) t;
		((EndFunctionLineToken) t).setFunctionDeclaration(this);
		break;
	    } else if (t instanceof ReturnLineToken) {
		((ReturnLineToken) (t)).setFunctionDeclaration(this);
	    }
	}
	if (endToken == null) {
	    throw new CompilerException("Attempt to find a matching ENDFUNCTION failed; Reached end of code while parsing.");
	}
    }

    public void precompile(Compiler c) {
	c.setCurrentFunction(ft);
    }

    public void compile(Compiler c) {
	c.setCurrentFunction(ft);

	c.getByteCode().push(this.jtbct);
	boolean ex = CompilableToken.compileNumber(this.ft.getLocalVariableByteCount(), c);
	AllocByteOnStackByteCodeToken abosbct = new AllocByteOnStackByteCodeToken();
	abosbct.setExtended(ex);
	c.getByteCode().push(abosbct);
    }

    @Override
    public int getID() {
	return 123;
    }

}
