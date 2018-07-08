package by.sfp.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ServiceProviderView extends VerticalLayout implements View {
    private Long id;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label(id.toString()));
    }

    public static ServiceProviderViewBuilder builder() {
        return new ServiceProviderViewBuilder();
    }

    public static class ServiceProviderViewBuilder {
        private ServiceProviderView view = new ServiceProviderView();

        private ServiceProviderViewBuilder() {}

        public ServiceProviderViewBuilder id(Long id) {
            view.id = id;
            return this;
        }

        public ServiceProviderView build() {
            return view;
        }
    }
}