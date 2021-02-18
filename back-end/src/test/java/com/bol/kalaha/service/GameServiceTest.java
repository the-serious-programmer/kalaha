package com.bol.kalaha.service;

import java.util.Optional;
import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.dto.mapper.GameMapper;
import com.bol.kalaha.exception.GameException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.type.GameStatus;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.supplier.GameTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bol.kalaha.model.supplier.GameSupplier.getDefaultGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    @Test
    void shouldCreateGameWithDefaultSetup() {
        final GameDTO expectedResult = GameTestSupplier.getGameDTO();
        final Game expectedCapture = getDefaultGame();
        final ArgumentCaptor<Game> argumentCaptor = ArgumentCaptor.forClass(Game.class);
        when(gameRepository.save(argumentCaptor.capture())).thenReturn(Game.builder().build());
        when(gameMapper.mapToDTO(any(Game.class))).thenReturn(expectedResult);

        final GameDTO result = gameService.createGame();

        final Game capturedGame = argumentCaptor.getValue();
        assertEquals(expectedCapture, capturedGame);
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldTakeTurn() {
        final UUID id = UUID.randomUUID();
        final Game game = GameTestSupplier.getGame();
        final GameDTO expectedResult = GameTestSupplier.getGameDTO();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.mapToDTO(game)).thenReturn(expectedResult);

        final GameDTO result = gameService.takeTurn(id, UUID.randomUUID(), UUID.randomUUID());

        assertEquals(expectedResult, result);
        verify(playerService).takeTurn(any(Game.class), any(UUID.class), any(UUID.class));
    }

    @Test
    void shouldThrowExceptionWhenGameIsNotFound() {
        final UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        try {
            gameService.takeTurn(id, UUID.randomUUID(), UUID.randomUUID());
        } catch (final GameException gameException) {
            assertEquals(GameException.NOT_FOUND, gameException.getMessage());
        }
    }

    @Test
    void shouldSetPlayingStatusWhenPlayerHasTurn() {
        final Game game = GameTestSupplier.getGame();
        game.setStatus(GameStatus.END);
        setStatusTest(GameStatus.PLAYING, game);
    }

    @Test
    void shouldSetEndStatusWhenPlayerHasTurn() {
        final Game game = GameTestSupplier.getGame();
        game.getPlayers().forEach(player -> player.setTurn(false));
        setStatusTest(GameStatus.END, game);
    }

    private void setStatusTest(final GameStatus expectedStatus, final Game input) {
        final ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(input));
        when(gameRepository.save(gameCaptor.capture())).thenReturn(input);
        when(gameMapper.mapToDTO(any(Game.class))).thenReturn(GameTestSupplier.getGameDTO());

        gameService.takeTurn(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        final Game capturedGame = gameCaptor.getValue();
        assertEquals(expectedStatus, capturedGame.getStatus());
    }
}
