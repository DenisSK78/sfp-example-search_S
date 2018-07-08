package by.sfp.vaadin.service.service_provider;

import by.sfp.vaadin.domain.ServiceProvider;
import by.sfp.vaadin.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private RestTemplate restTemplate;

    @Autowired
    public ServiceProviderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ServiceProvider> getServiceProviders(String uriParam) {
        ServiceProvider[] sps = restTemplate.getForObject( "/service_providers"+uriParam, ServiceProvider[].class);
        if (sps != null) {
            List<ServiceProvider> providerList = new ArrayList<>();
            Collections.addAll(providerList, sps);
            return providerList;
        }
        return Collections.emptyList();
    }

    public String getParamFromUrl(ServiceProvidersPageParam pageParm){
        StringBuilder sb = new StringBuilder("service-providers?action=search");
        if (pageParm.getName()!=null){
            sb.append("&name=");
            sb.append(pageParm.getName());
        }
        if (pageParm.getDomainCategoryId()!=null){
            sb.append("&domainCategory=");
            sb.append(pageParm.getDomainCategoryId());
        }
        Long [] clC = pageParm.getClassCategoriesIds();
        if (clC!=null){
            sb.append("&classCategories=[");
            for (int i = 0; i < clC.length; i++) {
                sb.append(clC[i]);
                if (i+2<=clC.length) sb.append(",");
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
