package org.catmash.domain.vote;

import org.catmash.domain.vote.models.CatMash;
import org.catmash.domain.vote.models.CatUrl;

import java.util.List;

public class VoteGenerator {
    private List<CatUrl> catUrls;
    private Randomize randomize;

    public VoteGenerator(List<CatUrl> catUrls, Randomize randomize) {
        this.catUrls = catUrls;
        this.randomize = randomize;
    }

    public CatMash generate() throws NotGeneratedVoteException {
        if(hasNotEnoughElement(catUrls.size())) {
            throw new NotGeneratedVoteException("Cannot generate votes because there are not enough cat");
        }

        CatUrl firstCat = pickRandomCat();
        CatUrl secondCat = pickRandomCatNotEqualsTo(firstCat);

        return new CatMash(
                firstCat,
                secondCat
        );
    }

    private CatUrl pickRandomCatNotEqualsTo(CatUrl firstCat) throws NotGeneratedVoteException {
        CatUrl secondCat;
        int generateTry = 0;

        do {
            secondCat = pickRandomCat();
            getAnOtherTryOrFail(++generateTry);
        } while(secondCat.equals(firstCat));

        return secondCat;
    }

    private void getAnOtherTryOrFail(int generateTry) throws NotGeneratedVoteException {
        if(generateTry >= 3) {
            throw new NotGeneratedVoteException("Cannot generate votes with different cat");
        }
    }

    private CatUrl pickRandomCat() {
        int availableCats = catUrls.size();

        return catUrls.get(randomize.rand(availableCats));
    }

    private boolean hasNotEnoughElement(int size) {
        return size <= 1;
    }
}
