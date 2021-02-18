package com.bol.kalaha.model.supplier;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PlayerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitSupplierTest {

    @Test
    void shouldCreatePitBasedOnPlayerType() {
        final List<Pit> pits = PitSupplier.getDefaultPits(PlayerType.FIRST);
        final List<Integer> positions = pits.stream()
            .map(Pit::getPosition)
            .collect(Collectors.toList());

        final List<Pit> pitsReverse = PitSupplier.getDefaultPits(PlayerType.SECOND);
        final List<Integer> positionsReverse = pitsReverse.stream()
            .map(Pit::getPosition)
            .collect(Collectors.toList());

        Collections.reverse(positionsReverse);

        assertEquals(positions, positionsReverse);
    }
}
