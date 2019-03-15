package org.catmash.vote.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JGivenStage
public class ThenVoteStage extends Stage<ThenVoteStage> {

    @ExpectedScenarioState
    private ResultActions mvcResult;

    public ThenVoteStage the_server_should_return_two_cats() throws Exception {
        mvcResult.andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"firstCat\": {\n" +
                        "    \"id\": \"MTgwODA3MA\",\n" +
                        "    \"url\": \"http://24.media.tumblr.com/tumblr_m82woaL5AD1rro1o5o1_1280.jpg\"\n" +
                        "  },\n" +
                        "  \"secondCat\": {\n" +
                        "    \"id\": \"tt\",\n" +
                        "    \"url\": \"http://24.media.tumblr.com/tumblr_m29a9d62C81r2rj8po1_500.jpg\"\n" +
                        "  }\n" +
                        "}"));
        return self();
    }
}
