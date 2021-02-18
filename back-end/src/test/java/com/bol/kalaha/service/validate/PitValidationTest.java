package com.bol.kalaha.service.validate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.PitTestSupplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PitValidationTest {
    private static final int EMPTY_PIT = 0;
    private static final int STONE = 1;
    private static final Pit SCORE_PIT = PitTestSupplier.getPit(PitType.SCORE);
    private static final Pit REGULAR_PIT = PitTestSupplier.getPit(PitType.REGULAR);

    @Test
    void shouldDetermineIsRegularPit() {
        assertFalse(PitValidation.isRegularPit(SCORE_PIT));
        assertTrue(PitValidation.isRegularPit(REGULAR_PIT));
    }

    @Test
    void shouldDetermineIsScorePit() {
        assertTrue(PitValidation.isScorePit(SCORE_PIT));
        assertFalse(PitValidation.isScorePit(REGULAR_PIT));
    }

    @Test
    void shouldDetermineIsEmpty() {
        assertFalse(PitValidation.isEmpty(SCORE_PIT));
        final Pit input = PitTestSupplier.getPit();
        input.setStones(EMPTY_PIT);
        assertTrue(PitValidation.isEmpty(input));
    }

    @Test
    void shouldDetermineIsFirstIteration() {
        final Pit pit = PitTestSupplier.getPit();
        pit.setLastSowedPit(false);
        final List<Pit> input = Collections.singletonList(pit);
        assertTrue(PitValidation.isFirstIteration(input));
        pit.setLastSowedPit(true);
        assertFalse(PitValidation.isFirstIteration(input));
    }

    @Test
    void shouldDetermineHasPit() {
        final List<Pit> input = Collections.singletonList(SCORE_PIT);
        assertTrue(PitValidation.hasPit(input, SCORE_PIT));
        assertFalse(PitValidation.hasPit(input, REGULAR_PIT));
    }

    @Test
    void shouldDetermineIsEndGame() {
        assertTrue(PitValidation.isEndGame(
            Collections.singletonList(SCORE_PIT)
        ));
        assertFalse(PitValidation.isEndGame(
            Collections.singletonList(REGULAR_PIT)
        ));
        final Pit input = PitTestSupplier.getPit(PitType.REGULAR);
        input.setStones(EMPTY_PIT);
        assertTrue(PitValidation.isEndGame(
            Collections.singletonList(input)
        ));
    }

    @Test
    void shouldDetermineIsCapturePit() {
        final Pit regularPit = PitTestSupplier.getPit(PitType.REGULAR);
        regularPit.setStones(STONE);
        final List<Pit> pits = Arrays.asList(regularPit, SCORE_PIT);
        assertTrue(PitValidation.isCapturePit(regularPit, pits));

        regularPit.setStones(STONE + STONE);
        assertFalse(PitValidation.isCapturePit(regularPit, pits));

        assertFalse(PitValidation.isCapturePit(SCORE_PIT, pits));
    }
}
