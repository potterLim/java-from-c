package com.example.lineardatastructures;

final class Node {
    private int mValue;
    private Node mNext;

    Node(int value) {
        mValue = value;
        mNext = null;
    }

    int getValue() {
        return mValue;
    }

    void changeValue(int value) {
        mValue = value;
    }

    Node getNextOrNull() {
        return mNext;
    }

    void changeNext(Node nextOrNull) {
        mNext = nextOrNull;
    }
}
