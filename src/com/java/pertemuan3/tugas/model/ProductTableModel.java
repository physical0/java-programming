package com.java.pertemuan3.tugas.model;

import java.util.ArrayList;


public class ProductTableModel<ProductModel> {
    private ArrayList<ProductModel> products;

    public ProductTableModel(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public void addProduct(ProductModel product) {
        products.add(product);
    }

    public void removeProduct(int index) {
        products.remove(index);
    }

    public int getRowCount() {
        return products.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductModel product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getCode();
            case 1:
                return product.getName();
            case 2:
                return product.getQty();
            case 3:
                return product.getPrice();
            default:
                return null;
        }
    }

}
