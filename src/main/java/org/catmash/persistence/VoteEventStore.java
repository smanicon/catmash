package org.catmash.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catmash.domain.vote.PersistenceException;
import org.catmash.domain.vote.models.CatVote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class VoteEventStore {

    private File eventSore;

    public VoteEventStore(@Value("${eventStoreFile}") File eventSore) {
        this.eventSore = eventSore;
    }

    public void persistVote(CatVote vote) throws PersistenceException {
        try(FileOutputStream eventOutputStream = new FileOutputStream(eventSore, true)){
            ObjectMapper mapper = new ObjectMapper();
            String jsonVote = mapper.writeValueAsString(vote);

            eventOutputStream.write(jsonVote.length());
            eventOutputStream.write(jsonVote.getBytes());
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }
}
