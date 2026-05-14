package com.example.lineardatastructures;

public class Queue {
    private static final int DEFAULT_INITIAL_CAPACITY = 4;

    private int[] mElements;
    private int mFront;
    private int mRear;
    private int mCount;

    public Queue() {
        mElements = new int[0];
        mFront = 0;
        mRear = 0;
        mCount = 0;
    }

    public Queue(int initialCapacity) {
        mElements = new int[initialCapacity];
        mFront = 0;
        mRear = 0;
        mCount = 0;
    }

    public boolean enqueue(int value) {
        ensureCapacity();

        mElements[mRear] = value;
        mRear = (mRear + 1) % mElements.length;
        ++mCount;

        return true;
    }

    public boolean dequeue(IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (mCount == 0) {
            return false;
        }

        outValue.changeValue(mElements[mFront]);
        mElements[mFront] = 0;

        mFront = (mFront + 1) % mElements.length;
        --mCount;

        return true;
    }

    public boolean peek(IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (mCount == 0) {
            return false;
        }

        outValue.changeValue(mElements[mFront]);

        return true;
    }

    public int getCount() {
        return mCount;
    }

    public int getCapacity() {
        return mElements.length;
    }

    public void clear() {
        for (int i = 0; i < mElements.length; ++i) {
            mElements[i] = 0;
        }

        mCount = 0;
        mFront = 0;
        mRear = 0;
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
            int readIndex = (mFront + i) % mElements.length;
            newElements[i] = mElements[readIndex];
        }

        mElements = newElements;
        mFront = 0;
        mRear = mCount;
    }
}
