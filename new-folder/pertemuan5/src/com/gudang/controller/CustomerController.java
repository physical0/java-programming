package com.gudang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gudang.model.Customer;

public class CustomerController implements Initializable {
    
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

        tableCustomer.setItems(customerList);
    }
    
    @FXML
    private void showAddCustomerDialog() {
        try {
            Stage dialogStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFormDialog.fxml"));
            Scene scene = new Scene(loader.load());

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setTitle("Add Customer");
            dialogStage.setScene(scene);

            TextField txtId = (TextField)scene.lookup("#txtId");
            TextField txtName = (TextField)scene.lookup("#txtName");
            TextField txtPhone = (TextField)scene.lookup("#txtPhone");
            TextField txtEmail = (TextField)scene.lookup("#txtEmail");
            Button btnSave = (Button)scene.lookup("#btnSave");
            Button btnCancel = (Button)scene.lookup("#btnCancel");
            Label titleLabel = (Label)scene.lookup("#titleLabel");

            titleLabel.setText("Add Customer");
            
            txtPhone.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getText().matches("[0-9]*")) {
                    return change;
                }
                return null;
            }));
            
            btnSave.setOnAction(event -> {
                if (validateAndSaveCustomer(txtId, txtName, txtPhone, txtEmail, null)) {
                    dialogStage.close();
                }
            });
            
            btnCancel.setOnAction(event -> dialogStage.close());
            
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to load dialog: " + e.getMessage());
        }
    }
    
    @FXML
    private void showEditCustomerDialog() {
        Customer selectedCustomer = tableCustomer.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a customer to edit");
            return;
        }
        
        try {
            Stage dialogStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFormDialog.fxml"));
            Scene scene = new Scene(loader.load());

            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.setTitle("Edit Customer");
            dialogStage.setScene(scene);

            TextField txtId = (TextField)scene.lookup("#txtId");
            TextField txtName = (TextField)scene.lookup("#txtName");
            TextField txtPhone = (TextField)scene.lookup("#txtPhone");
            TextField txtEmail = (TextField)scene.lookup("#txtEmail");
            Button btnSave = (Button)scene.lookup("#btnSave");
            Button btnCancel = (Button)scene.lookup("#btnCancel");
            Label titleLabel = (Label)scene.lookup("#titleLabel");
            
            titleLabel.setText("Edit Customer");
            
            txtId.setText(selectedCustomer.getId());
            txtName.setText(selectedCustomer.getName());
            txtPhone.setText(selectedCustomer.getPhone());
            txtEmail.setText(selectedCustomer.getEmail());
            
            txtPhone.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getText().matches("[0-9]*")) {
                    return change;
                }
                return null;
            }));
            
            // Handle button actions
            btnSave.setOnAction(event -> {
                if (validateAndSaveCustomer(txtId, txtName, txtPhone, txtEmail, selectedCustomer)) {
                    dialogStage.close();
                }
            });
            
            btnCancel.setOnAction(event -> dialogStage.close());
            
            // Show dialog
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to load dialog: " + e.getMessage());
        }
    }
    
    @FXML
    private void showDeleteCustomerDialog() {
        Customer selectedCustomer = tableCustomer.getSelectionModel().getSelectedItem();
        
        if (selectedCustomer == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a customer to delete");
            return;
        }
        
        // Show confirmation dialog
        Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Are you sure you want to delete?");
        confirmDialog.setContentText("You are about to delete the customer: " + selectedCustomer.getName() + " (ID: " + selectedCustomer.getId() + "). This action cannot be undone.");
        
        Optional<ButtonType> result = confirmDialog.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                customerList.remove(selectedCustomer);
                showAlert(AlertType.INFORMATION, "Success", "Customer deleted successfully");
            } catch (Exception e) {
                showAlert(AlertType.ERROR, "Error", "Failed to delete customer: " + e.getMessage());
            }
        }
    }
    
    private boolean validateAndSaveCustomer(TextField txtId, TextField txtName, TextField txtPhone, TextField txtEmail, Customer existingCustomer) {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            
            if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
                showAlert(AlertType.ERROR, "Incomplete Data", "ID, Name, and Email fields must be filled");
                return false;
            }
            
            // email validation
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showAlert(AlertType.ERROR, "Invalid Input", "Email address is not valid");
                return false;
            }
            
            // Phone validation
            if (phone.length() < 10 || phone.length() > 15) {
                showAlert(AlertType.ERROR, "Invalid Input", "Phone number must be between 10-15 digits");
                return false;
            }
            
            if (existingCustomer != null) {
                // Update existing customer
                existingCustomer.setId(id);
                existingCustomer.setName(name);
                existingCustomer.setPhone(phone);
                existingCustomer.setEmail(email);
                tableCustomer.refresh();
                showAlert(AlertType.INFORMATION, "Success", "Customer updated successfully");
            } else {
                // Create new customer
                Customer newCustomer = new Customer(id, name, phone, email);
                customerList.add(newCustomer);
                showAlert(AlertType.INFORMATION, "Success", "Customer added successfully");
            }
            
            return true;
            
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Operation failed: " + e.getMessage());
            return false;
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