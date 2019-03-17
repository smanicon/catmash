package org.catmash.restcontroller;

import org.catmash.domain.vote.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public CatMash generateVote() throws NotGeneratedVoteException {

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, randomize);

        return voteGenerator.generate();
    }

    @ExceptionHandler(NotGeneratedVoteException.class)
    public ResponseEntity<Object> notGenerateVoteExceptionHandler(NotGeneratedVoteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
