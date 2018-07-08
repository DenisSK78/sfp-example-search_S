package by.sfp.vaadin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class MessageSourceConfig {
    @Autowired
    private Environment environment;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(Arrays.stream(environment.getProperty("spring.messages.basename").split(","))
                .map(s -> "classpath:" + s.trim())
                .toArray(String[]::new));
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}