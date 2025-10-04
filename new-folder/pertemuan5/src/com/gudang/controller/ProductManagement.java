package com.gudang.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import com.gudang.model.Product;

public class ProductManagement implements Initializable {
    
    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;
    @FXML private TableView<Product> tableProduct;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private Product selectedProduct = null;
    private boolean editMode = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        txtQuantity.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));
        
        tableProduct.setItems(productList);
        
        tableProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProduct = newSelection;
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            } else {
                selectedProduct = null;
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
            }
        });
        
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
    }
    
    @FXML
    private void handleAdd() {
        try {
            String name = txtName.getText().trim();
            String quantityText = txtQuantity.getText().trim();
            
            // Validation
            if (name.isEmpty()) {
                showAlert(AlertType.ERROR, "Incomplete Data", "Product name must be filled");
                return;
            }
            
            if (quantityText.isEmpty()) {
                showAlert(AlertType.ERROR, "Incomplete Data", "Quantity must be filled");
                return;
            }
            
            int quantity;
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity < 0) {
                    showAlert(AlertType.ERROR, "Invalid Input", "Quantity must be a positive number");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Invalid Input", "Quantity must be a number");
                return;
            }
            
            if (editMode && selectedProduct != null) {
                selectedProduct.setName(name);
                selectedProduct.setQuantity(quantity);
                tableProduct.refresh();
                showAlert(AlertType.INFORMATION, "Success", "Product data has been updated");
                
                editMode = false;
                btnAdd.setText("Add");
            } else {
                // Add new product
                Product newProduct = new Product(name, quantity);
                productList.add(newProduct);
                showAlert(AlertType.INFORMATION, "Success", "Product has been added successfully");
            }
            
            // Clear form
            clearForm();
            
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleEdit() {
        if (selectedProduct != null) {
            txtName.setText(selectedProduct.getName());
            txtQuantity.setText(String.valueOf(selectedProduct.getQuantity()));
            
            editMode = true;
            btnAdd.setText("Update");
        }
    }
    
    @FXML
    private void handleDelete() {
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            clearForm();
            showAlert(AlertType.INFORMATION, "Success", "Product has been deleted successfully");
        }
    }
    
    @FXML
    private void clearForm() {
        txtName.clear();
        txtQuantity.clear();
        tableProduct.getSelectionModel().clearSelection();
        selectedProduct = null;
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        
        // Reset edit mode
        if (editMode) {
            editMode = false;
            btnAdd.setText("Add");
        }
    }
    
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}