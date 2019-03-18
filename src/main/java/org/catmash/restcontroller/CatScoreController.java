package org.catmash.restcontroller;

import org.catmash.domain.score.BestScore;
import org.catmash.domain.score.CatScore;
import org.catmash.domain.vote.PersistenceException;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.persistence.VoteEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class CatScoreController {

    @Autowired
    private List<CatUrl> catUrls;

    @Autowired
    private VoteEventStore eventStore;

    @GetMapping
    public Collection<CatScore> getScore() throws PersistenceException {
        BestScore score = new BestScore(catUrls);

        return score.getScore(eventStore.getVoteEvents());
    }
}
