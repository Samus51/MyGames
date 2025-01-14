package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;
import models.Juego;
import models.Usuario;

public class HistorialJuegosController implements Initializable {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private ImageView imgCerrar;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private Label lblGenero;

  @FXML
  private Label lblNombre;

  @FXML
  private Label lblTiempoJuego;

  @FXML
  private VBox listaJuegos;

  @FXML
  private BorderPane panelLogo;

  List<Juego> recentlyPlayed;

  private Usuario usuarioActivo;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    recentlyPlayed = new ArrayList<>(getRecentlyPlayed());

    try {
      for (Juego juego : recentlyPlayed) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/juego.fxml"));

        HBox hBox = fxmlLoader.load();
        JuegoController juegoController = fxmlLoader.getController();
        juegoController.setData(juego);

        listaJuegos.getChildren().add(hBox);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<Juego> getRecentlyPlayed() {
    List<Juego> ls = new ArrayList<>();

    Juego juego1 = new Juego();
    juego1.setNombre("Juego 1");
    juego1.setImagen("/Logo.png");
    juego1.setGenero("Accion");
    juego1.setTiempo_jugado("23");
    ls.add(juego1);

    Juego juego2 = new Juego();
    juego2.setImagen("/Logo.png");
    juego2.setNombre("Juego 2");
    juego2.setGenero("Accion");
    juego2.setTiempo_jugado("23");
    ls.add(juego2);

    Juego juego3 = new Juego();
    juego3.setImagen("/Logo.png");
    juego3.setNombre("Juego 3");
    juego3.setGenero("Accion");
    juego3.setTiempo_jugado("23");
    ls.add(juego3);

    Juego juego4 = new Juego();
    juego4.setImagen("/Logo.png");
    juego4.setNombre("Juego 4");
    juego4.setGenero("Accion");
    juego4.setTiempo_jugado("23");
    ls.add(juego4);

    Juego juego5 = new Juego();
    juego5.setImagen("/Logo.png");
    juego5.setNombre("Juego 5");
    juego5.setGenero("Accion");
    juego5.setTiempo_jugado("23");
    ls.add(juego5);

    Juego juego6 = new Juego();
    juego6.setImagen("/Logo.png");
    juego6.setNombre("Juego 6");
    juego6.setGenero("Accion");
    juego6.setTiempo_jugado("23");
    ls.add(juego6);

    Juego juego7 = new Juego();
    juego7.setImagen("/Logo.png");
    juego7.setNombre("Juego 7");
    juego7.setGenero("Accion");
    juego7.setTiempo_jugado("23");
    ls.add(juego7);

    return ls;
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

  private void abrirNuevaVentana(String fxml) {
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
}
