package com.example.expressionevaluator;

public final class ExpressionEvaluator {
    private ExpressionEvaluator() {
    }

    public static String evaluateOrNull(String expressionOrNull) {
        if (expressionOrNull == null) {
            return null;
        }

        String expression = expressionOrNull;

        int expressionLength = expression.length();

        int[] operands = new int[expressionLength];
        char[] operators = new char[expressionLength];

        int operandCount = 0;
        int operatorCount = 0;

        int index = 0;
        boolean isExpectingOperand = true;

        while (true) {
            index = skipWhitespace(expression, index);

            if (index >= expressionLength) {
                break;
            }

            char current = expression.charAt(index);

            if (isExpectingOperand) {
                if (current == '+') {
                    return null;
                }

                int sign = 1;

                if (current == '-') {
                    if (index + 1 >= expressionLength) {
                        return null;
                    }

                    char nextChar = expression.charAt(index + 1);
                    if (!isDigit(nextChar)) {
                        return null;
                    }

                    sign = -1;
                    ++index;
                }

                int digitCount = 0;
                long operandAbsValue = 0;
                boolean hasLeadingZero = false;

                while (true) {
                    if (index >= expressionLength) {
                        break;
                    }

                    current = expression.charAt(index);

                    if (!isDigit(current)) {
                        break;
                    }

                    if (digitCount == 0) {
                        if (current == '0') {
                            hasLeadingZero = true;
                        }
                    } else {
                        if (hasLeadingZero) {
                            return null;
                        }
                    }

                    operandAbsValue = (operandAbsValue * 10) + (current - '0');
                    ++digitCount;
                    ++index;
                }

                if (digitCount == 0) {
                    return null;
                }

                if (sign == -1 && operandAbsValue == 0) {
                    return null;
                }

                int operandValue;
                if (sign == -1) {
                    operandValue = (int) -operandAbsValue;
                } else {
                    operandValue = (int) operandAbsValue;
                }

                operands[operandCount] = operandValue;
                ++operandCount;

                isExpectingOperand = false;
            } else {
                if (!isBinaryOperator(current)) {
                    return null;
                }

                operators[operatorCount] = current;
                ++operatorCount;

                ++index;
                isExpectingOperand = true;
            }
        }

        if (operandCount == 0 || isExpectingOperand) {
            return null;
        }

        if (operatorCount != operandCount - 1) {
            return null;
        }

        int[] reducedOperands = new int[operandCount];
        char[] reducedOperators = new char[operatorCount];

        int reducedOperandCount = 0;
        int reducedOperatorCount = 0;

        reducedOperands[reducedOperandCount] = operands[0];
        ++reducedOperandCount;

        for (int i = 0; i < operatorCount; ++i) {
            char operator = operators[i];
            int rightOperand = operands[i + 1];

            if (operator == '*' || operator == '/') {
                int leftOperand = reducedOperands[reducedOperandCount - 1];

                if (operator == '/' && rightOperand == 0) {
                    return null;
                }

                int calculationResult;

                if (operator == '*') {
                    calculationResult = leftOperand * rightOperand;
                } else {
                    calculationResult = leftOperand / rightOperand;
                }

                reducedOperands[reducedOperandCount - 1] = calculationResult;
            } else {
                reducedOperators[reducedOperatorCount] = operator;
                ++reducedOperatorCount;

                reducedOperands[reducedOperandCount] = rightOperand;
                ++reducedOperandCount;
            }
        }

        int result = reducedOperands[0];

        for (int i = 0; i < reducedOperatorCount; ++i) {
            char operator = reducedOperators[i];
            int rightOperand = reducedOperands[i + 1];

            if (operator == '+') {
                result += rightOperand;
            } else {
                result -= rightOperand;
            }
        }

        return Integer.toString(result);
    }

    private static int skipWhitespace(String expression, int startIndex) {
        int index = startIndex;

        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }

        return index;
    }

    private static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }

    private static boolean isBinaryOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/';
    }
}
