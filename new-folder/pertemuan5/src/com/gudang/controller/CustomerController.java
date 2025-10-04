package com.gudang.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import com.gudang.model.Customer;

public class CustomerController implements Initializable {
    
    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TableView<Customer> tableCustomer;
    @FXML private TableColumn<Customer, String> colIdTitle;
    @FXML private TableColumn<Customer, String> colNameTitle;
    @FXML private TableColumn<Customer, String> colPhoneTitle;
    @FXML private TableColumn<Customer, String> colEmailTitle; 

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colIdTitle.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNameTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhoneTitle.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmailTitle.setCellValueFactory(new PropertyValueFactory<>("email")); 

        txtPhone.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));

        tableCustomer.setItems(customerList);
        
        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showCustomerDetails(newSelection);
            }
        });
    }
    
    @FXML
    private void addCustomer() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText(); 

            if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
                showAlert(AlertType.ERROR, "Data incomplete", "ID, Name, and Email must be filled");
                return;
            }

            // email address validation
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showAlert(AlertType.ERROR, "Invalid Input", "Email address is not valid");
                return;
            }
            
                
            // Phone format validation
            if (phone.length() < 10 || phone.length() > 15) {
                showAlert(AlertType.ERROR, "Invalid Input", "Phone number must be between 10-15 digits");
                return;
            }

            Customer newCustomer = new Customer(id, name, phone, email);

            customerList.add(newCustomer);
            
            clearForm();
            
            showAlert(AlertType.INFORMATION, "Success", "Customer data added successfully");
            
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to add customer: " + e.getMessage());
        }
    }
    
    @FXML
    private void updateCustomer() {
        Customer selectedCustomer = tableCustomer.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
            showAlert(AlertType.WARNING, "No data selected", "Please select the data you want to update");
            return;
        }
        
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();

            if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
                showAlert(AlertType.ERROR, "Data incomplete", "ID, Name, and Email must be filled");
                return;
            }

            if (phone.length() < 10 || phone.length() > 15) {
                showAlert(AlertType.ERROR, "Invalid Input", "Phone number must be between 10-15 digits");
                return;
            }

            // Update data
            selectedCustomer.setId(id);
            selectedCustomer.setName(name);
            selectedCustomer.setPhone(phone);
            selectedCustomer.setEmail(email);
            
            tableCustomer.refresh();
            
            clearForm();
            
            showAlert(AlertType.INFORMATION, "Success", "Customer data updated successfully");
            
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to update customer: " + e.getMessage());
        }
    }
    
    @FXML
    private void deleteCustomer() {
        Customer selectedCustomer = tableCustomer.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
            showAlert(AlertType.WARNING, "No data selected", "Please select the data you want to delete");
            return;
        }
        
        try {
            // Remove from list
            customerList.remove(selectedCustomer);
            
            clearForm();
            showAlert(AlertType.INFORMATION, "Success", "Customer data deleted successfully");
            
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to delete customer: " + e.getMessage());
        }
    }
    
    @FXML
    private void clearForm() {
        txtId.clear();
        txtName.clear();
        txtPhone.clear();
        txtEmail.clear(); 
        tableCustomer.getSelectionModel().clearSelection();
    }
    
    private void showCustomerDetails(Customer customer) {
        txtId.setText(customer.getId());
        txtName.setText(customer.getName());
        txtPhone.setText(customer.getPhone());
        txtEmail.setText(customer.getEmail());
    }
    
    
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}