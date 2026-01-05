package com.example.valuesandreferences;

public final class ValuesAndReferencesExample {
    public static void main(String[] args) {
        PrintHeader("Values & References");

        DemoPrimitiveCopy();
        DemoArrayReferenceMutation();
        DemoParameterReassignDoesNotAffectCaller();
        DemoStringReferenceButImmutable();
    }

    private static void DemoPrimitiveCopy() {
        PrintSectionTitle("Primitive assignment makes a copy");

        int a = 10;
        int b = a;

        b += 5;

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    private static void DemoArrayReferenceMutation() {
        PrintSectionTitle("Array reference allows visible mutation");

        int[] scores = new int[]{ 10, 20, 30 };

        System.out.println("Before IncreaseAll(scores, 7):");
        PrintIntArray(scores);

        IncreaseAll(scores, 7);

        System.out.println("After IncreaseAll(scores, 7):");
        PrintIntArray(scores);
    }

    private static void IncreaseAll(int[] values, int delta) {
        // Mutating the array changes the same array object the caller points to.
        for (int i = 0; i < values.length; ++i) {
            values[i] += delta;
        }
    }

    private static void DemoParameterReassignDoesNotAffectCaller() {
        PrintSectionTitle("Reassigning a parameter does not change the caller variable");

        int[] data = new int[]{ 1, 2, 3 };

        System.out.println("Before ReplaceArrayInside(data):");
        PrintIntArray(data);

        ReplaceArrayInside(data);

        System.out.println("After ReplaceArrayInside(data):");
        PrintIntArray(data);

        System.out.println("Note: The parameter was reassigned, but the caller still points to the original array.");
    }

    private static void ReplaceArrayInside(int[] values) {
        // This only changes the local copy of the reference.
        values = new int[]{ 999, 999, 999 };
        values[0] = 1000;
    }

    private static void DemoStringReferenceButImmutable() {
        PrintSectionTitle("String is a reference type, but String objects are immutable");

        String text = "Hello";

        System.out.println("Before AppendSuffix(text): " + text);
        AppendSuffix(text);
        System.out.println("After AppendSuffix(text):  " + text);

        System.out.println("Fix: Use the return value");
        text = AppendSuffixReturn(text);
        System.out.println("After text = AppendSuffixReturn(text): " + text);
    }

    private static void AppendSuffix(String text) {
        // This creates a new String and assigns it only to the local parameter.
        text = text + "!";
    }

    private static String AppendSuffixReturn(String text) {
        return text + "!";
    }

    private static void PrintIntArray(int[] values) {
        for (int i = 0; i < values.length; ++i) {
            if (i > 0) {
                System.out.print(", ");
            }

            System.out.print(values[i]);
        }

        System.out.println();
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
