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

import jdbc.Conector;
import org.controlsfx.control.CheckComboBox;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.InvalidationListener;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RegistroController {

  private static final String SQL_USUARIO_COMPROBACION = "Select * from usuarios where email = ?";
  private static final String SQL_USUARIO_INSERT = "INSERT INTO usuarios (nombre,email,fecha_registro,contraseña) values (?,?,?,?)";

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

  @FXML
  private ImageView imgMinimizar;

  @FXML
  CheckComboBox<String> lstGeneros;

  @FXML
  private ImageView imgClose;

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
  public void initialize() {
    // Crear la lista de géneros
    ObservableList<String> generos = FXCollections.observableArrayList("Acción", "Aventura", "RPG (Juegos de rol)",
        "Casual", "Arcade", "Multijugador masivo", "Familiar", "Educativo", "Indie", "Estrategia", "Simulación",
        "Plataforma", "Deportes", "Juegos de mesa", "Disparos", "Rompecabezas", "Carreras", "Lucha", "Cartas");

    // Configurar el CheckComboBox con la lista de géneros
    lstGeneros.getItems().addAll(generos);

    addZoomEffect(imgLogo);

  }

  @FXML
  void btnCrearCuentaPressed(MouseEvent event) {
    String userName = txtUser.getText();
    String email = txtEmail.getText();
    String contraseña;
    String contraseñaConfirmacion;

    if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Creacion de usuario");
      alert.setHeaderText("Error de formato de email");
      alert.setContentText("El formato de email no es válido. Email válido (email@example.com)");
      alert.showAndWait();

    }

    if (txtPassword.getText().isEmpty()) {
      contraseña = txtPasswordOculto.getText();
    } else {
      contraseña = txtPassword.getText();
    }

    if (txtConfirmarPassword.getText().isEmpty()) {
      contraseñaConfirmacion = txtConfirmarPasswordOculto.getText();
    } else {
      contraseñaConfirmacion = txtConfirmarPassword.getText();
    }

    if (!contraseña.equals(contraseñaConfirmacion)) {
      System.out.println("Las contraseñas no coinciden.");
    }

    if (!contraseña.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Creación de usuario");
      alert.setHeaderText("Error de formato de contraseña");
      alert.setContentText(
          "La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula, un número y un carácter especial.");
      alert.showAndWait();
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
      st2.setString(4, contraseña);

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
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    System.out.println("Cuenta creada con éxito");
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
  void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
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

}
