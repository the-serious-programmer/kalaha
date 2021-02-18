package com.bol.kalaha.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.model.Pit;
import org.springframework.stereotype.Component;

@Component
public class PitMapper {

    public List<PitDTO> mapToDTOs(final List<Pit> pits) {
        return pits.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    private PitDTO mapToDTO(final Pit pit) {
        return PitDTO.builder()
            .id(pit.getId())
            .position(pit.getPosition())
            .stones(pit.getStones())
            .type(pit.getType())
            .build();
    }
}
