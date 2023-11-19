package com.kotkina;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTests {

    private static boolean checkCombination(String hand, Combination combination) {
        return new PokerHand(hand).getMainCombination() == combination;
    }

    static Stream<Arguments> handsCombinations() {
        return Stream.of(
                Arguments.of("4S 5S 6S QC AD", Combination.HIGH_CARD),
                Arguments.of("4S JS 5S QC 4D", Combination.PAIR),
                Arguments.of("4C 5S 6S 7S 8S", Combination.STRAIGHT),
                Arguments.of("4S JS 5S JC JD", Combination.THREE_OF_A_KIND),
                Arguments.of("4S 5S 6S 7S 9S", Combination.FLUSH),
                Arguments.of("4S JS 5S JC 4D", Combination.TWO_PAIRS),
                Arguments.of("4S JS 4C JC JD", Combination.FULL_HOUSE),
                Arguments.of("8D 8H 6S 8C 8S", Combination.FOUR_OF_A_KIND),
                Arguments.of("4S 5S 6S 7S 8S", Combination.STRAIGHT_FLUSH)
        );
    }

    @ParameterizedTest
    @MethodSource("handsCombinations")
    public void correctCombinationTest(String hand, Combination combination) {
        assertTrue(checkCombination(hand, combination));
    }

    @ParameterizedTest
    @CsvSource({"4S 5S 6S 7S 8S,8D 8H 6S 8C 8S,-1", //STRAIGHT_FLUSH, FOUR_OF_A_KIND
            "4S 5S 6S QC AD,2C 5C 6C QH AS,-1", //HIGH_CARD, HIGH_CARD
            "AS TS AC TC TD,4S JS 4C JC JD,1", //FULL_HOUSE, FULL_HOUSE
            "4S JS 4C JC JD,4S JS 4C JC JD,0" //FULL_HOUSE, FULL_HOUSE
    })
    public void compareCombinations(String hand1, String hand2, int compareResult) {
        assertTrue(new PokerHand(hand1).compareTo(new PokerHand(hand2)) == compareResult);
    }

    @Test
    public void sortTest() {
        List<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand("4S 5S 6S KC AD")); // HIGH_CARD A K 6 5 4
        hands.add(new PokerHand("4S 5S 6S 7S 8S")); // STRAIGHT_FLUSH
        hands.add(new PokerHand("JD JH KS JC JS")); // FOUR_OF_A_KIND
        hands.add(new PokerHand("JS KS 6S QC AD")); // HIGH_CARD A K Q J 6

        Collections.sort(hands);
        List<String> sortedHandsList = hands.stream()
                .map(PokerHand::getHand)
                .toList();

        List<String> rightHandsList = List.of(
                "4S 5S 6S 7S 8S", // STRAIGHT_FLUSH
                "JD JH KS JC JS", // FOUR_OF_A_KIND
                "JS KS 6S QC AD", // HIGH_CARD A K Q J 6
                "4S 5S 6S KC AD" // HIGH_CARD A K 6 5 4
        );

        assertIterableEquals(sortedHandsList, rightHandsList);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1S 5S 6S 7S 8S", "5S 5S 6S 7S   "})
    @NullSource
    public void exceptionThrownTest(String hand) {
        assertThrows(IllegalArgumentException.class, () -> {
            new PokerHand(hand);
        });
    }
}