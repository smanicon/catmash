package org.catmash.domain.vote;

import org.assertj.core.api.Assertions;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatIdTuple;
import org.catmash.domain.vote.models.CatVote;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class VoteValidatorTest {
    @Test
    public void should_validate_a_vote() throws InvalidVoteException, PersistenceException {
        CatVote vote = CatVote.builder()
                .generatedVote(tuple(id("id1"), id("id2")))
                .choice(id("id1"))
                .build();

        VotePersistence persistence = Mockito.mock(VotePersistence.class);
        VoteValidator voteValidator = new VoteValidator(persistence);

        voteValidator.validate(vote);

        verify(persistence).persist(vote);
    }

    @Test
    public void should_not_persist_if_choice_id_is_not_in_generated_vote() throws PersistenceException {
        CatVote vote = CatVote.builder()
                .generatedVote(tuple(id("id1"), id("id2")))
                .choice(id("id3"))
                .build();

        VotePersistence persistence = Mockito.mock(VotePersistence.class);
        VoteValidator voteValidator = new VoteValidator(persistence);

        assertThatExceptionOfType(InvalidVoteException.class)
                .isThrownBy(() -> voteValidator.validate(vote))
                .withMessage("Invalide vote");

        verify(persistence, never()).persist(vote);
    }

    @Test
    public void should_throw_persistence_exception() throws PersistenceException {
        CatVote vote = CatVote.builder()
                .generatedVote(tuple(id("id1"), id("id2")))
                .choice(id("id1"))
                .build();

        VotePersistence persistence = Mockito.mock(VotePersistence.class);
        doThrow(new PersistenceException("error", null)).when(persistence).persist(any());

        VoteValidator voteValidator = new VoteValidator(persistence);

        Assertions.assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> voteValidator.validate(vote));
    }

    private CatId id(String id1) {
        return new CatId(id1);
    }

    private CatIdTuple tuple(CatId id1, CatId id2) {
        return new CatIdTuple(id1, id2);
    }

}
