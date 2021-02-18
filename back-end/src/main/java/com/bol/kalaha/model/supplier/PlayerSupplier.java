package com.bol.kalaha.model.supplier;

import java.util.Arrays;
import java.util.List;

import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.PlayerType;

import static com.bol.kalaha.model.supplier.PitSupplier.getDefaultPits;

public final class PlayerSupplier {

    private PlayerSupplier() {
    }

    public static List<Player> getDefaultPlayers() {
        return Arrays.asList(
            Player.builder()
                .type(PlayerType.FIRST)
                .isTurn(true)
                .pits(getDefaultPits(PlayerType.FIRST))
                .build(),
            Player.builder()
                .type(PlayerType.SECOND)
                .isTurn(false)
                .pits(getDefaultPits(PlayerType.SECOND))
                .build()
        );
    }
}
