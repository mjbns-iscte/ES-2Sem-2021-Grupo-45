package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Metricas_MetodosTest {
	static Metricas_Metodos mm;
	static File file;
	private LOC_methodTest loc = new LOC_methodTest();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mm = new Metricas_Metodos(2);
        file = new File("C://jasml//src//com//jasml//compiler//ParsingException.java");
        mm.analyze(file);
        
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void testMetricas_Metodos() {
		
	}

	@Test
	final void testAnalyze() throws Exception {
		ArrayList<ArrayList<String>> al =mm.getAl();
		assertNotNull(al);
		assertEquals(2,al.size());
		
		assertNotNull(al.get(0));
		assertEquals(6,al.get(0).size());
		
		assertNotNull(al.get(1));
		assertEquals(6,al.get(1).size());
		
		loc.setUpBeforeClass();
		loc.testVisitMethodDeclarationArrayListOfArrayListOfString();
	}

	@Test
	final void testGetAl() {
		assertNotNull(mm.getAl());
		assertEquals(2,mm.getAl().size());
	}

}
