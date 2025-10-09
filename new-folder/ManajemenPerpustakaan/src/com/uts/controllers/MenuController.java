package com.uts.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void openBook() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Book.fxml")); 
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

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
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Member window");
        }
    }

    @FXML
    private void openLoan() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Loan.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Loan window");
        }
    }
}
