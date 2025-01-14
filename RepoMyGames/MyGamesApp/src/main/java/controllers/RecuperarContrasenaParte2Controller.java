package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdbc.Conector;

public class RecuperarContrasenaParte2Controller {

  @FXML
  private TextField txtCodigo;

  @FXML
  private Button btnTogglePassword;

  @FXML
  private ImageView imgClose;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private ImageView imgTogglePassword;

  @FXML
  private Pane paginaFondo;

  @FXML
  private TextField txtConfirmarPassword;

  @FXML
  private PasswordField txtConfirmarPasswordOculto;

  @FXML
  private TextField txtPassword;

  @FXML
  private PasswordField txtPasswordOculto;

  @FXML
  void imgFlechaAtrasPressed(MouseEvent event) {
    try {
      // Obtener el Stage de la ventana actual (Recuperar Contraseña)
      Stage ventanaRecuperarContrasena = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Cargar el archivo FXML de la ventana principal
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VentanaPrincipal.fxml"));
      BorderPane root = loader.load();

      // Crear una nueva escena con el root cargado
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      // Crear un nuevo Stage (ventana) para la "Ventana Principal"
      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle("Ventana Principal");
      nuevaVentana.setScene(scene);

      // Maximizar la ventana
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      // Mostrar la nueva ventana
      nuevaVentana.show();

      // Cerrar la ventana actual (Recuperar Contraseña)
      ventanaRecuperarContrasena.close();

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

  // Variable para controlar la visibilidad de las contraseñas
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
  void btnEnviarPressed(MouseEvent event) {
    // Obtener valores de los campos de texto
    String codigo = txtCodigo.getText().trim();
    String nuevaPassword = txtPassword.isVisible() ? txtPassword.getText() : txtPasswordOculto.getText();
    String confirmarPassword = txtConfirmarPassword.isVisible() ? txtConfirmarPassword.getText()
        : txtConfirmarPasswordOculto.getText();

    // Validar campos vacíos
    if (codigo.isEmpty() || nuevaPassword.isEmpty() || confirmarPassword.isEmpty()) {
      mostrarAlerta("Error", "Todos los campos son obligatorios.");
      return;
    }

    // Validar que las contraseñas coincidan
    if (!nuevaPassword.equals(confirmarPassword)) {
      mostrarAlerta("Error", "Las contraseñas no coinciden.");
      return;
    }

    // Validar formato de la contraseña
    if (!nuevaPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Creación de usuario");
      alert.setHeaderText("Error de formato de contraseña");
      alert.setContentText(
          "La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula, un número y un carácter especial.");
      alert.showAndWait();
      return; // Detener la ejecución si la contraseña no es válida
    }

    // Usar la clase Conector para conectarse a la base de datos
    try (Connection conexion = Conector.conectar()) {
      if (conexion == null) {
        mostrarAlerta("Error", "No se pudo conectar a la base de datos.");
        return;
      }

      // Verificar si el código existe
      String queryVerificarCodigo = "SELECT COUNT(*) FROM usuarios WHERE codigo = ?";
      try (PreparedStatement stmtVerificar = conexion.prepareStatement(queryVerificarCodigo)) {
        stmtVerificar.setString(1, codigo);

        try (ResultSet rs = stmtVerificar.executeQuery()) {
          if (rs.next() && rs.getInt(1) == 0) {
            mostrarAlerta("Error", "El código ingresado no es válido.");
            return;
          }
        }
      }

      // Actualizar la contraseña
      String queryActualizarPassword = "UPDATE usuarios SET password = ? WHERE codigo = ?";
      try (PreparedStatement stmtActualizar = conexion.prepareStatement(queryActualizarPassword)) {
        stmtActualizar.setString(1, nuevaPassword); // Asegúrate de cifrar la contraseña aquí
        stmtActualizar.setString(2, codigo);

        int filasActualizadas = stmtActualizar.executeUpdate();
        if (filasActualizadas > 0) {
          mostrarAlerta("Éxito", "Contraseña actualizada correctamente.");
          redirigirAVentanaPrincipal(event);
        } else {
          mostrarAlerta("Error", "No se pudo actualizar la contraseña. Inténtelo de nuevo.");
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
      mostrarAlerta("Error", "Hubo un error al conectarse a la base de datos.");
    }
  }

  // Método para mostrar alertas
  private void mostrarAlerta(String titulo, String mensaje) {
    javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.INFORMATION);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  // Método para redirigir al usuario a la ventana principal
  private void redirigirAVentanaPrincipal(MouseEvent event) {
    try {
      Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
      BorderPane root = loader.load();

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle("Ventana Principal");
      nuevaVentana.setScene(scene);
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      nuevaVentana.show();
      ventanaActual.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      // Mostrar la nueva ventana
      nuevaVentana.show();

      // Transición de desvanecimiento para la primera ventana
      FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), nuevaVentana.getScene().getRoot());
      fadeOut.setFromValue(1.0);
      fadeOut.setToValue(0.0);

      ventanaPrincipal.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
