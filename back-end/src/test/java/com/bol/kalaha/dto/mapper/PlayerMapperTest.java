package com.bol.kalaha.dto.mapper;

import java.util.Collections;
import java.util.List;

import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.supplier.PitTestSupplier;
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
public class PlayerMapperTest {

    @Mock
    private PitMapper pitMapper;

    @InjectMocks
    private PlayerMapper playerMapper;

    @Test
    void shouldMapToDTO() {
        final List<PitDTO> expectedPits = Collections.singletonList(PitTestSupplier.getPitDTO());
        when(pitMapper.mapToDTOs(anyList())).thenReturn(expectedPits);
        final Player input = PlayerTestSupplier.getPlayer();

        final List<PlayerDTO> playerDTOs = playerMapper.mapToDTOs(
            Collections.singletonList(input)
        );

        final PlayerDTO result = playerDTOs.get(0);
        assertEquals(input.getId(), result.getId());
        assertEquals(input.isTurn(), result.isTurn());
        assertEquals(input.getType(), result.getType());
        assertEquals(expectedPits, result.getPits());
    }
}
