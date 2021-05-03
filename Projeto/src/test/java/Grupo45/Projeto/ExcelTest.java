package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ExcelTest {
	static Excel e;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		e = new Excel();
		e.setupExcel("gg45");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void testSetupExcel() throws IOException {

		assertNotNull(e);

		assertEquals("MethodID", e.getSheet().getRow(0).getCell(0).toString());
		assertEquals("package", e.getSheet().getRow(0).getCell(1).toString());
		assertEquals("class", e.getSheet().getRow(0).getCell(2).toString());
		assertEquals("method", e.getSheet().getRow(0).getCell(3).toString());
		assertEquals("NOM_class", e.getSheet().getRow(0).getCell(4).toString());
		assertEquals("LOC_class", e.getSheet().getRow(0).getCell(5).toString());
		assertEquals("WMC_class", e.getSheet().getRow(0).getCell(6).toString());
		// assertEquals("is_God_Class",e.getSheet().getRow(0).getCell(7).toString());
		assertEquals("LOC_method", e.getSheet().getRow(0).getCell(7).toString());
		assertEquals("CYCLO_method", e.getSheet().getRow(0).getCell(8).toString());
		// assertEquals("is_Long_Method",e.getSheet().getRow(0).getCell(10).toString());

	}

	@Test
	final void testGetSheet() {
		assertNotNull(e.getSheet());
		assertEquals("METRICAS", e.getSheet().getSheetName());

	}

	@Test
	final void testGetG_Path() {
		assertEquals("gg45", e.getG_path());
	}
	
	@Test
	final void testGetHeaderSize() {
		assertEquals(9, e.getHeaderSize());
	}
	
	@Test
	final void testGetMetricColumn() {
		assertEquals(0, e.getMetricColumn("MethodID"));
		assertEquals(0, e.getMetricColumn("NAOEXISTE"));
	}

}
