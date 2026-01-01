package com.example.bignumber;

public class BigNumberCalculator {
    public static String addOrNull(String num1, String num2) {
        String leftNormalized = normalizeSignedIntegerOrNull(num1);
        String rightNormalized = normalizeSignedIntegerOrNull(num2);

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

    public static String subtractOrNull(String num1, String num2) {
        String leftNormalized = normalizeSignedIntegerOrNull(num1);
        String rightNormalized = normalizeSignedIntegerOrNull(num2);

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

    private static boolean isValidSignedInteger(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }

        if (num.length() == 1 && num.charAt(0) == '-') {
            return false;
        }

        for (int i = 0; i < num.length(); ++i) {
            char c = num.charAt(i);

            if (c < '0' || c > '9') {
                if (i == 0 && c == '-') {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    private static String normalizeSignedIntegerOrNull(String num) {
        if (!isValidSignedInteger(num)) {
            return null;
        }

        boolean isNegative = false;
        int firstDigitIndex = 0;

        if (num.charAt(0) == '-') {
            isNegative = true;
            firstDigitIndex = 1;
        }

        while (firstDigitIndex < num.length() && num.charAt(firstDigitIndex) == '0') {
            firstDigitIndex++;
        }

        if (firstDigitIndex == num.length()) {
            return "0";
        }

        if (isNegative) {
            return "-" + num.substring(firstDigitIndex);
        }

        return num.substring(firstDigitIndex);
    }

    private static String extractAbsDigits(String signedNum) {
        if (signedNum.charAt(0) == '-') {
            return signedNum.substring(1);
        }

        return signedNum;
    }

    private static int compareAbsDigits(String digits1, String digits2) {
        if (digits1.length() > digits2.length()) {
            return 1;
        }

        if (digits1.length() < digits2.length()) {
            return -1;
        }

        for (int i = 0; i < digits1.length(); ++i) {
            char leftDigit = digits1.charAt(i);
            char rightDigit = digits2.charAt(i);

            if (leftDigit > rightDigit) {
                return 1;
            }

            if (leftDigit < rightDigit) {
                return -1;
            }
        }

        return 0;
    }

    private static String addAbsDigits(String digits1, String digits2) {
        String longerDigits;
        String shorterDigits;
        int longerLength;
        int shorterLength;

        if (digits1.length() > digits2.length()) {
            longerDigits = digits1;
            shorterDigits = digits2;
        } else {
            longerDigits = digits2;
            shorterDigits = digits1;
        }

        longerLength = longerDigits.length();
        shorterLength = shorterDigits.length();

        StringBuilder resultDigits = new StringBuilder(longerLength + 1);
        for (int i = 0; i < longerLength + 1; ++i) {
            resultDigits.append('0');
        }

        int alignmentOffset = longerLength - shorterLength;
        for (int i = longerLength; i >= 1; --i) {
            if (i - alignmentOffset - 1 >= 0) {
                resultDigits.setCharAt(i, (char) (longerDigits.charAt(i - 1) + shorterDigits.charAt(i - alignmentOffset - 1) - '0'));
            } else {
                resultDigits.setCharAt(i, longerDigits.charAt(i - 1));
            }
        }

        for (int i = longerLength; i >= 1; --i) {
            if (resultDigits.charAt(i) > '9') {
                resultDigits.setCharAt(i, (char) (resultDigits.charAt(i) - 10));
                resultDigits.setCharAt(i - 1, (char) (resultDigits.charAt(i - 1) + 1));
            }
        }

        if (resultDigits.charAt(0) == '0') {
            resultDigits.deleteCharAt(0);
        }

        return resultDigits.toString();
    }

    private static String subtractAbsDigits(String digits1, String digits2) {
        if (compareAbsDigits(digits1, digits2) < 0) {
            return subtractAbsDigits(digits2, digits1);
        }

        int longerLength = digits1.length();
        int shorterLength = digits2.length();

        StringBuilder resultDigits = new StringBuilder(longerLength);
        for (int i = 0; i < longerLength; ++i) {
            resultDigits.append('0');
        }

        int alignmentOffset = longerLength - shorterLength;

        for (int i = longerLength - 1; i >= 0; --i) {
            if (i - alignmentOffset >= 0) {
                resultDigits.setCharAt(i, (char) (digits1.charAt(i) - digits2.charAt(i - alignmentOffset) + '0'));
            } else {
                resultDigits.setCharAt(i, digits1.charAt(i));
            }
        }

        for (int i = longerLength - 1; i >= 1; --i) {
            if (resultDigits.charAt(i) < '0') {
                resultDigits.setCharAt(i, (char) (resultDigits.charAt(i) + 10));
                resultDigits.setCharAt(i - 1, (char) (resultDigits.charAt(i - 1) - 1));
            }
        }

        while (resultDigits.length() > 1 && resultDigits.charAt(0) == '0') {
            resultDigits.deleteCharAt(0);
        }

        return resultDigits.toString();
    }
}
