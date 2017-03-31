/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thegoodhen
 */
public class ShuntingYard {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// ... the code being measured ...    
	ExpressionParser p = new SimpleParser();
	//p.parse("+323");"2*5-2+(2+4)*2"
	//ArrayList<Token> tokenList = p.Tokenize("(2*(x+12)+3)");//p.Tokenize("(5+3)*12/3");//p.Tokenize("3+4");//p.Tokenize("slepice09+(2*kokon(2,3))+10/2");
	//ArrayList<Token> rpn = p.getRPN(tokenList);
	//for (Token t : rpn) {
	    //System.out.println(t.tokenString);
	//}

		//ExpressionParser p2=new SimpleLineParser();
	//String prog="byte kokodak;\n int slepice;\nslepice=500;\nslepice(slepice(1,2),slepice(3,4));\nkokodak=5+1;\nIF (kokodak)\nELSE\nENDIF\n byte slepice(byte bl, int b2)\n  byte pipka;\nint i;\nkokodak=0;\nFOR i=10; i;i=i+1;\n pipka=pipka+1; \nNEXT\n\nRETURN pipka;\nENDFUNCTION\n";
	//String prog="byte kokodak;\nint slepice;\nslepice=500;\nslepice1(slepice1(10,10),10);\nkokodak=5+1;\nIF (kokodak)\nELSE\nENDIF\n int slepice1(byte bl, int b2)\n  byte pipka;\nint i;\nkokodak=0;\nFOR i=10; i;i=i+1;\n pipka=pipka+1; \nNEXT\n\nRETURN pipka;\nENDFUNCTION\n";
	//String prog="byte kokodak;\nint slepice;\nslepice=500;\nslepice1(slepice1(90));\nkokodak=5+1;\nIF (kokodak)\nELSE\nENDIF\nint slepice1(byte bl)\n  int pipka;\nint i;\nkokodak=0;\nFOR i=10; i;i=i+1;\n pipka=pipka+1; \nNEXT\n\nRETURN pipka;\nENDFUNCTION\n";
	//tokenList=p2.Tokenize(prog);
	//String prog="byte kokodak;\nbyte slepice;\nslepice=3;\nkokodak=5;\n";//OK
	//String prog="byte kokodak;\n int slepice;\nslepice=3;\nkokodak=5;\n";
	//String prog="byte kokodak;\nkokodak=3;\nIF(kokodak)\nkokodak=10;\nELSE\nkokodak=5;\nENDIF\nkokodak=5;\n";
	//String prog="byte a;\n byte b;\nbyte kokon(byte parametr)\nRETURN parametr+1;\n ENDFUNCTION\n";
	//String prog="byte a;\n byte b;\nbyte kokon(byte parametr, byte param)\nint a; \na=10;\nRETURN parametr+1;\n ENDFUNCTION\n";
	//String prog="byte a;\nIF (10)\na=0;\nENDIF\nIF (5)\n\na=1;\nENDIF\n";
	//String prog="byte kokodak;\nIF (10)\nIF(5)\nkokodak=1;\nENDIF\nENDIF\n";
	//String prog="byte kokon;\nFOR kokon=10;kokon;kokon=kokon-1;\nbyte slepiceFluf;\nslepiceFluf=slepiceFluf+1;\nNEXT\n";
	//String prog="byte kokon;\nFOR kokon=0;kokon<=10;kokon=kokon+1;\nbyte slepiceFluf;\nslepiceFluf=slepiceFluf+1;\nNEXT\n";
	//String prog="byte kokon;\nbyte slepice;\n\nkokon=5;\nprintNumber(15);\nprintNumber(20);\nIF (kokon>=3 && kokon<=5)\nslepice=3;\nELSE\nslepice=2;\nENDIF\n";
	//String prog="byte slepice;\nFOR slepice=0;slepice<10;slepice=slepice+1;\nprintNumber(slepice*2);\nNEXT\ntest(2);\nbyte test(byte a)\nRETURN 2;\nENDFUNCTION\n";
	//String prog="printNumber(test(14));\nbyte test(byte a)\nRETURN 2;\nENDFUNCTION\n";
	//String prog="byte a;\na=20;\nprintNumber(test(2));\nprintNumber(2*a);\n int test(byte c)\n byte b;\nb=5;\nRETURN 1000;\nENDFUNCTION\n";
	//String prog="byte a;\na=20;\nprintNumber(test(a));\nprintNumber(2*a);\n int test(byte c)\n byte b;\nb=5;\nRETURN c+300;\nENDFUNCTION\n";
	//String prog="printNumber(fibo(20));\nint fibo(int n)\nint returnResult;\nprintNumber(500);\nIF n==0\nprintNumber(1);\nreturnResult=0;\nELSE\nIF n==1\nprintNumber(100);\nreturnResult=1;\nprintNumber(returnResult);\nELSE\nprintNumber(200);\nreturnResult=fibo(n-1)+fibo(n-2);\nprintNumber(300);\nENDIF\n\nENDIF\nprintNumber(450);\nRETURN returnResult;\nENDFUNCTION\n";
	//aktualne testujeme:
	//String prog="printNumber(fibo(20));\nprintNumber(20);\nint fibo(int n)\nint returnResult;\nIF n==0\nreturnResult=0;\nELSE\nIF n==1\nreturnResult=1;\nELSE\nreturnResult=fibo(n-1)+fibo(n-2);\nENDIF\n\nENDIF\nRETURN returnResult;\nENDFUNCTION\n";
	//divna vec:
	//String prog="printNumber(fibo(20));\nint fibo(int n)\nint returnResult;\nIF n==0\nreturnResult=0;\nELSE\nIF n==1\nreturnResult=1;\nELSE\nreturnResult=fibo(n-1)+fibo(n-2);\nENDIF\nENDIF\nRETURN returnResult;\nENDFUNCTION\n";
	//String prog="recursionTest(5);\nbyte recursionTest(byte n)\nIF(n>0)\nrecursionTest(n-1);\nprintNumber(n);\nENDIF\nRETURN 0;\nENDFUNCTION\n";
	//String prog="recursionTest(5);\nbyte recursionTest(byte n)\nIF(n>0)\nprintNumber(n);\nrecursionTest(n-1);\nENDIF\nRETURN 0;\nENDFUNCTION\n";
	//String prog="test(2);\nbyte test(byte n)\nIF (n==2)\nprintNumber(3);\ntest(3);\nENDIF\nENDFUNCTION\n";
	//String prog="test1(2);\n byte test1(byte n)\ntest2(n);\nENDFUNCTION\nbyte test2(byte n)\nprintNumber(n);\nENDFUNCTION\n";
	//String prog="test(1);\nbyte test(byte n)\ntest(n);\nRETURN 2;\nENDFUNCTION\n";
	//String prog="test(2);\ntest(3);\nbyte test(byte n)\nprintNumber(n);\nRETURN 0;\nENDFUNCTION\n";
	//String prog="byte kokon;\nkokon=1;\nIF(kokon==0)\nprintNumber(0);\nELSE\nIF(kokon==1)\nprintNumber(1);\nELSE\nprintNumber(2);\nENDIF\nENDIF\n";
	//String prog="int kokon;\nint kokon2;\nkokon2=1;\nkokon=1;\nIF kokon==3\nkokon=2;\nELSE\nIF kokon==1\nkokon2=10;\nENDIF\nENDIF\nprintNumber(kokon2);\n";
	//String prog="int a;\na=10;\nIF a==10\nprintText(\"plati\");\nEadsadNDIF\n";
//String prog="recursionTest(5);\nbyte recursionTest(byte n)\nIF(n>0)\nrecursionTest(n-1);\nprintNumber(n);\nENDIF\nRETURN 0;\nENDFUNCTION\n";
//String prog="recursionTest(5);\nbyte recursionTest(byte n)\nIF(n>0)\nrecursionTest(n-1);\nprintNumber(n);\nENDIF\nRETURN 0;\nENDFUNCTION\n";
	String prog = "printNumber(fibo(8));\nint fibo(int n)\nint returnResult;\nIF n==0\nreturnResult=0;\nELSE\nIF n==1\nreturnResult=1;\nELSE\nreturnResult=fibo(n-1)+fibo(n-2);\nENDIF\n\nENDIF\nRETURN returnResult;\nENDFUNCTION\n";
//String prog="test(2);\nbyte test(byte n)\nprintNumber(n);\nRETURN 0;\nENDFUNCTION\n";
	//String prog="byte i;\ni=0;\nFOR i=0;i<10;i=i+1;\nprintNumber(i);\nNEXT\n";
	//String prog="printNumber(0);\nprintNumber(1.0);\nprintNumber(2.0);\nprintNumber(3.0);\nprintNumber(4.0);\nprintNumber(5);\n";
	//String prog="int i;\ni=100;\nprintNumber(i+23);\n";

//String prog="printNumber(test(1));\nint test(int n)\nint returnResult;\nreturnResult=10;\nreturnResult=returnResult+n;\nprintNumber(returnResult);\nRETURN returnResult;\nENDFUNCTION\n";
	Compiler c = new Compiler();
	VirtualMachine vm = new VirtualMachine();
	try {
	    c.compile(prog);
	    vm.setProgram(c.getByteCodeAL());
	    vm.runProgram();
	} catch (CompilerException ex) {
	    Logger.getLogger(ShuntingYard.class.getName()).log(Level.SEVERE, null, ex);
	}

	/*
	 for(Token t:tokenList)
	 {
	 System.out.println(t.getClass().getSimpleName());
	 System.out.println(t.tokenString);
	 }
	 */
//long startTime = System.nanoTime();    
//p.parse(rpn);
	//System.out.println(p.parse(rpn).getTokenString());
//long estimatedTime = System.nanoTime() - startTime;
//System.out.println(estimatedTime);
    }

}
