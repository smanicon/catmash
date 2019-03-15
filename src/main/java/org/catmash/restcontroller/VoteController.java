package org.catmash.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @GetMapping("/generate")
    public String generateVote() {
        return "{\n" +
                "  \"firstCat\": {\n" +
                "    \"id\": \"MTgwODA3MA\",\n" +
                "    \"url\": \"http://24.media.tumblr.com/tumblr_m82woaL5AD1rro1o5o1_1280.jpg\"\n" +
                "  },\n" +
                "  \"secondCat\": {\n" +
                "    \"id\": \"tt\",\n" +
                "    \"url\": \"http://24.media.tumblr.com/tumblr_m29a9d62C81r2rj8po1_500.jpg\"\n" +
                "  }\n" +
                "}";

    }
}
