package org.catmash.restcontroller;

import org.catmash.domain.vote.CatMash;
import org.catmash.domain.vote.CatUrl;
import org.catmash.domain.vote.VoteGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @GetMapping("/generate")
    public CatMash generateVote() {

        List<CatUrl> catUrls = asList(
                new CatUrl("MTgwODA3MA", "http://24.media.tumblr.com/tumblr_m82woaL5AD1rro1o5o1_1280.jpg"),
                new CatUrl("tt", "http://24.media.tumblr.com/tumblr_m29a9d62C81r2rj8po1_500.jpg")
        );

        final int[] i = {0};

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, (x) -> i[0]++);

        return voteGenerator.generate();
    }
}
