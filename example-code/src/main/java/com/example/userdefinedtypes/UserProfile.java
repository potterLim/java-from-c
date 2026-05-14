package com.example.userdefinedtypes;

public class UserProfile {
    private String mNickname;
    private ESubscriptionTier mTier;

    public UserProfile() {
        // mNickname is not explicitly initialized -> default value is null
        // mTier is not explicitly initialized     -> default value is null
    }

    public UserProfile(String nicknameOrNull) {
        mNickname = nicknameOrNull;
        mTier = ESubscriptionTier.FREE;
    }

    public UserProfile(String nicknameOrNull, ESubscriptionTier tierOrNull) {
        mNickname = nicknameOrNull;

        if (tierOrNull == null) {
            mTier = ESubscriptionTier.FREE;
        } else {
            mTier = tierOrNull;
        }
    }

    public String getNicknameOrNull() {
        return mNickname;
    }

    public ESubscriptionTier getTierOrNull() {
        return mTier;
    }

    public void printSummary() {
        String printedNickname = mNickname;
        if (printedNickname == null) {
            printedNickname = "(not set)";
        }

        String printedTier = "(not set)";
        if (mTier != null) {
            printedTier = mTier.toString();
        }

        System.out.println("nickname = " + printedNickname);
        System.out.println("tier     = " + printedTier);
    }
}
