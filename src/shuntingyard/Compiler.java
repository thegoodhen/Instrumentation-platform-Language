/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author thegoodhen
 */
public class Compiler {

	HashMap<String, VariableToken> globalVariablesMap = new HashMap();
	HashMap<String, FunctionToken> functionMap = new HashMap();
	HashMap<String, Integer> stringMap = new HashMap();
	ExpressionParser p = new SimpleParser();
	LineParser lp = new SimpleLineParser();
	LinkedList<Token> byteCode; //TODO: see if we could use ArrayList to improve the speed of get()
	LinkedList<Token> functionByteCode; //TODO: see if we could use ArrayList to improve the speed of get()
	ArrayList<Token> linesList;
	private FunctionToken currentFunction;
	ArrayList<Token> byteCodeAL = new ArrayList<>();
	int bytesOnHeapCount = 0;

	public Compiler() {
		this.byteCode = new LinkedList<>();
		this.functionByteCode = new LinkedList<>();
	}

	ArrayList<Token> getLines() {
		return this.linesList;
	}

	public ExpressionParser getExpressionParser() {
		return p;
	}

	public void setExpressionParser(ExpressionParser ep)
	{
		this.p=ep;
	}

	public LinkedList<Token> getByteCode() {
		if (currentFunction == null) {
			return this.byteCode;
		} else {
			return this.functionByteCode;
		}
	}

	public ArrayList<Token> getByteCodeAL() {
		return this.byteCodeAL;
	}

	public VariableToken getGlobalVariable(String name) {
		return globalVariablesMap.get(name);
	}

	public FunctionToken getFunction(String name) {
		return functionMap.get(name);
	}

	public void addGlobalVariable(VariableToken v) {
		System.out.println("Adding a global variable: " + v.tokenString);
		v.setVariableID(this.getHeapByteCount());
		v.setGlobal();
		this.bytesOnHeapCount += v.getNumberOfBytes();
		globalVariablesMap.put(v.getTokenString(), v);
	}

	public int addString(String s) {
		int stringStart = this.getHeapByteCount();
		this.stringMap.put(s, stringStart);
		this.bytesOnHeapCount += s.length() + 1;//adding one to include the "End string" symbol.
		return stringStart;
	}

	public void addFunction(FunctionToken f) {
		System.out.println("Adding a function: " + f.getTokenString());
		//int numberOfElementsInMap=globalVariablesMap.size();//TODO: id should correspond to the heap location
		//v.setVariableID(numberOfElementsInMap);
		functionMap.put(f.getTokenString(), f);
	}

