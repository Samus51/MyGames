package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RegistroController {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnCrearCuenta;

  @FXML
  private Button btnTogglePassword;

  @FXML
  private ComboBox<?> cbxGenero;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgTogglePassword;

  @FXML
  private Label lblConfirmarPassword;

  @FXML
  private Label lblPassword;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser2;

  @FXML
  private Circle logoClip;

  @FXML
  private BorderPane panelLogo;

  @FXML
  private TextField txtConfirmarPassword;

  @FXML
  private PasswordField txtConfirmarPasswordOculto;

  @FXML
  private TextField txtEmail;

  @FXML
  private TextField txtPassword;

  @FXML
  private PasswordField txtPasswordOculto;

  @FXML
  private TextField txtUser;


    private boolean isPasswordVisible = false;

    @FXML
    private void togglePasswordVisibility() {
      // Cambiar la visibilidad de los campos
      isPasswordVisible = !isPasswordVisible;

      if (isPasswordVisible) {
        txtPasswordOculto.setVisible(false);
        txtPassword.setVisible(true);
        txtPassword.setText(txtPasswordOculto.getText());
        
        txtConfirmarPasswordOculto.setVisible(false);
        txtConfirmarPassword.setVisible(true);
        txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());
        
        imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
      } else {
        txtPasswordOculto.setVisible(true);
        txtPassword.setVisible(false);
        txtPasswordOculto.setText(txtPassword.getText());
        
        txtConfirmarPasswordOculto.setVisible(true);
        txtConfirmarPassword.setVisible(false);
        txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());
        
        imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
      }
    }
    
    @FXML
    void btnCrearCuentaPressed(MouseEvent event) {

    }

    @FXML
    void flechaAtrasPressed(MouseEvent event) {
      try {
        // Obtener el Stage de la ventana principal y cerrarla
        Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();


        // Cargar el nuevo archivo FXML (el que contiene la vista "Crear Cuenta")
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VentanaPrincipal.fxml"));
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

        // Mostrar la nueva ventana
        nuevaVentana.show();
        
        ventanaPrincipal.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}
