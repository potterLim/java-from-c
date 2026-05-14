package com.example.bignumber;

public final class Main {
    private static int sPassedCount;
    private static int sFailedCount;
    private static String sFailureMessage;

    private Main() {
    }

    public static void main(String[] args) {
        verifyAdditionCases();
        verifySubtractionCases();
        verifyInvalidInputs();

        printSummary();
    }

    private static void verifyAdditionCases() {
        verifyScenario(
                "addition handles signs, normalization, and large values",
                checkEquals("positive values", "579", BigNumberCalculator.addOrNull("123", "456"))
                        && checkEquals("negative plus positive", "333", BigNumberCalculator.addOrNull("-123", "456"))
                        && checkEquals("positive plus negative", "-333", BigNumberCalculator.addOrNull("123", "-456"))
                        && checkEquals("two negative values", "-579", BigNumberCalculator.addOrNull("-123", "-456"))
                        && checkEquals("leading zeros", "123", BigNumberCalculator.addOrNull("0000123", "000"))
                        && checkEquals("negative zero", "0", BigNumberCalculator.addOrNull("-0000", "0"))
                        && checkEquals("opposite normalized values", "0", BigNumberCalculator.addOrNull("-0007", "0007"))
                        && checkEquals("large carry", "1000000000000000000000", BigNumberCalculator.addOrNull("999999999999999999999", "1"))
        );
    }

    private static void verifySubtractionCases() {
        verifyScenario(
                "subtraction handles order, signs, and large values",
                checkEquals("smaller right value", "333", BigNumberCalculator.subtractOrNull("456", "123"))
                        && checkEquals("larger right value", "-333", BigNumberCalculator.subtractOrNull("123", "456"))
                        && checkEquals("negative values", "-333", BigNumberCalculator.subtractOrNull("-456", "-123"))
                        && checkEquals("negative right value", "333", BigNumberCalculator.subtractOrNull("-123", "-456"))
                        && checkEquals("same normalized values", "0", BigNumberCalculator.subtractOrNull("-0007", "-0007"))
                        && checkEquals("large borrow", "999999999999999999999", BigNumberCalculator.subtractOrNull("1000000000000000000000", "1"))
        );
    }

    private static void verifyInvalidInputs() {
        verifyScenario(
                "invalid integer strings return null",
                checkNull("null left value", BigNumberCalculator.addOrNull(null, "1"))
                        && checkNull("null right value", BigNumberCalculator.addOrNull("1", null))
                        && checkNull("empty value", BigNumberCalculator.addOrNull("", "1"))
                        && checkNull("sign-only value", BigNumberCalculator.addOrNull("-", "1"))
                        && checkNull("plus sign", BigNumberCalculator.addOrNull("+12", "1"))
                        && checkNull("decimal value", BigNumberCalculator.addOrNull("12.3", "1"))
                        && checkNull("binary-style value", BigNumberCalculator.subtractOrNull("0b1010", "1"))
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
