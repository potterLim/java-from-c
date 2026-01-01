package com.example.spaceconvoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConvoyLane {
    private static final double LIGHT_MULTIPLIER = 1.25;
    private static final double MEDIUM_MULTIPLIER = 1.00;
    private static final double HEAVY_MULTIPLIER = 0.85;
    private static final double ARMORED_MULTIPLIER = 0.70;

    private static final int MINIMUM_DAMAGE = 1;

    private final String laneName;
    private final int capacity;
    private int turns;

    private final ArrayList<Ship> ships;

    public ConvoyLane(String laneName, int capacity) {
        this.laneName = laneName;
        this.capacity = capacity;
        this.turns = 0;
        this.ships = new ArrayList<>();
    }

    public String getLaneName() {
        return laneName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTurns() {
        return turns;
    }

    public void loadShips(String filePath) {
        ships.clear();
        turns = 0;

        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));

            for (String line : lines) {
                if (ships.size() >= capacity) {
                    break;
                }

                if (line.isBlank()) {
                    continue;
                }

                ships.add(parseShip(line));
            }
        } catch (IOException e) {
            ships.clear();
            turns = 0;
        }
    }

    public void advanceTurn() {
        int shipCount = ships.size();
        if (shipCount <= 1) {
            return;
        }

        int[] predictedDamages = predictIncomingDamageInternal(shipCount);

        for (int defenderIndex = 0; defenderIndex < shipCount; ++defenderIndex) {
            ships.get(defenderIndex).applyHullChange(-predictedDamages[defenderIndex]);
        }

        removeDestroyedShips();

        for (int i = 0; i < ships.size(); ++i) {
            ships.get(i).repair();
        }

        turns++;
    }

    public int[] predictIncomingDamageOrNull() {
        int shipCount = ships.size();
        if (shipCount == 0) {
            return null;
        }

        return predictIncomingDamageInternal(shipCount);
    }

    private Ship parseShip(String line) {
        String[] tokens = line.split(",", -1);

        String shipName = tokens[0];
        HullGrade shipHullGrade = HullGrade.valueOf(tokens[1]);
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

            Ship attacker = ships.get(attackerIndex);
            Ship defender = ships.get(defenderIndex);

            int damageAfterShield = attacker.getLaserDamage() - defender.getShield();
            if (damageAfterShield <= 0) {
                predictedDamages[defenderIndex] = 0;
                continue;
            }

            double hullMultiplier;
            switch (defender.getHullGrade()) {
                case LIGHT:
                    hullMultiplier = LIGHT_MULTIPLIER;
                    break;
                case MEDIUM:
                    hullMultiplier = MEDIUM_MULTIPLIER;
                    break;
                case HEAVY:
                    hullMultiplier = HEAVY_MULTIPLIER;
                    break;
                case ARMORED:
                    hullMultiplier = ARMORED_MULTIPLIER;
                    break;
                default:
                    hullMultiplier = MEDIUM_MULTIPLIER;
                    break;
            }

            int finalDamage = (int) (damageAfterShield * hullMultiplier);
            if (finalDamage < MINIMUM_DAMAGE) {
                finalDamage = MINIMUM_DAMAGE;
            }

            predictedDamages[defenderIndex] = finalDamage;
        }

        return predictedDamages;
    }

    private void removeDestroyedShips() {
        for (int i = ships.size() - 1; i >= 0; --i) {
            if (ships.get(i).getHull() == 0) {
                ships.remove(i);
            }
        }
    }
}
