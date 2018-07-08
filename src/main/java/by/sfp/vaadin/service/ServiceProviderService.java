package by.sfp.vaadin.service;

import by.sfp.vaadin.domain.ServiceProvider;
import by.sfp.vaadin.service.service_provider.ServiceProvidersPageParam;

import java.util.List;

public interface ServiceProviderService {
    List<ServiceProvider> getServiceProviders(String uriParam);

    String getParamFromUrl(ServiceProvidersPageParam pageParam);
}
