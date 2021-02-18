package com.bol.kalaha.controller;

import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping()
    public ResponseEntity<GameDTO> createGame() {
        log.info("Received createGame request");
        final GameDTO game = gameService.createGame();
        log.info("Successfully saved new Game with id {}", game.getId());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(game);
    }

    @PutMapping("/{id}/player/{playerId}/pit/{pitId}")
    public ResponseEntity<GameDTO> takeTurn(
        @PathVariable final UUID id,
        @PathVariable final UUID playerId,
        @PathVariable final UUID pitId
    ) {
        log.info("Received takeTurn request with gameId {}, playerId {} and pitId {}", id, playerId, pitId);
        final GameDTO game = gameService.takeTurn(id, playerId, pitId);
        log.info("Successfully sowed stones for gameId {}", id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(game);
    }
}
