package org.catmash.restcontroller;

import org.catmash.domain.vote.*;
import org.catmash.domain.vote.models.CatMash;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.models.CatVote;
import org.catmash.persistence.VoteEventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    Randomize randomize;

    @Autowired
    List<CatUrl> catUrls;

    @Autowired
    VoteEventStore eventStore;

    @GetMapping("/generate")
    public CatMash generateVote() throws NotGeneratedVoteException {

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, randomize);

        return voteGenerator.generate();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postVote(@RequestBody CatVote vote) throws InvalidVoteException, PersistenceException {
        VoteValidator voteValidator = new VoteValidator(eventStore::persistVote);
        voteValidator.validate(vote);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Object> persistenceException(PersistenceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidVoteException.class)
    public ResponseEntity<Object> invalidVoteExceptionHandler(InvalidVoteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotGeneratedVoteException.class)
    public ResponseEntity<Object> notGenerateVoteExceptionHandler(NotGeneratedVoteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
