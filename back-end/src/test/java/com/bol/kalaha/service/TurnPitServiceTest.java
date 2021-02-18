package com.bol.kalaha.service;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.exception.PitException;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.supplier.PlayerTestSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.bol.kalaha.exception.PitException.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TurnPitServiceTest {
    private static final int EMPTY_PIT = 0;

    private TurnPitService turnPitService;
    private Player player;
    private List<Pit> pits;
    private Pit pit;

    @BeforeEach
    void setUp() {
        turnPitService = new TurnPitService();
        player = PlayerTestSupplier.getPlayer();
        pits = player.getPits();
        pit = pits.get(0);
    }

    @Test
    void shouldSetTurnPit() {
        pit.setType(PitType.REGULAR);

        turnPitService.setTurnPit(pit.getId(), pits);

        assertTrue(pit.isTurnPit());
    }

    @Test
    void shouldThrowExceptionWhenPitIsNotFound() {
        getTurnPitThrowExceptionTest(UUID.randomUUID());
    }

    @Test
    void shouldThrowExceptionWhenPitIsEmpty() {
        pit.setStones(EMPTY_PIT);
        getTurnPitThrowExceptionTest(pit.getId());
    }

    @Test
    void shouldThrowExceptionWhenPitIsScore() {
        pit.setType(PitType.SCORE);
        getTurnPitThrowExceptionTest(pit.getId());
    }

    private void getTurnPitThrowExceptionTest(final UUID pitId) {
        try {
            turnPitService.setTurnPit(pitId, player.getPits());
        } catch (final PitException exception) {
            assertEquals(NOT_FOUND, exception.getMessage());
        }
    }

    @Test
    void shouldGetTurnPit() {
        pit.setTurnPit(true);

        final Pit result = turnPitService.getTurnPit(pits);

        assertEquals(pit, result);
    }

    @Test
    void shouldThrowExceptionWhenNoTurnPitIsPresentOnGetTurnPit() {
        try {
            turnPitService.getTurnPit(pits);
        } catch (final PitException pitException) {
            assertEquals(NOT_FOUND, pitException.getMessage());
        }
    }
}
