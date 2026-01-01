package com.example.spaceconvoy;

public class Ship {
    private static final double LIGHT_MULTIPLIER = 1.25;
    private static final double MEDIUM_MULTIPLIER = 1.00;
    private static final double HEAVY_MULTIPLIER = 0.85;
    private static final double ARMORED_MULTIPLIER = 0.70;

    private static final int MINIMUM_DAMAGE = 1;

    private final String name;
    private final HullGrade hullGrade;
    private int hull;
    private final int laserDamage;
    private final int shield;
    private final int repairAmount;

    public Ship(String name, HullGrade hullGrade, int hull, int laserDamage, int shield, int repairAmount) {
        this.name = name;
        this.hullGrade = hullGrade;
        this.hull = hull;
        this.laserDamage = laserDamage;
        this.shield = shield;
        this.repairAmount = repairAmount;
    }

    public String getName() {
        return name;
    }

    public HullGrade getHullGrade() {
        return hullGrade;
    }

    public int getHull() {
        return hull;
    }

    public int getLaserDamage() {
        return laserDamage;
    }

    public int getShield() {
        return shield;
    }

    public int getRepairAmount() {
        return repairAmount;
    }

    public void applyHullChange(int delta) {
        hull += delta;

        if (hull < 0) {
            hull = 0;
        }
    }

    public void fireLaser(Ship otherShip) {
        int damageAfterShield = laserDamage - otherShip.shield;
        if (damageAfterShield <= 0) {
            return;
        }

        double hullMultiplier;
        switch (otherShip.hullGrade) {
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

        otherShip.applyHullChange(-finalDamage);
    }

    public void repair() {
        applyHullChange(repairAmount);
    }
}
