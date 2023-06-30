package telran.text;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public class Strings {
	static HashMap<String, BinaryOperator<Double>> mapOperations;
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
public static String arithmeticExpressionV() {
	String operandRE = operandV();
	String operatorRE = operator();
	return String.format("%1$s(%2$s%1$s)*",operandRE, operatorRE );
}
public static String operator() {
	return "\\s*([-+*/])\\s*";
}
public static String operand() {
	//assumption: not unary operators
	return "(\\d+[.]?\\d*|\\d*[.]\\d+)";
}
public static String operandV() {
	return "("+operand()+"|"+javaVariableName()+")";
}
public static boolean isArithmeticExpression(String expression) {
	expression = expression.trim();
	return expression.matches(arithmeticExpression());
}
public static boolean isArithmeticExpressionV(String expression) {
	expression = expression.trim();
	return expression.matches(arithmeticExpressionV());
}
public static double computeExpression(String expression) {
	
	if (!isArithmeticExpression(expression)) {
		throw new IllegalArgumentException("Wrong arithmetic expression");
	}
	expression = expression.replaceAll("\\s+", "");
	String[] operands = expression.split(operator());
	String [] operators = expression.split(operand());
	double res = stringToDouble(operands[0]);
	for(int i = 1; i < operands.length; i++) {
		double operand = stringToDouble(operands[i]);
		res = mapOperations.get(operators[i]).apply(res, operand);
	}
	
	return res;
}

private static double stringToDouble(String operand) {
	String [] intAndDec = operand.split("[.]");
	int intOperand = Integer.parseInt(intAndDec[0]);
	double decOperand = 0.0;
	if(intAndDec.length > 1) {
		String decimal = intAndDec[1];
		int decimalPow = (int) Math.pow(10, decimal.length());
		decOperand = Integer.parseInt(intAndDec[1]);
		decOperand /= decimalPow;
		}
	return intOperand + decOperand;
}

//Update whole code for any numbers (double)
//Update code taking into consideration possible variable names
public static double computeExpression(String expression,
		HashMap<String, Double> mapVariables) {
	if(!isArithmeticExpressionV(expression)) {
		throw new IllegalArgumentException();	
	}
	expression = expression.replaceAll("\\s+", "");
	String[] operands = expression.split(operator());
	String[] operators = expression.split(operandV());
	double res = varOrDouble(operands[0], mapVariables);
	for(int i = 1;i < operands.length;i++) {
		double operand = varOrDouble(operands[i], mapVariables);
		res = mapOperations.get(operators[i]).apply(res, operand);
	}
	return res;
}
private static double varOrDouble(String operand, HashMap<String, Double> mapVariables) {
	Double res = mapVariables.get(operand);
	if(res == null) {
		try {
//			res = Double.parseDouble(operand);
			res = stringToDouble(operand);
		} catch(Exception e) {
			throw new NoSuchElementException();
		}		
	}	
	return res;
}

}
