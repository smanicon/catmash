package org.catmash.domain.vote;

import org.catmash.domain.vote.models.CatVote;

public interface VotePersistence {
    void persist(CatVote vote);
}
