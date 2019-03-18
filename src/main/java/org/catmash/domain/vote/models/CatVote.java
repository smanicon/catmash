package org.catmash.domain.vote.models;

import lombok.*;

@Value
@Builder
public class CatVote {
    CatIdTuple generatedVote;
    CatId choice;
}
