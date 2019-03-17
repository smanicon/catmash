package org.catmash.configurations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.catmash.domain.vote.CatUrl;
import org.catmash.domain.vote.Randomize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Configuration
public class CatmashConfiguration {

    @Bean
    public Randomize getRandomizeBean() {
        Random random = new Random();
        return random::nextInt;
    }

    @Bean
    public List<CatUrl> loadCatJsonBean(
            StdDeserializer<CatUrl[]> catDeserialiser,
            @Value("classpath:cats.json") Resource catsJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(CatUrl[].class, catDeserialiser);

        mapper.registerModule(module);

        CatUrl[] catUrls = mapper.readValue(catsJson.getFile(), CatUrl[].class);

        return asList(catUrls);
    }

    @Bean
    public StdDeserializer<CatUrl[]> catsDeserializer() {
        return new StdDeserializer<CatUrl[]>((JavaType) null) {

            @Override
            public CatUrl[] deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                JsonNode node = jp.getCodec().readTree(jp);

                List<CatUrl> catUrls = new ArrayList<>();

                for(JsonNode n : node.get("images")) {
                    String url = n.get("url").asText();
                    String id = n.get("id").asText();

                    catUrls.add(new CatUrl(id, url));
                }

                return catUrls.toArray(new CatUrl[0]);
            }
        };
    }
}
