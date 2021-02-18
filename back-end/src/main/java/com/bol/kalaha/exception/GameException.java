package com.bol.kalaha.exception;

public final class GameException extends RuntimeException {
    public static final String NOT_FOUND = "The game could not be found with the given arguments";

    public GameException(final String message) {
        super(message);
    }
}
