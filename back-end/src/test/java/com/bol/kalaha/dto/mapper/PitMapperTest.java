package com.bol.kalaha.dto.mapper;

import java.util.Collections;
import java.util.List;

import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.supplier.PitTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitMapperTest {

    private PitMapper pitMapper;

    @BeforeEach
    void setUp() {
        pitMapper = new PitMapper();
    }

    @Test
    void shouldMapToDTO() {
        final Pit input = PitTestSupplier.getPit();

        final List<PitDTO> pitDTOs = pitMapper.mapToDTOs(Collections.singletonList(input));

        final PitDTO result = pitDTOs.get(0);
        assertEquals(input.getId(), result.getId());
        assertEquals(input.getPosition(), result.getPosition());
        assertEquals(input.getStones(), result.getStones());
        assertEquals(input.getType(), result.getType());
    }
}
