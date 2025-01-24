package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import jdbc.Conector;
import models.EnviarCorreo;

import org.controlsfx.control.CheckComboBox;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

/**
 * Controlador de Registro
 */
public class RegistroController {

  // Constantes
  // SQL
  private static final String SQL_USUARIO_COMPROBACION = "Select * from usuarios where email = ?";
  private static final String SQL_USUARIO_INSERT = "INSERT INTO usuarios (nombre,email,fecha_registro,contrasena) values (?,?,?,?)";
  // Correo
  private static final String EMAIL_FROM = "soportemygames@gmail.com";
  private static String PASSWORD_FROM = "cmol lytj vnub uanm";
  // Pantallas
  private static final String STYLES = "/styles.css";
  private static final String LOGIN = "/views/Login.fxml";
  // Fotos
  private static final String OJO_NEGRO = "/ojoNegro.png";
  private static final String OJO_NEGRO_TACHADO = "/ojoNegroTachado.png";

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnCrearCuenta;

  @FXML
  private Button btnOjoPassword;

  @FXML
  private ComboBox<?> cbxGenero;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgOjoPassword;

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

  @FXML
  private ImageView imgMinimizar;

  @FXML
  CheckComboBox<String> lstGeneros;

  @FXML
  private ImageView imgCerrar;

  private boolean esPasswordVisible = false;

