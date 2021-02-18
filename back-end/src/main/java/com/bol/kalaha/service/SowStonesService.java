package com.bol.kalaha.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.service.validate.PitValidation;
import org.springframework.stereotype.Service;

@Service
public class SowStonesService {
    private static final int EMPTY_PIT = 0;
    private static final int STONE = 1;
    private static final int NO_PIT_SKIP = 0;

    public boolean sowStones(final List<Pit> pits, final Pit turnPit) {
        final AtomicBoolean hasSowedTurnPit = new AtomicBoolean(false);
        pits
            .stream()
            .skip(skipPits(turnPit, pits))
            .filter(pit -> filterPitType(pit, PitValidation.hasPit(pits, turnPit)))
            .limit(turnPit.getStones())
            .forEach(pit -> {
                if (pit.isTurnPit()) {
                    hasSowedTurnPit.set(true);
                }
                sowStone(pits, pit, turnPit);
            });
        return hasSowedTurnPit.get();
    }

    private void sowStone(final List<Pit> pits, final Pit pitToSow, final Pit turnPit) {
        removeStone(turnPit);
        addStone(pitToSow);
        pits.forEach(pit -> pit.setLastSowedPit(false));
        pitToSow.setLastSowedPit(true);
    }

    private int skipPits(final Pit turnPit, final List<Pit> pits) {
        if (PitValidation.hasPit(pits, turnPit) && PitValidation.isFirstIteration(pits)) {
            return pits.indexOf(turnPit) + STONE;
        }
        return NO_PIT_SKIP;
    }

    private boolean filterPitType(final Pit pit, final boolean hasTurnPit) {
        if (hasTurnPit) {
            return true;
        }
        return PitValidation.isRegularPit(pit);
    }

    public void addStone(final Pit pit) {
        pit.setStones(pit.getStones() + STONE);
    }

    private void removeStone(final Pit pit) {
        pit.setStones(pit.getStones() - STONE);
    }

    public void mergePitStones(final Pit scorePit, final List<Pit> pits) {
        pits.stream()
            .filter(PitValidation::isRegularPit)
            .filter(pit -> !PitValidation.isEmpty(pit))
            .forEach(pit -> {
                scorePit.setStones(scorePit.getStones() + pit.getStones());
                pit.setStones(EMPTY_PIT);
            });
    }
}
