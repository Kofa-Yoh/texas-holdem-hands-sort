package com.kotkina;

public enum Combination {
    HIGH_CARD(0), PAIR(1), TWO_PAIRS(2), THREE_OF_A_KIND(3), STRAIGHT(4),
    FLUSH(5), FULL_HOUSE(6), FOUR_OF_A_KIND(7), STRAIGHT_FLUSH(8);

    private int order;

    Combination(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
