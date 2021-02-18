package com.bol.kalaha.service;

import java.util.UUID;

import com.bol.kalaha.exception.PlayerException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.service.validate.PitValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bol.kalaha.exception.PlayerException.CURRENT_PLAYER_NOT_FOUND;
import static com.bol.kalaha.exception.PlayerException.OTHER_PLAYER_NOT_FOUND;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PitService pitService;
    private final PitSortService pitSortService;

    public void takeTurn(final Game game, final UUID playerId, final UUID pitId) {
        final Player currentPlayer = getCurrentPlayer(game, playerId);
        final Player otherPlayer = getOtherPlayer(game);

        final Pit lastSowedPit = pitService.takeTurn(pitId, currentPlayer, otherPlayer);
        pitSortService.sortPits(currentPlayer, otherPlayer);
        pitService.captureStones(lastSowedPit, currentPlayer, otherPlayer);
        setTurns(lastSowedPit, currentPlayer, otherPlayer);
    }

    private Player getCurrentPlayer(final Game game, final UUID playerId) {
        return game.getPlayers()
            .stream()
            .filter(player -> player.getId().equals(playerId))
            .filter(Player::isTurn)
            .findAny()
            .orElseThrow(() -> new PlayerException(CURRENT_PLAYER_NOT_FOUND));
    }

    private Player getOtherPlayer(final Game game) {
        return game
            .getPlayers()
            .stream()
            .filter(player -> !player.isTurn())
            .findAny()
            .orElseThrow(() -> new PlayerException(OTHER_PLAYER_NOT_FOUND));
    }

    private void setTurns(final Pit lastPit, final Player currentPlayer, final Player otherPlayer) {
        if (PitValidation.isEndGame(currentPlayer.getPits())) {
            endGame(currentPlayer, otherPlayer);
        } else if (lastPit.getType() != PitType.SCORE) {
            currentPlayer.setTurn(false);
            otherPlayer.setTurn(true);
        }
    }

    private void endGame(final Player currentPlayer, final Player otherPlayer) {
        currentPlayer.setTurn(false);
        otherPlayer.setTurn(false);
        pitService.emptyIntoScorePit(otherPlayer);
    }
}
