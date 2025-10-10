package com.uts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuController {
    // Store references to controllers
    private BooksController booksController;
    private MembersController membersController;

    @FXML
    private void openBook() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Book.fxml")); 
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
            // Store the controller reference
            booksController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Book window");
        }
    }

    @FXML
    private void openMember() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Member.fxml")); 
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
            membersController = loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Member window");
        }
    }

    @FXML
    private void openLoan() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Transactions.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
            TransactionsController transactionsController = loader.getController();
            if (booksController != null) {
                transactionsController.setBooksController(booksController);
            }
            if (membersController != null) {
                transactionsController.setMembersController(membersController);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Loan window");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}