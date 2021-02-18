package com.bol.kalaha.model.supplier;

import java.util.List;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.model.type.GameStatus;

import static com.bol.kalaha.model.supplier.PlayerSupplier.getDefaultPlayers;

public final class GameSupplier {

    private GameSupplier() {
    }

    public static Game getDefaultGame() {
        final List<Player> players = getDefaultPlayers();
        return Game.builder()
            .status(GameStatus.PLAYING)
            .players(players)
            .build();
    }
}
