package org.catmash.vote.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.catmash.restcontroller.VoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@JGivenStage
public class WhenVoteStage extends Stage<WhenVoteStage> {

    private MockMvc mvc;

    @Autowired
    private VoteController voteController;

    @ProvidedScenarioState
    private ResultActions mvcResult;

    @BeforeStage
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(voteController).build();
    }

    public WhenVoteStage he_is_asking_for_a_vote() throws Exception {
        mvcResult = mvc.perform(get("/votes/generate"));
        return self();
    }
}
