package com.example.lineardatastructures;

public final class Main {
    private static int sPassedCount;
    private static int sFailedCount;
    private static String sFailureMessage;

    private Main() {
    }

    public static void main(String[] args) {
        verifyList();
        verifyStack();
        verifyQueue();
        verifyLinkedList();

        printSummary();
    }

    private static void verifyList() {
        List list = new List();

        boolean isPassed = checkTrue("new list is empty", list.isEmpty())
                && checkEquals("new list capacity", 0, list.getCapacity())
                && checkTrue("add first value", list.add(5))
                && checkTrue("add second value", list.add(7))
                && checkTrue("add third value", list.add(9))
                && checkTrue("add fourth value", list.add(11))
                && checkEquals("first capacity growth", 4, list.getCapacity())
                && checkTrue("add fifth value", list.add(13))
                && checkEquals("second capacity growth", 8, list.getCapacity())
                && checkTrue("insert in middle", list.insert(2, 8))
                && checkFalse("reject invalid insert", list.insert(7, 99))
                && checkValues("values after insert", list, new int[] { 5, 7, 8, 9, 11, 13 })
                && checkTrue("set middle value", list.set(2, 80))
                && checkFalse("reject invalid set", list.set(6, 99))
                && checkTrue("remove first value", list.removeAt(0))
                && checkTrue("remove last value", list.removeAt(4))
                && checkFalse("reject invalid remove", list.removeAt(-1))
                && checkValues("values after remove", list, new int[] { 7, 80, 9, 11 });

        if (isPassed) {
            list.clear();
            isPassed = checkTrue("clear makes list empty", list.isEmpty())
                    && checkEquals("clear preserves capacity", 8, list.getCapacity());
        }

        verifyScenario("array list preserves order and capacity rules", isPassed);
    }

    private static void verifyStack() {
        Stack stack = new Stack(2);
        IntValue outValue = new IntValue();

        boolean isPassed = checkTrue("new stack is empty", stack.isEmpty())
                && checkEquals("initial capacity", 2, stack.getCapacity())
                && checkTrue("push first value", stack.push(5))
                && checkTrue("push second value", stack.push(7))
                && checkTrue("push third value", stack.push(9))
                && checkEquals("capacity grows", 4, stack.getCapacity())
                && checkEquals("count after push", 3, stack.getCount())
                && checkTrue("peek succeeds", stack.peek(outValue))
                && checkEquals("peek returns top", 9, outValue.getValue())
                && checkEquals("peek keeps count", 3, stack.getCount())
                && checkTrue("pop top", stack.pop(outValue))
                && checkEquals("first pop value", 9, outValue.getValue())
                && checkTrue("pop next", stack.pop(outValue))
                && checkEquals("second pop value", 7, outValue.getValue());

        if (isPassed) {
            stack.clear();
            isPassed = checkTrue("clear makes stack empty", stack.isEmpty())
                    && checkEquals("clear preserves capacity", 4, stack.getCapacity())
                    && checkFalse("reject empty pop", stack.pop(outValue))
                    && checkFalse("reject empty peek", stack.peek(outValue));
        }

        verifyScenario("stack follows LIFO and capacity rules", isPassed);
    }

    private static void verifyQueue() {
        Queue queue = new Queue(3);
        IntValue outValue = new IntValue();

        boolean isPassed = checkTrue("new queue is empty", queue.isEmpty())
                && checkEquals("initial capacity", 3, queue.getCapacity())
                && checkTrue("enqueue first value", queue.enqueue(5))
                && checkTrue("enqueue second value", queue.enqueue(7))
                && checkTrue("enqueue third value", queue.enqueue(9))
                && checkEquals("count after enqueue", 3, queue.getCount())
                && checkTrue("peek succeeds", queue.peek(outValue))
                && checkEquals("peek returns front", 5, outValue.getValue())
                && checkTrue("dequeue first value", queue.dequeue(outValue))
                && checkEquals("first dequeue value", 5, outValue.getValue())
                && checkTrue("enqueue wraps rear", queue.enqueue(11))
                && checkTrue("enqueue triggers growth", queue.enqueue(13))
                && checkEquals("capacity grows", 6, queue.getCapacity())
                && checkEquals("count after growth", 4, queue.getCount())
                && checkTrue("dequeue second value", queue.dequeue(outValue))
                && checkEquals("second dequeue value", 7, outValue.getValue())
                && checkTrue("dequeue third value", queue.dequeue(outValue))
                && checkEquals("third dequeue value", 9, outValue.getValue())
                && checkTrue("dequeue wrapped value", queue.dequeue(outValue))
                && checkEquals("wrapped dequeue value", 11, outValue.getValue())
                && checkTrue("dequeue grown value", queue.dequeue(outValue))
                && checkEquals("grown dequeue value", 13, outValue.getValue());

        if (isPassed) {
            queue.clear();
            isPassed = checkTrue("clear makes queue empty", queue.isEmpty())
                    && checkEquals("clear preserves capacity", 6, queue.getCapacity())
                    && checkFalse("reject empty dequeue", queue.dequeue(outValue))
                    && checkFalse("reject empty peek", queue.peek(outValue));
        }

        verifyScenario("queue follows FIFO, wraparound, and capacity rules", isPassed);
    }

