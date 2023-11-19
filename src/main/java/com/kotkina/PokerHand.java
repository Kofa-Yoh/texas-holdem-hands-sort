package com.kotkina;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PokerHand implements Comparable<PokerHand> {

    private static final int CARDS_NUMBER = 5;
    private String hand;
    private List<Integer> values = new ArrayList<>();
    private List<Character> suits = new ArrayList<>();
    private Combination mainCombination = Combination.HIGH_CARD;
    private int firstMaxInCombination = 0;
    private int secondMaxInCombination = 0;

    public PokerHand(String hand) {
        checkHand(hand);
        this.hand = hand;

        String[] cards = hand.split(" ");
        for (String card : cards) {
            values.add(CardValue.getNumByVal(card.charAt(0)));
            suits.add(card.charAt(1));
        }
        checkValuesAndSuits();
        values.sort(Comparator.naturalOrder());
        suits.sort(Comparator.naturalOrder());

        defineCombinationAndMaxValues();
    }

    private void checkHand(String hand) {
        if (hand == null || hand.trim().length() == 0) {
            throw new IllegalArgumentException("Hand shouldn't be empty");
        }
    }

    private void checkValuesAndSuits() {
        if (values.contains(0) || suits.contains(' ')) {
            throw new IllegalArgumentException("Hand contains illegal symbol");
        }
        if (values.size() != CARDS_NUMBER || suits.size() != CARDS_NUMBER) {
            throw new IllegalArgumentException("Hand doesn't have enough cards");
        }
    }

    private void defineCombinationAndMaxValues() {
        boolean haveSameSuit = suits.get(suits.size() - 1) == suits.get(0);

        int sameValuesNumber = 1;
        int nearbyValuesNumber = 1;

        Integer prevVal = null;
        Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()) {
            Integer val = iterator.next();
            if (prevVal == null) {
                prevVal = val;
                continue;
            }

            int dif = val - prevVal;
            if (dif == 0) {
                nearbyValuesNumber = 1;
                sameValuesNumber++;
            } else {
                if (sameValuesNumber > 1) {
                    setCombinationAndMaxValues(prevVal, sameValuesNumber, nearbyValuesNumber, haveSameSuit);
                    sameValuesNumber = 1;
                }
                if (dif == 1) {
                    nearbyValuesNumber++;
                } else {
                    nearbyValuesNumber = 1;
                }
            }
            prevVal = val;
        }

        if (mainCombination.getOrder() >= Combination.FULL_HOUSE.getOrder()) return;

        setCombinationAndMaxValues(prevVal, sameValuesNumber, nearbyValuesNumber, haveSameSuit);
    }

    private void setCombinationAndMaxValues(int val, int sameValuesNumber, int nearbyValuesNumber, boolean haveSameSuit) {
        switch (sameValuesNumber) {
            case 1:
                break;
            case 4:
                mainCombination = Combination.FOUR_OF_A_KIND;
                firstMaxInCombination = val;
                return;
            case 3:
                if (mainCombination == Combination.PAIR) {
                    mainCombination = Combination.FULL_HOUSE;
                    secondMaxInCombination = firstMaxInCombination;
                } else {
                    mainCombination = Combination.THREE_OF_A_KIND;
                }
                firstMaxInCombination = val;
                return;
            case 2:
                if (mainCombination == Combination.THREE_OF_A_KIND) {
                    mainCombination = Combination.FULL_HOUSE;
                    secondMaxInCombination = val;
                    return;
                } else if (mainCombination == Combination.PAIR) {
                    mainCombination = Combination.TWO_PAIRS;
                    secondMaxInCombination = firstMaxInCombination < val ? firstMaxInCombination : val;
                    firstMaxInCombination = firstMaxInCombination < val ? val : firstMaxInCombination;
                    return;
                } else {
                    mainCombination = Combination.PAIR;
                    firstMaxInCombination = val;
                    return;
                }
        }
        if (nearbyValuesNumber == values.size()) {
            mainCombination = haveSameSuit ? Combination.STRAIGHT_FLUSH : Combination.STRAIGHT;
            firstMaxInCombination = val;
        } else if (haveSameSuit) {
            mainCombination = Combination.FLUSH;
            firstMaxInCombination = val;
        } else if (mainCombination == Combination.HIGH_CARD) {
            firstMaxInCombination = values.get(values.size() - 1);
        }
    }

    @Override
    public int compareTo(PokerHand o) {
        if (o.values == null || values == null) {
            return 0;
        }

        int combinationCompare = Integer.compare(o.mainCombination.getOrder(), mainCombination.getOrder());
        if (combinationCompare != 0) {
            return combinationCompare;
        }

        int firstMaxCompare = Integer.compare(o.firstMaxInCombination, firstMaxInCombination);
        if (firstMaxCompare != 0) {
            return firstMaxCompare;
        }

        int secondMaxCompare = Integer.compare(o.secondMaxInCombination, secondMaxInCombination);
        if (secondMaxCompare != 0) {
            return secondMaxCompare;
        }

        for (int i = values.size() - 1; i >= 0; i--) {
            int compare = Integer.compare(o.values.get(i), values.get(i));
            if (compare != 0) {
                return compare;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return String.format("PokerHand{\'%s\', %s}", hand, mainCombination);
    }

    public Combination getMainCombination() {
        return mainCombination;
    }

    public String getHand() {
        return hand;
    }
}
