package com.bol.kalaha.supplier;

import java.util.Arrays;
import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.type.GameStatus;

public final class GameTestSupplier {

    private GameTestSupplier() {
    }

    public static GameDTO getGameDTO() {
        return GameDTO.builder()
            .id(UUID.randomUUID())
            .players(
                Arrays.asList(
                    PlayerTestSupplier.getPlayerDTO(),
                    PlayerTestSupplier.getPlayerDTO()
                )
            )
            .status(GameStatus.PLAYING)
            .build();
    }

    public static Game getGame() {
        return Game.builder()
            .id(UUID.randomUUID())
            .players(
                Arrays.asList(
                    PlayerTestSupplier.getPlayer(true),
                    PlayerTestSupplier.getPlayer(false)
                )
            )
            .status(GameStatus.PLAYING)
            .build();
    }
}
