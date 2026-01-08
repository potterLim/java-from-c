package com.example.userdefinedtypes;

public class UserProfile {
    private String nickname;
    private SubscriptionTier tier;

    public UserProfile() {
        // nickname is not explicitly initialized -> default value is null
        // tier is not explicitly initialized     -> default value is null
    }

    public UserProfile(String nickname) {
        this.nickname = nickname;
        tier = SubscriptionTier.FREE;
    }

    public UserProfile(String nickname, SubscriptionTier tier) {
        this.nickname = nickname;

        if (tier == null) {
            this.tier = SubscriptionTier.FREE;
        } else {
            this.tier = tier;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public SubscriptionTier getTier() {
        return tier;
    }

    public void printSummary() {
        String printedNickname = nickname;
        if (printedNickname == null) {
            printedNickname = "(not set)";
        }

        String printedTier = "(not set)";
        if (tier != null) {
            printedTier = tier.toString();
        }

        System.out.println("nickname = " + printedNickname);
        System.out.println("tier     = " + printedTier);
    }
}
