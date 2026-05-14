package com.example.spaceconvoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Main {
    private static int sPassedCount;
    private static int sFailedCount;
    private static String sFailureMessage;

    private Main() {
    }

    public static void main(String[] args) {
        verifyShipBehavior();
        verifyConvoyLaneBehavior();

        printSummary();
    }

    private static void verifyShipBehavior() {
        Ship ship = new Ship("Ship", EHullGrade.MEDIUM, 40, 14, 8, 3);

        ship.applyHullChange(6);
        boolean isPassed = checkEquals("positive hull change", 46, ship.getHull());

        ship.applyHullChange(-100);
        isPassed = isPassed && checkEquals("hull lower bound", 0, ship.getHull());

        Ship defender = new Ship("Defender", EHullGrade.HEAVY, 24, 11, 6, 4);
        Ship attacker = new Ship("Attacker", EHullGrade.MEDIUM, 28, 15, 10, 3);

        attacker.fireLaser(defender);
        isPassed = isPassed && checkEquals("shield and hull grade", 17, defender.getHull());

        defender.repair();
        isPassed = isPassed && checkEquals("repair", 21, defender.getHull());

        Ship armoredDefender = new Ship("Armored", EHullGrade.ARMORED, 10, 0, 1, 0);
        Ship weakAttacker = new Ship("Weak", EHullGrade.LIGHT, 10, 2, 0, 0);
        weakAttacker.fireLaser(armoredDefender);
        isPassed = isPassed && checkEquals("minimum non-zero damage", 9, armoredDefender.getHull());

        Ship shieldedDefender = new Ship("Shielded", EHullGrade.MEDIUM, 10, 0, 5, 0);
        Ship blockedAttacker = new Ship("Blocked", EHullGrade.MEDIUM, 10, 5, 0, 0);
        blockedAttacker.fireLaser(shieldedDefender);
        isPassed = isPassed && checkEquals("shield blocks damage", 10, shieldedDefender.getHull());

        verifyScenario("ship applies hull, damage, and repair rules", isPassed);
    }

    private static void verifyConvoyLaneBehavior() {
        Path shipFilePath = null;

        try {
            shipFilePath = Files.createTempFile("space-convoy-main-", ".csv");
            Files.writeString(
                    shipFilePath,
                    String.join(
                            "\n",
                            "Falcon,MEDIUM,32,14,8,3",
                            "Vanguard,HEAVY,38,16,10,2",
                            "Swift,LIGHT,26,18,5,2",
                            "Bulwark,ARMORED,45,12,14,1",
                            "Raven,MEDIUM,30,15,7,3"
                    )
            );

            ConvoyLane lane = new ConvoyLane("Outer Rim Convoy", 5);
            boolean isPassed = checkNull("empty lane", lane.predictIncomingDamageOrNull());

            lane.loadShips(shipFilePath.toString());
            isPassed = isPassed && checkArrayEquals("incoming damage", new int[] { 8, 6, 8, 1, 7 }, lane.predictIncomingDamageOrNull());

            lane.advanceTurn();
            isPassed = isPassed && checkEquals("turn count", 1, lane.getTurns());
            isPassed = isPassed && checkArrayEquals("ship order after turn", new int[] { 8, 6, 8, 1, 7 }, lane.predictIncomingDamageOrNull());

            ConvoyLane limitedLane = new ConvoyLane("Limited Convoy", 3);
            limitedLane.loadShips(shipFilePath.toString());
            isPassed = isPassed && checkArrayEquals("capacity limit", new int[] { 8, 6, 11 }, limitedLane.predictIncomingDamageOrNull());

            ConvoyLane singleShipLane = new ConvoyLane("Single Ship Convoy", 1);
            singleShipLane.loadShips(shipFilePath.toString());
            isPassed = isPassed && checkArrayEquals("single ship prediction", new int[] { 0 }, singleShipLane.predictIncomingDamageOrNull());

            limitedLane.loadShips("   ");
            isPassed = isPassed && checkNull("invalid load clears lane", limitedLane.predictIncomingDamageOrNull());

            verifyScenario("convoy lane loads ships and predicts damage", isPassed);
        } catch (IOException exception) {
            fail("convoy file setup", exception.getMessage());
        } finally {
            deletePathIfExists(shipFilePath);
        }

        verifyDestroyedShipsAreRemovedAfterLaserPhase();
    }

    private static void verifyDestroyedShipsAreRemovedAfterLaserPhase() {
        Path shipFilePath = null;

        try {
            shipFilePath = Files.createTempFile("space-convoy-main-destroyed-", ".csv");
            Files.writeString(
                    shipFilePath,
                    String.join(
                            "\n",
                            "Alpha,LIGHT,3,10,0,5",
                            "Beta,MEDIUM,20,10,0,0"
                    )
            );

            ConvoyLane lane = new ConvoyLane("Removal Convoy", 2);
            lane.loadShips(shipFilePath.toString());

            boolean isPassed = checkArrayEquals("initial prediction", new int[] { 12, 10 }, lane.predictIncomingDamageOrNull());

            lane.advanceTurn();

            isPassed = isPassed && checkEquals("turn count after removal", 1, lane.getTurns())
                    && checkArrayEquals("destroyed ship removed", new int[] { 0 }, lane.predictIncomingDamageOrNull());

            verifyScenario("destroyed ships are removed before repair", isPassed);
        } catch (IOException exception) {
            fail("destroyed ship setup", exception.getMessage());
        } finally {
            deletePathIfExists(shipFilePath);
        }
    }

    private static boolean checkEquals(String name, int expected, int actual) {
        if (expected == actual) {
            return true;
        }

        sFailureMessage = name + ": expected=" + expected + ", actual=" + actual;
        return false;
    }

    private static boolean checkArrayEquals(String name, int[] expected, int[] actualOrNull) {
        if (actualOrNull == null) {
            sFailureMessage = name + ": expected array, actual=null";
            return false;
        }

        if (expected.length != actualOrNull.length) {
            sFailureMessage = name + ": expected length=" + expected.length + ", actual length=" + actualOrNull.length;
            return false;
        }

        for (int i = 0; i < expected.length; ++i) {
            if (expected[i] != actualOrNull[i]) {
                sFailureMessage = name + ": index=" + i + ", expected=" + expected[i] + ", actual=" + actualOrNull[i];
                return false;
            }
        }

        return true;
    }

    private static boolean checkNull(String name, int[] actualOrNull) {
        if (actualOrNull == null) {
            return true;
        }

        sFailureMessage = name + ": expected=null";
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

    private static void deletePathIfExists(Path pathOrNull) {
        if (pathOrNull == null) {
            return;
        }

        try {
            Files.deleteIfExists(pathOrNull);
        } catch (IOException ignored) {
        }
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
