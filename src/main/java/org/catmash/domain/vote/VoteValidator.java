package org.catmash.domain.vote;

import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatIdTuple;
import org.catmash.domain.vote.models.CatVote;

public class VoteValidator {
    private VotePersistence persistance;

    public VoteValidator(VotePersistence persistance) {

        this.persistance = persistance;
    }

    public void validate(CatVote vote) throws InvalidVoteException, PersistenceException {
        CatId choice = vote.getChoice();
        CatIdTuple tuple = vote.getGeneratedVote();

        if (! isCatInTuple(choice, tuple)) {
            throw new InvalidVoteException("Invalide vote");
        }

        persistance.persist(vote);
    }

    private boolean isCatInTuple(CatId cat, CatIdTuple tuple) {
        return cat.equals(tuple.getFirstId())
                || cat.equals(tuple.getSecondId());
    }
}
