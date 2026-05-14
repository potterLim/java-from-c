package com.example.collectiondatamanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class CollectionDataManagementExample {
    private CollectionDataManagementExample() {
    }

    public static void main(String[] args) {
        printHeader("Collection Data Management");

        demoWrapperTypes();
        demoArrayList();
        demoHashSet();
        demoHashMap();
    }

    private static void demoWrapperTypes() {
        printSectionTitle("Wrapper types: primitive values in collections");

        ArrayList<Integer> scores = new ArrayList<>();

        scores.add(90);
        scores.add(85);
        scores.add(100);

        int firstScore = scores.get(0);
        int totalScore = 0;

        for (int score : scores) {
            totalScore += score;
        }

        System.out.println("Scores = " + scores);
        System.out.println("firstScore = " + firstScore);
        System.out.println("totalScore = " + totalScore);
    }

    private static void demoArrayList() {
        printSectionTitle("ArrayList: ordered list, index-based access");

        ArrayList<String> todoItems = new ArrayList<>();

        todoItems.add("Wake up");
        todoItems.add("Study Java");
        todoItems.add("Have lunch");

        System.out.println("Initial list:");
        printArrayList(todoItems);

        // Insert at index (shifts elements to the right).
        todoItems.add(1, "Take a shower");

        System.out.println();
        System.out.println("After insert at index 1:");
        printArrayList(todoItems);

        // Update by index.
        todoItems.set(2, "Study Java (focused)");

        System.out.println();
        System.out.println("After set at index 2:");
        printArrayList(todoItems);

        // Remove by index.
        todoItems.remove(0);

        System.out.println();
        System.out.println("After remove at index 0:");
        printArrayList(todoItems);

        System.out.println();
        System.out.println("Size = " + todoItems.size());
        System.out.println("IndexOf(\"Have lunch\") = " + todoItems.indexOf("Have lunch"));
    }

    private static void printArrayList(ArrayList<String> items) {
        for (int i = 0; i < items.size(); ++i) {
            System.out.println(i + ": " + items.get(i));
        }
    }

    private static void demoHashSet() {
        printSectionTitle("HashSet: unique elements, membership check");

        HashSet<String> keywords = new HashSet<>();

        System.out.println("Add \"java\": " + keywords.add("java"));
        System.out.println("Add \"collections\": " + keywords.add("collections"));
        System.out.println("Add \"java\" again: " + keywords.add("java"));

        System.out.println();
        System.out.println("Contains \"java\": " + keywords.contains("java"));
        System.out.println("Contains \"oop\": " + keywords.contains("oop"));

        System.out.println();
        System.out.println("Remove \"collections\": " + keywords.remove("collections"));
        System.out.println("Remove \"collections\" again: " + keywords.remove("collections"));

        System.out.println();
        System.out.println("Size (unique count) = " + keywords.size());
    }

    private static void demoHashMap() {
        printSectionTitle("HashMap: key -> value mapping, check key existence");

        HashMap<Integer, String> namesById = new HashMap<>();

        namesById.put(1, "Alice");
        namesById.put(2, "Bob");
        namesById.put(3, "Charlie");

        System.out.println("Size (key count) = " + namesById.size());

        System.out.println();
        System.out.println("Get by key:");
        System.out.println("Key 2 -> " + namesById.get(2));
        System.out.println("Key 99 -> " + namesById.get(99));

        System.out.println();
        System.out.println("containsKey checks the key only:");
        System.out.println("containsKey(1): " + namesById.containsKey(1));
        System.out.println("containsKey(99): " + namesById.containsKey(99));

        System.out.println();
        System.out.println("Overwrite value for key 2:");
        namesById.put(2, "Bobby");
        System.out.println("Key 2 -> " + namesById.get(2));

        System.out.println();
        System.out.println("Remove key 3:");
        System.out.println("remove(3) -> " + namesById.remove(3));
        System.out.println("containsKey(3): " + namesById.containsKey(3));
        System.out.println("Size = " + namesById.size());
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
