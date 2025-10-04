package com.gudang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuController {
    
    @FXML
    private void openProduct() {
        // Placeholder for Product management screen
        showInfoAlert("Product Management", "Product Management feature will be available soon.");
    }
    
    @FXML
    private void openCustomer() {
        // Open Customer management screen
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Customer Management");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showInfoAlert("Error", "Failed to open Customer Management: " + e.getMessage());
        }
    }
    
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}