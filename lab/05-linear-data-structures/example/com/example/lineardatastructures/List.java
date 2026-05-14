package com.example.lineardatastructures;

public class List {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] mElements;
    private int mCount;

    public List() {
        mElements = new int[0];
        mCount = 0;
    }

    public List(int initialCapacity) {
        mElements = new int[initialCapacity];
        mCount = 0;
    }

    public boolean add(int value) {
        ensureCapacity();
        mElements[mCount] = value;
        ++mCount;

        return true;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > mCount) {
            return false;
        }

        ensureCapacity();

        for (int i = mCount; i > index; --i) {
            mElements[i] = mElements[i - 1];
        }

        mElements[index] = value;
        ++mCount;

        return true;
    }

    public boolean removeAt(int index) {
        if (index < 0 || index >= mCount) {
            return false;
        }

        for (int i = index; i < mCount - 1; ++i) {
            mElements[i] = mElements[i + 1];
        }

        --mCount;
        mElements[mCount] = 0;

        return true;
    }

    public boolean get(int index, IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (index < 0 || index >= mCount) {
            return false;
        }

        outValue.changeValue(mElements[index]);

        return true;
    }

    public boolean set(int index, int value) {
        if (index < 0 || index >= mCount) {
            return false;
        }

        mElements[index] = value;

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
