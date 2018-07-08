package by.sfp.vaadin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProvider {
    private Long id;
    private String name;
    private String city;
    private String street;
    private String building;
    private String block;
    private String mobile;
    private String phone;
    private String email;
    private String site;
    private List<ClassCategory> classCategories;
}
