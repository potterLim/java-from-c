package com.example.lineardatastructures;

public class Stack {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] mElements;
    private int mCount;

    public Stack() {
        mElements = new int[0];
        mCount = 0;
    }

    public Stack(int initialCapacity) {
        mElements = new int[initialCapacity];
        mCount = 0;
    }

    public boolean push(int value) {
        ensureCapacity();
        mElements[mCount] = value;
        ++mCount;

        return true;
    }

    public boolean pop(IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (mCount == 0) {
            return false;
        }

        int topIndex = mCount - 1;
        outValue.changeValue(mElements[topIndex]);
        mElements[topIndex] = 0;
        --mCount;

        return true;
    }

    public boolean peek(IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (mCount == 0) {
            return false;
        }

        outValue.changeValue(mElements[mCount - 1]);

        return true;
    }

    public int getCount() {
        return mCount;
    }

    public int getCapacity() {
        return mElements.length;
    }

    public void clear() {
        for (int i = 0; i < mCount; ++i) {
            mElements[i] = 0;
        }

        mCount = 0;
    }

    public boolean isEmpty() {
        return mCount == 0;
    }

    private void ensureCapacity() {
        if (mCount < mElements.length) {
            return;
        }

        int newCapacity;
        if (mElements.length == 0) {
            newCapacity = DEFAULT_INITIAL_CAPACITY;
        } else {
            newCapacity = mElements.length * 2;
        }

        int[] newElements = new int[newCapacity];

        for (int i = 0; i < mCount; ++i) {
            newElements[i] = mElements[i];
        }

        mElements = newElements;
    }
}
