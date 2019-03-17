package org.catmash.domain.vote;

public class PersistenceException extends Exception {
    public PersistenceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
