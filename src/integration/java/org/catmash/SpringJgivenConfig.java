package org.catmash;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import org.catmash.domain.vote.CatUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@EnableJGiven
@Configuration
@ComponentScan
public class SpringJgivenConfig {

    @Bean
    @Primary
    public List<CatUrl> getCatUrl() {
        return new ArrayList<>();
    }

}
