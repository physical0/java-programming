package com.uts.controllers;

import java.util.Optional;

import com.uts.model.Member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class MembersController {
    @FXML private TextField memberIdField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    
    @FXML private TableView<Member> membersTable;
    @FXML private TableColumn<Member, String> idColumn;
    @FXML private TableColumn<Member, String> nameColumn;
    @FXML private TableColumn<Member, String> addressColumn;
    @FXML private TableColumn<Member, String> phoneColumn;
    
    private ObservableList<Member> membersList = FXCollections.observableArrayList();
    private boolean isEditMode = false;
    private Member memberBeingEdited;
    
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        membersTable.setItems(membersList);
        
        membersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editButton.setDisable(newSelection == null);
            deleteButton.setDisable(newSelection == null);
        });
        
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        
    }
    
    @FXML
    private void handleAddMember() {
        if (isEditMode) {
            updateMember();
        } else {
            addNewMember();
        }
    }
    
    private void addNewMember() {
        String id = memberIdField.getText().trim();
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        
        // Validate id, name, and phone number fields
        if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            handleMandatoryFields();
            return;
        }
        
        // Check for duplicate ID
        boolean idExists = membersList.stream().anyMatch(member -> member.getId().equals(id));
        if (idExists) {
            handleIdExists();
            return;
        }
        
        // Validate phone number (only digits)
        if (!phone.matches("\\d+")) {
            handleInvalidPhoneNumber();
            return;
        }
        
        Member newMember = new Member(id, name, address, phone);
        membersList.add(newMember);
        
        clearFields();
    }
    
    private void handleMandatoryFields() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Member ID, Name and Phone Number are mandatory fields.");
        alert.showAndWait();
    }
    
    private void handleIdExists() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Member ID already exists.");
        alert.showAndWait();
    }
    
    private void handleInvalidPhoneNumber() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Phone number must contain only digits.");
        alert.showAndWait();
    }
    
    @FXML
    private void handleEditMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            // Switch to edit mode
            isEditMode = true;
            memberBeingEdited = selectedMember;
            
            memberIdField.setText(selectedMember.getId());
            nameField.setText(selectedMember.getName());
            addressField.setText(selectedMember.getAddress());
            phoneField.setText(selectedMember.getPhone());
            
            memberIdField.setDisable(true);
            
            addButton.setText("Update Member");
        }
    }
    
    private void updateMember() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Name and Phone Number are required");
            alert.showAndWait();
            return;
        }
        
        if (!phone.matches("\\d+")) {
            handleInvalidPhoneNumber();
            return;
        }
        
        memberBeingEdited.setName(name);
        memberBeingEdited.setAddress(address);
        memberBeingEdited.setPhone(phone);
        
        membersTable.refresh();
        
        exitEditMode();
    }
    
    @FXML
    private void handleDeleteMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
            confirmDialog.setTitle("Delete Confirmation");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Are you sure you want to delete this member?");
            
            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                membersList.remove(selectedMember);
            }
        }
    }
    
    private void clearFields() {
        memberIdField.clear();
        nameField.clear();
        addressField.clear();
        phoneField.clear();
    }
    
    private void exitEditMode() {
        isEditMode = false;
        memberBeingEdited = null;
        memberIdField.setDisable(false);
        addButton.setText("Add Member");
        
        Book selectedMember = membersTable.getSelectionModel().getSelectedItem();
        deleteButton.setDisable(selectedMember == null);
        
        clearFields();
    }
}