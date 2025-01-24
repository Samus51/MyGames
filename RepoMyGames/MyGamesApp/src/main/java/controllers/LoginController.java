package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.util.Duration;
import jdbc.Conector;

/**
 * Controlador de Login
 */
public class LoginController {

  // Constantes
  // SQL
  private static final String SQL_USUARIO = "Select * from usuarios where nombre = ? and contrasena = ?";
  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String REGISTRO = "/views/Registro.fxml";
  private static final String RECUPERAR_CONTRASENA = "/views/RecuperarContrasena.fxml";
  private static final String HOME = "/views/Home.fxml";
  // Fotos
  private static final String OJO_NEGRO = "/ojoNegro.png";
  private static final String OJO_NEGRO_TACHADO = "/ojoNegroTachado.png";

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnLogin;

  @FXML
  private Button btnOjoPassword;

  @FXML
  private ImageView imgClose;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private ImageView imgOjoPassword;

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
  private ImageView cargando;

  @FXML
  private TextField txtPassword;

  @FXML
  private TextField txtPasswordLimpio;

  @FXML
  private PasswordField txtPasswordOculto;

  @FXML
  private TextField txtUsuario;

  private boolean esPasswordVisible = false;

  @FXML
  void initialize() {
    addZoomEffect(imgLogo);
    cargando.setVisible(false);
  }

  @FXML
  void btnLoginPressed(MouseEvent event) {
    String user = txtUsuario.getText();
    String password = txtPassword.getText().isEmpty() ? txtPasswordOculto.getText() : txtPassword.getText();

    Connection cone = Conector.conectar();
    try (PreparedStatement st = cone.prepareStatement(SQL_USUARIO)) {
      st.setString(1, user);
      st.setString(2, password);

      ResultSet rs = st.executeQuery();

      if (rs.next()) {
        // Ocultar el texto del botón y mostrar el GIF de carga
        btnLogin.setText("");
        cargando.setVisible(true);
        btnLogin.setDisable(true);

        // Usamos un Task para hacer el proceso en un hilo secundario y evitar congelar
        // la UI
        Task<Void> task = new Task<Void>() {
          protected Void call() throws Exception {
            Thread.sleep(5000);
            return null;
          }

          protected void succeeded() {
            // Llamamos a este método cuando el Task se complete con éxito
            abrirNuevaVentana(HOME);
          }

          protected void failed() {
            // Si ocurre algún error durante el Task, restablecer la UI
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Login");
            alerta.setContentText("Hubo un error al procesar la solicitud.");
            alerta.setHeaderText("Error de Login");
            alerta.showAndWait();

            // Restaurar la UI
            btnLogin.setText("Iniciar sesión");
            cargando.setVisible(false);
            btnLogin.setDisable(false);
          }
        };
        // Ejecutar el Task en un hilo secundario
        new Thread(task).start();
      } else {
        // Si el login es incorrecto, mostrar un mensaje de error
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Login");
        alerta.setContentText("Usuario o contraseña incorrectos");
        alerta.setHeaderText("Error de Login");
        alerta.showAndWait();

        // Restaurar la UI en caso de error
        btnLogin.setText("Iniciar sesión");
        cargando.setVisible(false);
        btnLogin.setDisable(false);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void mostrarPassword() {
    // Cambiar la visibilidad de los campos
    esPasswordVisible = !esPasswordVisible;

    if (esPasswordVisible) {
      txtPasswordOculto.setVisible(false);
      txtPassword.setVisible(true);
      txtPassword.setText(txtPasswordOculto.getText());
      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO_TACHADO)));
    } else {
      txtPasswordOculto.setVisible(true);
      txtPassword.setVisible(false);
      txtPasswordOculto.setText(txtPassword.getText());
      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO)));
    }
  }

  @FXML
  void crearCuentaPressed(MouseEvent event) {
    abrirNuevaVentana(REGISTRO);
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
  void lblRecuperarContrasenaPressed(MouseEvent event) {
    abrirNuevaVentana(RECUPERAR_CONTRASENA);
  }

  // Método para agregar efecto de zoom a un ImageView
  private void addZoomEffect(ImageView imageView) {
    imageView.setOnMouseEntered(event -> {
      ScaleTransition zoomIn = new ScaleTransition(Duration.millis(200), imageView);
      zoomIn.setToX(1.2);
      zoomIn.setToY(1.2);
      zoomIn.play();
    });

    imageView.setOnMouseExited(event -> {
      ScaleTransition zoomOut = new ScaleTransition(Duration.millis(200), imageView);
      zoomOut.setToX(1.0);
      zoomOut.setToY(1.0);
      zoomOut.play();
    });
  }

  /**
   * Metodo para abrir una nueva ventana y cerrar la actual
   * 
   * @param fxml Ventana fxml
   */
  private void abrirNuevaVentana(String fxml) {
    try {
      // Obtener el Stage de la ventana principal
      Stage ventanaPrincipal = (Stage) VentanaPrincipal.getScene().getWindow();

      // Cargar el archivo FXML de la nueva ventana
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      Pane root = loader.load();

      // Crear una nueva escena con el root cargado
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

      // Crear un nuevo Stage
      Stage nuevaVentana = new Stage();
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