package com.example.syntacticconvenience;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class SyntacticConvenienceExample {
    public static void main(String[] args) {
        printHeader("Syntactic Convenience");

        demoForeachWithList();
        demoForeachWithSet();
        demoForeachWithMap();
        demoVarBasics();
        demoVarWithForeach();
    }

    private static void demoForeachWithList() {
        printSectionTitle("foreach with ArrayList: value-based iteration");

        ArrayList<Integer> scores = new ArrayList<Integer>();
        scores.add(70);
        scores.add(85);
        scores.add(90);
        scores.add(75);

        int sum = 0;

        // Enhanced for-loop focuses on values, not indexes.
        for (int score : scores) {
            sum += score;
        }

        double average = 0.0;
        if (!scores.isEmpty()) {
            average = (double) sum / scores.size();
        }

        System.out.println("Scores:");
        printIndexedList(scores);

        System.out.println();
        System.out.println("Average score = " + average);
    }

    private static void printIndexedList(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    private static void demoForeachWithSet() {
        printSectionTitle("foreach with HashSet: unique elements");

        HashSet<String> commands = new HashSet<String>();

        commands.add("start");
        commands.add("stop");
        commands.add("pause");
        commands.add("start"); // duplicate

        System.out.println("Command count = " + commands.size());
        System.out.println("Commands:");

        // Order is not guaranteed in a HashSet.
        for (String command : commands) {
            System.out.println("  " + command);
        }

        System.out.println();
        System.out.println("Note: foreach is for reading, not modifying.");
    }

    private static void demoForeachWithMap() {
        printSectionTitle("foreach with HashMap: key-value pairs");

        HashMap<String, Integer> stock = new HashMap<String, Integer>();
        stock.put("apple", 3);
        stock.put("banana", 5);
        stock.put("orange", 2);

        int total = 0;

        for (HashMap.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("item=" + entry.getKey() + ", count=" + entry.getValue());
            total += entry.getValue();
        }

        System.out.println();
        System.out.println("Total item count = " + total);
    }

    private static void demoVarBasics() {
        printSectionTitle("var basics: local type inference (Java 10+)");

        // var requires an initializer and infers a fixed type.
        var title = "System Log";
        var retryLimit = 5;
        var ratios = new double[]{ 0.25, 0.5, 1.0 };

        System.out.println("Title = " + title);
        System.out.println("Retry limit = " + retryLimit);
        System.out.println("Ratio count = " + ratios.length);
    }

    private static void demoVarWithForeach() {
        printSectionTitle("var with foreach");

        ArrayList<String> messages = new ArrayList<String>();
        messages.add("INFO");
        messages.add("WARN");
        messages.add("INFO");
        messages.add("ERROR");

        HashMap<String, Integer> counts = new HashMap<String, Integer>();

        for (var msg : messages) {
            int currentCount = 0;
            if (counts.containsKey(msg)) {
                currentCount = counts.get(msg);
            }

            counts.put(msg, currentCount + 1);
        }

        System.out.println("Message counts:");

        for (var entry : counts.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
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
