package org.catmash.vote.stages;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.attachment.MediaType;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.catmash.configurations.CatmashConfiguration;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.Randomize;
import org.catmash.persistence.VoteEventStore;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@JGivenStage
public class GivenGenerateVoteStage extends Stage<GivenGenerateVoteStage> {

    @MockBean
    Randomize randomize;

    @MockBean
    VoteEventStore eventStore;

    @Autowired
    List<CatUrl> catUrls;

    @BeforeStage
    void setupt() {
        catUrls.clear();
    }

    @ExpectedScenarioState
    CurrentStep currentStep;

    public GivenGenerateVoteStage an_user_want_to_vote() {
        return self();
    }

    public GivenGenerateVoteStage the_system_has_loaded_the_file(@Quoted String jsonFile) throws IOException {
        Resource jsonCatRessource = new ClassPathResource(jsonFile);

        CatmashConfiguration configuration = new CatmashConfiguration();
        StdDeserializer<CatUrl[]> deserializer = configuration.catsDeserializer();
        catUrls.addAll(configuration.loadCatJsonBean(deserializer, jsonCatRessource));

        Attachment attachment = Attachment.fromTextFile(jsonCatRessource.getFile(), MediaType.JSON_UTF_8);
        currentStep.addAttachment(attachment);

        return self();
    }

    public GivenGenerateVoteStage the_generated_numbers_are(int firstRandomNumber, Integer...nextRandomNumbers) {
        BDDMockito.when(randomize.rand(anyInt())).thenReturn(firstRandomNumber, nextRandomNumbers);

        return self();
    }
}
