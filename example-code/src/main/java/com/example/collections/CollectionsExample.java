package com.example.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class CollectionsExample {
    public static void main(String[] args) {
        printHeader("Collections");

        demoArrayList();
        demoHashSet();
        demoHashMap();
    }

    private static void demoArrayList() {
        printSectionTitle("ArrayList: ordered list, index-based access");

        ArrayList<String> todoList = new ArrayList<>();

        todoList.add("Wake up");
        todoList.add("Study Java");
        todoList.add("Have lunch");

        System.out.println("Initial list:");
        printArrayList(todoList);

        // Insert at index (shifts elements to the right).
        todoList.add(1, "Take a shower");

        System.out.println();
        System.out.println("After insert at index 1:");
        printArrayList(todoList);

        // Update by index.
        todoList.set(2, "Study Java (focused)");

        System.out.println();
        System.out.println("After set at index 2:");
        printArrayList(todoList);

        // Remove by index.
        todoList.remove(0);

        System.out.println();
        System.out.println("After remove at index 0:");
        printArrayList(todoList);

        System.out.println();
        System.out.println("Size = " + todoList.size());
        System.out.println("IndexOf(\"Have lunch\") = " + todoList.indexOf("Have lunch"));
    }

    private static void printArrayList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(i + ": " + list.get(i));
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

        HashMap<Integer, String> nameById = new HashMap<>();

        nameById.put(1, "Alice");
        nameById.put(2, "Bob");
        nameById.put(3, "Charlie");

        System.out.println("Size (key count) = " + nameById.size());

        System.out.println();
        System.out.println("Get by key:");
        System.out.println("Key 2 -> " + nameById.get(2));
        System.out.println("Key 99 -> " + nameById.get(99));

        System.out.println();
        System.out.println("containsKey checks the key only:");
        System.out.println("containsKey(1): " + nameById.containsKey(1));
        System.out.println("containsKey(99): " + nameById.containsKey(99));

        System.out.println();
        System.out.println("Overwrite value for key 2:");
        nameById.put(2, "Bobby");
        System.out.println("Key 2 -> " + nameById.get(2));

        System.out.println();
        System.out.println("Remove key 3:");
        System.out.println("remove(3) -> " + nameById.remove(3));
        System.out.println("containsKey(3): " + nameById.containsKey(3));
        System.out.println("Size = " + nameById.size());
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
