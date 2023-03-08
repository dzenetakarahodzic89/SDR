package ba.com.zira.sdr.core.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MathExpressionEvaluator {

    private Map<String, Integer> operators;
    private static final Logger LOGGER = LoggerFactory.getLogger(MathExpressionEvaluator.class);

    public MathExpressionEvaluator() {
        operators = new HashMap<>();
        operators.put("-", 0);
        operators.put("+", 0);
        operators.put("/", 1);
        operators.put("*", 1);
        operators.put("^", 2);
    }

    public double eval(String expression) throws IllegalArgumentException, NumberFormatException, ArithmeticException {
        if (expression == null || expression.trim().length() == 0) {
            throw new IllegalArgumentException("Empty expression or null");
        }
        expression = expression.replaceAll("\\s+", "");
        expression = expression.replace("(-", "(0-");
        if (expression.startsWith("-")) {
            expression = "0" + expression;
        }
        Pattern pattern = Pattern.compile("(((\\d{0,8}[.])?\\d{1,8})|([\\+\\-\\*\\/\\(\\)\\^]))");

        Matcher matcher = pattern.matcher(expression);

        int counter = 0;
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.start() != counter) {
                throw new IllegalArgumentException(
                        "Invalid Expression:" + expression + ". Error between " + counter + " end " + matcher.start());
            }
            tokens.add(matcher.group().trim());
            counter += tokens.get(tokens.size() - 1).length();
        }
        if (counter != expression.length()) {
            throw new IllegalArgumentException("Invalid end of expression");
        }

        Deque<String> stack = new ArrayDeque<>();
        List<String> output = new ArrayList<>();

        for (String token : tokens) {
            if (operators.containsKey(token)) {
                while (!stack.isEmpty() && operators.containsKey(stack.peek())
                        && ((operators.get(token) <= operators.get(stack.peek()) && !token.equals("^"))
                                || (operators.get(token) < operators.get(stack.peek()) && token.equals("^")))) {
                    output.add(stack.pop());
                }
                stack.push(token);

            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty()) {
                    if (!stack.peek().equals("(")) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        Deque<Double> doubles = new ArrayDeque<>();
        for (String token : output) {
            if (!operators.containsKey(token) && token.matches("(\\d*[.])?\\d+")) {
                try {
                    doubles.push(Double.parseDouble(token));
                } catch (NumberFormatException n) {
                    LOGGER.debug(n.getMessage());
                    throw new NumberFormatException();
                }
            } else {
                if (doubles.size() > 1) {
                    double op1 = doubles.pop();
                    double op2 = doubles.pop();
                    switch (token) {
                    case "+":
                        doubles.push(op2 + op1);
                        break;
                    case "-":
                        doubles.push(op2 - op1);
                        break;
                    case "*":
                        doubles.push(op2 * op1);
                        break;
                    case "/":
                        if (op1 == 0) {
                            throw new ArithmeticException("Division by 0");
                        }
                        doubles.push(Math.floor(op2 / op1));
                        break;
                    case "^":
                        doubles.push(Math.pow(op2, op1));
                        break;
                    default:
                        throw new IllegalArgumentException(token + " is not an operator or is not handled");
                    }
                }
            }
        }
        if (doubles.isEmpty() || doubles.size() > 1) {
            throw new IllegalArgumentException("Invalid expression, could not find a result. An operator seems to be absent");
        }
        return doubles.peek();
    }
}
