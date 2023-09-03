package common;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Double.parseDouble;
import static java.util.Optional.*;

@Slf4j
public class Calculator {

    private final Double firstInput;

    private final Double secondInput;

    private String operator;

    private static final String ERROR_MESSAGE_TEXT = "Invalid operator has been found: %s";

    private final CalculationCompletedListener listener = new CalculationListener();

    public Calculator(String input1, String input2, String operator) {
        var optInput1 = ofNullable(input1);
        var optInput2 = ofNullable(input2);
        var optOperator = ofNullable(operator);

        if (optInput1.isEmpty() || optInput2.isEmpty() || optOperator.isEmpty()
                || optInput1.get().isEmpty() || optInput2.get().isEmpty() || optOperator.get().isEmpty()) {
            log.error("Empty strings or null values are not allowed");
            throw new IllegalArgumentException("Inputs and operator must be non-null and non-empty");
        }

        this.firstInput = parseDouble(optInput1.get());
        log.info(String.format("Input for first number: %s", optInput1));
        this.secondInput = parseDouble(optInput2.get());
        log.info(String.format("Input for second number: %s", optInput2));
        this.operator = optOperator.get();
        log.info(String.format("Input for operator: %s", optOperator));
    }


    public Double calculate() throws DivisionByZeroException {
        Double result;

        if (!isValidOperator()) {
            log.error(String.format(ERROR_MESSAGE_TEXT, operator));
            throw new IllegalArgumentException("Invalid operator");
        }

        switch (operator) {
            case "+" -> result = firstInput + secondInput;
            case "-" -> result = firstInput - secondInput;
            case "*" -> result = firstInput * secondInput;
            case "/" -> {
                if (secondInput.equals(0.0)) {
                    log.error(String.format("Invalid number has been found: %s cannot be zero", secondInput));
                    throw new DivisionByZeroException("Division by zero is forbidden");
                }
                result = firstInput / secondInput;
            }
            default -> {
                log.error(String.format(ERROR_MESSAGE_TEXT, operator));
                throw new ArithmeticException(ERROR_MESSAGE_TEXT);
            }
        }

        listener.onCalculationComplete(this, result);
        return result;
    }

    private boolean isValidOperator() {
        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {
            log.info(String.format("Entered operator is: %s", operator));
            return true;
        } else if (operator.contains("+") || operator.contains("-") || operator.contains("*") || operator.contains("/")) {
            log.info(String.format("Operator is incorrect, but readable: %s", operator));
            operatorStringCorrector(operator);
            return true;
        } else {
            log.error(String.format(ERROR_MESSAGE_TEXT, operator));
            return false;
        }
    }

    private void operatorStringCorrector(String string) {

        //Example of string immutability
        String trimmedString = string.trim();

        log.info(String.format("Old string still exist: %s", string));
        log.info(String.format("New string: %s", trimmedString));

        char[] charArray = trimmedString.toCharArray();

        for (char c : charArray) {
            if (c == '*' || c == '/' || c == '+' || c == '-') {
                this.operator = String.valueOf(c);
            }
        }
    }

    // Не очень придумал, что делать с этими методами

    public String getErrorMessage() {
        return "";
    }

    public boolean isSuccessful() {
        return false;
    }

    public void setCalculationCompletedListener(CalculationCompletedListener listener) {
    }


    @Override
    public String toString() {
        return "current inputs {" +
                "firstInput=" + firstInput +
                ", secondInput=" + secondInput +
                ", operator='" + operator + '\'' +
                '}';
    }
}

