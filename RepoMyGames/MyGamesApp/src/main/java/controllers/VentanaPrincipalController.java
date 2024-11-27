package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
  private Label lblCrearCuenta;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser1;

  @FXML
  private Circle logoClip;

  @FXML
  private BorderPane panelLogo;

  @FXML
  private TextField txtPassword;

  @FXML
  private TextField txtPasswordClear;

  @FXML
  private PasswordField txtPasswordOculto;

  @FXML
  private TextField txtUsuario;


  private boolean isPasswordVisible = false;

  @FXML
  private void togglePasswordVisibility() {
    // Cambiar la visibilidad de los campos
    isPasswordVisible = !isPasswordVisible;

    if (isPasswordVisible) {
      txtPasswordOculto.setVisible(false);
      txtPassword.setVisible(true);
      txtPassword.setText(txtPasswordOculto.getText());
      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
    } else {
      txtPasswordOculto.setVisible(true);
      txtPassword.setVisible(false);
      txtPasswordOculto.setText(txtPassword.getText());
      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
    }
  }
  

  @FXML
  void crearCuentaPressed(MouseEvent event) {
      try {
          // Cargar el nuevo archivo FXML (el que contiene la vista "Crear Cuenta")
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Registro.fxml"));
          
          // Cargar la nueva interfaz
          BorderPane newLayout = loader.load();
          
          // Obtener la etapa (ventana) actual y configurar la nueva escena
          Stage stage = (Stage) VentanaPrincipal.getScene().getWindow();
          Scene newScene = new Scene(newLayout);
          
          // Configurar la nueva escena
          stage.setScene(newScene);
          
          // Hacer que la ventana esté en pantalla completa
          stage.setFullScreen(true);
          
          // Mostrar la nueva escena
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }


}
