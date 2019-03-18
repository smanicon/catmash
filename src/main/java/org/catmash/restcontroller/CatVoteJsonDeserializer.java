package org.catmash.restcontroller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.catmash.domain.vote.models.CatId;
import org.catmash.domain.vote.models.CatIdTuple;
import org.catmash.domain.vote.models.CatVote;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CatVoteJsonDeserializer extends JsonDeserializer<CatVote> {
    @Override
    public CatVote deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode rootNode = jp.getCodec().readTree(jp);

        TreeNode generatedVote = getChildNode(jp, rootNode, "generatedVote");
        String firstCat = getString(jp, generatedVote, "firstCat");
        String secondeCat = getString(jp, generatedVote, "secondCat");

        String choosed = getString(jp, rootNode, "choosed");

        return CatVote.builder()
                .generatedVote(new CatIdTuple(new CatId(firstCat), new CatId(secondeCat)))
                .choice(new CatId(choosed))
                .build();

    }

    private String getString(JsonParser jp, TreeNode node, String fieldName) throws JsonParseException {
        TreeNode fieldNode = getChildNode(jp, node, fieldName);

        if ( ! (fieldNode instanceof TextNode)) {
            throw new JsonParseException(jp, fieldName + " should be a text object");
        }
        return ((TextNode) fieldNode).asText();
    }

    private TreeNode getChildNode(JsonParser jp, TreeNode rootNode, String childName) throws JsonParseException {
        TreeNode childNode = rootNode.get(childName);

        if (childNode == null) {
            throw new JsonParseException(jp, childName + " is missing");
        }

        return childNode;
    }
}
