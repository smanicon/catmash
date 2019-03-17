package org.catmash.vote.stages;

import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.Hidden;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.assertj.core.util.Files;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JGivenStage
public class ThenVoteStage extends Stage<ThenVoteStage> {
    @ExpectedScenarioState
    CurrentStep currentStep;

    @ExpectedScenarioState
    private ResultActions mvcResult;

    public ThenVoteStage the_server_should_return_status_ok() throws Exception {
        mvcResult.andExpect(status().isOk());
        return self();
    }

    public ThenVoteStage return_the_generated_vote(@Hidden String json) throws Exception {
        Resource resource = new ClassPathResource(json);
        File jsonFile = resource.getFile();

        mvcResult.andExpect(MockMvcResultMatchers.content().json(Files.contentOf(jsonFile, "utf8")));

        Attachment attachment = Attachment.json(Files.contentOf(jsonFile, "utf8")).withTitle("Result");
        currentStep.addAttachment(attachment);

        return self();
    }
}
