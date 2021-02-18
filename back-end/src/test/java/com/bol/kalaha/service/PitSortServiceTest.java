package com.bol.kalaha.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.comparator.PitComparator;
import com.bol.kalaha.supplier.PitTestSupplier;
import com.bol.kalaha.supplier.PlayerTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PitSortServiceTest {
    private static final PitComparator PIT_COMPARATOR = new PitComparator();
    private PitSortService pitSortService;

    @BeforeEach
    void setUp() {
        pitSortService = new PitSortService();
    }

    @Test
    void shouldSortPits() {
        final Pit zero = PitTestSupplier.getPit(0);
        final Pit one = PitTestSupplier.getPit(1);
        final Pit two = PitTestSupplier.getPit(2);
        final List<Pit> expectedResult = Arrays.asList(zero, one, two);
        final List<Pit> input = Arrays.asList(two, zero, one);
        final Player player = Player.builder()
            .pits(input)
            .build();
        pitSortService.sortPits(player, player);
        assertEquals(expectedResult, input);
    }

    @Test
    void shouldSortPitsForSowing() {
        final Player currentPlayer = PlayerTestSupplier.getPlayer();
        final Player otherPlayer = PlayerTestSupplier.getPlayer();
        final List<Pit> currentPlayerPits = currentPlayer.getPits();
        final List<Pit> otherPlayerPits = otherPlayer.getPits();

        final List<Pit> currentPlayerPitsCopy = new ArrayList<>(currentPlayerPits);
        final List<Pit> otherPlayerPitsCopy = new ArrayList<>(otherPlayerPits);

        pitSortService.sortPitsForSowing(currentPlayerPits, otherPlayerPits);

        currentPlayerPitsCopy.sort(PIT_COMPARATOR);
        otherPlayerPitsCopy.sort(PIT_COMPARATOR);
        assertEquals(currentPlayerPitsCopy, currentPlayerPits);
        assertEquals(otherPlayerPitsCopy, otherPlayerPits);
    }
}
