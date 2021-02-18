package com.bol.kalaha.service;

import java.util.List;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.comparator.PitComparator;
import com.bol.kalaha.model.comparator.PitPositionComparator;
import org.springframework.stereotype.Service;

@Service
public class PitSortService {
    private static final PitComparator PIT_COMPARATOR = new PitComparator();
    private static final PitPositionComparator PIT_POSITION_COMPARATOR = new PitPositionComparator();

    public void sortPits(final Player currentPlayer, final Player otherPlayer) {
        currentPlayer.getPits().sort(PIT_POSITION_COMPARATOR);
        otherPlayer.getPits().sort(PIT_POSITION_COMPARATOR);
    }

    public void sortPitsForSowing(final List<Pit> currentPlayerPits, final List<Pit> otherPlayerPits) {
        currentPlayerPits.sort(PIT_COMPARATOR);
        otherPlayerPits.sort(PIT_COMPARATOR);
    }
}
