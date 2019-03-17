package org.catmash.restcontroller;

import org.catmash.domain.vote.CatMash;
import org.catmash.domain.vote.CatUrl;
import org.catmash.domain.vote.Randomize;
import org.catmash.domain.vote.VoteGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    Randomize randomize;

    @Autowired
    List<CatUrl> catUrls;

    @GetMapping("/generate")
    public CatMash generateVote() {

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, randomize);

        return voteGenerator.generate();
    }
}
