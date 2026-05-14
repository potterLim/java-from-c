package com.example.spaceconvoy;

public final class Ship {
    private static final double LIGHT_MULTIPLIER = 1.25;
    private static final double MEDIUM_MULTIPLIER = 1.00;
    private static final double HEAVY_MULTIPLIER = 0.85;
    private static final double ARMORED_MULTIPLIER = 0.70;

    private static final int MINIMUM_DAMAGE = 1;

    private final String mName;
    private final EHullGrade mHullGrade;
    private int mHull;
    private final int mLaserDamage;
    private final int mShield;
    private final int mRepairAmount;

    public Ship(String name, EHullGrade hullGrade, int hull, int laserDamage, int shield, int repairAmount) {
        mName = name;
        mHullGrade = hullGrade;
        mHull = hull;
        mLaserDamage = laserDamage;
        mShield = shield;
        mRepairAmount = repairAmount;
    }

    public String getName() {
        return mName;
    }

    public EHullGrade getHullGrade() {
        return mHullGrade;
    }

    public int getHull() {
        return mHull;
    }

    public int getLaserDamage() {
        return mLaserDamage;
    }

    public int getShield() {
        return mShield;
    }

    public int getRepairAmount() {
        return mRepairAmount;
    }

    public void applyHullChange(int delta) {
        mHull += delta;

        if (mHull < 0) {
            mHull = 0;
        }
    }

    public void fireLaser(Ship otherShip) {
        int finalDamage = calculateLaserDamageTo(otherShip);
        otherShip.applyHullChange(-finalDamage);
    }

    public void repair() {
        applyHullChange(mRepairAmount);
    }

    int calculateLaserDamageTo(Ship otherShip) {
        int damageAfterShield = mLaserDamage - otherShip.mShield;
        if (damageAfterShield <= 0) {
            return 0;
        }

        double hullMultiplier = getDamageMultiplier(otherShip.mHullGrade);

        int finalDamage = (int) (damageAfterShield * hullMultiplier);
        if (finalDamage < MINIMUM_DAMAGE) {
            finalDamage = MINIMUM_DAMAGE;
        }

        return finalDamage;
    }

    private static double getDamageMultiplier(EHullGrade hullGrade) {
        switch (hullGrade) {
            case LIGHT:
                return LIGHT_MULTIPLIER;
            case MEDIUM:
                return MEDIUM_MULTIPLIER;
            case HEAVY:
                return HEAVY_MULTIPLIER;
            case ARMORED:
                return ARMORED_MULTIPLIER;
            default:
                assert (false) : "Unexpected hull grade: " + hullGrade;
                return MEDIUM_MULTIPLIER;
        }
    }
}
