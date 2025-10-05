package com.gudang.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private Label lblStatus;

    @FXML
    private void loginAction() {
        String user = txtUser.getText();
        String pass = txtPass.getText();
        if (user == null || user.isEmpty()) {
            lblStatus.setText("Username is empty!");
            return;
        }
        if (pass == null || pass.isEmpty()) {
            lblStatus.setText("Password is empty!");
            return;
        }
        if (user.equals("admin") && pass.equals("admin")) {
            lblStatus.setText("Login successful");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
                Stage stage = (Stage) txtUser.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Main Menu");

            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblStatus.setText("Failed to Login");
        }
    }
}
