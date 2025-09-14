package com.java.pertemuan3.tugas.model;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private ArrayList<ProductModel> products;
    private String[] columnNames = {"Code", "Name", "Quantity", "Price"};

    public ProductTableModel(ArrayList<ProductModel> products) {
        this.products = products;
    }

    public void addProduct(ProductModel product) {
        products.add(product);
        fireTableRowsInserted(products.size()-1, products.size()-1);
    }

    public void removeProduct(int index) {
        products.remove(index);
        fireTableRowsDeleted(index, index);
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
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
