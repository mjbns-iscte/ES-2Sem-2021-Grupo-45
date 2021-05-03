package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConditionTest {
	static Condition c;
	static Condition c1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		c = new Condition("NOM_Class",">",100);
		c1 = new Condition("NOM_Class:<:100");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	
	@Test
	void testEvaluate() {
		assertTrue(c.evaluate(150));
		assertTrue(c1.evaluate(50));
	}

	@Test
	void testToString() {
		assertEquals("NOM_Class:>:100",c.toString());
	}

	@Test
	void testGetMetric() {
		assertEquals("NOM_Class",c.getMetric());
	} 

	@Test
	void testGetSignal() {
		assertEquals(">",c.getSignal());
	}

	@Test
	void testGetLimit() {
		assertEquals("100",c.getLimit());
	}

}