	public void compile(String s) {
		this.byteCode = new LinkedList<>();
		this.byteCodeAL=new ArrayList<>();
		System.out.println("Compilation started...");
		linesList = lp.Tokenize(s);
		firstPass(linesList);
		secondPass(linesList);
		thirdPass(linesList);
		//boolean ex = CompilableToken.compileNumber(getHeapByteCount(), this);
		byte upperByte = HelpByteMethods.getUpperByte(getHeapByteCount());
		byte lowerByte = HelpByteMethods.getLowerByte(getHeapByteCount());

		compileStrings();
		AllocByteOnHeapByteCodeToken abohbct = new AllocByteOnHeapByteCodeToken();
		abohbct.setExtended(true);
		getByteCode().add(abohbct);
		getByteCode().add(new NumericByteCodeToken(lowerByte));
		getByteCode().add(new NumericByteCodeToken(upperByte));

		this.byteCode.push(new NoOperationByteCodeToken());
		this.byteCode.push(new StopByteCodeToken());
		this.byteCode.addAll(0, functionByteCode);

		for (int i = byteCode.size() - 1; i >= 0; i--)//hotfix here, slows everything down, should be fixed soon!
		{
			byteCodeAL.add(byteCode.get(i));
		}
		//byteCodeAL.add(new NoOperationByteCodeToken());
		//byteCodeAL.add(new StopByteCodeToken());

		boolean everyJumpResolved = false;
		while (!everyJumpResolved) {
			ArrayList<Token> byteCodeCopy = (ArrayList<Token>) byteCodeAL.clone();//shallow copy to avoid ConcurrentModificationException
			everyJumpResolved = true;
			for (Token t : byteCodeAL) {
				if (t instanceof AbstractJumpByteCodeToken) {
					boolean recompiled = (((AbstractJumpByteCodeToken) t).updateTargetInfo(byteCodeCopy));
					if (recompiled) {
						everyJumpResolved = false;
					}
				}
			}

			for (Token t : byteCodeAL) {
				if (t instanceof JumpTargetByteCodeToken) {
					boolean removed = byteCodeCopy.remove(t);
					System.out.println("removed: " + removed);
				}
			}

			byteCodeAL = byteCodeCopy;
		}

		int metadataBytesCount=(byteCodeAL.size()/8)+1;
		ArrayList<Byte> numericByteCode=new ArrayList<>();

		numericByteCode.add(HelpByteMethods.getUpperByte(metadataBytesCount));
		numericByteCode.add(HelpByteMethods.getLowerByte(metadataBytesCount));

		for(int i=0;i<metadataBytesCount;i++)
		{
			numericByteCode.add((byte)0);
		}
		int i=0;
		for(Token t:byteCodeAL)
		{
			if(t instanceof IRunnableToken)
			{
				numericByteCode.add(((IRunnableToken)(t)).getCode());
			}
			if (!(t instanceof NumericByteCodeToken))
			{
				byte maskByte=numericByteCode.get((i/8)+2);
				maskByte|=(1<<i%8);
				numericByteCode.set((i/8)+2, maskByte);
			}
			i++;
		}

		if (byteCode.isEmpty());
		System.out.println("Compilation end...");
		System.out.println("konec");
		StringBuilder sb=new StringBuilder();
		sb.append("char program[]={");
		for(int i2=0;i2<numericByteCode.size()-1;i2++)
		{
			sb.append(numericByteCode.get(i2)).append(", ");
		}
			sb.append(numericByteCode.get(numericByteCode.size()-1));
			sb.append("};");
System.out.println(sb.toString());
		
	}

	private void firstPass(ArrayList<Token> al) {
		for (Token t : al) {
			((LineToken) t).prepare(this);
		}

	}

	private void secondPass(ArrayList<Token> al) {

		for (Token t : al) {
			((LineToken) t).precompile(this);
		}
	}

	private void thirdPass(ArrayList<Token> al) {

		for (Token t : al) {
			((LineToken) t).compile(this);
		}
	}

	public void issueWaring(String s) {
		System.err.println(s);
	}

	public void issueError(String s) {
		System.err.println(s);
	}

	public void setCurrentFunction(FunctionToken ft)//TODO: throw runtimeException when this is called outside the compilation stage!
	{
		this.currentFunction = ft;
	}

	public FunctionToken getCurrentFunction() {
		return this.currentFunction;
	}

	private int getHeapByteCount() {
		/*
		 int returnNumber = 0;
		 for (Token t : globalVariablesMap.values()) {
		 if (t instanceof VariableToken) {
		 returnNumber += ((VariableToken) t).getNumberOfBytes();
		 }
		 }
		 return returnNumber;
		 */
		return this.bytesOnHeapCount;
	}

	private void compileStrings() {
		for (String s : stringMap.keySet()) {
			byte lsb = HelpByteMethods.getLowerByte(stringMap.get(s));
			byte msb = HelpByteMethods.getUpperByte(stringMap.get(s));
			//boolean ex=CompilableToken.compileNumber(stringMap.get(s), this);
			AllocStringOnHeapByteCodeToken asohbct = new AllocStringOnHeapByteCodeToken();
			asohbct.setExtended(true);
			this.byteCode.add(asohbct);
			this.byteCode.add(new NumericByteCodeToken(lsb));
			this.byteCode.add(new NumericByteCodeToken(msb));
			byte charVal = 0;
			for (int i = 0; i < s.length(); i++) {
				charVal = (byte) s.charAt(i);
				this.byteCode.add(new NumericByteCodeToken(charVal));
			}
			this.byteCode.add(new NumericByteCodeToken((byte) 0));//end string
		}
	}

}
