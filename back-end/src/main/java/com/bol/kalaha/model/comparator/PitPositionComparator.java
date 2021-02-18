package com.bol.kalaha.model.comparator;

import java.util.Comparator;

import com.bol.kalaha.model.Pit;

public class PitPositionComparator implements Comparator<Pit> {
    @Override
    public int compare(final Pit a, final Pit b) {
        return a.getPosition().compareTo(b.getPosition());
    }
}
