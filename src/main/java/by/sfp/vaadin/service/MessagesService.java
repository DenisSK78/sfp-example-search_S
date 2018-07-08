package by.sfp.vaadin.service;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessagesService implements MessageSourceAware {
    private MessageSource messageSource;

    public String getMessage(String string) {
        return messageSource.getMessage(string, null, Locale.getDefault());
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}