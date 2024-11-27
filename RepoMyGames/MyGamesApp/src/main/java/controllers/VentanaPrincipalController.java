package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

public class VentanaPrincipalController {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnLogin;

  @FXML
  private Button btnTogglePassword;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgTogglePassword;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser1;

  @FXML
  private Circle logoClip;

  @FXML
  private BorderPane panelLogo;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField txtPasswordClear;

  @FXML
  private TextField txtPasswordField;

  @FXML
  private TextField txtUsuario;

  private boolean isPasswordVisible = false;

  @FXML
  private void togglePasswordVisibility() {
    // Cambiar la visibilidad de los campos
    isPasswordVisible = !isPasswordVisible;

    if (isPasswordVisible) {
      passwordField.setVisible(false);
      txtPasswordField.setVisible(true);
      txtPasswordField.setText(passwordField.getText());
      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
    } else {
      passwordField.setVisible(true);
      txtPasswordField.setVisible(false);
      passwordField.setText(txtPasswordField.getText());
      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
    }
  }
}
