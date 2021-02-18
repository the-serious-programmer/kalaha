package com.bol.kalaha.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerMapper {

    private final PitMapper pitMapper;

    public List<PlayerDTO> mapToDTOs(final List<Player> players) {
        return players
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    private PlayerDTO mapToDTO(final Player player) {
        final List<PitDTO> pits = pitMapper.mapToDTOs(player.getPits());
        return PlayerDTO.builder()
            .id(player.getId())
            .type(player.getType())
            .turn(player.isTurn())
            .pits(pits)
            .build();
    }
}
