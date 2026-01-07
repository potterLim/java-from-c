package com.example.expressiveness;

import java.lang.StringBuilder;

public final class ExpressivenessExample {
    public static void main(String[] args) {
        printHeader("Expressiveness");

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

        System.out.println("Add(int, int) -> " + total);
        System.out.println("Add(double, double) / 2 -> " + average);
    }

    private static void log(String message) {
        System.out.println("[INFO] " + message);
    }

    private static void log(String category, String message) {
        System.out.println("[" + category + "] " + message);
    }

    private static void log(String category, String message, int code) {
        System.out.println("[" + category + "-" + code + "] " + message);
    }

    private static int add(int a, int b) {
        return a + b;
    }

    private static double add(double a, double b) {
        return a + b;
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
        StringBuilder sb = new StringBuilder(32);

        for (int i = 0; i < items.length; ++i) {
            if (i > 0) {
                sb.append(',');
            }

            sb.append(items[i]);
        }

        return sb.toString();
    }

    private static String buildNumberReport(int n) {
        // Build a multi-line report using append chains.
        StringBuilder sb = new StringBuilder(64);

        sb.append("Number Report").append('\n');
        sb.append("=============").append('\n');

        for (int i = 1; i <= n; ++i) {
            sb.append("i=").append(i);
            sb.append(", square=").append(i * i);
            sb.append('\n');
        }

        sb.append("End.").append('\n');

        return sb.toString();
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
