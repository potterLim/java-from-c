package com.example.datastructures;

public class List {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] elements;
    private int count;

    public List() {
        elements = new int[0];
        count = 0;
    }

    public List(int initialCapacity) {
        elements = new int[initialCapacity];
        count = 0;
    }

    public boolean add(int value) {
        ensureCapacity();
        elements[count++] = value;

        return true;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > count) {
            return false;
        }

        ensureCapacity();

        for (int i = count; i > index; --i) {
            elements[i] = elements[i - 1];
        }

        elements[index] = value;
        count++;

        return true;
    }

    public boolean removeAt(int index) {
        if (index < 0 || index >= count) {
            return false;
        }

        for (int i = index; i < count - 1; ++i) {
            elements[i] = elements[i + 1];
        }

        count--;

        return true;
    }

    public boolean get(int index, IntValue outValue) {
        if (index < 0 || index >= count) {
            return false;
        }

        outValue.value = elements[index];

        return true;
    }

    public boolean set(int index, int value) {
        if (index < 0 || index >= count) {
            return false;
        }

        elements[index] = value;

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
