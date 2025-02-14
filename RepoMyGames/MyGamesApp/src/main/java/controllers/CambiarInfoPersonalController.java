package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;
import models.Usuario;
import utils.VentanaUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.controlsfx.control.CheckComboBox;

/**
 * Controlador de CambiarInfoPersonal
 */
public class CambiarInfoPersonalController {

  // Contantes
  // SQL
  private static final String SQL_ELIMINAR_CUENTA = "DELETE FROM usuarios WHERE id_usuario = ?";
  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String LOGIN = "/views/Login.fxml";
  private static final String HOME = "/views/Home.fxml";
  private static final String HISTORIAL_JUEGOS = "/views/HistorialJuegos.fxml";

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnAplicarCambios;

  @FXML
  private Button btnOjoPassword;

  @FXML
  private ImageView imgCerrar;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private Label lblHistorialJuegos;

  @FXML
  private ImageView imgOjoPassword;

  @FXML
  private Label lblConfirmarPassword;

  @FXML
  private Label lblPassword;

  @FXML
  private Label lblCerrarSesion;

  @FXML
  private Label lblElminarCuenta;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser2;

  @FXML
  CheckComboBox<String> lstGeneros;

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

  private Usuario usuarioActivo;

  private boolean esPasswordVisible = false;

  @FXML
  public void initialize() {
    // Crear la lista de géneros
    ObservableList<String> generos = FXCollections.observableArrayList("Action", "Adventure",
        "RPG (Role-Playing Games)", "Casual", "Arcade", "Massively Multiplayer", "Family", "Educational", "Indie",
        "Strategy", "Simulation", "Platform", "Sports", "Board Games", "Shooter", "Puzzle", "Racing", "Fighting",
        "Card Games");

    // Configurar el CheckComboBox con la lista de géneros
    lstGeneros.getItems().addAll(generos);

    cargarDatosUsuario();
  }

