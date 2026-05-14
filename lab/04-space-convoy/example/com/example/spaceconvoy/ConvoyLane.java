package com.example.spaceconvoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ConvoyLane {
    private final String mLaneName;
    private final int mCapacity;
    private int mTurns;

    private final ArrayList<Ship> mShips;

    public ConvoyLane(String laneName, int capacity) {
        mLaneName = laneName;
        mCapacity = capacity;
        mTurns = 0;
        mShips = new ArrayList<>();
    }

    public String getLaneName() {
        return mLaneName;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getTurns() {
        return mTurns;
    }

    public void loadShips(String filePathOrNull) {
        mShips.clear();
        mTurns = 0;

        if (filePathOrNull == null || filePathOrNull.isBlank()) {
            return;
        }

        Path shipFilePath;
        try {
            shipFilePath = Path.of(filePathOrNull);
        } catch (InvalidPathException ignored) {
            return;
        }

        try {
            List<String> shipLines = Files.readAllLines(shipFilePath);

            for (String line : shipLines) {
                if (mShips.size() >= mCapacity) {
                    break;
                }

                if (line.isBlank()) {
                    continue;
                }

                mShips.add(parseShip(line));
            }
        } catch (IOException ignored) {
            mShips.clear();
            mTurns = 0;
        }
    }

    public void advanceTurn() {
        int shipCount = mShips.size();
        if (shipCount <= 1) {
            return;
        }

        int[] predictedDamages = predictIncomingDamageInternal(shipCount);

        for (int defenderIndex = 0; defenderIndex < shipCount; ++defenderIndex) {
            mShips.get(defenderIndex).applyHullChange(-predictedDamages[defenderIndex]);
        }

        removeDestroyedShips();

        for (int i = 0; i < mShips.size(); ++i) {
            mShips.get(i).repair();
        }

        ++mTurns;
    }

    public int[] predictIncomingDamageOrNull() {
        int shipCount = mShips.size();
        if (shipCount == 0) {
            return null;
        }

        if (shipCount == 1) {
            return new int[]{ 0 };
        }

        return predictIncomingDamageInternal(shipCount);
    }

    private Ship parseShip(String line) {
        String[] tokens = line.split(",", -1);

        String shipName = tokens[0];
        EHullGrade shipHullGrade = EHullGrade.valueOf(tokens[1]);
        int initialHull = Integer.parseInt(tokens[2]);
        int laserDamage = Integer.parseInt(tokens[3]);
        int shield = Integer.parseInt(tokens[4]);
        int repairAmount = Integer.parseInt(tokens[5]);

        return new Ship(shipName, shipHullGrade, initialHull, laserDamage, shield, repairAmount);
    }

    private int[] predictIncomingDamageInternal(int shipCount) {
        int[] predictedDamages = new int[shipCount];

        for (int defenderIndex = 0; defenderIndex < shipCount; ++defenderIndex) {
            int attackerIndex = (defenderIndex + 1) % shipCount;

            Ship attacker = mShips.get(attackerIndex);
            Ship defender = mShips.get(defenderIndex);

            predictedDamages[defenderIndex] = attacker.calculateLaserDamageTo(defender);
        }

        return predictedDamages;
    }

    private void removeDestroyedShips() {
        for (int i = mShips.size() - 1; i >= 0; --i) {
            if (mShips.get(i).getHull() == 0) {
                mShips.remove(i);
            }
        }
    }

}
