package com.example.datastructures;

public class Queue {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] elements;
    private int front;
    private int rear;
    private int count;

    public Queue() {
        elements = new int[0];
        front = 0;
        rear = 0;
        count = 0;
    }

    public Queue(int initialCapacity) {
        elements = new int[initialCapacity];
        front = 0;
        rear = 0;
        count = 0;
    }

    public boolean enqueue(int value) {
        ensureCapacity();

        elements[rear] = value;
        rear = (rear + 1) % elements.length;
        count++;

        return true;
    }

    public boolean dequeue(IntValue outValue) {
        if (count == 0) {
            return false;
        }

        outValue.value = elements[front];
        elements[front] = 0;

        front = (front + 1) % elements.length;
        count--;

        return true;
    }

    public boolean peek(IntValue outValue) {
        if (count == 0) {
            return false;
        }

        outValue.value = elements[front];

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
        front = 0;
        rear = 0;
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
            int readIndex = (front + i) % elements.length;
            newElements[i] = elements[readIndex];
        }

        elements = newElements;
        front = 0;
        rear = count;
    }
}
