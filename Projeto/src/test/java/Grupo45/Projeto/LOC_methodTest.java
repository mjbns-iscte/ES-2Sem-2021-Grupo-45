package Grupo45.Projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import junit.framework.TestCase;

public class LOC_methodTest extends TestCase {
	static ArrayList<ArrayList<String>> aa;
	static LOC_method l;

	public LOC_methodTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		aa = new ArrayList<ArrayList<String>>();
		ArrayList<String> a1 = new ArrayList<String>();
		ArrayList<String> a2 = new ArrayList<String>();
		aa.add(a1);
		aa.add(a2);		
		l = new LOC_method();
		
		File file = new File("C://jasml//src//com//jasml//compiler//ParsingException.java");	
	    InputStream is = new FileInputStream(file);
		 CompilationUnit cu = StaticJavaParser.parse(is);
		 new LOC_method().visit(cu, aa);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testVisit() {
		
		assertNotNull(aa);
		assertNotNull(aa.get(0));
		assertNotNull(aa.get(1));
		
		assertEquals(2, aa.size());
		assertEquals(6, aa.get(0).size());
		assertEquals(6, aa.get(1).size());
		
		assertEquals("ParsingException(int, int, int, String)", aa.get(0).get(0));
		assertEquals("6", aa.get(1).get(0));
		assertEquals("ParsingException(int, int, String)", aa.get(0).get(1));
		assertEquals("5", aa.get(1).get(1));
		assertEquals("ParsingException(int, String)", aa.get(0).get(2));
		assertEquals("4", aa.get(1).get(2));
		assertEquals("ParsingException(String, Exception)", aa.get(0).get(3));
		assertEquals("3", aa.get(1).get(3));
		assertEquals("ParsingException(String)", aa.get(0).get(4));
		assertEquals("3", aa.get(1).get(4));
		assertEquals("getMessage()", aa.get(0).get(5));
		assertEquals("21", aa.get(1).get(5));
		
		
	}



	

}
