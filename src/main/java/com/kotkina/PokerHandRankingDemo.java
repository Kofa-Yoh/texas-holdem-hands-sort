package com.kotkina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerHandRankingDemo {

    public static void main(String[] args) {

        List<PokerHand> hands = new ArrayList<>();
        try {
            hands.add(new PokerHand("KS 2H 5C JD TD"));
            hands.add(new PokerHand("KS 2H 5C JD JD"));
            hands.add(new PokerHand("KS 2H 2C JD TD"));
            hands.add(new PokerHand("KS 2H 2C JD 2D"));
            hands.add(new PokerHand("JS 2H 2C JD 2D"));
            hands.add(new PokerHand("JS 2H 5C JD 2D"));
            hands.add(new PokerHand("2C 3C JC 4C 5C"));
            hands.add(new PokerHand("5S 3H 2H JH AH"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        Collections.sort(hands);
        hands.forEach(System.out::println);
    }
}
