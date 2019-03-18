package org.catmash.domain.vote;

public class InvalidVoteException extends Exception {
    InvalidVoteException(String msg) {
        super(msg);
    }
}
