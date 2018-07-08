package by.sfp.vaadin.component.field;

import com.vaadin.ui.TextField;

public class SearchTextField extends TextField{
    public SearchTextField(String placeholder) {
        setStyleName("searchTextField");
        setPlaceholder(placeholder);
        setMaxLength(50);
    }
}
