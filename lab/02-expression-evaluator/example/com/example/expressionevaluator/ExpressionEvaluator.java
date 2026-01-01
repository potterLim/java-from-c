package com.example.expressionevaluator;

public class ExpressionEvaluator {
    private static final int INVALID_OPERAND = Integer.MIN_VALUE;

    public static String evaluateOrNull(String expression) {
        assert (expression != null);

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
                    index++;
                }

                int operandValue = parseOperandOrNull(expression, expressionLength, index);
                if (operandValue == INVALID_OPERAND) {
                    return null;
                }

                if (sign == -1 && operandValue == 0) {
                    return null;
                }

                int nextIndex = parseOperandNextIndex(expression, expressionLength, index);
                if (nextIndex < 0) {
                    return null;
                }

                index = nextIndex;

                if (sign == -1) {
                    operandValue = -operandValue;
                }

                operands[operandCount] = operandValue;
                operandCount++;

                isExpectingOperand = false;
            } else {
                if (!isBinaryOperator(current)) {
                    return null;
                }

                operators[operatorCount] = current;
                operatorCount++;

                index++;
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
        reducedOperandCount++;

        for (int i = 0; i < operatorCount; i++) {
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
                reducedOperatorCount++;

                reducedOperands[reducedOperandCount] = rightOperand;
                reducedOperandCount++;
            }
        }

        int result = reducedOperands[0];

        for (int i = 0; i < reducedOperatorCount; i++) {
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

    private static int parseOperandOrNull(String expression, int length, int startIndex) {
        int index = startIndex;

        if (index >= length) {
            return INVALID_OPERAND;
        }

        char firstChar = expression.charAt(index);
        if (!isDigit(firstChar)) {
            return INVALID_OPERAND;
        }

        int digitCount = 0;
        int value = 0;
        boolean hasLeadingZero = false;

        while (true) {
            if (index >= length) {
                break;
            }

            char currentChar = expression.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (!isDigit(currentChar)) {
                break;
            }

            if (digitCount == 0) {
                if (currentChar == '0') {
                    hasLeadingZero = true;
                }
            } else {
                if (hasLeadingZero) {
                    return INVALID_OPERAND;
                }
            }

            value = (value * 10) + (currentChar - '0');
            digitCount++;
            index++;
        }

        if (digitCount == 0) {
            return INVALID_OPERAND;
        }

        return value;
    }

    private static int parseOperandNextIndex(String expression, int length, int startIndex) {
        int index = startIndex;

        if (index >= length) {
            return -1;
        }

        char firstChar = expression.charAt(index);
        if (!isDigit(firstChar)) {
            return -1;
        }

        int digitCount = 0;
        boolean hasLeadingZero = false;

        while (true) {
            if (index >= length) {
                break;
            }

            char currentChar = expression.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (!isDigit(currentChar)) {
                break;
            }

            if (digitCount == 0) {
                if (currentChar == '0') {
                    hasLeadingZero = true;
                }
            } else {
                if (hasLeadingZero) {
                    return -1;
                }
            }

            digitCount++;
            index++;
        }

        if (digitCount == 0) {
            return -1;
        }

        return index;
    }

    private static int skipWhitespace(String expression, int startIndex) {
        int index = startIndex;

        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }

        return index;
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isBinaryOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
