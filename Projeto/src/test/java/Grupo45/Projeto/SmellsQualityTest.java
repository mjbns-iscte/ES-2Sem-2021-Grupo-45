package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SmellsQualityTest {
	static SmellsQuality sq;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		sq = new SmellsQuality();
		sq.readCodeSmellsExcel();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	final void test() {
		assertNotNull(sq.getIslong());
		assertEquals(false,sq.getIslong().get("GrammerException(int,String)"));
		assertNotNull(sq.getIsgod());
		assertEquals(false,sq.getIsgod().get("GrammerException"));

		
	}

}
