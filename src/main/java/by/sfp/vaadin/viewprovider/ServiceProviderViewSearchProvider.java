package by.sfp.vaadin.viewprovider;

import by.sfp.vaadin.view.ServiceProviderSearchNameView;
import by.sfp.vaadin.viewprovider.resolver.ServiceProviderViewSearchResolver;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderViewSearchProvider implements ViewProvider {

    private final ServiceProviderViewSearchResolver resolver;

    private final ServiceProviderSearchNameView serviceProviderSearchNameView;

    @Autowired
    public ServiceProviderViewSearchProvider(ServiceProviderViewSearchResolver resolver, ServiceProviderSearchNameView serviceProviderSearchNameView) {
        this.resolver = resolver;
        this.serviceProviderSearchNameView = serviceProviderSearchNameView;
    }

    @Override
    public String getViewName(String url) {
        return resolver.valid(url) ? url : null;
    }

    @Override
    public View getView(String url) {
        return serviceProviderSearchNameView;
    }
}
