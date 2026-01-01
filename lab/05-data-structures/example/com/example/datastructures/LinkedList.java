package com.example.datastructures;

public class LinkedList {
    private Node head;
    private Node tail;
    private int count;

    public LinkedList() {
        head = null;
        tail = null;
        count = 0;
    }

    public boolean add(int value) {
        Node nodeToAdd = new Node(value);

        if (count == 0) {
            head = nodeToAdd;
            tail = nodeToAdd;
            count = 1;

            return true;
        }

        tail.setNext(nodeToAdd);
        tail = nodeToAdd;
        count++;

        return true;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > count) {
            return false;
        }

        if (index == count) {
            return add(value);
        }

        Node nodeToInsert = new Node(value);

        if (index == 0) {
            nodeToInsert.setNext(head);
            head = nodeToInsert;

            if (count == 0) {
                tail = nodeToInsert;
            }

            count++;

            return true;
        }

        Node previousNode = getNodeAt(index - 1);
        Node nextNode = previousNode.getNext();

        previousNode.setNext(nodeToInsert);
        nodeToInsert.setNext(nextNode);

        count++;

        return true;
    }

    public boolean removeAt(int index) {
        if (index < 0 || index >= count) {
            return false;
        }

        if (index == 0) {
            Node nextHead = head.getNext();
            head = nextHead;
            count--;

            if (count == 0) {
                tail = null;
            }

            return true;
        }

        Node previousNode = getNodeAt(index - 1);
        Node nodeToRemove = previousNode.getNext();
        Node nextNode = nodeToRemove.getNext();

        previousNode.setNext(nextNode);
        count--;

        if (index == count) {
            tail = previousNode;
        }

        return true;
    }

    public boolean get(int index, IntValue outValue) {
        if (index < 0 || index >= count) {
            return false;
        }

        Node node = getNodeAt(index);
        outValue.value = node.getValue();

        return true;
    }

    public boolean set(int index, int value) {
        if (index < 0 || index >= count) {
            return false;
        }

        Node node = getNodeAt(index);
        node.setValue(value);

        return true;
    }

    public int getCount() {
        return count;
    }

    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private Node getNodeAt(int index) {
        Node node = head;

        for (int i = 0; i < index; ++i) {
            node = node.getNext();
        }

        return node;
    }
}