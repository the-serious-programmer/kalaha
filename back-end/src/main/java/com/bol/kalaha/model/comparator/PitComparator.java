package com.bol.kalaha.model.comparator;

import java.util.Comparator;

import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;

public class PitComparator implements Comparator<Pit> {
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;

    @Override
    public int compare(final Pit a, final Pit b) {
        if (a.getType() == b.getType()) {
            return a.getPosition().compareTo(b.getPosition());
        } else if (a.getType() == PitType.REGULAR) {
            return LESS_THAN;
        }
        return GREATER_THAN;
    }
}
