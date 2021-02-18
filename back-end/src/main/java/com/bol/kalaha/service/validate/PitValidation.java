package com.bol.kalaha.service.validate;

import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;

public final class PitValidation {
    private static final int EMPTY_PIT = 0;
    private static final int STONE = 1;

    private PitValidation() {
    }

    public static boolean isRegularPit(final Pit pit) {
        return pit.getType() == PitType.REGULAR;
    }

    public static boolean isScorePit(final Pit pit) {
        return pit.getType() == PitType.SCORE;
    }

    public static boolean isEmpty(final Pit pit) {
        return pit.getStones() == EMPTY_PIT;
    }

    public static boolean isFirstIteration(final List<Pit> pits) {
        return pits.stream().noneMatch(Pit::isLastSowedPit);
    }

    public static boolean hasPit(final List<Pit> pits, final Pit pit) {
        return pits.contains(pit);
    }

    public static boolean hasLastSowedPit(final List<Pit> pits) {
        return pits.stream().anyMatch(Pit::isLastSowedPit);
    }

    public static boolean isEndGame(final List<Pit> pits) {
        return pits.stream()
            .filter(PitValidation::isRegularPit)
            .allMatch(PitValidation::isEmpty);
    }

    public static boolean isCapturePit(final Pit lastSowedPit, final List<Pit> pits) {
        return PitValidation.hasPit(pits, lastSowedPit) &&
            PitValidation.isRegularPit(lastSowedPit) &&
            lastSowedPit.getStones() == STONE;
    }
}
