package org.catmash.domain.score;

import org.assertj.core.api.Assertions;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatIdTuple;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.models.CatVote;
import org.junit.Test;

import java.util.List;

import static io.vavr.collection.Stream.ofAll;
import static java.util.Arrays.asList;

public class BestScoreTest {

    private static final CatId CAT_1 = new CatId("1");
    private static final CatId CAT_2 = new CatId("2");
    private static final CatId CAT_3 = new CatId("3");
    private static final CatId CAT_4 = new CatId("4");
    private static final CatId CAT_5 = new CatId("5");

    private static final CatId CAT_NOT_EXISTS = new CatId("NotExist");

    public static final CatUrl CAT_URL_1 = new CatUrl(CAT_1.getId(), "url1");
    public static final CatUrl CAT_URL_2 = new CatUrl(CAT_2.getId(), "url2");
    public static final CatUrl CAT_URL_3 = new CatUrl(CAT_3.getId(), "url3");
    public static final CatUrl CAT_URL_4 = new CatUrl(CAT_4.getId(), "url4");
    public static final CatUrl CAT_URL_5 = new CatUrl(CAT_5.getId(), "url5");

    private static final List<CatUrl> CAT_URLS = asList(
            CAT_URL_1,
            CAT_URL_2,
            CAT_URL_3,
            CAT_URL_4,
            CAT_URL_5
    );

    private BestScore bestScore = new BestScore(CAT_URLS);

    @Test
    public void should_return_the_best_scores() {

        List<CatVote> catVotes = asList(
                generateVote(CAT_1, CAT_2, CAT_2),
                generateVote(CAT_3, CAT_2, CAT_2),
                generateVote(CAT_1, CAT_4, CAT_1),
                generateVote(CAT_1, CAT_5, CAT_1),
                generateVote(CAT_1, CAT_2, CAT_1)
        );

        Assertions.assertThat(bestScore.getScore(catVotes))
                .containsExactly(
                        new CatScore(CAT_URL_1, 3),
                        new CatScore(CAT_URL_2, 2)
                );
    }

    @Test
    public void should_remove_not_existent_element() {

        List<CatVote> catVotes = asList(
                generateVote(CAT_1, CAT_NOT_EXISTS, CAT_NOT_EXISTS),
                generateVote(CAT_3, CAT_NOT_EXISTS, CAT_NOT_EXISTS),
                generateVote(CAT_1, CAT_4, CAT_1),
                generateVote(CAT_1, CAT_5, CAT_1),
                generateVote(CAT_1, CAT_NOT_EXISTS, CAT_1)
        );

        Assertions.assertThat(bestScore.getScore(catVotes))
                .containsExactly(
                        new CatScore(CAT_URL_1, 3)
                );
    }

    private CatVote generateVote(CatId cat1, CatId cat2, CatId choice) {
        return CatVote.builder()
                .generatedVote(new CatIdTuple(cat1, cat2))
                .choice(choice)
                .build();
    }

}
