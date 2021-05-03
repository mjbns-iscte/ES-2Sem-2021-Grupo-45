package Grupo45.Projeto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RuleTest {
	static Rule r;
	static Rule r1;
	static ArrayList<Condition> c;
	static ArrayList<String> o;
	static ArrayList<Integer> i;
	static Condition c2;
	static Condition c1;
	static String s;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		c= new ArrayList<Condition>();
		o= new ArrayList<String>();
		i = new ArrayList<Integer>();
		c1 = new Condition("NOM_Class:<:100");
		c2 = new Condition("LOC_Class:<:100");
		c.add(c1);
		c.add(c2);
		s = new String("AND");
		
		o.add(s);
		
		r = new Rule("is_God_Class", c, o);
		r1= new Rule("is_Long_Method LOC_method:>:10 OR CYCLO_method:>:5");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testRuleEvaluate() {
		i.add(11);
		i.add(12);
		assertTrue(r.ruleEvaluate(i));
	}

	@Test
	void testGetRuleName() {
		assertEquals("is_God_Class", r.getRuleName());
	}

	@Test
	void testToString() {
		assertEquals("is_Long_Method LOC_method:>:10 OR CYCLO_method:>:5", r1.toString());
		assertEquals("is_God_Class: :NOM_Class:<:100: :AND: :LOC_Class:<:100", r.toString());
	}

	@Test
	void testGetNumberOfConditions() {
		assertEquals(2, r.getNumberOfConditions());
	}

	@Test
	void testGetCondition() {
		assertEquals(c1, r.getCondition(0));
		assertEquals(c2, r.getCondition(1));
	}

	@Test
	void testIsClassRule() {
		assertFalse(r.isClassRule());
	}

	@Test
	void testGetOperator() {
		assertEquals(s, r.getOperator(0));
	}

}
