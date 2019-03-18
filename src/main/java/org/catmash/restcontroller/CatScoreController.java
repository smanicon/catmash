package org.catmash.restcontroller;

import org.catmash.domain.score.BestScore;
import org.catmash.domain.score.CatScore;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.models.CatVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class CatScoreController {

    @Autowired
    private List<CatUrl> catUrls;

    @GetMapping
    public Collection<CatScore> getScore() {
        BestScore score = new BestScore(catUrls);

        return score.getScore(Collections.singletonList(CatVote.builder().choice(new CatId("tt")).build()));
    }
}
