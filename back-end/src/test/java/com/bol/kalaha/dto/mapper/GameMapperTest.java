package com.bol.kalaha.dto.mapper;

import java.util.Collections;
import java.util.List;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.supplier.GameTestSupplier;
import com.bol.kalaha.supplier.PlayerTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameMapperTest {

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private GameMapper gameMapper;

    @Test
    void shouldMapToDTO() {
        final List<PlayerDTO> expectedPlayers = Collections.singletonList(PlayerTestSupplier.getPlayerDTO());
        when(playerMapper.mapToDTOs(anyList())).thenReturn(expectedPlayers);
        final Game input = GameTestSupplier.getGame();

        final GameDTO gameDTO = gameMapper.mapToDTO(input);

        assertEquals(input.getId(), gameDTO.getId());
        assertEquals(input.getStatus(), gameDTO.getStatus());
        assertEquals(expectedPlayers, gameDTO.getPlayers());
    }
}
