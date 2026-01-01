package com.example.datastructures;

public class Stack {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] elements;
    private int count;

    public Stack() {
        elements = new int[0];
        count = 0;
    }

    public Stack(int initialCapacity) {
        elements = new int[initialCapacity];
        count = 0;
    }

    public boolean push(int value) {
        ensureCapacity();
        elements[count++] = value;

        return true;
    }

    public boolean pop(IntValue outValue) {
        if (count == 0) {
            return false;
        }

        outValue.value = elements[count - 1];
        count--;

        return true;
    }

    public boolean peek(IntValue outValue) {
        if (count == 0) {
            return false;
        }

        outValue.value = elements[count - 1];

        return true;
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return elements.length;
    }

    public void clear() {
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private void ensureCapacity() {
        if (count < elements.length) {
            return;
        }

        int newCapacity;
        if (elements.length == 0) {
            newCapacity = DEFAULT_INITIAL_CAPACITY;
        } else {
            newCapacity = elements.length * 2;
        }

        int[] newElements = new int[newCapacity];

        for (int i = 0; i < count; ++i) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }
}
