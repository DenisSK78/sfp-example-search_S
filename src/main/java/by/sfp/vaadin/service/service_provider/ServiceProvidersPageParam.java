package by.sfp.vaadin.service.service_provider;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;


@Data
@NoArgsConstructor
public class ServiceProvidersPageParam {
    private String name;
    private Long domainCategoryId;
    private Long[] classCategoriesIds;

    public String getParamsFromUri(){
        return UriComponentsBuilder
            .fromUriString("")
            .queryParam("name", name)
            .queryParam("domainCategoryId", domainCategoryId)
            .queryParam("classCategoriesIds", (Object[]) classCategoriesIds)
            .build()
            .toUriString();
    }
}