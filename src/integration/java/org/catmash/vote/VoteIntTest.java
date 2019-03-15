package org.catmash.vote;

import com.tngtech.jgiven.integration.spring.SpringRuleScenarioTest;
import org.catmash.SpringJgivenConfig;
import org.catmash.vote.stages.GivenVoteStage;
import org.catmash.vote.stages.ThenVoteStage;
import org.catmash.vote.stages.WhenVoteStage;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest( classes = { MockServletContext.class, SpringJgivenConfig.class } )
@WebAppConfiguration
public class VoteIntTest
        extends SpringRuleScenarioTest<GivenVoteStage, WhenVoteStage, ThenVoteStage> {

    @Test
    public void should_generate_a_vote() throws Exception {
        given().an_user_want_to_vote();
        when().he_is_asking_for_a_vote();
        then().the_server_should_return_two_cats();
    }
}
