package com.bol.kalaha.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.bol.kalaha.exception.PitException;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.service.validate.PitValidation;
import com.bol.kalaha.supplier.PitTestSupplier;
import com.bol.kalaha.supplier.PlayerTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bol.kalaha.exception.PitException.LAST_SOWED_NOT_FOUND;
import static com.bol.kalaha.exception.PitException.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PitServiceTest {
    private static final int EMPTY_PIT = 0;
    private static final int STONE = 1;

    @Captor
    private ArgumentCaptor<List<Pit>> pitsToMergeCaptor;

    @Mock
    private SowStonesService sowStonesService;

    @Mock
    private TurnPitService turnPitService;

    @Mock
    private PitSortService pitSortService;

    @InjectMocks
    private PitService pitService;

    @Test
    void shouldRecursivelySowStones() {
        final Player currentPlayer = PlayerTestSupplier.getPlayer();
        final Player otherPlayer = PlayerTestSupplier.getPlayer();
        final Pit turnPit = currentPlayer.getPits().get(1);
        final int initialStones = turnPit.getStones();
        when(turnPitService.getTurnPit(anyList())).thenReturn(turnPit);

        doAnswer((final InvocationOnMock invocation) -> {
            turnPit.setLastSowedPit(true);
            turnPit.setStones(turnPit.getStones() - STONE);
            return false;
        }).when(sowStonesService).sowStones(anyList(), any(Pit.class));

        pitService.takeTurn(turnPit.getId(), currentPlayer, otherPlayer);

        verify(sowStonesService, times(initialStones)).sowStones(anyList(), any(Pit.class));
    }

    @Test
    void shouldSetTurnPitStonesWhenHasSowedTurnPitAndReturnLastSowedPit() {
        takeTurnTest(true, 1);
    }

    @Test
    void shouldNotSetTurnPitStonesWhenHasNoSowedTurnPitAndReturnLastSowedPit() {
        takeTurnTest(false, 0);
    }

    private void takeTurnTest(final boolean hasSowedTurnPit, final int addStoneTimes) {
        final Pit turnPit = PitTestSupplier.getPit();
        final Player currentPlayer = Player.builder()
            .pits(Collections.singletonList(turnPit))
            .build();
        turnPit.setStones(EMPTY_PIT);
        when(turnPitService.getTurnPit(anyList())).thenReturn(turnPit);
        when(sowStonesService.sowStones(anyList(), any(Pit.class)))
            .thenAnswer((final InvocationOnMock onMock) -> {
                turnPit.setLastSowedPit(true);
                return hasSowedTurnPit;
            });

        final Pit lastSowedPit = pitService.takeTurn(turnPit.getId(), currentPlayer, PlayerTestSupplier.getPlayer());

        assertEquals(turnPit, lastSowedPit);
        verify(turnPitService).setTurnPit(any(UUID.class), anyList());
        verify(pitSortService).sortPitsForSowing(anyList(), anyList());
        verify(sowStonesService, times(addStoneTimes)).addStone(turnPit);
    }

    @Test
    void shouldGetLastSowedPitFromOtherPlayer() {
        final Pit expectedLastSowedPit = PitTestSupplier.getPit();
        final Player otherPlayer = Player.builder()
            .pits(Collections.singletonList(expectedLastSowedPit))
            .build();
        final Pit turnPit = PitTestSupplier.getPit();
        final Player currentPlayer = Player.builder()
            .pits(Collections.singletonList(turnPit))
            .build();
        turnPit.setStones(EMPTY_PIT);
        when(turnPitService.getTurnPit(anyList())).thenReturn(turnPit);
        when(sowStonesService.sowStones(anyList(), any(Pit.class)))
            .thenAnswer((final InvocationOnMock onMock) -> {
                expectedLastSowedPit.setLastSowedPit(true);
                return false;
            });

        final Pit lastSowedPit = pitService.takeTurn(turnPit.getId(), currentPlayer, otherPlayer);

        assertEquals(expectedLastSowedPit, lastSowedPit);
    }

    @Test
    void shouldResetLastSowedPitAndThrowExceptionWhenNoLastSowedPitIsFound() {
        final Pit turnPit = PitTestSupplier.getPit();
        turnPit.setStones(EMPTY_PIT);
        when(turnPitService.getTurnPit(anyList())).thenReturn(turnPit);
        when(sowStonesService.sowStones(anyList(), any(Pit.class))).thenReturn(true);
        try {
            pitService.takeTurn(turnPit.getId(), PlayerTestSupplier.getPlayer(), PlayerTestSupplier.getPlayer());
        } catch (final PitException pitException) {
            assertEquals(LAST_SOWED_NOT_FOUND, pitException.getMessage());
        }
    }

    @Test
    void shouldCaptureStones() {
        final Player currentPlayer = PlayerTestSupplier.getPlayer();
        prepareForCapture(currentPlayer.getPits());
        captureStonesTest(currentPlayer, 1);
    }

    @Test
    void shouldNotCaptureStones() {
        final Player currentPlayer = PlayerTestSupplier.getPlayer();
        captureStonesTest(currentPlayer, 0);
    }

    private void captureStonesTest(final Player currentPlayer, final int captureStonesTimesCalled) {
        final Player otherPlayer = PlayerTestSupplier.getPlayer();
        final Pit lastSowedPit = getLastSowedPit(currentPlayer.getPits());

        pitService.captureStones(lastSowedPit, currentPlayer, otherPlayer);

        verify(sowStonesService, times(captureStonesTimesCalled)).mergePitStones(any(Pit.class), anyList());
    }

    @Test
    void shouldGetOppositePit() {
        final Player currentPlayer = PlayerTestSupplier.getPlayer();
        final List<Pit> currentPlayerPits = currentPlayer.getPits();
        final Pit lastSowedPit = getLastSowedPit(currentPlayerPits);
        prepareForCapture(currentPlayerPits);

        final Player otherPlayer = PlayerTestSupplier.getPlayer();
        final List<Pit> otherPlayerPits = otherPlayer.getPits();

        final Pit expectedOppositePit = otherPlayerPits.get(otherPlayerPits.size() - lastSowedPit.getPosition());

        doNothing().when(sowStonesService).mergePitStones(any(Pit.class), pitsToMergeCaptor.capture());

        pitService.captureStones(lastSowedPit, currentPlayer, otherPlayer);

        final List<Pit> pitsToMerge = pitsToMergeCaptor.getValue();
        assertTrue(pitsToMerge.stream()
            .anyMatch(pit -> pit.getId().equals(expectedOppositePit.getId())));
        assertTrue(pitsToMerge.stream()
            .anyMatch(pit -> pit.getId().equals(lastSowedPit.getId())));
    }

    private void prepareForCapture(final List<Pit> pits) {
        pits.stream()
            .filter(PitValidation::isRegularPit)
            .forEach(pit -> pit.setStones(STONE));
    }

    private Pit getLastSowedPit(final List<Pit> pits) {
        return pits
            .stream()
            .filter(PitValidation::isRegularPit)
            .findAny()
            .orElseThrow();
    }

    @Test
    void shouldEmptyAllStonesIntoScorePit() {
        final Player player = PlayerTestSupplier.getPlayer();
        player.getPits()
            .forEach(pit -> pit.setType(PitType.SCORE));

        pitService.emptyIntoScorePit(player);

        verify(sowStonesService).mergePitStones(any(Pit.class), anyList());
    }

    @Test
    void shouldThrowExceptionWhenNoScorePitIsFoundWhenEmptyingIntoScorePit() {
        final Player player = PlayerTestSupplier.getPlayer();
        player.getPits()
            .forEach(pit -> pit.setType(PitType.REGULAR));

        try {
            pitService.emptyIntoScorePit(player);
        } catch (final PitException pitException) {
            assertEquals(NOT_FOUND, pitException.getMessage());
        }
    }
}
