package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;
import models.EnviarCorreo;

/**
 * Controlador de RecuperarContrasena
 */
public class RecuperarContrasenaController {

  // Constantes
  // SQL
  private static final String SQL_EMAIL = "SELECT * FROM usuarios WHERE email = ?";
  private static final String SQL_CODIGO = "UPDATE usuarios SET codigo_seguridad = ? WHERE email = ?";
  // Correo
  private static final String EMAIL_FROM = "soportemygames@gmail.com";
  private static final String PASSWORD_FROM = "cmol lytj vnub uanm";
  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String LOGIN = "/views/Login.fxml";
  private static final String RECUPERAR_CONTRASENA_PARTE_2 = "/views/RecuperarContrasenaParte2.fxml";

  @FXML
  private Button btnEnviar;
  @FXML
  private ImageView imgClose, imgFlechaAtras, imgMinimizar;
  @FXML
  private Pane paginaFondo;
  @FXML
  private TextField txtCorreo;

  @FXML
  /**
   * Método para cuando se pulse el botón minimizar
   * 
   * @param event
   */
  private void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón cerrar
   * 
   * @param event
   */
  private void cerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }

  @FXML
  /**
   * Método para cuando se pulse la flecha atrás
   * 
   * @param event
   */
  private void imgFlechaAtrasPressed(MouseEvent event) {
    abrirNuevaVentana(LOGIN, "Ventana Principal", event);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón enviar
   * @param event
   */
  private void btnEnviarPressed(MouseEvent event) {
    String email = txtCorreo.getText();

    try (Connection cone = Conector.conectar();
        PreparedStatement st = cone.prepareStatement(SQL_EMAIL);
        PreparedStatement st2 = cone.prepareStatement(SQL_CODIGO)) {

      st.setString(1, email);
      ResultSet rs = st.executeQuery();

      if (rs.next()) {
        String codigo = generarCodigoSeguridad();
        EnviarCorreo enviarCorreo = new EnviarCorreo();
        enviarCorreo.enviarCorreo(EMAIL_FROM, PASSWORD_FROM, email, "Código de Seguridad",
            generarContenidoCorreo(codigo));
        actualizarCodigoSeguridad(st2, email, codigo);
        abrirVentanaRecuperacion(event, email);
      } else {
        mostrarAlerta(AlertType.ERROR, "Error de Email", "El correo ingresado no existe.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error", "Ocurrió un error en la base de datos.");
    } catch (Exception e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error", "Ocurrió un error inesperado.");
    }
  }

  // Métodos auxiliares
  private Stage obtenerVentana(MouseEvent event) {
    return (Stage) ((Node) event.getSource()).getScene().getWindow();
  }

  /**
   * Metodo para abrir una nueva ventana y cerrar la actual
   * 
   * @param fxml Ventana fxml
   */
  private void abrirNuevaVentana(String fxml, String titulo, MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      BorderPane root = loader.load();

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle(titulo);
      nuevaVentana.setScene(scene);
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      nuevaVentana.show();
      obtenerVentana(event).close();
    } catch (IOException e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error", "No se pudo cargar la ventana.");
    }
  }

  private void abrirVentanaRecuperacion(MouseEvent event, String email) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(RECUPERAR_CONTRASENA_PARTE_2));
      Pane root = loader.load();

      RecuperarContrasenaParte2Controller controller = loader.getController();
      controller.setEmail(email);

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle("Recuperación de Contraseña");
      nuevaVentana.setScene(scene);
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      nuevaVentana.show();
      obtenerVentana(event).close();
    } catch (IOException e) {
      e.printStackTrace();
      mostrarAlerta(AlertType.ERROR, "Error", "No se pudo cargar la ventana de recuperación.");
    }
  }

  private void actualizarCodigoSeguridad(PreparedStatement st, String email, String codigo) throws SQLException {
    st.setString(1, codigo);
    st.setString(2, email);
    st.executeUpdate();
  }

  /**
   * Método para mostrar alerta
   * 
   * @param tipo    Tipo de alerta
   * @param titulo  Título de la alerta
   * @param mensaje Mensaje de la alerta
   */
  private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
    Alert alert = new Alert(tipo);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  /**
   * Método para generar un código de seguridad random
   * 
   * @return Código de seguridad random
   */
  private String generarCodigoSeguridad() {
    return String.valueOf(100000 + new Random().nextInt(900000));
  }

  /**
   * Metodo para generar el contenido del correo, cogiendo el código de seguridad
   * generado para mostrarlo en el correo
   * 
   * @param codigo Código de seguridad generados
   * @return Contenido del correo
   */
  private String generarContenidoCorreo(String codigo) {
    return "<html>"
        + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; text-align: center; padding: 30px;'>"
        + "<div style='background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;'>"
        + "<h1 style='color: #333; font-size: 24px;'>¡Recupera tu cuenta en MyGames!</h1>"
        + "<img src='https://raw.githubusercontent.com/Samus51/MyGames/main/LogoMyGames.png' "
        + "alt='Logo MyGames' style='width: 200px; margin: 20px auto;'>"
        + "<p style='font-size: 18px; color: #555;'>Tu código de seguridad es:</p>"
        + "<div style='background-color: blue; color: white; font-size: 24px; font-weight: bold; padding: 15px 25px; border-radius: 5px; display: inline-block;'>"
        + codigo + "</div>"
        + "<p style='font-size: 16px; color: #555; margin-top: 20px;'>Por favor, no compartas este código con nadie.</p>"
        + "<p style='font-size: 14px; color: #888;'>Si no solicitaste este código, por favor ignora este mensaje.</p>"
        + "<p style='font-size: 16px; color: #333; margin-top: 30px;'>¡Gracias por ser parte de MyGames!</p>"
        + "<hr style='border: 0; border-top: 1px solid #eee; margin: 30px 0;'>"
        + "<p style='font-size: 12px; color: #aaa;'>Equipo MyGames</p>" + "</div>" + "</body>" + "</html>";
  }
}
