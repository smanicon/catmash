package org.catmash.domain.vote;

import java.util.List;

public class VoteGenerator {
    private List<CatUrl> catUrls;
    private Randomize randomize;

    public VoteGenerator(List<CatUrl> catUrls, Randomize randomize) {
        this.catUrls = catUrls;
        this.randomize = randomize;
    }

    public CatMash generate() {
        if(hasNotEnoughElement(catUrls.size())) {
            throw new IllegalStateException("Cannot generate votes because there are not enough cat");
        }

        CatUrl firstCat = pickRandomCat();
        CatUrl secondCat = pickRandomCatNotEqualsTo(firstCat);

        return new CatMash(
                firstCat,
                secondCat
        );
    }

    private CatUrl pickRandomCatNotEqualsTo(CatUrl firstCat) {
        CatUrl secondCat;
        int generateTry = 0;

        do {
            secondCat = pickRandomCat();
            getAnOtherTryOrFail(++generateTry);
        } while(secondCat.equals(firstCat));

        return secondCat;
    }

    private void getAnOtherTryOrFail(int generateTry) {
        if(generateTry >= 3) {
            throw new IllegalStateException("Cannot generate votes with different cat");
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
