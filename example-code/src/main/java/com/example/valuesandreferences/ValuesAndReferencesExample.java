package com.example.valuesandreferences;

public final class ValuesAndReferencesExample {
    private ValuesAndReferencesExample() {
    }

    public static void main(String[] args) {
        printHeader("Values and References");

        demoPrimitiveCopy();
        demoArrayReferenceMutation();
        demoParameterReassignDoesNotAffectCaller();
        demoStringReferenceButImmutable();
    }

    private static void demoPrimitiveCopy() {
        printSectionTitle("Primitive assignment makes a copy");

        int originalValue = 10;
        int copiedValue = originalValue;

        copiedValue += 5;

        System.out.println("originalValue = " + originalValue);
        System.out.println("copiedValue   = " + copiedValue);
    }

    private static void demoArrayReferenceMutation() {
        printSectionTitle("Array reference allows visible mutation");

        int[] scores = new int[]{ 10, 20, 30 };

        System.out.println("Before increaseAll(scores, 7):");
        printIntArray(scores);

        increaseAll(scores, 7);

        System.out.println("After increaseAll(scores, 7):");
        printIntArray(scores);
    }

    private static void increaseAll(int[] values, int delta) {
        // Mutating the array changes the same array object the caller points to.
        for (int i = 0; i < values.length; ++i) {
            values[i] += delta;
        }
    }

    private static void demoParameterReassignDoesNotAffectCaller() {
        printSectionTitle("Reassigning a parameter does not change the caller variable");

        int[] numbers = new int[]{ 1, 2, 3 };

        System.out.println("Before replaceArrayInside(numbers):");
        printIntArray(numbers);

        replaceArrayInside(numbers);

        System.out.println("After replaceArrayInside(numbers):");
        printIntArray(numbers);

        System.out.println("Note: The parameter was reassigned, but the caller still points to the original array.");
    }

    private static void replaceArrayInside(int[] values) {
        // This only changes the local copy of the reference.
        values = new int[]{ 999, 999, 999 };
        values[0] = 1000;
    }

    private static void demoStringReferenceButImmutable() {
        printSectionTitle("String is a reference type, but String objects are immutable");

        String text = "Hello";

        System.out.println("Before appendSuffix(text): " + text);
        appendSuffix(text);
        System.out.println("After appendSuffix(text):  " + text);

        System.out.println("Fix: Use the return value");
        text = appendSuffixReturn(text);
        System.out.println("After text = appendSuffixReturn(text): " + text);
    }

    private static void appendSuffix(String text) {
        // This creates a new String and assigns it only to the local parameter.
        text = text + "!";
    }

    private static String appendSuffixReturn(String text) {
        return text + "!";
    }

    private static void printIntArray(int[] values) {
        for (int i = 0; i < values.length; ++i) {
            if (i > 0) {
                System.out.print(", ");
            }

            System.out.print(values[i]);
        }

        System.out.println();
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
