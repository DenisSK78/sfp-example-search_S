package by.sfp.vaadin.component.layout;

import by.sfp.vaadin.component.button.TagClassCategory;
import by.sfp.vaadin.component.label.AddressLabel;
import by.sfp.vaadin.component.label.SPSearchNameLabel;
import by.sfp.vaadin.domain.ServiceProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.GridLayout;


public class ServiceProviderInfoLayout extends GridLayout{
    public ServiceProviderInfoLayout(ServiceProvider serviceProvider, String searchString) {
        setStyleName("serviceProviderInfoLayout");
        SPSearchNameLabel spSearchNameLabel = new SPSearchNameLabel(createNameForSearchName(serviceProvider.getName(), searchString), ContentMode.HTML);
        AddressLabel addressLabel = new AddressLabel(createAddress(serviceProvider));
        TagClassCategoryLayout tagClassCategoryLayout = new TagClassCategoryLayout();
        for (int i = 0; i < serviceProvider.getClassCategories().size(); i++) {
            tagClassCategoryLayout.addComponent(new TagClassCategory(serviceProvider.getClassCategories().get(i).getName()));
        }
        this.addComponents(spSearchNameLabel, addressLabel, tagClassCategoryLayout);
    }

    public ServiceProviderInfoLayout(ServiceProvider serviceProvider){
        setStyleName("serviceProviderInfoLayout");
        SPSearchNameLabel spSearchNameLabel = new SPSearchNameLabel(createNameForSearch(serviceProvider.getName()), ContentMode.HTML);
        AddressLabel addressLabel = new AddressLabel(createAddress(serviceProvider));
        TagClassCategoryLayout tagClassCategoryLayout = new TagClassCategoryLayout();
        for (int i = 0; i < serviceProvider.getClassCategories().size(); i++) {
            tagClassCategoryLayout.addComponent(new TagClassCategory(serviceProvider.getClassCategories().get(i).getName()));
        }
        this.addComponents(spSearchNameLabel, addressLabel, tagClassCategoryLayout);
    }

    private String createAddress(ServiceProvider serviceProvider){
        StringBuilder sb = new StringBuilder();
        sb.append(serviceProvider.getCity());
        if (serviceProvider.getStreet()!=null) {
            sb.append(", ");
            sb.append(serviceProvider.getStreet());
        }
        if (serviceProvider.getStreet()!=null) {
            sb.append(", ");
            sb.append(serviceProvider.getBuilding());
            if (serviceProvider.getBlock()!=null) {
                sb.append("/");
                sb.append(serviceProvider.getBlock());
            }
        }
        return sb.toString();
    }

    private String createNameForSearchName(String name, String searchString){
        String search = searchString.toLowerCase();
        String str = name.toLowerCase();
        int indexFirst = str.indexOf(search);
        if (indexFirst == 0){
            search = search.substring(0, 1).toUpperCase() + search.substring(1);
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        }else {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return "<div class='div-name'>" + str.replaceFirst(search, "<span class='underline'>"+search+"</span>") + "</div>";
    }

    private String createNameForSearch(String name){
        return "<span class='underline'>"+name+"</span>";
    }
}
