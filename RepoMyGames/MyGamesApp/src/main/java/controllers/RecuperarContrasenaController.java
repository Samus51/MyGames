package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdbc.Conector;

public class RecuperarContrasenaController {

  private static final String SQL_EMAIL = "Select * from usuarios where email = ?";
  private static final String SQL_CODIGO = "UPDATE usuarios set codigo_seguridad = ? where email = ?";

  private static String EMAIL_FROM = "soportemygames@gmail.com";
  private static String PASSWORD_FROM = "cmol lytj vnub uanm";

  @FXML
  private Button btnEnviar;

  @FXML
  private ImageView imgClose;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private Pane paginaFondo;

  @FXML
  private TextField txtCorreo;

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

  @FXML
  void btnEnviarPressed(MouseEvent event) {
    try (Connection cone = Conector.conectar();
        PreparedStatement st = cone.prepareStatement(SQL_EMAIL);
        PreparedStatement st2 = cone.prepareStatement(SQL_CODIGO)) {

      String email = txtCorreo.getText();
      st.setString(1, email);
      ResultSet rs = st.executeQuery();

      if (rs.next()) {
        // Generar el código de seguridad
        String codigo = generarCodigoSeguridad();

        // Configurar el correo
        String emailTo = email; // Obtener el correo del usuario desde el ResultSet
        String subject = "Código de seguridad";
        String content = "<p>Hola,</p>" + "<p>Tu código de seguridad es: <strong>" + codigo + "</strong></p>"
            + "<p>Por favor, no compartas este código con nadie.</p>";

        // Enviar el correo
        enviarCorreo(EMAIL_FROM, PASSWORD_FROM, emailTo, subject, content);
        System.out.println("Correo enviado con el código: " + codigo);

        st2.setString(1, codigo);
        st2.setString(2, email);
        st2.executeUpdate();

        // Después de enviar el correo, abrir la siguiente ventana de recuperación
        Stage ventanaRecuperarContrasena = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecuperarContrasenaParte2.fxml"));
        Pane root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle("Ventana Principal");
        nuevaVentana.setScene(scene);
        nuevaVentana.setMaximized(true);
        nuevaVentana.setResizable(false);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);

        nuevaVentana.show();
        ventanaRecuperarContrasena.close();

      } else {
        // Si no se encuentra el email en la base de datos, mostrar el error
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error al recuperar usuario");
        alert.setHeaderText("Error de email");
        alert.setContentText("El email no existe.");
        alert.showAndWait();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void enviarCorreo(String emailFrom, String passwordFrom, String emailTo, String subject, String content) {
    Properties mProperties = new Properties();

    // Configuración del servidor SMTP
    mProperties.put("mail.smtp.host", "smtp.gmail.com");
    mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    mProperties.setProperty("mail.smtp.starttls.enable", "true");
    mProperties.setProperty("mail.smtp.port", "587");
    mProperties.setProperty("mail.smtp.user", emailFrom);
    mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
    mProperties.setProperty("mail.smtp.auth", "true");

    // Crear la sesión de correo
    Session mSession = Session.getDefaultInstance(mProperties);

    try {
      // Crear el mensaje
      MimeMessage mCorreo = new MimeMessage(mSession);
      mCorreo.setFrom(new InternetAddress(emailFrom));
      mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
      mCorreo.setSubject(subject);
      mCorreo.setText(content, "ISO-8859-1", "html");

      // Enviar el correo
      Transport mTransport = mSession.getTransport("smtp");
      mTransport.connect(emailFrom, passwordFrom);
      mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
      mTransport.close();

      System.out.println("Correo enviado a: " + emailTo);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public String generarCodigoSeguridad() {
    Random random = new Random();
    int codigo = 100000 + random.nextInt(900000);
    return String.valueOf(codigo);
  }

}
