package org.catmash.domain.vote.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CatVote {
    CatIdTuple generatedVote;
    CatId choice;
}
