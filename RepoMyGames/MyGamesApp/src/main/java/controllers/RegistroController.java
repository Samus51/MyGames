package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

public class RegistroController {

    @FXML
    private BorderPane VentanaPrincipal;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnTogglePassword;

    @FXML
    private ComboBox<?> cbGenero;

    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgTogglePassword;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblUser1;

    @FXML
    private Label lblUser11;

    @FXML
    private Label lblUser2;

    @FXML
    private Circle logoClip;

    @FXML
    private BorderPane panelLogo;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPasswordField;

    @FXML
    private TextField txtPasswordField1;

    @FXML
    private TextField txtUser;

    @FXML
    void togglePasswordVisibility(ActionEvent event) {

    }

}
