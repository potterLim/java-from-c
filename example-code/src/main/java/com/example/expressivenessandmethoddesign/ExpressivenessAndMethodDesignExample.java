package com.example.expressivenessandmethoddesign;

public final class ExpressivenessAndMethodDesignExample {
    private ExpressivenessAndMethodDesignExample() {
    }

    public static void main(String[] args) {
        printHeader("Expressiveness and Method Design");

        demoOverloading();
        demoStringBuilder();
    }

    private static void demoOverloading() {
        printSectionTitle("Method overloading: same name, different parameter lists");

        log("System started");
        log("AUTH", "Login succeeded");
        log("PAYMENT", "Charge accepted", 202);

        int total = add(3, 4);
        double average = add(3.0, 4.0) / 2.0;

        System.out.println("add(int, int) -> " + total);
        System.out.println("add(double, double) / 2 -> " + average);
    }

    private static void log(String message) {
        System.out.println("[INFO] " + message);
    }

    private static void log(String category, String message) {
        System.out.println("[" + category + "] " + message);
    }

    private static void log(String category, String message, int statusCode) {
        System.out.println("[" + category + "-" + statusCode + "] " + message);
    }

    private static int add(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }

    private static double add(double leftValue, double rightValue) {
        return leftValue + rightValue;
    }

    private static void demoStringBuilder() {
        printSectionTitle("StringBuilder: build strings efficiently");

        String[] items = new String[]{ "apple", "banana", "carrot", "donut" };

        String csv = buildCsv(items);
        System.out.println("CSV: " + csv);

        System.out.println();
        System.out.println(buildNumberReport(6));
    }

    private static String buildCsv(String[] items) {
        // Join items into a single line without repeated String concatenation.
        StringBuilder csvBuilder = new StringBuilder(32);

        for (int i = 0; i < items.length; ++i) {
            if (i > 0) {
                csvBuilder.append(',');
            }

            csvBuilder.append(items[i]);
        }

        return csvBuilder.toString();
    }

    private static String buildNumberReport(int maxNumber) {
        // Build a multi-line report using append chains.
        StringBuilder reportBuilder = new StringBuilder(64);

        reportBuilder.append("Number Report").append('\n');
        reportBuilder.append("=============").append('\n');

        for (int i = 1; i <= maxNumber; ++i) {
            reportBuilder.append("i=").append(i);
            reportBuilder.append(", square=").append(i * i);
            reportBuilder.append('\n');
        }

        reportBuilder.append("End.").append('\n');

        return reportBuilder.toString();
    }

    private static void printHeader(String title) {
        System.out.println(title + " Example");
        System.out.println("========================================");
    }

    private static void printSectionTitle(String title) {
        System.out.println();
        System.out.println("[" + title + "]");
        System.out.println("----------------------------------------");
    }
}
