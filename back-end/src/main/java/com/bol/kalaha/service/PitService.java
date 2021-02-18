package com.bol.kalaha.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.bol.kalaha.exception.PitException;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.service.validate.PitValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bol.kalaha.exception.PitException.LAST_SOWED_NOT_FOUND;
import static com.bol.kalaha.exception.PitException.NOT_FOUND;

@Service
@AllArgsConstructor
public class PitService {

    private final SowStonesService sowStonesService;
    private final TurnPitService turnPitService;
    private final PitSortService pitSortService;

    public Pit takeTurn(final UUID pitId, final Player currentPlayer, final Player otherPlayer) {
        final List<Pit> currentPlayerPits = currentPlayer.getPits();
        turnPitService.setTurnPit(pitId, currentPlayerPits);

        final List<Pit> otherPlayerPits = otherPlayer.getPits();
        resetLastSowedPit(currentPlayerPits);
        resetLastSowedPit(otherPlayerPits);
        pitSortService.sortPitsForSowing(currentPlayerPits, otherPlayerPits);

        return sowPits(currentPlayerPits, otherPlayerPits);
    }

    private Pit sowPits(final List<Pit> currentPlayerPits, final List<Pit> otherPlayerPits) {
        final Pit turnPit = turnPitService.getTurnPit(currentPlayerPits);
        final boolean hasSowedTurnPit = sowStonesService.sowStones(currentPlayerPits, turnPit);
        sowStonesService.sowStones(otherPlayerPits, turnPit);
        if (!PitValidation.isEmpty(turnPit)) {
            sowPits(currentPlayerPits, otherPlayerPits);
        }
        setNewTurnPitStones(hasSowedTurnPit, turnPit);
        return getLastSowedPit(currentPlayerPits, otherPlayerPits);
    }

    private void setNewTurnPitStones(final boolean hasSowedTurnPit, final Pit turnPit) {
        if (hasSowedTurnPit) {
            sowStonesService.addStone(turnPit);
        }
    }

    private Pit getLastSowedPit(final List<Pit> currentPlayerPits, final List<Pit> otherPlayerPits) {
        if (PitValidation.hasLastSowedPit(otherPlayerPits)) {
            return getLastSowedPit(otherPlayerPits);
        }
        return getLastSowedPit(currentPlayerPits);
    }

    private void resetLastSowedPit(final List<Pit> pits) {
        pits.forEach(pit -> pit.setLastSowedPit(false));
    }

    private Pit getLastSowedPit(final List<Pit> pits) {
        return pits.stream()
            .filter(Pit::isLastSowedPit)
            .findAny()
            .orElseThrow(() -> new PitException(LAST_SOWED_NOT_FOUND));
    }

    public void captureStones(final Pit lastSowedPit, final Player currentPlayer, final Player otherPlayer) {
        if (PitValidation.isCapturePit(lastSowedPit, currentPlayer.getPits())) {
            final Pit oppositePit = getOppositePit(lastSowedPit, otherPlayer);
            final Pit scorePit = getScorePit(currentPlayer);
            final List<Pit> pitsToEmpty = Arrays.asList(oppositePit, lastSowedPit);
            sowStonesService.mergePitStones(scorePit, pitsToEmpty);
        }
    }

    private Pit getOppositePit(final Pit pit, final Player player) {
        final List<Pit> pits = player.getPits();
        final int oppositePitPosition = pits.size() - pit.getPosition();
        return pits.get(oppositePitPosition);
    }

    public void emptyIntoScorePit(final Player player) {
        final Pit scorePit = getScorePit(player);
        sowStonesService.mergePitStones(scorePit, player.getPits());
    }

    private Pit getScorePit(final Player player) {
        return player.getPits()
            .stream()
            .filter(PitValidation::isScorePit)
            .findAny()
            .orElseThrow(() -> new PitException(NOT_FOUND));
    }
}
