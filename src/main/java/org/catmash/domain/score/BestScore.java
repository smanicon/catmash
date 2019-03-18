package org.catmash.domain.score;

import io.vavr.Tuple2;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatUrl;
import org.catmash.domain.vote.models.CatVote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.vavr.collection.Stream.ofAll;

class BestScore {
    private List<CatUrl> catUrls;
    private final Map<CatId, CatUrl> urls;

    public BestScore(List<CatUrl> catUrls) {

        urls = ofAll(catUrls)
                .groupBy(CatUrl::getId)
                .map((id, urls) -> new Tuple2<>(new CatId(id), urls.get(0)))
                .toJavaMap();
    }

    public Collection<CatScore> getScore(List<CatVote> catVotes) {

        return ofAll(catVotes)
                .map(CatVote::getChoice)
                .groupBy((x) -> x)
                .map((id, l) -> new Tuple2<>(id, l.length()))
                .filter( (t) -> urls.containsKey(t._1))
                .map((id, n) -> new Tuple2<>(urls.get(id), n))
                .iterator(CatScore::new)
                .toStream()
                .sortBy(CatScore::getScore)
                .reverse()
                .toJavaList()

                ;

    }
}
