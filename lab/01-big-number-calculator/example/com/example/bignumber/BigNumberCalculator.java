package com.example.bignumber;

public final class BigNumberCalculator {
    private BigNumberCalculator() {
    }

    public static String addOrNull(String leftNumberOrNull, String rightNumberOrNull) {
        String leftNormalized = normalizeSignedIntegerOrNull(leftNumberOrNull);
        String rightNormalized = normalizeSignedIntegerOrNull(rightNumberOrNull);

        if (leftNormalized == null || rightNormalized == null) {
            return null;
        }

        if (leftNormalized.equals("0")) {
            return rightNormalized;
        }

        if (rightNormalized.equals("0")) {
            return leftNormalized;
        }

        boolean isLeftNegative = (leftNormalized.charAt(0) == '-');
        boolean isRightNegative = (rightNormalized.charAt(0) == '-');

        String leftAbsDigits = extractAbsDigits(leftNormalized);
        String rightAbsDigits = extractAbsDigits(rightNormalized);

        if (isLeftNegative == isRightNegative) {
            String sumAbsDigits = addAbsDigits(leftAbsDigits, rightAbsDigits);

            if (isLeftNegative) {
                return "-" + sumAbsDigits;
            }

            return sumAbsDigits;
        }

        int absCompareResult = compareAbsDigits(leftAbsDigits, rightAbsDigits);
        if (absCompareResult == 0) {
            return "0";
        }

        String largerAbsDigits;
        String smallerAbsDigits;
        boolean isNegativeResult;

        if (absCompareResult > 0) {
            largerAbsDigits = leftAbsDigits;
            smallerAbsDigits = rightAbsDigits;
            isNegativeResult = isLeftNegative;
        } else {
            largerAbsDigits = rightAbsDigits;
            smallerAbsDigits = leftAbsDigits;
            isNegativeResult = isRightNegative;
        }

        String diffAbsDigits = subtractAbsDigits(largerAbsDigits, smallerAbsDigits);

        if (isNegativeResult) {
            return "-" + diffAbsDigits;
        }

        return diffAbsDigits;
    }

    public static String subtractOrNull(String leftNumberOrNull, String rightNumberOrNull) {
        String leftNormalized = normalizeSignedIntegerOrNull(leftNumberOrNull);
        String rightNormalized = normalizeSignedIntegerOrNull(rightNumberOrNull);

        if (leftNormalized == null || rightNormalized == null) {
            return null;
        }

        if (rightNormalized.equals("0")) {
            return leftNormalized;
        }

        if (leftNormalized.equals("0")) {
            if (rightNormalized.charAt(0) == '-') {
                return extractAbsDigits(rightNormalized);
            }

            return "-" + rightNormalized;
        }

        String invertedRight;
        if (rightNormalized.charAt(0) == '-') {
            invertedRight = extractAbsDigits(rightNormalized);
        } else {
            invertedRight = "-" + rightNormalized;
        }

        return addOrNull(leftNormalized, invertedRight);
    }

    private static boolean isValidSignedInteger(String numberOrNull) {
        if (numberOrNull == null || numberOrNull.length() == 0) {
            return false;
        }

        if (numberOrNull.length() == 1 && numberOrNull.charAt(0) == '-') {
            return false;
        }

        for (int i = 0; i < numberOrNull.length(); ++i) {
            char currentChar = numberOrNull.charAt(i);

            if (currentChar < '0' || currentChar > '9') {
                if (i == 0 && currentChar == '-') {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    private static String normalizeSignedIntegerOrNull(String numberOrNull) {
        if (!isValidSignedInteger(numberOrNull)) {
            return null;
        }

        boolean isNegative = false;
        int firstDigitIndex = 0;

        if (numberOrNull.charAt(0) == '-') {
            isNegative = true;
            firstDigitIndex = 1;
        }

        while (firstDigitIndex < numberOrNull.length() && numberOrNull.charAt(firstDigitIndex) == '0') {
            ++firstDigitIndex;
        }

        if (firstDigitIndex == numberOrNull.length()) {
            return "0";
        }

        if (isNegative) {
            return "-" + numberOrNull.substring(firstDigitIndex);
        }

        return numberOrNull.substring(firstDigitIndex);
    }

    private static String extractAbsDigits(String signedNumber) {
        if (signedNumber.charAt(0) == '-') {
            return signedNumber.substring(1);
        }

        return signedNumber;
    }

    private static int compareAbsDigits(String leftDigits, String rightDigits) {
        if (leftDigits.length() > rightDigits.length()) {
            return 1;
        }

        if (leftDigits.length() < rightDigits.length()) {
            return -1;
        }

        for (int i = 0; i < leftDigits.length(); ++i) {
            char leftDigit = leftDigits.charAt(i);
            char rightDigit = rightDigits.charAt(i);

            if (leftDigit > rightDigit) {
                return 1;
            }

            if (leftDigit < rightDigit) {
                return -1;
            }
        }

        return 0;
    }

    private static String addAbsDigits(String leftDigits, String rightDigits) {
        int leftIndex = leftDigits.length() - 1;
        int rightIndex = rightDigits.length() - 1;
        int carry = 0;

        StringBuilder reversedResultDigits = new StringBuilder(Math.max(leftDigits.length(), rightDigits.length()) + 1);

        while (leftIndex >= 0 || rightIndex >= 0 || carry > 0) {
            int leftDigit = 0;
            if (leftIndex >= 0) {
                leftDigit = leftDigits.charAt(leftIndex) - '0';
            }

            int rightDigit = 0;
            if (rightIndex >= 0) {
                rightDigit = rightDigits.charAt(rightIndex) - '0';
            }

            int digitSum = leftDigit + rightDigit + carry;
            reversedResultDigits.append((char) ('0' + (digitSum % 10)));
            carry = digitSum / 10;

            --leftIndex;
            --rightIndex;
        }

        return reversedResultDigits.reverse().toString();
    }

    private static String subtractAbsDigits(String leftDigits, String rightDigits) {
        if (compareAbsDigits(leftDigits, rightDigits) < 0) {
            return subtractAbsDigits(rightDigits, leftDigits);
        }

        int leftIndex = leftDigits.length() - 1;
        int rightIndex = rightDigits.length() - 1;
        int borrow = 0;

        StringBuilder reversedResultDigits = new StringBuilder(leftDigits.length());

        while (leftIndex >= 0) {
            int leftDigit = (leftDigits.charAt(leftIndex) - '0') - borrow;

            int rightDigit = 0;
            if (rightIndex >= 0) {
                rightDigit = rightDigits.charAt(rightIndex) - '0';
            }

            if (leftDigit < rightDigit) {
                leftDigit += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            int digitDifference = leftDigit - rightDigit;
            reversedResultDigits.append((char) ('0' + digitDifference));

            --leftIndex;
            --rightIndex;
        }

        while (reversedResultDigits.length() > 1 && reversedResultDigits.charAt(reversedResultDigits.length() - 1) == '0') {
            reversedResultDigits.deleteCharAt(reversedResultDigits.length() - 1);
        }

        return reversedResultDigits.reverse().toString();
    }
}
