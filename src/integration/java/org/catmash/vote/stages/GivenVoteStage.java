package org.catmash.vote.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.integration.spring.JGivenStage;

@JGivenStage
public class GivenVoteStage extends Stage<GivenVoteStage> {

    public GivenVoteStage an_user_want_to_vote() {
        return self();
    }
}
