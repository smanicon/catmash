package org.catmash.domain.vote;

class InvalidVoteException extends Exception {
    InvalidVoteException(String msg) {
        super(msg);
    }
}
