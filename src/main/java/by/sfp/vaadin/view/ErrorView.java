package by.sfp.vaadin.view;

import by.sfp.vaadin.service.MessagesService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView
public class ErrorView extends VerticalLayout implements View {

    private MessagesService messagesService;

    @Autowired
    public ErrorView(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostConstruct
    public void initPage() {
        addComponent(new Label(messagesService.getMessage("ErrorView.no.page")));
    }
}