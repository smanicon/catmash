package org.catmash.domain.vote;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VoteGeneratorTest {

    private static final CatUrl CAT_URL_1 = new CatUrl("id1", "url1");
    private static final CatUrl CAT_URL_2 = new CatUrl("id2", "url2");
    private static final CatUrl CAT_URL_3 = new CatUrl("id3", "url3");

    @Test
    public void should_return_all_the_two_cats_if_input_has_two_cats() throws NotGeneratedVoteException {
        List<CatUrl> catUrls = asList(
                CAT_URL_1,
                CAT_URL_2
        );

        int[] i = {0};
        VoteGenerator voteGenerator = new VoteGenerator(catUrls, (x) -> i[0]++);

        assertThat(voteGenerator.generate()).isEqualTo(
                new CatMash(
                        CAT_URL_1,
                        CAT_URL_2
                )
        );
    }

    @Test
    public void should_throw_an_exception_if_input_has_one_cat() {
        List<CatUrl> catUrls = singletonList(CAT_URL_1);

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, (i) -> 0);

        assertThatExceptionOfType(NotGeneratedVoteException.class)
                .isThrownBy(voteGenerator::generate)
                .withMessage("Cannot generate votes because there are not enough cat");
    }

    @Test
    public void should_throw_an_exception_if_input_is_empty() {
        List<CatUrl> catUrls = emptyList();

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, (i) -> 0);

        assertThatExceptionOfType(NotGeneratedVoteException.class)
                .isThrownBy(voteGenerator::generate)
                .withMessage("Cannot generate votes because there are not enough cat");
    }

    @Test
    public void should_pick_two_cats_from_random_input() throws NotGeneratedVoteException {
        List<CatUrl> catUrls = asList(
                CAT_URL_1,
                CAT_URL_2,
                CAT_URL_3
        );

        Randomize r = mock(Randomize.class);
        when(r.rand(eq(3))).thenReturn(2, 0);

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, r);

        assertThat(voteGenerator.generate()).isEqualTo(
                new CatMash(
                        CAT_URL_3,
                        CAT_URL_1
                )
        );
    }

    @Test
    public void should_not_return_the_same_cat() throws NotGeneratedVoteException {
        List<CatUrl> catUrls = asList(
                CAT_URL_1,
                CAT_URL_2,
                CAT_URL_3
        );

        Randomize r = mock(Randomize.class);
        when(r.rand(eq(3))).thenReturn(2, 2, 0);

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, r);

        assertThat(voteGenerator.generate()).isEqualTo(
                new CatMash(
                        CAT_URL_3,
                        CAT_URL_1
                )
        );
    }

    @Test
    public void should_throw_exception_if_same_random_cat_is_returned_3_times() {
        List<CatUrl> catUrls = asList(
                CAT_URL_1,
                CAT_URL_2,
                CAT_URL_3
        );

        Randomize r = mock(Randomize.class);
        when(r.rand(eq(3)))
                .thenReturn(2, 2, 2, 2)
                .thenThrow(new AssertionError("No more call"));

        VoteGenerator voteGenerator = new VoteGenerator(catUrls, r);

        assertThatExceptionOfType(NotGeneratedVoteException.class)
                .isThrownBy(voteGenerator::generate)
                .withMessage("Cannot generate votes with different cat");

        Mockito.verify(r, times(4)).rand(eq(3));
    }
}