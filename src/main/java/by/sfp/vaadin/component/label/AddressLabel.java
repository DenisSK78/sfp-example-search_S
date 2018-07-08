package by.sfp.vaadin.component.label;

import com.vaadin.ui.Label;

public class AddressLabel extends Label {
    public AddressLabel(String address) {
        setStyleName("addressLabel");
        setValue(address);
    }
}
