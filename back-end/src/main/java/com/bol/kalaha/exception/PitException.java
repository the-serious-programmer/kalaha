package com.bol.kalaha.exception;

public final class PitException extends RuntimeException {
    public static final String NOT_FOUND = "The pit could not be found.";
    public static final String LAST_SOWED_NOT_FOUND = "The last sowed pit could not be found.";

    public PitException(final String message) {
        super(message);
    }
}
