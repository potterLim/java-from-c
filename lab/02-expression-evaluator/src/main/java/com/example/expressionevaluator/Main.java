package com.example.expressionevaluator;

public final class Main {
    private static int sPassedCount;
    private static int sFailedCount;
    private static String sFailureMessage;

    private Main() {
    }

    public static void main(String[] args) {
        verifyValidExpressions();
        verifyInvalidExpressions();

        printSummary();
    }

    private static void verifyValidExpressions() {
        verifyScenario(
                "valid expressions calculate expected results",
                checkEquals("single number", "7", ExpressionEvaluator.evaluateOrNull("7"))
                        && checkEquals("negative number", "-7", ExpressionEvaluator.evaluateOrNull("-7"))
                        && checkEquals("minimum int operand", "-2147483648", ExpressionEvaluator.evaluateOrNull("-2147483648"))
                        && checkEquals("minimum int operand in expression", "-2147483648", ExpressionEvaluator.evaluateOrNull("-2147483648 + 0"))
                        && checkEquals("addition with whitespace", "30", ExpressionEvaluator.evaluateOrNull("  10  +   20 "))
                        && checkEquals("operator precedence", "14", ExpressionEvaluator.evaluateOrNull("2 + 3 * 4"))
                        && checkEquals("left-to-right operators", "12", ExpressionEvaluator.evaluateOrNull("20 / 3 * 2"))
                        && checkEquals("unary minus after operator", "13", ExpressionEvaluator.evaluateOrNull("10 - -3"))
                        && checkEquals("mixed operators", "21", ExpressionEvaluator.evaluateOrNull("2 + 3 * 7 - 2"))
        );
    }

    private static void verifyInvalidExpressions() {
        verifyScenario(
                "invalid expressions return null",
                checkNull("null expression", ExpressionEvaluator.evaluateOrNull(null))
                        && checkNull("empty expression", ExpressionEvaluator.evaluateOrNull(""))
                        && checkNull("blank expression", ExpressionEvaluator.evaluateOrNull("   "))
                        && checkNull("unary plus", ExpressionEvaluator.evaluateOrNull("+7"))
                        && checkNull("space after unary minus", ExpressionEvaluator.evaluateOrNull("- 7"))
                        && checkNull("trailing operator", ExpressionEvaluator.evaluateOrNull("1 +"))
                        && checkNull("space inside number", ExpressionEvaluator.evaluateOrNull("1 2 + 3"))
                        && checkNull("repeated operator", ExpressionEvaluator.evaluateOrNull("2 * * 3"))
                        && checkNull("leading zero", ExpressionEvaluator.evaluateOrNull("01 + 2"))
                        && checkNull("division by zero", ExpressionEvaluator.evaluateOrNull("4 / 0"))
        );
    }

    private static boolean checkEquals(String name, String expected, String actual) {
        if (expected.equals(actual)) {
            return true;
        }

        sFailureMessage = name + ": expected=" + expected + ", actual=" + actual;
        return false;
    }

    private static boolean checkNull(String name, String actual) {
        if (actual == null) {
            return true;
        }

        sFailureMessage = name + ": expected=null, actual=" + actual;
        return false;
    }

    private static void verifyScenario(String name, boolean isPassed) {
        if (isPassed) {
            pass(name);
            return;
        }

        fail(name, sFailureMessage);
        sFailureMessage = null;
    }

    private static void pass(String name) {
        ++sPassedCount;
        System.out.println("[PASS] " + name);
    }

    private static void fail(String name, String message) {
        ++sFailedCount;
        System.out.println("[FAIL] " + name + " (" + message + ")");
    }

    private static void printSummary() {
        System.out.println();
        System.out.println("Passed: " + sPassedCount);
        System.out.println("Failed: " + sFailedCount);
    }
}
