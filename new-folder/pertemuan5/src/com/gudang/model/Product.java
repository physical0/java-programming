package com.gudang.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    
    public Product() {
        this(null, 0);
    }
    
    public Product(String name, int quantity) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public SimpleStringProperty nameProperty() {
        return name;
    }
    
    public int getQuantity() {
        return quantity.get();
    }
    
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    
    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
    
    @Override
    public String toString() {
        return name.get();
    }
}