    private static void verifyLinkedList() {
        LinkedList list = new LinkedList();

        boolean isPassed = checkTrue("new linked list is empty", list.isEmpty())
                && checkTrue("add first value", list.add(5))
                && checkTrue("add second value", list.add(7))
                && checkTrue("insert in middle", list.insert(1, 6))
                && checkTrue("insert at front", list.insert(0, 4))
                && checkTrue("insert at end", list.insert(4, 9))
                && checkFalse("reject invalid insert", list.insert(6, 99))
                && checkValues("values after insert", list, new int[] { 4, 5, 6, 7, 9 })
                && checkTrue("set middle value", list.set(2, 60))
                && checkFalse("reject invalid set", list.set(5, 99))
                && checkTrue("remove first value", list.removeAt(0))
                && checkTrue("remove last value", list.removeAt(3))
                && checkValues("values after edge removals", list, new int[] { 5, 60, 7 })
                && checkTrue("remove middle value", list.removeAt(1))
                && checkFalse("reject invalid remove", list.removeAt(2))
                && checkValues("values after middle removal", list, new int[] { 5, 7 });

        if (isPassed) {
            list.clear();
            isPassed = checkTrue("clear makes linked list empty", list.isEmpty())
                    && checkEquals("clear resets count", 0, list.getCount());
        }

        verifyScenario("linked list preserves links and boundary rules", isPassed);
    }

    private static boolean checkValues(String name, List list, int[] expected) {
        if (!checkEquals(name + " count", expected.length, list.getCount())) {
            return false;
        }

        IntValue outValue = new IntValue();

        for (int i = 0; i < expected.length; ++i) {
            if (!list.get(i, outValue)) {
                sFailureMessage = name + ": get failed at index=" + i;
                return false;
            }

            if (outValue.getValue() != expected[i]) {
                sFailureMessage = name + ": index=" + i + ", expected=" + expected[i] + ", actual=" + outValue.getValue();
                return false;
            }
        }

        return true;
    }

    private static boolean checkValues(String name, LinkedList list, int[] expected) {
        if (!checkEquals(name + " count", expected.length, list.getCount())) {
            return false;
        }

        IntValue outValue = new IntValue();

        for (int i = 0; i < expected.length; ++i) {
            if (!list.get(i, outValue)) {
                sFailureMessage = name + ": get failed at index=" + i;
                return false;
            }

            if (outValue.getValue() != expected[i]) {
                sFailureMessage = name + ": index=" + i + ", expected=" + expected[i] + ", actual=" + outValue.getValue();
                return false;
            }
        }

        return true;
    }

    private static boolean checkEquals(String name, int expected, int actual) {
        if (expected == actual) {
            return true;
        }

        sFailureMessage = name + ": expected=" + expected + ", actual=" + actual;
        return false;
    }

    private static boolean checkTrue(String name, boolean actual) {
        if (actual) {
            return true;
        }

        sFailureMessage = name + ": expected=true, actual=false";
        return false;
    }

    private static boolean checkFalse(String name, boolean actual) {
        if (!actual) {
            return true;
        }

        sFailureMessage = name + ": expected=false, actual=true";
        return false;
    }

    private static void verifyScenario(String name, boolean isPassed) {
        if (isPassed) {
            pass(name);
            return;
        }

        fail(name, sFailureMessage);
        sFailureMessage = null;
    }

    private static void pass(String name) {
        ++sPassedCount;
        System.out.println("[PASS] " + name);
    }

    private static void fail(String name, String message) {
        ++sFailedCount;
        System.out.println("[FAIL] " + name + " (" + message + ")");
    }

    private static void printSummary() {
        System.out.println();
        System.out.println("Passed: " + sPassedCount);
        System.out.println("Failed: " + sFailedCount);
    }
}
