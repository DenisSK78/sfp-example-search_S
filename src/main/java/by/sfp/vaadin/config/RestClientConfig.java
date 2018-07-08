package by.sfp.vaadin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(environment.getProperty("rest.client.root.path")).build();
    }
}


