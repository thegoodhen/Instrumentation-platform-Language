package testsBasic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shuntingyard.CompilerException;
import shuntingyard.VirtualMachine;

/**
 *
 * @author thegoodhen
 */
public class CompilerTests {

    private VirtualMachine vm;
    shuntingyard.Compiler c;
    private String prog;
    private final PrintStream stdOutBkUp = System.out;
    private final PrintStream stdErrBkUp = System.err;
    private final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();

    public CompilerTests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
	vm = new VirtualMachine();
	c = new shuntingyard.Compiler();
    }

    @Before
    public void setUpStreams() {

	System.setOut(new PrintStream(stdOut));
	System.setErr(new PrintStream(stdErr));
    }

    @After
    public void cleanUpStreams() {
	System.setOut(this.stdOutBkUp);
	System.setErr(this.stdErrBkUp);
    }

    @After
    public void tearDown() {
	/*
	 c.compile(prog);
	 vm.setProgram(c.getByteCodeAL());
	 vm.runProgram();
	 */
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testBasicOperators() {
	String response = runProgram("printNumber(1+2+3);\n");
	assertEquals("6\r\n", response);
    }

    @Test
    public void testBasicOperators2() {
	String response = runProgram("printNumber(1+2-3);\n");
	assertEquals("0\r\n", response);
    }

    @Test
    public void testBasicOperators3() {
	String response = runProgram("printNumber(5*4/2);\n");
	assertEquals("10\r\n", response);
    }

    @Test
    public void testBasicOperators4() {
	String response = runProgram("printNumber(10/2*5);\n");
	assertEquals("25\r\n", response);
    }

    @Test
    public void testBasicOperators5() {
	String response = runProgram("printNumber(20/2/2);\n");
	assertEquals("5\r\n", response);
    }

    @Test
    public void testOperatorPrecedence() {
	String response = runProgram("printNumber(2+3*2);\n");
	assertEquals("8\r\n", response);
    }

    @Test
    public void testOperatorPrecedence2() {
	String response = runProgram("printNumber(2+6/2);\n");
	assertEquals("5\r\n", response);
    }

    @Test
    public void testOperatorPrecedence3() {
	String response = runProgram("printNumber(2+20/2*2-1);\n");
	assertEquals("21\r\n", response);
    }

    @Test
    public void testOperatorPrecedence4() {
	String response = runProgram("printNumber(2+2*20/5-1);\n");
	assertEquals("9\r\n", response);
    }

    @Test
    public void testOperatorPrecedence5() {
	String response = runProgram("printNumber(10>1==4<19);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testOperatorPrecedence6() {
	String response = runProgram("printNumber(10>20||3<4==4<19&&7>3);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testOperatorPrecedence7() {
	String response = runProgram("printNumber(10+1>2);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testOperatorPrecedence8() {
	String response = runProgram("printNumber(10+1>2 && 10<10+1);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testOperatorPrecedence9() {
	String response = runProgram("printNumber(10+1>2);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testOperatorPrecedence10() {
	String response = runProgram("printNumber(10<10+1);\n");
	assertEquals("1\r\n", response);
    }

    @Test
    public void testVariableAssignment() {
	String response = runProgram("byte a;\na=30;\nprintNumber(a);\n");
	assertEquals("30\r\n", response);
    }

    @Test
    public void testVariableAssignment2() {
	String response = runProgram("int a;\na=3000;\nprintNumber(a);\n");
	assertEquals("3000\r\n", response);
    }

    @Test
    public void testVariableAssignment3() {
	String response = runProgram("int a;\na=3000;\nprintNumber(a);\n");
	assertEquals("3000\r\n", response);
    }

    /**
     * sum of two bytes is a byte #feature #wontfix
     */
    @Test
    public void testVariableAssignment4() {
	String response = runProgram("int a;\na=100+100+100+100;\nprintNumber(a);\n");
	assertEquals("144\r\n", response);
    }

    @Test
    public void testVariableAssignment5() {
	String response = runProgram("int a;\na=100+300;\nprintNumber(a);\n");
	assertEquals("400\r\n", response);
    }

    @Test
    public void testVariableAssignment6() {
	String response = runProgram("int a;\nint b;\nint c;\nc=3;\nb=2;\na=b+c;\nprintNumber(a);\n");
	assertEquals("5\r\n", response);
    }

    @Test
    public void testVariableAssignment7() {
	runProgram("int a;\na=\"slepice\";\n");
    }

    @Test
    public void testVariableAssignment8() {
	String response = runProgram("int a;\nint b;\nint c;\nc=3;\nb=\"slepice\";\na=b+c;\nprintNumber(a);\n");
	assertEquals("9\r\n", response);
    }

    @Test
    public void testVariableAssignment9() {
	runProgram("int a;\na=2;\n");

    }

    @Test
    public void testVariableAssignment10() {
	String response = runProgram("int a;\nint b;\nint c;\nc=3;\nb=\"slepice\";\nc=\"pipka\";\na=b+c;\nprintNumber(a);\n");
	assertEquals("20\r\n", response);
    }

    @Test
    public void testVariableAssignment11() {
	String response = runProgram("int a;\nint b;\na=\"kokodak\";\nint c;\nb=10;\na=4;\na=1;\nb=2;\nc=3;\na=b+c;\nprintNumber(a);\n");
	assertEquals("5\r\n", response);
    }

    @Test
    public void testIf() {
	String response = runProgram("IF(3)\nprintNumber(4);\nENDIF\n");
	assertEquals("4\r\n", response);

    }

    @Test
    public void testIf2() {
	String response = runProgram("byte b;\nb=3;\nIF(b)\nprintNumber(b);\nELSE\nprintNumber(b-1);\nENDIF\n");
	assertEquals("3\r\n", response);

    }

    @Test
    public void testIf3() {
	String response = runProgram("byte b;\nb=5;\nIF(b==3)\nb=4;\nELSE\nb=10;\nENDIF\nprintNumber(b);\n");
	assertEquals("10\r\n", response);

    }

    @Test
    public void testIf4() {
	String response = runProgram("IF (1)\n"
		+ "printNumber(10);\n"
		+ "ELSE\n"
		+ "printNumber(20);\n"
		+ "ENDIF\n");
	assertEquals("10\r\n", response);

    }

    @Test
    public void testIf5() {
	String response = runProgram("byte kokon()\n"
		+ "IF(1)\n"
		+ "printNumber(10);\n"
		+ "ELSE\n"
		+ "printNumber(20);\n"
		+ "ENDIF\n"
		+ "RETURN 0;\n"
		+ "ENDFUNCTION\n"
		+ "kokon();\n");
	assertEquals("10\r\n", response);

    }

    @Test
    public void testNestedIf() {
	String response = runProgram("byte a;\na=10;\nIF(a==10)\nIF(a>9)\nprintNumber(a);\nENDIF\nENDIF\n");
	assertEquals("10\r\n", response);
    }

    @Test
    public void testForLoop() {
	String response = runProgram("int i;\ni=0;\nFOR i=0;i<10;i=i+1;\nprintNumber(i);\nNEXT\n");
	assertEquals("0\r\n1\r\n2\r\n3\r\n4\r\n5\r\n6\r\n7\r\n8\r\n9\r\n", response);
    }

    @Test
    public void testFunctionCalls1() {
	String response = runProgram("test(2);\nbyte test(byte n)\nprintNumber(n);\nRETURN 0;\nENDFUNCTION\n");
	assertEquals("2\r\n", response);
    }

    @Test
    public void testFunctionCalls2() {
	String response = runProgram("test1(2);\n byte test1(byte n)\ntest2(n);\nRETURN 0;\nENDFUNCTION\nbyte test2(byte n)\nprintNumber(n);\nRETURN 0;\nENDFUNCTION\n");
	assertEquals("2\r\n", response);
    }

    @Test
    public void testRecursion() {

	String response = runProgram("recursionTest(5);\nbyte recursionTest(byte n)\nIF(n>0)\nrecursionTest(n-1);\nprintNumber(n);\nENDIF\nRETURN 0;\nENDFUNCTION\n");
	assertEquals("1\r\n2\r\n3\r\n4\r\n5\r\n", response);
    }

    @Test
    public void fibo() {

	String response = runProgram("printNumber(fibo(20));\nint fibo(int n)\nint returnResult;\nIF n==0\nreturnResult=0;\nELSE\nIF n==1\nreturnResult=1;\nELSE\nreturnResult=fibo(n-1)+fibo(n-2);\nENDIF\n\nENDIF\nRETURN returnResult;\nENDFUNCTION\n");
	assertEquals("6765\r\n", response);
    }

    @Test
    public void testLocalVars() {

	String response = runProgram("printNumber(test(1));\nint test(int n)\nint returnResult;\nreturnResult=10;\nreturnResult=returnResult+n;\nRETURN returnResult;\nENDFUNCTION\n");
	assertEquals("11\r\n", response);
    }

    @Test
    public void testPrintNumber() {
	String response = runProgram("printNumber(10);\n");
	assertEquals("10\r\n", response);
    }

    @Test
    public void testPrintNumber2() {
	String response = runProgram("printNumber(3000);\n");
	assertEquals("3000\r\n", response);
    }

    @Test
    public void testPrintNumber3() {
	String response = runProgram("printNumber(1.2345);\n");
	assertTrue(response.contains("1.2345"));
    }

    @Test
    public void testPrintString() {
	String response = runProgram("printText(\"kokodak\");\n");
	assertEquals("kokodak\r\n", response);
    }

    @Test
    public void testPrintString2() {
	String response = runProgram("int a;\na=\"kokodak\";\nprintText(a);\n");
	assertEquals("kokodak\r\n", response);
    }


    /*
     @Test
     public void testPrintString3()
     {
     String response = runProgram("int a;\nint b;\na=\"opelichana \";\nb=\"slepice\";\nint peri=10;\nprintText(slepice-peri);\n");
     assertEquals("opelichana slepice\r\n", response);
     }
     */
    @Test
    public void testPrintString4() {
	String response = runProgram("int a;\na=\"kokodak\";\nprintText(a+2);\n");
	assertEquals("kodak\r\n", response);
    }

    @Test
    public void testSb1() {
	String response = runProgram("int a;\na=sbAlloc(3);\nprintNumber(a);\n");
	assertEquals("0\r\n", response);
    }

    @Test
    public void testSb2() {
	String response = runProgram("int a;\na=sbAlloc(3);\nsetSb(a,0,10);\nprintNumber(sb(a,0));\n");
	assertEquals("10\r\n", response);
    }

    @Test
    public void testSb3() {
	String response = runProgram("int a;\na=sbAlloc(5);\nbyte i;\ni=0;\nFOR i=0; i<5;i=i+1;\nsetSb(a,i,i+1);\nNEXT\nFOR i=4;i>=0;i=i-1;\nprintNumber(sb(a,i));\nNEXT\n");
	assertEquals("5\r\n4\r\n3\r\n2\r\n1\r\n", response);
    }

    @Test
    public void testSb4() {
	String response = runProgram("int a;\na=siAlloc(5);\nsetSi(a,3,280);\nprintNumber(si(a,3));\n");
	assertEquals("280\r\n", response);
    }

    @Test
    public void testSb5() {
	String response = runProgram("int a;\na=siAlloc(5);\nint i;\ni=0;\nFOR i=0; i<5;i=i+1;\nsetSi(a,i,i+254);\nNEXT\nFOR i=4;i>=0;i=i-1;\nprintNumber(si(a,i));\nNEXT\n");
	assertEquals("258\r\n257\r\n256\r\n255\r\n254\r\n", response);
    }

    @Test
    public void testBadConversion() {
	String response = runProgramReturnErrors("byte a;\na=1024;\n");
	assertTrue(response.contains("Invalid assignment"));
    }

    @Test
    public void testFloat() {
	String response = runProgram("float a;\nprintNumber(a);\n");
	assertEquals("0\r\n", response);
    }

    @Test
    public void testFloat2() {
	String response = runProgram("float a;\na=1.23;\nprintNumber(a);\n");
	assertTrue(response.contains("1.23"));
    }

    @Test
    public void testFloat3() {
	String response = runProgram("float a;\na=10;\nprintNumber(a);\n");
	assertTrue(response.contains("10"));
    }

    @Test
    public void testFloatExpression() {
	String response = runProgram("float a;\na=4;\nprintNumber(a/2);\n");
	assertEquals("2\r\n", response);
    }

    @Test
    public void testFloatExpression2() {
	String response = runProgram("float a;\na=4.0;\nprintNumber(a*1.0/2);\n");
	assertEquals("2\r\n", response);
    }

    @Test
    public void testFloatExpression3() {
	String response = runProgram("float a;\na=4.0;\nfloat b;\nb=1.2;\nprintNumber(a+2*b);\n");
	assertEquals("6.4\r\n", response);
    }

    @Test
    public void testImplicitConversion() {
	String response = runProgram("byte a;\na=4;\n int b;\nb=400;\nprintNumber(a+b);\n");
	assertEquals("404\r\n", response);
    }

    @Test
    public void testImplicitConversion2() {
	String response = runProgram("int a;\na=400;\nbyte b;\nb=4;\nprintNumber(a+b);\n");
	assertEquals("404\r\n", response);
    }

    @Test
    public void testImplicitConversion3() {
	String response = runProgram("byte a;\na=100;\nfloat b;\nb=4.0;\nprintNumber(a+b);\n");
	assertEquals("104\r\n", response);
    }

    @Test
    public void testImplicitConversion4() {
	String response = runProgram("int a;\na=400;\nfloat b;\nb=4.0;\nprintNumber(a+b);\n");
	assertEquals("404\r\n", response);
    }

    /*
     @Test
     public void testAtari() {
     String response = runProgram("int a;\na=0;\nFOR a=0; a<10000;a=a+1;\nprintNumber(a);\nNEXT\n");
     assertEquals("404\r\n", response);
     }
     */

    /*
     @Test
     public void testConditionkokodak() {
     String response = runProgram("float a;\na=1.23;\nIF (a&&1)\nprintNumber(30);\nENDIF\n");
     assertEquals("30\r\n", response);
     }
     */
    private String runProgram(String prog) {
	try {
	    c.compile(prog);
	    vm.setProgram(c.getByteCodeAL());
	    stdOut.reset();
	    vm.runProgram();
	    String stdOutString = stdOut.toString();
	    String stdErrString = stdErr.toString();
	    System.setOut(this.stdOutBkUp);
	    System.setErr(this.stdErrBkUp);
	    System.out.println(stdOutString);
	    System.err.println(stdErrString);
	    return stdOutString;
	} catch (CompilerException ex) {
	    Logger.getLogger(CompilerTests.class.getName()).log(Level.SEVERE, null, ex);
	}
	return "";
    }

    private String runProgramReturnErrors(String prog) {
	try {
	    c.compile(prog);
	    vm.setProgram(c.getByteCodeAL());
	    stdOut.reset();
	    vm.runProgram();
	    String stdOutString = stdOut.toString();
	    String stdErrString = stdErr.toString();
	    System.setOut(this.stdOutBkUp);
	    System.setErr(this.stdErrBkUp);
	    System.out.println(stdOutString);
	    System.err.println(stdErrString);
	    return stdErrString;
	} catch (CompilerException ex) {
	    Logger.getLogger(CompilerTests.class.getName()).log(Level.SEVERE, null, ex);
	}
	return "";

    }

}
