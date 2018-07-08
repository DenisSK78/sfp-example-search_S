package by.sfp.vaadin.component.button;

import com.vaadin.ui.Button;

public class ButtonSearchName extends Button {
    public ButtonSearchName(String caption) {
        super(caption);
        addStyleName("buttonSearchNameOff");
        setEnabled(false);
    }

    public void setbuttonSearchNameOff() {
        setEnabled(false);
        setStyleName("buttonSearchNameOff");
    }

    public void setbuttonSearchNameOn() {
        setEnabled(true);
        setClickShortcut(13);
        setStyleName("buttonSearchNameOn");
    }
}

