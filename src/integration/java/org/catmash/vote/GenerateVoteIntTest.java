package org.catmash.vote;

import com.tngtech.jgiven.integration.spring.SpringRuleScenarioTest;
import org.catmash.SpringJgivenConfig;
import org.catmash.vote.stages.GivenGenerateVoteStage;
import org.catmash.vote.stages.ThenGenerateVoteStage;
import org.catmash.vote.stages.WhenGenerateVoteStage;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest( classes = { MockServletContext.class, SpringJgivenConfig.class } )
@WebAppConfiguration
public class GenerateVoteIntTest
        extends SpringRuleScenarioTest<GivenGenerateVoteStage, WhenGenerateVoteStage, ThenGenerateVoteStage> {

    @Test
    public void should_generate_a_vote() throws Exception {
        given().an_user_want_to_vote()
            .and().the_system_has_loaded_the_file("json/parameter/3_cats.json")
            .and().the_generated_numbers_are(0, 1);
        when().he_is_asking_for_a_vote();
        then().the_server_should_return_status_ok()
                .and().return_the_generated_vote("json/expected/votes/get/generated/cat_1_and_cat_2.json");
    }

    @Test
    public void should_not_generate_vote_if_not_enough_cat_url() throws Exception {
        given().an_user_want_to_vote()
                .and().the_system_has_loaded_the_file("json/parameter/no_cat.json");
        when().he_is_asking_for_a_vote();
        then().the_server_should_return_status_error_500();
    }
}
