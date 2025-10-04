package com.gudang.controller;

import javafx.fxml.FXML;
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
            lblStatus.setText("Username kosong");
            return;
        }
        if (pass == null || pass.isEmpty()) {
            lblStatus.setText("Password kosong");
            return;
        }
        // simple stub for demo
        if (user.equals("admin") && pass.equals("admin")) {
            lblStatus.setText("Login berhasil");
        } else {
            lblStatus.setText("Login gagal");
        }
    }
}
