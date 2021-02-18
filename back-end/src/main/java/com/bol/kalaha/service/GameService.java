package com.bol.kalaha.service;

import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.dto.mapper.GameMapper;
import com.bol.kalaha.exception.GameException;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.GameStatus;
import com.bol.kalaha.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bol.kalaha.exception.GameException.NOT_FOUND;
import static com.bol.kalaha.model.supplier.GameSupplier.getDefaultGame;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final GameMapper gameMapper;

    @Transactional
    public GameDTO createGame() {
        final Game game = gameRepository.save(getDefaultGame());
        return gameMapper.mapToDTO(game);
    }

    @Transactional
    public GameDTO takeTurn(final UUID id, final UUID playerId, final UUID pitId) {
        final Game game = getGame(id);
        playerService.takeTurn(game, playerId, pitId);
        setStatus(game);
        final Game savedGame = gameRepository.save(game);
        return gameMapper.mapToDTO(savedGame);
    }

    private Game getGame(final UUID id) {
        return gameRepository
            .findById(id)
            .orElseThrow(() -> new GameException(NOT_FOUND));
    }

    private void setStatus(final Game game) {
        final boolean hasActiveTurn = game
            .getPlayers()
            .stream()
            .anyMatch(Player::isTurn);
        if (hasActiveTurn) {
            game.setStatus(GameStatus.PLAYING);
        } else {
            game.setStatus(GameStatus.END);
        }
    }
}
