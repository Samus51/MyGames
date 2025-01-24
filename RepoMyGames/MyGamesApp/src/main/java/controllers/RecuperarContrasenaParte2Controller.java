package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;

/**
 * Controlador de RecuperarContrasenaParte2
 */
public class RecuperarContrasenaParte2Controller {

  // Constantes
  // SQL
  private static final String SQL_CODIGO = "SELECT codigo_seguridad FROM usuarios WHERE email = ?";
  private static final String SQL_PASSWORDS = "UPDATE usuarios SET contrasena = ? WHERE email = ?";
  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String LOGIN = "/views/Login.fxml";
  private static final String RECUPERAR_CONTRASENA = "/views/RecuperarContrasena.fxml";
  // Fotos
  private static final String OJO_NEGRO = "/ojoNegro.png";
  private static final String OJO_NEGRO_TACHADO = "/ojoNegroTachado.png";

  // Atributos
  private String email;
  // Variable para controlar la visibilidad de las contraseñas
  private boolean isPasswordVisible = false;

  public void setEmail(String email) {
    this.email = email;
  }

  @FXML
  private TextField txtCodigo;

  @FXML
  private Button btnTogglePassword;

  @FXML
  private ImageView imgClose, imgFlechaAtras, imgMinimizar, imgTogglePassword;

  @FXML
  private Pane paginaFondo;

  @FXML
  private TextField txtConfirmarPassword, txtPassword;

  @FXML
  private PasswordField txtConfirmarPasswordOculto, txtPasswordOculto;

  @FXML
  /**
   * Método para cuando se pulse la flecha atrás
   * 
   * @param event
   */
  private void imgFlechaAtrasPressed(MouseEvent event) {
    abrirNuevaVentana(RECUPERAR_CONTRASENA);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón de minimizar
   * 
   * @param event
   */
  private void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón de cerrar
   * 
   * @param event
   */
  private void cerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }

  @FXML
  private void togglePasswordVisibility() {
    isPasswordVisible = !isPasswordVisible;

    if (isPasswordVisible) {
      txtPassword.setText(txtPasswordOculto.getText());
      txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());

      txtPasswordOculto.setVisible(false);
      txtConfirmarPasswordOculto.setVisible(false);

      txtPassword.setVisible(true);
      txtConfirmarPassword.setVisible(true);

      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO)));
    } else {
      txtPasswordOculto.setText(txtPassword.getText());
      txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

      txtPassword.setVisible(false);
      txtConfirmarPassword.setVisible(false);

      txtPasswordOculto.setVisible(true);
      txtConfirmarPasswordOculto.setVisible(true);

      imgTogglePassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO_TACHADO)));
    }
  }

  @FXML
  /**
   * Método para cuando se pulse el botón de enviar
   * 
   * @param event
   */
  private void btnEnviarPressed(MouseEvent event) {
    // Cogemos los textos de los txt
    String codigo = txtCodigo.getText();
    String emailUser = email;
    String password = txtPassword.getText().isEmpty() ? txtPasswordOculto.getText() : txtPassword.getText();
    String passwordConfirmar = txtConfirmarPassword.getText().isEmpty() ? txtConfirmarPasswordOculto.getText()
        : txtConfirmarPassword.getText();

    // Comprobamos que coinciden las contraseñas
    if (!password.equals(passwordConfirmar)) {
      mostrarAlerta(AlertType.WARNING, "Error de Contraseña", "La contraseña y su confirmación no coinciden.");
      return;
    }

    // Validamos el formato de la contraseña
    if (!validarFormatoPassword(password)) {
      mostrarAlerta(AlertType.WARNING, "Formato Incorrecto",
          "La contraseña debe tener al menos 8 caracteres, incluir una mayúscula, una minúscula, un número y un carácter especial.");
      return;
    }

    try (Connection cone = Conector.conectar();
        PreparedStatement st = cone.prepareStatement(SQL_CODIGO);
        PreparedStatement st2 = cone.prepareStatement(SQL_PASSWORDS)) {

      st.setString(1, emailUser);

      ResultSet rs = st.executeQuery();
      if (rs.next() && codigo.equals(rs.getString("codigo_seguridad"))) {
        st2.setString(1, password);
        st2.setString(2, email);
        st2.executeUpdate();

        mostrarAlerta(AlertType.INFORMATION, "Recuperación Exitosa", "Su contraseña ha sido actualizada.");
        abrirNuevaVentana(LOGIN);
      } else {
        mostrarAlerta(AlertType.ERROR, "Código Incorrecto", "El código de seguridad no es correcto.");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error en la Base de Datos", "Ocurrió un error al actualizar la contraseña.");
    }
  }

  /**
   * Método para abrir una nueva ventana y cerrar la actual.
   *
   * @param fxml Ruta del archivo FXML de la nueva ventana.
   */
  private void abrirNuevaVentana(String fxml) {
    try {
      Stage ventanaPrincipal = (Stage) paginaFondo.getScene().getWindow();

      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      Pane root = loader.load();

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

      Stage nuevaVentana = new Stage();
      nuevaVentana.setScene(scene);
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      nuevaVentana.show();
      ventanaPrincipal.close();
    } catch (IOException e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error al Abrir Ventana", "No se pudo abrir la ventana solicitada.");
    }
  }

  /**
   * Método para mostrar una alerta.
   */
  private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  /**
   * Método para validar el formato de una contraseña.
   */
  private boolean validarFormatoPassword(String password) {
    return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$");
  }
}