  @FXML
  /**
   * Método para cuando se pulse el ojo en la contraseña
   */
  private void mostrarPassword() {
    // Cambiar la visibilidad de los campos
    esPasswordVisible = !esPasswordVisible;

    if (esPasswordVisible) {
      txtPasswordOculto.setVisible(false);
      txtPassword.setVisible(true);
      txtPassword.setText(txtPasswordOculto.getText());

      txtConfirmarPasswordOculto.setVisible(false);
      txtConfirmarPassword.setVisible(true);
      txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());

      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO_TACHADO)));
    } else {
      txtPasswordOculto.setVisible(true);
      txtPassword.setVisible(false);
      txtPasswordOculto.setText(txtPassword.getText());

      txtConfirmarPasswordOculto.setVisible(true);
      txtConfirmarPassword.setVisible(false);
      txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO)));
    }
  }

  @FXML
  public void initialize() {
    // Crear la lista de géneros
    ObservableList<String> generos = FXCollections.observableArrayList("Acción", "Aventura", "RPG (Juegos de rol)",
        "Casual", "Arcade", "Multijugador masivo", "Familiar", "Educativo", "Indie", "Estrategia", "Simulación",
        "Plataforma", "Deportes", "Juegos de mesa", "Disparos", "Rompecabezas", "Carreras", "Lucha", "Cartas");

    // Configurar el CheckComboBox con la lista de géneros
    lstGeneros.getItems().addAll(generos);
    // Zoom del logo al pasar por encima
    efectoZoom(imgLogo);

  }

  @FXML
  /**
   * Método para cuando se pulse crear cuenta
   * 
   * @param event
   */
  void btnCrearCuentaPressed(MouseEvent event) {
    String userName = txtUser.getText();
    String email = txtEmail.getText();
    String contrasena;
    String contrasenaConfirmacion;

    if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Creacion de usuario");
      alert.setHeaderText("Error de formato de email");
      alert.setContentText("El formato de email no es válido. Email válido (email@example.com)");
      alert.showAndWait();

    }

    if (txtPassword.getText().isEmpty()) {
      contrasena = txtPasswordOculto.getText();
    } else {
      contrasena = txtPassword.getText();
    }

    if (txtConfirmarPassword.getText().isEmpty()) {
      contrasenaConfirmacion = txtConfirmarPasswordOculto.getText();
    } else {
      contrasenaConfirmacion = txtConfirmarPassword.getText();
    }

    if (!contrasena.equals(contrasenaConfirmacion)) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Creación de usuario");
      alert.setHeaderText("Error de contraseña");
      alert.setContentText("La contraseña no coincide con la de confirmacion.");
      alert.showAndWait();
      return;
    }

    if (!contrasena.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$")) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Creación de usuario");
      alert.setHeaderText("Error de formato de contraseña");
      alert.setContentText(
          "La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula, un número y un carácter especial.");
      alert.showAndWait();
      return;
    }

    List<String> generosPreferidos = getListGeneros();

    if (generosPreferidos == null || generosPreferidos.size() == 0) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Selección de géneros favoritos");
      alert.setHeaderText("Ningun genero fue seleccionado");
      alert.setContentText("Debe seleccionar al menos 1 genero favorito.");
      alert.showAndWait();
      return;
    }
    List<Integer> generosIds = getGenerosIdsByNames(generosPreferidos);

    Connection cone = Conector.conectar();
    try (PreparedStatement st = cone.prepareStatement(SQL_USUARIO_COMPROBACION);
        PreparedStatement st2 = cone.prepareStatement(SQL_USUARIO_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
      st.setString(1, email);
      ResultSet rs = st.executeQuery();

      if (rs.next()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error al crear usuario");
        alert.setHeaderText("Creación de Usuario errónea");
        alert.setContentText("El usuario que quiere crear ya existe.");
        alert.showAndWait();
      }

      st2.setString(1, userName);
      st2.setString(2, email);
      st2.setDate(3, new Date(System.currentTimeMillis()));
      st2.setString(4, contrasena);

      int rs2 = st2.executeUpdate();
      if (rs2 == 1) {
        ResultSet rsId = st2.getGeneratedKeys();
        int idUsuario = -1;
        if (rsId.next()) {
          idUsuario = rsId.getInt(1);
        }

        String sqlInsertGeneroFavorito = "INSERT INTO generos_favoritos (id_usuario, id_genero) VALUES (?, ?)";
        try (PreparedStatement stGenero = cone.prepareStatement(sqlInsertGeneroFavorito)) {
          for (Integer generoId : generosIds) {
            stGenero.setInt(1, idUsuario);
            stGenero.setInt(2, generoId);
            stGenero.executeUpdate();
          }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Usuario Creado");
        alert.setHeaderText("Creación de Usuario con éxito");
        alert.setContentText("El usuario ha sido creado correctamente.");
        alert.showAndWait();
        flechaAtrasPressed(event);
        String subject = "Creación de Cuenta";
        // Construir el mensaje HTML
        String content = "<html>"
            + "<body style='font-family: Arial, sans-serif; background-color: #f4f7fc; text-align: center; padding: 30px;'>"
            + "<div style='background-color: #ffffff; padding: 40px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;'>"
            + "<h1 style='color: #333; font-size: 26px; margin-bottom: 20px;'>¡Bienvenido a MyGames!</h1>"
            + "<img src='https://raw.githubusercontent.com/Samus51/MyGames/main/LogoMyGames.png' "
            + "alt='Logo MyGames' style='width: 250px; height: auto; margin: 20px auto; display: block;'>"
            + "<p style='font-size: 18px; color: #555; margin-top: 20px;'>Hola <strong style='color: #4CAF50;'>"
            + userName + "</strong>,</p>"
            + "<p style='font-size: 16px; color: #555;'>Tu cuenta ha sido creada exitosamente. Ahora puedes acceder y disfrutar de todo lo que MyGames tiene para ofrecerte.</p>"
            + "<p style='font-size: 16px; color: #555; margin-top: 20px;'>¡Estamos emocionados de tenerte en nuestra comunidad de jugadores!</p>"
            + "<p style='font-size: 14px; color: #888; margin-top: 20px;'>Si tienes alguna pregunta, no dudes en contactarnos. ¡Gracias por unirte a nuestra comunidad!</p>"
            + "<hr style='border: 0; border-top: 1px solid #eee; margin: 30px 0;'>"
            + "<p style='font-size: 14px; color: #888;'>Equipo MyGames</p>" + "</div>" + "</body>" + "</html>";

        EnviarCorreo enviarCorreo = new EnviarCorreo();
        enviarCorreo.enviarCorreo(EMAIL_FROM, PASSWORD_FROM, email, subject, content);
      }
    } catch (SQLException | MessagingException e) {
      e.printStackTrace();
    }
    System.out.println("Cuenta creada con éxito");
  }

  @FXML
  /**
   * Método para cuando se pulse la flecha atrás
   * 
   * @param event
   */
  void flechaAtrasPressed(MouseEvent event) {
    abrirNuevaVentana(LOGIN);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón minimizar
   * 
   * @param event
   */
  void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón de cerrar
   * 
   * @param event
   */
  void cerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }

  public List<String> getListGeneros() {
    return new ArrayList<>(lstGeneros.getCheckModel().getCheckedItems());
  }

  public List<Integer> getGenerosIdsByNames(List<String> nombresGeneros) {
    if (nombresGeneros == null || nombresGeneros.isEmpty()) {
      System.out.println("No hay géneros proporcionados.");
      return new ArrayList<>();
    }

    Map<String, Integer> generosMap = cargarGenerosDeBaseDeDatos();
    List<Integer> generosIds = new ArrayList<>();

    for (String genero : nombresGeneros) {
      Integer id = generosMap.get(genero);
      if (id != null) {
        generosIds.add(id);
      } else {
        System.out.println("Género no encontrado en la base de datos: " + genero);
      }
    }

    return generosIds;
  }

  private Map<String, Integer> cargarGenerosDeBaseDeDatos() {
    Map<String, Integer> generosMap = new HashMap<>();
    String sql = "SELECT nombre, id_genero FROM generos";

    try (Connection cone = Conector.conectar();
        PreparedStatement st = cone.prepareStatement(sql);
        ResultSet rs = st.executeQuery()) {

      while (rs.next()) {
        generosMap.put(rs.getString("nombre"), rs.getInt("id_genero"));
      }

    } catch (SQLException e) {
      System.err.println("Error al cargar los géneros: " + e.getMessage());
    }

    return generosMap;
  }

  /**
   * Método para agregar efecto de zoom a un ImageView
   * 
   * @param imageView Imagen
   */
  private void efectoZoom(ImageView imageView) {
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
