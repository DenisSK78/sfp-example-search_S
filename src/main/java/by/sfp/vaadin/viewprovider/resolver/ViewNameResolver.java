package by.sfp.vaadin.viewprovider.resolver;

public interface ViewNameResolver<T> {
    boolean valid(String url);

    T path(String url);
}