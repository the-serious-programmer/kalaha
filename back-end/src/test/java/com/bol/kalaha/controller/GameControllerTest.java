package com.bol.kalaha.controller;

import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.service.GameService;
import com.bol.kalaha.supplier.GameTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Test
    void shouldCreateGame() {
        final GameDTO expectedResult = GameTestSupplier.getGameDTO();
        when(gameService.createGame()).thenReturn(expectedResult);

        final ResponseEntity<GameDTO> result = gameController.createGame();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResult, result.getBody());
        verify(gameService).createGame();
    }

    @Test
    void shouldTakeTurn() {
        final UUID id = UUID.randomUUID();
        final UUID playerId = UUID.randomUUID();
        final UUID pitId = UUID.randomUUID();
        final GameDTO expectedResult = GameTestSupplier.getGameDTO();
        when(gameService.takeTurn(id, playerId, pitId)).thenReturn(expectedResult);

        final ResponseEntity<GameDTO> result = gameController.takeTurn(id, playerId, pitId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResult, result.getBody());
        verify(gameService).takeTurn(id, playerId, pitId);
    }
}
