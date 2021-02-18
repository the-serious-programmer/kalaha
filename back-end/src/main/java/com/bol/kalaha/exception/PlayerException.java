package com.bol.kalaha.exception;

public final class PlayerException extends RuntimeException {
    public static final String CURRENT_PLAYER_NOT_FOUND =
        "The current player could not be found or did not have the turn";
    public static final String OTHER_PLAYER_NOT_FOUND = "The other player could not be found or did have the turn";

    public PlayerException(final String message) {
        super(message);
    }
}
