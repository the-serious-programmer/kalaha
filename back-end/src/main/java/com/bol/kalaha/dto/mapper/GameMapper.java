package com.bol.kalaha.dto.mapper;

import java.util.List;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.model.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameMapper {

    private final PlayerMapper playerMapper;

    public GameDTO mapToDTO(final Game game) {
        final List<PlayerDTO> players = playerMapper.mapToDTOs(game.getPlayers());
        return GameDTO.builder()
            .id(game.getId())
            .status(game.getStatus())
            .players(players)
            .build();
    }
}
