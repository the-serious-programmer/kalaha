package com.bol.kalaha.supplier;

import java.util.UUID;

import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.type.PitType;

public final class PitTestSupplier {
    public static final int STONES = 6;
    private static final int POSITION = 1;

    private PitTestSupplier() {
    }

    public static PitDTO getPitDTO() {
        return PitDTO.builder()
            .id(UUID.randomUUID())
            .position(POSITION)
            .stones(STONES)
            .type(PitType.REGULAR)
            .build();
    }

    public static Pit getPit() {
        return getPit(POSITION);
    }

    public static Pit getPit(final PitType type) {
        return getPit(POSITION, type);
    }

    public static Pit getPit(final int position) {
        return getPit(position, PitType.REGULAR);
    }

    public static Pit getPit(final int position, final PitType type) {
        return Pit.builder()
            .id(UUID.randomUUID())
            .position(position)
            .stones(STONES)
            .turnPit(false)
            .lastSowedPit(false)
            .type(type)
            .build();
    }
}
