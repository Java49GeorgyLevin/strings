package telran.text.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import telran.text.Strings;

class StringsTest {
static HashMap<String, Double> mapVariables;
static {
	mapVariables = new HashMap<>();
	mapVariables.put("abc", 6.0);
	mapVariables.put("a1", 1000.0);
	mapVariables.put("a2", 60.0);
}
	
		
		
	
	@Test
	void javaVariableNameTest() {
		String regex = Strings.javaVariableName();
		assertTrue("a".matches(regex));
		assertTrue("$".matches(regex));
		assertTrue("$2".matches(regex));
		assertTrue("$_".matches(regex));
		assertTrue("__".matches(regex));
		assertTrue("_2".matches(regex));
		assertTrue("a_b".matches(regex));
		assertTrue("A_B".matches(regex));
		assertTrue("abc12345678900000".matches(regex));
		assertFalse("1a".matches(regex));
		assertFalse("_".matches(regex));
		assertFalse("a#".matches(regex));
		assertFalse("a b".matches(regex));
		assertFalse("a-b".matches(regex));
		assertFalse(" ab".matches(regex));
		
	}
	@Test
	void zero_300Test() {
		String regex = Strings.zero_300();
		assertTrue("0".matches(regex));
		assertTrue("9".matches(regex));
		assertTrue("299".matches(regex));
		assertTrue("300".matches(regex));
		assertTrue("99".matches(regex));
		assertFalse("01".matches(regex));
		assertFalse("00".matches(regex));
		assertFalse("1111".matches(regex));
		assertFalse("301".matches(regex));
		assertFalse("3000".matches(regex));
		assertFalse("310".matches(regex));
		assertFalse("-1".matches(regex));
		assertFalse("3 ".matches(regex));
		
		
		
	}
	@Test
	void ipV4OctetTest() {
		String regex = Strings.ipV4Octet();
		assertTrue("000".matches(regex));
		assertTrue("00".matches(regex));
		assertTrue("0".matches(regex));
		assertTrue("99".matches(regex));
		assertTrue("1".matches(regex));
		assertTrue("10".matches(regex));
		assertTrue("199".matches(regex));
		assertTrue("200".matches(regex));
		assertTrue("249".matches(regex));
		assertTrue("250".matches(regex));
		assertTrue("255".matches(regex));
		assertFalse("0000".matches(regex));
		assertFalse(" 1".matches(regex));
		assertFalse(".0".matches(regex));
		assertFalse("-1".matches(regex));
		assertFalse("256".matches(regex));
		assertFalse("1000".matches(regex));
	}
	@Test
	void IpV4Test() {
		String regex = Strings.ipV4();
		assertTrue("0.0.0.0".matches(regex));
		assertTrue("1.1.1.1".matches(regex));
		assertTrue("99.99.12.09".matches(regex));
		assertTrue("100.199.200.255".matches(regex));
		assertFalse(".1.2.3.4".matches(regex));
		assertFalse("1.2.3.4.".matches(regex));
		assertFalse(".1.&2.3.4".matches(regex));
		assertFalse("1.2.3".matches(regex));
		assertFalse("1.2.3.4.5".matches(regex));
		assertFalse("123 123 123 123".matches(regex));
	}
	@Test
	void arithmeticExpressionTest() {
		assertTrue(Strings.isArithmeticExpression(" 12 "));//12
		assertTrue(Strings.isArithmeticExpression(" 12/ abc"));//abc = 6
		assertTrue(Strings.isArithmeticExpression("12/2"));//6
		assertTrue(Strings.isArithmeticExpression(" 12*  2 / 3 + a1 "));//a1 = 1000
		assertTrue(Strings.isArithmeticExpression(" 120 / a2 + 100 - 2 * 2.5 / 500 "));//a2=60
		assertFalse(Strings.isArithmeticExpression(" 12 18"));
		assertFalse(Strings.isArithmeticExpression(" 12/3&4"));
		assertFalse(Strings.isArithmeticExpression(" 12+20-"));
		assertFalse(Strings.isArithmeticExpression(" 12/ 18 + 100 10"));
		assertFalse(Strings.isArithmeticExpression(" 12/ 18 + 100 + 1v"));
		
	}
	@Test
	void computeExpressionTest() {
		assertEquals(12, Strings.computeExpression(" 12 ", mapVariables));
		assertEquals(2, Strings.computeExpression(" 12/ 6", mapVariables));
		assertEquals(6, Strings.computeExpression("12/2", mapVariables));
		assertEquals(1008, Strings.computeExpression(" 12*  2 / 3 + 1000 ", mapVariables));
		assertEquals(0.5, Strings.computeExpression(" 120 / a2 + 100 - 2 * 2.5 / 500 ", mapVariables));
		assertThrowsExactly(IllegalArgumentException.class,
				() -> Strings.computeExpression(" 12/ 18 + 100 10", mapVariables));
		assertThrowsExactly(NoSuchElementException.class, () ->
		Strings.computeExpression(" 12/ 18 + 100-a15", mapVariables));
	}

}
