package by.sfp.vaadin.ui;

import by.sfp.vaadin.view.ErrorView;
import by.sfp.vaadin.viewprovider.ServiceProviderViewProvider;
import by.sfp.vaadin.viewprovider.ServiceProviderViewSearchProvider;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
@Theme("sfp")
public class Root extends UI {

    private final ServiceProviderViewProvider serviceProviderViewProvider;

    private final ServiceProviderViewSearchProvider serviceProviderViewSearchProvider;

    @Autowired
    public Root(ServiceProviderViewProvider serviceProviderViewProvider, ServiceProviderViewSearchProvider serviceProviderViewSearchProvider) {
        this.serviceProviderViewProvider = serviceProviderViewProvider;
        this.serviceProviderViewSearchProvider = serviceProviderViewSearchProvider;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getNavigator().addProvider(serviceProviderViewSearchProvider);
        getNavigator().addProvider(serviceProviderViewProvider);
        getNavigator().setErrorView(ErrorView.class);
    }
}