package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaPrincipalController {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnLogin;

  @FXML
  private Button btnTogglePassword;

  @FXML
  private ImageView imgClose;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgMinimizar;

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
      // Obtener el Stage de la ventana principal y cerrarla
      Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Cargar el nuevo archivo FXML (el que contiene la vista "Crear Cuenta")
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Registro.fxml"));
      BorderPane root = loader.load();

      // Crear una nueva escena con el root cargado
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      // Crear un nuevo Stage (ventana) para la "Crear Cuenta"
      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle("Crear Cuenta");
      nuevaVentana.setScene(scene);

      // Maximizar la ventana
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      // Mostrar la nueva ventana
      nuevaVentana.show();

      ventanaPrincipal.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  void cerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }
  @FXML
  void lblRecuperarContraseñaPressed(MouseEvent event) {
      try {
          // Obtener el Stage de la ventana principal
          Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();

          // Cargar el archivo FXML de la ventana de Recuperar Contraseña
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecuperarContraseña.fxml"));
          Pane root = loader.load();

          // Crear una nueva escena con el root cargado
          Scene scene = new Scene(root);
          scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

          // Crear un nuevo Stage (ventana) para "Recuperar Contraseña"
          Stage nuevaVentana = new Stage();
          nuevaVentana.setTitle("Recuperar Contraseña");
          nuevaVentana.setScene(scene);

          // Maximizar la ventana
          nuevaVentana.setMaximized(true);
          nuevaVentana.setResizable(false);
          nuevaVentana.initStyle(StageStyle.UNDECORATED);

          // Mostrar la nueva ventana
          nuevaVentana.show();

          // Cerrar la ventana principal
          ventanaPrincipal.close();

      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}
