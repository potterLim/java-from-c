package com.example.expressiveness;

import java.lang.StringBuilder;

public final class ExpressivenessExample {
    public static void main(String[] args) {
        PrintHeader("Expressiveness");

        DemoOverloading();
        DemoStringBuilder();
    }

    private static void DemoOverloading() {
        PrintSectionTitle("Method overloading: same name, different parameter lists");

        Log("System started");
        Log("AUTH", "Login succeeded");
        Log("PAYMENT", "Charge accepted", 202);

        int total = Add(3, 4);
        double average = Add(3.0, 4.0) / 2.0;

        System.out.println("Add(int, int) -> " + total);
        System.out.println("Add(double, double) / 2 -> " + average);
    }

    private static void Log(String message) {
        System.out.println("[INFO] " + message);
    }

    private static void Log(String category, String message) {
        System.out.println("[" + category + "] " + message);
    }

    private static void Log(String category, String message, int code) {
        System.out.println("[" + category + "-" + code + "] " + message);
    }

    private static int Add(int a, int b) {
        return a + b;
    }

    private static double Add(double a, double b) {
        return a + b;
    }

    private static void DemoStringBuilder() {
        PrintSectionTitle("StringBuilder: build strings efficiently");

        String[] items = new String[]{ "apple", "banana", "carrot", "donut" };

        String csv = BuildCsv(items);
        System.out.println("CSV: " + csv);

        System.out.println();
        System.out.println(BuildNumberReport(6));
    }

    private static String BuildCsv(String[] items) {
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

    private static String BuildNumberReport(int n) {
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

    private static void PrintHeader(String title) {
        System.out.println(title + " Example");
        System.out.println("========================================");
    }

    private static void PrintSectionTitle(String title) {
        System.out.println();
        System.out.println("[" + title + "]");
        System.out.println("----------------------------------------");
    }
}
