/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

/**
 *
 * @author thegoodhen
 */
public class FunctionToken extends CompilableToken {

	HashMap<String, VariableToken> argumentMap = new HashMap<>();
	private ArrayList<VariableToken> argumentList = new ArrayList<>();
	HashMap<String, VariableToken> localVariableMap = new HashMap<>();
	private FunctionDeclarationLineToken declaration;
	public boolean processingParameters = false;
	ArrayList<Token> actualParameterList = new ArrayList<>();
	private NumberToken returnType = new ByteNumberToken("0");

	public int getArgumentByteCount() {
		int returnNumber = 0;
		for (Token t : argumentList) {
			if (t instanceof VariableToken) {
				returnNumber += ((VariableToken) t).getNumberOfBytes();
			}
		}
		return returnNumber;
	}

	public int getLocalVariableByteCount() {
		int returnNumber = 0;
		for (Token t : localVariableMap.values()) {
			if (t instanceof VariableToken) {
				returnNumber += ((VariableToken) t).getNumberOfBytes();
			}
		}
		return returnNumber;
	}

	public FunctionToken(String tokenString) {
		super(tokenString);
	}

	public void setReturnType(NumberToken nt) {
		this.returnType = nt;
	}

	public NumberToken getReturnType() {
		return this.returnType;
	}

	@Override
	public String getRegex() {
		return "([A-Za-z][A-Za-z0-9]*)\\(";
	}

	public HashMap<String, VariableToken> getArgumentMap() {
		return argumentMap;
	}

	public ArrayList<VariableToken> getArgumentList() {
		return this.argumentList;
	}

	public int getArgumentCount() {
		return this.argumentMap.size();
	}

	void setFunctionDeclaration(FunctionDeclarationLineToken fdlt) {
		this.declaration = fdlt;
	}

	FunctionDeclarationLineToken getDeclaration() {
		return this.declaration;
	}

	private void updateArgumentVariableIDs() {
		int currentId = 0;
		for (int i = this.argumentList.size() - 1; i >= 0; i--) {
			VariableToken vt = this.argumentList.get(i);
			currentId += vt.getNumberOfBytes();
			currentId+=4;
			currentId=-currentId;
			vt.setVariableID(currentId);
		}
	}

	public void addArgument(VariableToken vt) {
		vt.setParam();
		//vt.setVariableID(this.getArgumentByteCount());
		this.argumentMap.put(vt.getTokenString(), vt);
		this.argumentList.add(vt);//storing duplicate links doesn't introduce significant overhead, given the low argument count for a typical function.
		this.updateArgumentVariableIDs();
		System.out.println("Adding a function parameter: \"" + vt.getTokenString() + "\" into a function " + this.getTokenString());
		System.out.println("Number of arg bytes: " + this.getArgumentByteCount());
	}

	public void addLocalVariable(VariableToken vt) {
		vt.setLocal();
		vt.setVariableID(this.getLocalVariableByteCount());
		this.localVariableMap.put(vt.getTokenString(), vt);
		//int numberOfElementsInMap = localVariableMap.size();//TODO: id should correspond to the stack location
		//vt.setVariableID(numberOfElementsInMap);
		System.out.println("Adding a local variable: \"" + vt.getTokenString() + "\" into a function " + this.getTokenString());
	}

	public VariableToken getLocalVariable(String s) {
		return this.localVariableMap.get(s);
	}

	public VariableToken getParameter(String s) {
		return this.argumentMap.get(s);
	}

	@Override
	public int getID() {
		return Token.FUNCTIONCALL;
	}

	/**
	 * Look the definition of this function up...
	 *
	 * @param c
	 */
	protected void doLookup(Compiler c) {

		FunctionToken ft = c.getFunction(this.tokenString);
		if (ft == null) {
			System.err.println("Error: Attempt to call an unknown function: \"" + this.getTokenString() + "\" !");
		} else {
			//TODO: maybe copy more? :3
			this.declaration = ft.getDeclaration();
			this.returnType = ft.getReturnType();
			this.argumentList = ft.getArgumentList();
			this.argumentMap = ft.getArgumentMap();
		}

	}

	protected void compileCall(Compiler c) {
		c.getByteCode().push(new FunctionCallByteCodeToken(this.getDeclaration().getJumpTarget()));
	}

	@Override
	public void compile(LinkedList<Token> theStack, Compiler c) {
		int startingPositionOfArgumentOnStack = 0;//this variable holds the 
		//starting position of the argument we're 
		//currently iterating over; 
		//the representation of that number is compatible 
		//with the way public static void 
		//compileByteToIntCast(int index, Compiler c) handles stuff

		doLookup(c);

		for (int i = 0; i < this.getArgumentCount(); i++) {
			actualParameterList.add(null);
		}
		for (int i = this.getArgumentCount() - 1; i >= 0; i--) {
			if (!theStack.isEmpty()) {
				Token currentParameter = theStack.pop();
				actualParameterList.set(i, currentParameter);
				startingPositionOfArgumentOnStack += ((NumberToken) (currentParameter)).getNumberOfBytes();

				if (((NumberToken) currentParameter).isImplicitlyCastableTo(argumentList.get(i).getType())) {
					((NumberToken) currentParameter).compileCastTo(argumentList.get(i).getType(), startingPositionOfArgumentOnStack - 1, c);
				} else {

					System.err.println("Cannot implicitly cast " + ((NumberToken) currentParameter).getShortName() + " into " + argumentList.get(i).getType() + "; this might lead to a lossy conversion, however the function call isn't valid without this illegal conversion.");
				}

				/*
				 if (argumentList.get(i).getType() instanceof IntegerNumberToken) {
				 if (currentParameter instanceof ByteNumberToken)//formal argument is int, found byte, we need to cast it to int
				 {
				 compileByteToIntCast(startingPositionOfArgumentOnStack - 1, c);
				 }
				 } else if (argumentList.get(i).getType() instanceof ByteNumberToken) {
				 if (!(currentParameter instanceof ByteNumberToken)) {
				 System.err.println("Error: cannot cast int to byte in this function call. This might lead to a precision loss");
				 }
				 }

				 } else {
				 System.err.println("Not enough arguments for function: " + this.getTokenString() + "!");
				 }
				 */
			}
		}

		compileCall(c);

		theStack.push(this.returnType);

	}

}
