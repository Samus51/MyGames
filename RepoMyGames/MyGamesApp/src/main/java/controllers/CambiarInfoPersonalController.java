package controllers;

import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;
import models.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

import org.controlsfx.control.CheckComboBox;

public class CambiarInfoPersonalController {

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
  private CheckComboBox<?> lstGeneros;

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

  @FXML
  void btnAplicarCambiosPressed(MouseEvent event) {

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
    abrirNuevaVentana("/views/Home.fxml");
  }

  @FXML
  void lblCambiarInfoPressed(MouseEvent event) {
    abrirNuevaVentana("/views/CambiarInfoPersonal.fxml");
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
      abrirNuevaVentana("/views/Login.fxml");
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
          abrirNuevaVentana("/views/Login.fxml");
        } else {
          // Si el usuario cancela
          alerta.close();
        }
      }
    });

  }

  private void eliminarCuenta() {
    // Establecemos la consulta SQL
    String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

    // Suponiendo que el id_usuario lo obtienes desde una sesión activa
    int idUsuario = obtenerIdUsuario();

    try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Establecer el valor del parámetro en la consulta (id_usuario)
      stmt.setInt(1, idUsuario);

      // Ejecutar la eliminación
      int filasAfectadas = stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private int obtenerIdUsuario() {
    if (usuarioActivo != null) {
      return usuarioActivo.getIdUsuario();
    }
    return -1;
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
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

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

  @FXML
  void mostrarPassword(ActionEvent event) {

  }

}
