package org.catmash.domain.score;

import lombok.Value;
import org.catmash.domain.vote.models.CatUrl;

@Value
public class CatScore {
    CatUrl caturl;
    int score;
}
