package Grupo45.Projeto;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class Metricas_MetodosTest extends TestCase {
	static Metricas_Metodos mm;
	static File file;
	static LOC_methodTest loc = new LOC_methodTest("loc");

	

	protected void setUp() throws Exception {
		mm = new Metricas_Metodos(2);
        file = new File("C://jasml//src//com//jasml//compiler//ParsingException.java");
        mm.analyze(file);
        super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	

	public final void testAnalyze() throws Exception {
		ArrayList<ArrayList<String>> al =mm.getAl();
		assertNotNull(al);
		assertEquals(2,al.size());
		
		assertNotNull(al.get(0));
		assertEquals(6,al.get(0).size());
		
		assertNotNull(al.get(1));
		assertEquals(6,al.get(1).size());
		
		loc.setUp();
		loc.testVisit();
		
		
	}
	public final void testGetAl() {
		assertNotNull(mm.getAl());
		assertEquals(2,mm.getAl().size());
	}
	




}
