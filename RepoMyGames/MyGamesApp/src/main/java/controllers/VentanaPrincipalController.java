package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class VentanaPrincipalController {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnLogin;

  @FXML
  private ImageView imgLogo;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser1;

  @FXML
  private BorderPane panelLogo;

  @FXML
  private TextField txtPasswordClear;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField txtPasswordField;

  // Método para alternar la visibilidad de la contraseña
  @FXML
  private void togglePasswordVisibility() {
    // Si el PasswordField está visible, lo ocultamos y mostramos el TextField
    if (passwordField.isVisible()) {
      passwordField.setVisible(false);
      txtPasswordField.setVisible(true);
      txtPasswordField.setText(passwordField.getText());
    } else {
      passwordField.setVisible(true);
      txtPasswordField.setVisible(false);
      passwordField.setText(txtPasswordField.getText());
    }
  }
}
