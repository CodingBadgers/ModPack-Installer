package com.mcbadgercraft.installer.packs.exception;

/**
 * Signals a exception has occurred whilst interacting with the pack repositories
 */
public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