  @FXML
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

      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
    } else {
      txtPasswordOculto.setVisible(true);
      txtPassword.setVisible(false);
      txtPasswordOculto.setText(txtPassword.getText());

      txtConfirmarPasswordOculto.setVisible(true);
      txtConfirmarPassword.setVisible(false);
      txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

      imgOjoPassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
    }
  }

  @FXML
  void btnAplicarCambiosPressed(MouseEvent event) {
    // 1. Validar los campos
    String nuevoEmail = txtEmail.getText().trim();
    String nuevoUsuario = txtUser.getText().trim();
    String nuevaPassword = txtPassword.getText().trim();
    String confirmarPassword = txtConfirmarPassword.getText().trim();

    // Obtener los géneros seleccionados del CheckComboBox
    ObservableList<String> generosSeleccionadosList = lstGeneros.getCheckModel().getCheckedItems();

    // 2. Usar el método de validación de VentanaUtil
    boolean esValido = VentanaUtil.validarContrasena(nuevaPassword, confirmarPassword, generosSeleccionadosList);

    if (!esValido) {
      return; // Si la validación falla, no continuar con la actualización
    }

    // 3. Hash de la nueva contraseña
    String contrasenaHash = null;
    try {
      contrasenaHash = VentanaUtil.hashPassword(nuevaPassword);
    } catch (Exception e) {
      e.printStackTrace();
      mostrarAlerta("Error de hash", "Ocurrió un error al generar el hash de la contraseña.", Alert.AlertType.ERROR);
      return;
    }

    // 4. Si es válido, actualizar los datos en la base de datos
    String sql = "UPDATE usuarios SET contrasena = ?, email = ?, generos = ? WHERE id_usuario = ?";

    try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      // Establecer los valores de los parámetros (la contraseña ahora está hasheada)
      stmt.setString(1, contrasenaHash);
      stmt.setString(2, nuevoEmail); // Aquí se actualiza el email
      stmt.setString(3, String.join(",", generosSeleccionadosList)); // Aquí se actualizan los géneros
      stmt.setInt(4, obtenerIdUsuario());

      // Ejecutar la actualización
      int filasAfectadas = stmt.executeUpdate();

      // Verificar si la actualización fue exitosa
      if (filasAfectadas > 0) {
        mostrarAlerta("Éxito", "Los cambios se aplicaron correctamente.", Alert.AlertType.INFORMATION);
        txtPassword.clear();
        txtConfirmarPassword.clear();
        txtPasswordOculto.clear();
        txtConfirmarPasswordOculto.clear();

      } else {
        mostrarAlerta("Error", "No se pudieron aplicar los cambios. Intenta nuevamente.", Alert.AlertType.ERROR);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      mostrarAlerta("Error de base de datos",
          "Ocurrió un error al intentar actualizar la información. Por favor, inténtalo de nuevo.",
          Alert.AlertType.ERROR);
    }
  }

  private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
    Alert alerta = new Alert(tipo);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(contenido);
    alerta.showAndWait();
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
  void flechaAtrasPressed(MouseEvent event) {
    abrirNuevaVentana(HOME);
  }

  @FXML
  void lblHistorialJuegosPressed(MouseEvent event) {
    abrirNuevaVentana(HISTORIAL_JUEGOS);
  }

  @FXML
  void lblCerrarSesionPressed(MouseEvent event) {
    // Crear la alerta de tipo confirmación
    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
    alerta.setTitle("Cerrar sesión");
    alerta.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
    alerta.setContentText("Confirma si deseas salir de tu cuenta actual.");

    // Personalizar los botones de la alerta
    ButtonType botonConfirmar = new ButtonType("Aceptar");
    ButtonType botonCancelar = new ButtonType("Cancelar");

    // Establecer los botones personalizados
    alerta.getButtonTypes().setAll(botonConfirmar, botonCancelar);

    // Mostrar la alerta y manejar la respuesta de forma sencilla
    alerta.showAndWait();

    // Verificar cuál fue el botón presionado
    if (alerta.getResult() == botonConfirmar) {
      abrirNuevaVentana(LOGIN);
    } else {
      alerta.close();
    }
  }

  @FXML
  void lblEliminarCuentaPressed(MouseEvent event) {
    // Crear la alerta de tipo confirmación
    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
    alerta.setTitle("Eliminar Cuenta");
    alerta.setHeaderText("¿Estás seguro de que deseas eliminar tu cuenta?");
    alerta.setContentText("Esta acción es irreversible. Si aceptas, perderás todos tus datos.");

    // Personalizar los botones de la alerta
    ButtonType botonConfirmar = new ButtonType("Aceptar");
    ButtonType botonCancelar = new ButtonType("Cancelar");

    // Establecer los botones personalizados
    alerta.getButtonTypes().setAll(botonConfirmar, botonCancelar);

    // Mostrar la alerta y manejar la respuesta
    alerta.showAndWait().ifPresent(new Consumer<ButtonType>() {
      @Override
      public void accept(ButtonType respuesta) {
        if (respuesta == botonConfirmar) {
          eliminarCuenta();
          abrirNuevaVentana(LOGIN);
        } else {
          // Si el usuario cancela
          alerta.close();
        }
      }
    });

  }

  private void eliminarCuenta() {
    // Establecemos la consulta SQL
    String sql = SQL_ELIMINAR_CUENTA;

    // Suponiendo que el id_usuario lo obtienes desde una sesión activa
    int idUsuario = obtenerIdUsuario();

    try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Establecer el valor del parámetro en la consulta (id_usuario)
      stmt.setInt(1, idUsuario);

      // Ejecutar la eliminación
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private int obtenerIdUsuario() {
   	return HomeController.getUsuario().getIdUsuario();
  }

  public void abrirNuevaVentana(String fxml) {
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

  private void cargarDatosUsuario() {
    // Obtener los datos del usuario activo desde la base de datos (usando el
    // id_usuario)
    int idUsuario = obtenerIdUsuario();

    // Realiza la consulta para obtener el nombre, email y géneros del usuario
    String sql = "SELECT nombre, email, generos FROM usuarios WHERE id_usuario = ?";

    try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, idUsuario);
      var rs = stmt.executeQuery();

      if (rs.next()) {
        // Obtener los valores de la base de datos
        String nombre = rs.getString("nombre");
        String email = rs.getString("email");
        String generosUsuario = rs.getString("generos");

        // Establecer los valores en los campos correspondientes
        txtUser.setText(nombre);
        txtEmail.setText(email);

        // Deshabilitar los campos de usuario y email
        txtUser.setDisable(true);
        txtEmail.setDisable(true);

        // Cargar los géneros en el CheckComboBox
        // Generar una lista de géneros seleccionados del string 'generosUsuario' (que
        // es una cadena separada por comas)
        String[] generosSeleccionados = generosUsuario.split(",");
        lstGeneros.getCheckModel().clearChecks();
        for (String genero : generosSeleccionados) {
          if (lstGeneros.getItems().contains(genero.trim())) {
            lstGeneros.getCheckModel().check(lstGeneros.getItems().indexOf(genero.trim()));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
