package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Metricas_MetodosTest{
	static Metricas_Metodos mm;
	static Metricas_Metodos mm1;
	static Metricas_Metodos mm2;
	static File file;
	static File path;
	static File file1;
	static HashMap<String, Integer> map;
	static int loc_class;
	static int nom_class;
	static int wmc_class;
	static Excel e;
	static ArrayList <File> files;
	static ArrayList<Condition> cs = new ArrayList<>();
	static ArrayList<String> ops = new ArrayList<>();
	static Rule r =new Rule("is_Long", cs, ops);
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mm = new Metricas_Metodos();
		mm1= new Metricas_Metodos();
		mm2= new Metricas_Metodos();
      //  file = new File("C:\\Users\\jtfgb\\Downloads\\ES_Projeto Teste\\src\\com\\jasml\\compiler\\ParsingException.java");
       // file1= new File("C:\\Users\\jtfgb\\Downloads\\ES_Projeto Teste\\src");
        //path= new File("C:\\Users\\jtfgb\\Downloads\\ES_Projeto Teste\\src");
		file = new File("C:\\Users\\migue\\Documents\\Projeto_ES\\src\\com\\jasml\\compiler\\SourceCodeParser.java");
		file1 = new File("C:\\Users\\migue\\Documents\\Projeto_ES\\src");
		path = new File("C:\\Users\\migue\\Documents\\Projeto_ES\\src");
        mm.analyze(file);
        mm.analyzeCyclometicComplexity(file);
        e = new Excel();
        e.setupExcel("gg45");
        files = mm.search(file1);
        
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void testMetricas_Metodos() {
		
	}

	@Test
	final void testAnalyze() throws Exception {
		ArrayList<ArrayList<String>> al =mm.getMainArray();
		
		int loc_class = mm.getLoc_class();
		int nom_class = mm.getNom_class();

		assertNotNull(al);
		assertNotNull(loc_class);
		assertNotNull(nom_class);
		
		
		assertNotNull(al.get(0));
		assertEquals(32,al.get(0).size());
		
		assertNotNull(al.get(1));
		assertEquals(32,al.get(1).size());
		
	}
	
	@Test
	final void testAnalyzeCyclometicComplexity () throws Exception {
		ArrayList<ArrayList<String>> al =mm.getMainArray();
		HashMap<String, Integer> h = mm.getMap();
		int wmc_class = mm.getWmc();
		
		assertNotNull(al.get(0));
		assertNotNull(h);
		assertNotNull(wmc_class);
		
		assertEquals(32, h.size());
		assertEquals(303, wmc_class);
	}
	

	@Test
	final void testSearch() {
		assertNotNull(path.listFiles());
		assertEquals(43,files.size());
		
	}
	

	@Test
	final void testGetMainArray() {
		assertNotNull(mm.getMainArray());
		assertEquals(2,mm.getMainArray().size());
	}
	
	@Test
	final void testGetNomClass() {
		assertNotNull(mm.getNom_class());
		assertEquals(32,mm.getNom_class());
	}
	
	@Test
	final void testGetLocClass() {
		assertNotNull(mm.getLoc_class());
		assertEquals(1371,mm.getLoc_class());
	}
	
	@Test
	final void getWmc() {
		assertNotNull(mm.getWmc());
		assertEquals(303,mm.getWmc());
	}
	
	@Test
	final void testMetricsToExcel() throws IOException {
		String name = e.getG_path();
		FileInputStream is = new FileInputStream(new File(name));
		Workbook w = new XSSFWorkbook(is);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
		assertNotNull(files);
		assertEquals("MethodID",sheet.getRow(0).getCell(0).toString());
		assertEquals("1.0",sheet.getRow(1).getCell(0).toString());
		assertEquals("2.0",sheet.getRow(2).getCell(0).toString());
		assertEquals("3.0",sheet.getRow(3).getCell(0).toString());
		w.close();
	}
	
	@Test
	final void testGetPackage() throws IOException {
		mm.metricsToExcel(files, e);
		String name = e.getG_path();
		FileInputStream is = new FileInputStream(new File(name));
		Workbook w = new XSSFWorkbook(is);
		org.apache.poi.ss.usermodel.Sheet sheet = w.getSheetAt(0);
		assertNotNull(files);
		assertEquals("com.jasml.classes",sheet.getRow(1).getCell(1).toString());
		assertEquals("com.jasml.compiler",sheet.getRow(88).getCell(1).toString());
		w.close();
	}

	@Test
	final void testGetRuleArray(){
		assertNotNull(mm.getRuleArray());
	}

	@Test
	final void testAddRuleToArray(){
		cs.add(new Condition("LOC_method", ">", 50));
		cs.add(new Condition("CYCLO_method", ">", 10));
		ops.add("OR");
		ops.add("OR");
		assertEquals(0,mm.getRuleArray().size());
		mm.addRuleToArray(new Rule("is_Long_Method", cs, ops));
		assertEquals(1,mm.getRuleArray().size());
	}
	@Test
	final void testRemoveRule() {
		mm.addRuleToArray(r);
		assertEquals(1,mm.getRuleArray().size());
		mm.removeRule(r);
		assertEquals(0,mm.getRuleArray().size());
	}
	@Test
	final void testReadTextFile() throws IOException {
		assertEquals(0,mm1.getRuleArray().size());
		mm1.readTextFile();                                              
		assertEquals(true,mm1.getRuleArray().size()>0);          
		
	}
	@Test
	final void testGetRuleNamed() {
		mm1 = new Metricas_Metodos();
		mm1.addRuleToArray(new Rule("is_Long_Method", cs, ops));
		assertNotNull(mm1.getRuleNamed("is_Long_Method"));
		assertThrows(NullPointerException.class, ()->{
	        mm.getRuleNamed("a");                                             
	    });

	}
	
	@Test
	final void testCreateTestRule() {
		assertEquals(0,mm2.getRuleArray().size());
		mm2.createTestRule();
		assertEquals(1,mm2.getRuleArray().size());

	}
	
	@Test
	final void testApplyRule() throws IOException {
		LinkedHashSet<String> output = mm.applyRule(r,e);
		Iterator<String> it = output.iterator();
		assertNotNull(output);

	}
	

}
