package telran.text;

import java.util.HashMap;
import java.util.function.BinaryOperator;

public class Strings {
	static HashMap<String, BinaryOperator<Integer>> mapOperations;
	static {
		mapOperations = new HashMap<>();
		mapOperations.put("-", (a, b) -> a - b);
		mapOperations.put("+", (a, b) -> a + b);
		mapOperations.put("*", (a, b) -> a * b);
		mapOperations.put("/", (a, b) -> a / b);
	}
public static String javaVariableName() {
	
	return "([a-zA-Z$][\\w$]*|_[\\w$]+)";
}
public static String zero_300() {
	
	return "[1-9]\\d?|[1-2]\\d\\d|300|0";
}
public static String ipV4Octet() {
	//TODO
	//positive number from 0 to 255 and leading zeros are allowed
	return  "([01]?\\d\\d?|2([0-4]\\d|5[0-5]))";
}
public static String ipV4() {
	String octetRegex = ipV4Octet();
	return String.format("(%s\\.){3}%1$s",octetRegex);
}
public static String arithmeticExpression() {
	String operandRE = operand();
	String operatorRE = operator();
	return String.format("%1$s(%2$s%1$s)*",operandRE, operatorRE );
}
public static String operator() {
	return "\\s*([-+*/])\\s*";
}
public static String operand() {
	//assumption: not unary operators
	return "(\\d+)";
}
public static boolean isArithmeticExpression(String expression) {
	expression = expression.trim();
	return expression.matches(arithmeticExpression());
}
public static int computeExpression(String expression) {
	
	if (!isArithmeticExpression(expression)) {
		throw new IllegalArgumentException("Wrong arithmetic expression");
	}
	expression = expression.replaceAll("\\s+", "");
	String[] operands = expression.split(operator());
	String [] operators = expression.split(operand());
	int res = Integer.parseInt(operands[0]);
	for(int i = 1; i < operands.length; i++) {
		int operand = Integer.parseInt(operands[i]);
		res = mapOperations.get(operators[i]).apply(res, operand);
	}
	
	return res;
}
//Update whole code for any numbers (double)
//Update code taking into consideration possible variable names
public static double computeExpression(String expression,
		HashMap<String, Double> mapVariables) {
	//TODO
	return 0;
}

}
