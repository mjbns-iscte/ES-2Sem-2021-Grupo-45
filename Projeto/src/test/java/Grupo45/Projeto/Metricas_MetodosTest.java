package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Metricas_MetodosTest {
	static Metricas_Metodos mm;
	static File file;
	static File file1;
	private LOC_methodTest loc = new LOC_methodTest();
	private Cyclo_methodTest cic = new Cyclo_methodTest();
	static HashMap<String, Integer> map;
	static int loc_class;
	static int nom_class;
	static int wmc_class;
	static Excel e;
	static Excel e1;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mm = new Metricas_Metodos(2);
        file = new File("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\src\\com\\jasml\\compiler\\ParsingException.java");
        file1= new File("C:\\Users\\Amado\\Desktop\\Gosto muito de programar\\src");
        mm.analyze(file);
        mm.analyzeCyclometicComplexity(file);
        e = new Excel();
        e1 = new Excel();
        e.setupExcel("g45");
        e1 = mm.metricsToExcel(mm.search(file1), e);
        
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
		//HashMap<String, Integer> h = mm.getMap();
		int loc_class = mm.getLoc_class();
		int nom_class = mm.getNom_class();
		//int wmc_class = mm.getWmc();
		assertNotNull(al);
		assertNotNull(loc_class);
		assertNotNull(nom_class);
		
		
		assertNotNull(al.get(0));
		assertEquals(6,al.get(0).size());
		
		assertNotNull(al.get(1));
		assertEquals(6,al.get(1).size());
		
		
		//loc.testVisit();
	}
	
	@Test
	final void testAnalyzeCyclometicComplexity () throws Exception {
		ArrayList<ArrayList<String>> al =mm.getAl();
		HashMap<String, Integer> h = mm.getMap();
		int wmc_class = mm.getWmc();
		
		assertNotNull(al.get(0));
		assertNotNull(h);
		assertNotNull(wmc_class);
		
		assertEquals(6, h.size());
		assertEquals(11, wmc_class);
		
		//assertThrows(FileNotFoundException.this, () ->  )
		
		//cic.testCyclo_method();
	}
	

	@Test
	final void testGetAl() {
		assertNotNull(mm.getAl());
		assertEquals(2,mm.getAl().size());
	}
	
	@Test
	final void testGetNomClass() {
		assertNotNull(mm.getNom_class());
		assertEquals(6,mm.getNom_class());
	}
	
	@Test
	final void testGetLocClass() {
		assertNotNull(mm.getLoc_class());
		assertEquals(50,mm.getLoc_class());
	}
	
	@Test
	final void getWmc() {
		assertNotNull(mm.getWmc());
		assertEquals(11,mm.getWmc());
	}
	
	@Test
	final void testMetricsToExcel() {
		assertNotNull(e1);
		assertNotNull(mm.search(file1));
		assertEquals("MethodID",e1.getSheet().getRow(0).getCell(0).toString());
		
	}
	

}
