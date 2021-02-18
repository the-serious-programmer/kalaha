package com.bol.kalaha.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.PitTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SowServiceTest {
    private static final int EMPTY_PIT = 0;
    private static final int STONE = 1;

    private SowStonesService sowStonesService;
    private Pit turnPit;
    private Pit regularPitOne;
    private Pit regularPitThree;
    private Pit scorePit;

    @BeforeEach
    void setUp() {
        sowStonesService = new SowStonesService();
        turnPit = PitTestSupplier.getPit();
        turnPit.setTurnPit(true);
        scorePit = PitTestSupplier.getPit(0, PitType.SCORE);
        regularPitOne = PitTestSupplier.getPit(1, PitType.REGULAR);
        regularPitThree = PitTestSupplier.getPit(3, PitType.REGULAR);
    }

    @Test
    void shouldNotSowTurnPit() {
        final List<Pit> input = Arrays.asList(turnPit, scorePit, regularPitOne);

        final boolean result = sowStonesService.sowStones(input, turnPit);

        assertFalse(result);
    }

    @Test
    void shouldSowTurnPit() {
        turnPit.setLastSowedPit(true);
        final List<Pit> input = Collections.singletonList(turnPit);
        final int initialTurnPitStones = turnPit.getStones();

        final boolean result = sowStonesService.sowStones(input, turnPit);

        assertEquals(initialTurnPitStones, turnPit.getStones());
        assertTrue(result);
        assertTrue(turnPit.isLastSowedPit());
    }

    @Test
    void shouldOnlySowRegularPitsForOtherPlayerPits() {
        final List<Pit> input = Arrays.asList(scorePit, regularPitOne);

        final int expectedTurnPitStones = turnPit.getStones() - STONE;
        final int expectedRegularStones = regularPitOne.getStones() + STONE;

        sowStonesService.sowStones(input, turnPit);

        assertEquals(expectedTurnPitStones, turnPit.getStones());
        assertEquals(expectedRegularStones, regularPitOne.getStones());
    }

    @Test
    void shouldSkipTurnPitPosition() {
        final List<Pit> input = Arrays.asList(turnPit, regularPitOne, regularPitThree);
        input.forEach(pit -> pit.setLastSowedPit(false));
        final int expectedTurnPitStones = turnPit.getStones() - STONE - STONE;
        final int expectedPitOneStones = regularPitOne.getStones() + STONE;
        final int expectedPitThreeStones = regularPitThree.getStones() + STONE;

        sowStonesService.sowStones(input, turnPit);

        assertEquals(expectedTurnPitStones, turnPit.getStones());
        assertEquals(expectedPitOneStones, regularPitOne.getStones());
        assertEquals(expectedPitThreeStones, regularPitThree.getStones());
    }

    @Test
    void shouldNotSkipWhenNoTurnPit() {
        final List<Pit> input = Arrays.asList(regularPitOne, regularPitThree);

        final int expectedTurnPitStones = turnPit.getStones() - STONE - STONE;
        final int expectedRegularStones = regularPitThree.getStones() + STONE;

        sowStonesService.sowStones(input, turnPit);

        assertEquals(expectedTurnPitStones, turnPit.getStones());
        assertEquals(expectedRegularStones, regularPitOne.getStones());
        assertEquals(expectedRegularStones, regularPitThree.getStones());
    }

    @Test
    void shouldNotSkipWhenNoLastSowedPitIsSet() {
        final List<Pit> input = Arrays.asList(turnPit, regularPitOne, regularPitThree);

        final int expectedTurnPitStones = turnPit.getStones() - STONE - STONE;
        final int expectedRegularStones = regularPitThree.getStones() + STONE;

        sowStonesService.sowStones(input, turnPit);

        assertEquals(expectedTurnPitStones, turnPit.getStones());
        assertEquals(expectedRegularStones, regularPitOne.getStones());
        assertEquals(expectedRegularStones, regularPitThree.getStones());
    }

    @Test
    void shouldSetLastSowedPit() {
        final List<Pit> input = Arrays.asList(turnPit, regularPitOne, regularPitThree);

        sowStonesService.sowStones(input, turnPit);

        assertTrue(regularPitThree.isLastSowedPit());
    }

    @Test
    void shouldMergePitStones() {
        final int expectedStones = scorePit.getStones() + regularPitOne.getStones() + regularPitThree.getStones();
        final List<Pit> input = Arrays.asList(regularPitOne, regularPitThree);

        sowStonesService.mergePitStones(scorePit, input);

        assertEquals(expectedStones, scorePit.getStones());
        assertEquals(EMPTY_PIT, regularPitOne.getStones());
        assertEquals(EMPTY_PIT, regularPitThree.getStones());
    }
}
