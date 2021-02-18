package com.bol.kalaha.supplier;

import java.util.Arrays;
import java.util.UUID;

import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PitType;
import com.bol.kalaha.model.type.PlayerType;

public final class PlayerTestSupplier {

    private PlayerTestSupplier() {
    }

    public static PlayerDTO getPlayerDTO() {
        return PlayerDTO.builder()
            .id(UUID.randomUUID())
            .type(PlayerType.FIRST)
            .turn(false)
            .pits(
                Arrays.asList(PitTestSupplier.getPitDTO(), PitTestSupplier.getPitDTO())
            )
            .build();
    }

    public static Player getPlayer() {
        return getPlayer(true);
    }

    public static Player getPlayer(final boolean isTurn) {
        return Player.builder()
            .id(UUID.randomUUID())
            .pits(
                Arrays.asList(PitTestSupplier.getPit(1, PitType.SCORE), PitTestSupplier.getPit(2, PitType.REGULAR))
            )
            .isTurn(isTurn)
            .type(PlayerType.FIRST)
            .build();
    }
}
