package com.example.syntaxandlanguageergonomics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class SyntaxAndLanguageErgonomicsExample {
    private SyntaxAndLanguageErgonomicsExample() {
    }

    public static void main(String[] args) {
        printHeader("Syntax and Language Ergonomics");

        demoForeachWithList();
        demoForeachWithSet();
        demoForeachWithMap();
        demoForeachVariableReassignment();
        demoVarBasics();
        demoVarWithForeach();
    }

    private static void demoForeachWithList() {
        printSectionTitle("foreach with ArrayList: value-based iteration");

        ArrayList<Integer> scores = new ArrayList<>();
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

    private static void printIndexedList(ArrayList<Integer> scores) {
        for (int i = 0; i < scores.size(); ++i) {
            System.out.println(i + ": " + scores.get(i));
        }
    }

    private static void demoForeachWithSet() {
        printSectionTitle("foreach with HashSet: unique elements");

        HashSet<String> commands = new HashSet<>();

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
    }

    private static void demoForeachWithMap() {
        printSectionTitle("foreach with HashMap: key-value pairs");

        HashMap<String, Integer> stockCountsByItemName = new HashMap<>();
        stockCountsByItemName.put("apple", 3);
        stockCountsByItemName.put("banana", 5);
        stockCountsByItemName.put("orange", 2);

        int total = 0;

        for (HashMap.Entry<String, Integer> entry : stockCountsByItemName.entrySet()) {
            System.out.println("item=" + entry.getKey() + ", count=" + entry.getValue());
            total += entry.getValue();
        }

        System.out.println();
        System.out.println("Total item count = " + total);
    }

    private static void demoForeachVariableReassignment() {
        printSectionTitle("foreach variable reassignment does not replace elements");

        ArrayList<String> commands = new ArrayList<>();
        commands.add("start");
        commands.add("stop");
        commands.add("pause");

        System.out.println("Before:");
        printStringList(commands);

        for (String command : commands) {
            command = command.toUpperCase();
        }

        System.out.println();
        System.out.println("After reassigning the foreach variable:");
        printStringList(commands);
    }

    private static void printStringList(ArrayList<String> values) {
        for (int i = 0; i < values.size(); ++i) {
            System.out.println(i + ": " + values.get(i));
        }
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

        ArrayList<String> messages = new ArrayList<>();
        messages.add("INFO");
        messages.add("WARN");
        messages.add("INFO");
        messages.add("ERROR");

        HashMap<String, Integer> messageCountsByMessage = new HashMap<>();

        for (var message : messages) {
            int currentCount = 0;
            if (messageCountsByMessage.containsKey(message)) {
                currentCount = messageCountsByMessage.get(message);
            }

            messageCountsByMessage.put(message, currentCount + 1);
        }

        System.out.println("Message counts:");

        for (var entry : messageCountsByMessage.entrySet()) {
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
