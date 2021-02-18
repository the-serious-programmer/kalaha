package com.bol.kalaha.model.supplier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.model.type.PlayerType;

public final class PitSupplier {
    private static final List<Integer> POSITIONS = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    private static final int REGULAR_STONES = 6;
    private static final int SCORE_STONES = 0;

    private PitSupplier() {
    }

    public static List<Pit> getDefaultPits(final PlayerType playerType) {
        if (playerType == PlayerType.SECOND) {
            Collections.reverse(POSITIONS);
        } else {
            Collections.sort(POSITIONS);
        }
        final List<Pit> pits = Arrays.asList(
            Pit.builder().type(PitType.SCORE).stones(SCORE_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build(),
            Pit.builder().type(PitType.REGULAR).stones(REGULAR_STONES).build()
        );
        return assignPositions(pits);
    }

    private static List<Pit> assignPositions(final List<Pit> pits) {
        for (int i = 0; i < POSITIONS.size(); i++) {
            pits.get(i).setPosition(POSITIONS.get(i));
        }
        return pits;
    }
}
