package com.bol.kalaha.service;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.exception.PlayerException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.GameTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bol.kalaha.exception.PlayerException.CURRENT_PLAYER_NOT_FOUND;
import static com.bol.kalaha.exception.PlayerException.OTHER_PLAYER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    private static final int EMPTY_PIT = 0;

    @Mock
    private PitService pitService;

    @Mock
    private PitSortService pitSortService;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void shouldTakeTurn() {
        final Game game = GameTestSupplier.getGame();
        final Player player = game.getPlayers().get(0);
        final List<Pit> pits = player.getPits();
        final Pit pit = pits.get(0);
        pit.setType(PitType.REGULAR);

        takeTurnTest(game, player, pit, false);

        verify(pitSortService).sortPits(any(Player.class), any(Player.class));
    }

    @Test
    void shouldSetTurnsWhenGameHasEnded() {
        final Game game = GameTestSupplier.getGame();
        final Player player = game.getPlayers().get(0);
        final List<Pit> pits = player.getPits();
        pits.forEach(pit -> pit.setStones(EMPTY_PIT));
        final Pit pit = pits.get(0);
        pit.setType(PitType.REGULAR);

        takeTurnTest(game, player, pits.get(0), false);

        verify(pitService).emptyIntoScorePit(any(Player.class));
    }

    @Test
    void shouldNotSetTurnsWhenLastPitIsScore() {
        final Game game = GameTestSupplier.getGame();
        final Player player = game.getPlayers().get(0);
        final List<Pit> pits = player.getPits();
        final Pit pit = pits.get(0);
        pit.setType(PitType.SCORE);

        takeTurnTest(game, player, pit, true);
    }

    private void takeTurnTest(final Game game, final Player player, final Pit pit, final boolean isTurn) {
        when(pitService.takeTurn(any(UUID.class), any(Player.class), any(Player.class))).thenReturn(pit);

        playerService.takeTurn(game, player.getId(), pit.getId());

        assertEquals(isTurn, player.isTurn());
        verify(pitSortService).sortPits(any(Player.class), any(Player.class));
        verify(pitService).captureStones(any(Pit.class), any(Player.class), any(Player.class));
    }

    @Test
    void shouldThrowExceptionWhenCurrentPlayerNotFound() {
        final Game game = GameTestSupplier.getGame();

        try {
            playerService.takeTurn(game, UUID.randomUUID(), UUID.randomUUID());
        } catch (final PlayerException playerException) {
            assertEquals(CURRENT_PLAYER_NOT_FOUND, playerException.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenCurrentPlayerHasNoTurn() {
        final Game game = GameTestSupplier.getGame();
        final Player player = game.getPlayers().get(0);
        player.setTurn(false);

        try {
            playerService.takeTurn(game, player.getId(), UUID.randomUUID());
        } catch (final PlayerException playerException) {
            assertEquals(CURRENT_PLAYER_NOT_FOUND, playerException.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionWhenOtherPlayerHasTurn() {
        final Game game = GameTestSupplier.getGame();
        final Player currentPlayer = game.getPlayers().get(0);
        final Player otherPlayer = game.getPlayers().get(1);
        otherPlayer.setTurn(true);

        try {
            playerService.takeTurn(game, currentPlayer.getId(), UUID.randomUUID());
        } catch (final PlayerException playerException) {
            assertEquals(OTHER_PLAYER_NOT_FOUND, playerException.getMessage());
        }
    }
}
