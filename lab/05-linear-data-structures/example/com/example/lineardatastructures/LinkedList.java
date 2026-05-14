package com.example.lineardatastructures;

public class LinkedList {
    private Node mHead;
    private Node mTail;
    private int mCount;

    public LinkedList() {
        mHead = null;
        mTail = null;
        mCount = 0;
    }

    public boolean add(int value) {
        Node nodeToAdd = new Node(value);

        if (mCount == 0) {
            mHead = nodeToAdd;
            mTail = nodeToAdd;
            mCount = 1;

            return true;
        }

        mTail.changeNext(nodeToAdd);
        mTail = nodeToAdd;
        ++mCount;

        return true;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > mCount) {
            return false;
        }

        if (index == mCount) {
            return add(value);
        }

        Node nodeToInsert = new Node(value);

        if (index == 0) {
            nodeToInsert.changeNext(mHead);
            mHead = nodeToInsert;

            if (mCount == 0) {
                mTail = nodeToInsert;
            }

            ++mCount;

            return true;
        }

        Node previousNode = getNodeAt(index - 1);
        Node nextNode = previousNode.getNextOrNull();

        previousNode.changeNext(nodeToInsert);
        nodeToInsert.changeNext(nextNode);

        ++mCount;

        return true;
    }

    public boolean removeAt(int index) {
        if (index < 0 || index >= mCount) {
            return false;
        }

        if (index == 0) {
            Node nodeToRemove = mHead;
            Node nextHeadOrNull = nodeToRemove.getNextOrNull();
            mHead = nextHeadOrNull;
            nodeToRemove.changeNext(null);
            --mCount;

            if (mCount == 0) {
                mTail = null;
            }

            return true;
        }

        Node previousNode = getNodeAt(index - 1);
        Node nodeToRemove = previousNode.getNextOrNull();
        Node nextNode = nodeToRemove.getNextOrNull();

        previousNode.changeNext(nextNode);
        nodeToRemove.changeNext(null);
        --mCount;

        if (index == mCount) {
            mTail = previousNode;
        }

        return true;
    }

    public boolean get(int index, IntValue outValue) {
        assert (outValue != null) : "outValue must not be null";

        if (index < 0 || index >= mCount) {
            return false;
        }

        Node node = getNodeAt(index);
        outValue.changeValue(node.getValue());

        return true;
    }

    public boolean set(int index, int value) {
        if (index < 0 || index >= mCount) {
            return false;
        }

        Node node = getNodeAt(index);
        node.changeValue(value);

        return true;
    }

    public int getCount() {
        return mCount;
    }

    public void clear() {
        mHead = null;
        mTail = null;
        mCount = 0;
    }

    public boolean isEmpty() {
        return mCount == 0;
    }

    private Node getNodeAt(int index) {
        Node node = mHead;

        for (int i = 0; i < index; ++i) {
            node = node.getNextOrNull();
        }

        return node;
    }
}
