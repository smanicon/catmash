package org.catmash.persistence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.catmash.domain.vote.PersistenceException;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatIdTuple;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.models.CatVote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VoteEventStore {

    private File eventSore;

    public VoteEventStore(@Value("${eventStoreFile}") File eventSore) {
        this.eventSore = eventSore;
    }

    public void persistVote(CatVote vote) throws PersistenceException {
        try (FileOutputStream eventOutputStream = new FileOutputStream(eventSore, true)) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonVote = mapper.writeValueAsString(vote);

            eventOutputStream.write(jsonVote.length());
            eventOutputStream.write(jsonVote.getBytes());
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public List<CatVote> getVoteEvents() throws PersistenceException {
        try (FileInputStream eventInputStream = new FileInputStream(eventSore)) {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(CatVote.class, catVoteDeserializer());

            mapper.registerModule(module);


            List<CatVote> toReturn = new ArrayList<>();

            int size = 0;
            while ( (size = eventInputStream.read()) !=  -1){

                byte[] b = new byte[size];
                eventInputStream.read(b);

                CatVote catVote = mapper.readValue(new String(b), CatVote.class);
                toReturn.add(catVote);
            }

            return toReturn;

        } catch (IOException e) {
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    public StdDeserializer<CatVote> catVoteDeserializer() {
        return new StdDeserializer<CatVote>((JavaType) null) {

            @Override
            public CatVote deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                JsonNode node = jp.getCodec().readTree(jp);

                JsonNode generatedVote = node.get("generatedVote");
                String firstCat = generatedVote.get("firstId").get("id").asText();
                String secondeCat = generatedVote.get("secondId").get("id").asText();

                String choice = node.get("choice").get("id").asText();

                return CatVote.builder()
                        .generatedVote(new CatIdTuple(new CatId(firstCat), new CatId(secondeCat)))
                        .choice(new CatId(choice))
                        .build();
            }
        };
    }

}
