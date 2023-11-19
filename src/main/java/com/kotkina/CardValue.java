package com.kotkina;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum CardValue {
    TWO('2', 2), THREE('3', 3), FOUR('4', 4), FIVE('5', 5), SIX('6', 6),
    SEVEN('7', 7), EIGHT('8', 8), NINE('9', 9), TEN('T', 10),
    JACK('J', 11), QUEEN('Q', 12), KING('K', 13), ACE('A', 14);

    private char val;
    private int num;

    CardValue(char val, int num) {
        this.val = val;
        this.num = num;
    }

    private static final Map<Character, Integer> cardValuesMap = new HashMap<>();

    static {
        cardValuesMap.putAll(Arrays.stream(CardValue.values())
                .collect(Collectors.toMap(CardValue::getVal, CardValue::getNum)));
    }

    public static int getNumByVal(char val) {
        return cardValuesMap.getOrDefault(val, 0);
    }

    public char getVal() {
        return val;
    }

    public int getNum() {
        return num;
    }
}
