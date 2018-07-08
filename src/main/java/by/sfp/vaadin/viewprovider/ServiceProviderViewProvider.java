package by.sfp.vaadin.viewprovider;

import by.sfp.vaadin.view.ServiceProviderView;
import by.sfp.vaadin.viewprovider.resolver.ServiceProviderViewNameResolver;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderViewProvider implements ViewProvider {
    private final ServiceProviderViewNameResolver viewNameResolver;

    @Autowired
    public ServiceProviderViewProvider(ServiceProviderViewNameResolver viewNameResolver) {
        this.viewNameResolver = viewNameResolver;
    }

    @Override
    public String getViewName(String url) {
        return viewNameResolver.valid(url) ? url : null;
    }

    @Override
    public View getView(String url) {
        Long id = viewNameResolver.path(url);
        return ServiceProviderView.builder()
                .id(id)
                .build();
    }
}