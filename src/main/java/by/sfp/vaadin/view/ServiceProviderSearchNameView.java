package by.sfp.vaadin.view;

import by.sfp.vaadin.component.button.ButtonSearchName;
import by.sfp.vaadin.component.field.SearchTextField;
import by.sfp.vaadin.component.label.HiddenLabel;
import by.sfp.vaadin.component.label.ResultSearchLabel;
import by.sfp.vaadin.component.layout.AllProvidersInfoLayout;
import by.sfp.vaadin.component.layout.SearchNameLayout;
import by.sfp.vaadin.component.layout.ServiceProviderInfoLayout;
import by.sfp.vaadin.domain.ServiceProvider;
import by.sfp.vaadin.service.MessagesService;
import by.sfp.vaadin.service.service_provider.ServiceProviderServiceImpl;
import by.sfp.vaadin.service.service_provider.ServiceProvidersPageParam;
import by.sfp.vaadin.viewprovider.resolver.ServiceProviderViewSearchResolver;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@SpringComponent
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServiceProviderSearchNameView extends VerticalLayout implements View {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ButtonSearchName buttonSearchName;
    private ResultSearchLabel resultSearchLabel;
    private SearchTextField searchTextField;
    private AllProvidersInfoLayout allProvidersLayout;
    private List<ServiceProvider> serviceProviders;
    private HiddenLabel hiddenLabel;
    private ServiceProvidersPageParam pageParam;

    @Autowired
    ServiceProviderViewSearchResolver resolver;

    @Autowired
    ServiceProviderServiceImpl providerService;

    @Autowired
    MessagesService messagesService;

    @Autowired
    ServiceProviderViewSearchResolver viewSearchResolver;


    public ServiceProviderSearchNameView(){
        setWidth("100%");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        SearchNameLayout searchNameLayout = new SearchNameLayout();
        searchTextField = new SearchTextField(messagesService.getMessage("SearchTextField.placeholder"));
        buttonSearchName = new ButtonSearchName(messagesService.getMessage("ButtonSearch.caption"));
        resultSearchLabel = new ResultSearchLabel();
        allProvidersLayout = new AllProvidersInfoLayout();
        hiddenLabel = new HiddenLabel();
        searchNameLayout.addComponents(searchTextField, buttonSearchName, resultSearchLabel, allProvidersLayout, hiddenLabel);
        addComponent(searchNameLayout);
        try {
            serviceProviders = providerService.getServiceProviders(pageParam.getParamsFromUri());
        } catch (HttpClientErrorException e) {
            log.error(String.format("%s Method: GET: /service_providers %s", e.getMessage(), e.getResponseBodyAsString()));
            this.getUI().getNavigator().navigateTo("ErrorView");
        }
        addServiceProviders(serviceProviders, messagesService.getMessage("ResultSearchLabel.NoMatchesFound"));

        searchTextField.addValueChangeListener(valueChangeEvent -> {
            int len = valueChangeEvent.getValue().length();
            if (len < 1) buttonSearchName.setbuttonSearchNameOff();
            else buttonSearchName.setbuttonSearchNameOn();
        });

        buttonSearchName.addClickListener(clickEvent -> {
            if (!hiddenLabel.getValue().equals(searchTextField.getValue()) & !searchTextField.getValue().trim().equals("")) {
                pageParam.setName(searchTextField.getValue().trim());
                allProvidersLayout.removeAllComponents();
                resultSearchLabel.setValue("");
                try {
                    serviceProviders = providerService.getServiceProviders(pageParam.getParamsFromUri());
                } catch (HttpClientErrorException e) {
                    log.error(String.format("%s Method: GET: /service_providers %s", e.getMessage(), e.getResponseBodyAsString()));
                    this.getUI().getNavigator().navigateTo("ErrorView");
                }
                addServiceProviders(serviceProviders, messagesService.getMessage("ResultSearchLabel.NoMatchesFound"));
                hiddenLabel.setValue(pageParam.getName());
                pageParam.setDomainCategoryId(null);
                pageParam.setClassCategoriesIds(null);
                Navigator.UriFragmentManager uriManager = new Navigator.UriFragmentManager(Page.getCurrent());
                uriManager.setState(providerService.getParamFromUrl(pageParam));
            }
        });
    }

    private void addServiceProviders (List < ServiceProvider > providers, String msgNoFound) {
        if (serviceProviders.size() > 0) {
            if (pageParam.getName() != null) {
                for (ServiceProvider serviceProvider : serviceProviders) {
                    allProvidersLayout.addComponent(new ServiceProviderInfoLayout(serviceProvider, pageParam.getName()));
                }
            } else {
                for (ServiceProvider serviceProvider : serviceProviders) {
                    allProvidersLayout.addComponent(new ServiceProviderInfoLayout(serviceProvider));
                }
            }
        } else {
            resultSearchLabel.setValue(msgNoFound);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.removeAllComponents();
        pageParam = resolver.path(event.getViewName());
    }
}