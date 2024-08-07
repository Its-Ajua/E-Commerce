package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart<T> {
    private List<T> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}