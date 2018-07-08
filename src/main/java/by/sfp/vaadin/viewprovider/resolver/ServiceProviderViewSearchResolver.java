package by.sfp.vaadin.viewprovider.resolver;

import by.sfp.vaadin.service.service_provider.ServiceProvidersPageParam;
import org.springframework.stereotype.Component;

@Component
public class ServiceProviderViewSearchResolver implements ViewNameResolver<ServiceProvidersPageParam> {

    @Override
    public boolean valid(String url) {
        return url.matches("^(service-providers[?]action=search)(&name=[^&|$]*)?(&domainCategory=(\\d)*)?(&classCategories=\\[((\\d|,)*)?\\d]$)?");
    }

    @Override
    public ServiceProvidersPageParam path(String url) {
        ServiceProvidersPageParam pageParam = new ServiceProvidersPageParam();
        String[] arrParamKeyAndVals = url.split("&");
        if (arrParamKeyAndVals.length < 2) {
            return pageParam;
        }else {
            for (int i = 1; i < arrParamKeyAndVals.length; i++) {
                String[] arrKeyAndVal = arrParamKeyAndVals[i].split("=");
                switch (arrKeyAndVal[0]){
                    case "name": pageParam.setName(arrKeyAndVal[1]); break;
                    case "domainCategory": pageParam.setDomainCategoryId(Long.valueOf(arrKeyAndVal[1])); break;
                    case "classCategories":
                        String[] strParam = (arrKeyAndVal[1].substring(1,arrKeyAndVal[1].length()-1)).split(",");
                        Long[] arrLong = new Long[strParam.length];
                        for (int j = 0; j < strParam.length ; j++) {
                            arrLong[j] = Long.valueOf(strParam[j]);
                        }
                        pageParam.setClassCategoriesIds(arrLong);
                        break;
                }
            }
        }
        return pageParam;
    }
}