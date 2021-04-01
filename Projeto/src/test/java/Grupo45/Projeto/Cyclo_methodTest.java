package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Cyclo_methodTest {
	static ArrayList<String> names;
	static HashMap<String, Integer> h;
	static File f;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		names = new ArrayList<String>();
		f = new File("C:\\Users\\migue\\Documents\\Projeto\\src\\com\\jasml\\compiler\\SourceCodeParser.java");
		InputStream is = new FileInputStream(f);
		h = new Cyclo_method().cyclo_method(is);

		System.out.println("Cyclo_methodTest.setUpBeforeClass() executed");

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Cyclo_methodTest.tearDownAfterClass() executed");
	}

	@Test
	void testCyclo_method() {

		assertNotNull(names);
		assertNotNull(f);
		assertNotNull(h);

		assertEquals(32, h.size());
		assertEquals(32, names.size());
		assertEquals(32, h.values().size());

		assertTrue(h.containsKey("SourceCodeParser(File)"));
		assertTrue(h.containsKey("parseField()"));
		assertTrue(h.containsKey("updateLabelLinks(Hashtable, ArrayList)"));
		assertTrue(h.containsKey("parseClassAttributes()"));
		assertTrue(h.containsKey("parseDouble(String)"));
		assertTrue(h.containsKey("parseMaxStackOrLocals(Attribute_Code)"));
	}

}
