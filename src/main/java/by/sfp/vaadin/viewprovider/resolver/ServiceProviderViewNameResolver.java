package by.sfp.vaadin.viewprovider.resolver;

import org.springframework.stereotype.Component;

@Component
public class ServiceProviderViewNameResolver implements ViewNameResolver<Long> {
    @Override
    public boolean valid(String url) {
        return url.matches("^service-providers/[0-9]{1,19}$");
    }

    @Override
    public Long path(String url) {
        return Long.parseLong(url.split("/")[1]);
    }
}