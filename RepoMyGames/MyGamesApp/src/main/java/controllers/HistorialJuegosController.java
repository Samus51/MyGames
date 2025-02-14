package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.Juego;
import models.Usuario;
import utils.VentanaUtil;

/**
 * Controlador de HistorialJuegos
 */
public class HistorialJuegosController implements Initializable {

  // Constantes
  // SQL
  private static final String SQL_ELIMINAR_CUENTA = "DELETE FROM usuarios WHERE id_usuario = ?";

  private static final String SQL_SELECT__CONTADOR_JUEGOS_JUGADOS = "Select count(id_juego_jugado) FROM juegos_jugados WHERE id_usuario = ?";
  private static final String SQL_SELECT_JUEGOS_JUGADOS = "Select juegos.titulo, juegos.generos, juegos.tiempo_jugado,juegos.imagen_principal FROM juegos_jugados inner join juegos on juegos_jugados.id_juego = juegos.id_juego WHERE juegos_jugados.id_usuario = ?";

  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String LOGIN = "/views/Login.fxml";
  private static final String JUEGO = "/views/Juego.fxml";
  private static final String CAMBIAR_INFO_PERSONAL = "/views/CambiarInfoPersonal.fxml";
  private static final String HOME = "/views/Home.fxml";

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
        fxmlLoader.setLocation(getClass().getResource(JUEGO));

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
    List<Juego> juegos = new ArrayList<>();

    // Obtener el ID del usuario
    int idUsuario = obtenerIdUsuario();

    // Primero contar la cantidad de juegos jugados por el usuario
    String sqlContador = SQL_SELECT__CONTADOR_JUEGOS_JUGADOS;
    int contadorJuegos = 0;

    try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sqlContador)) {

      // Establecer el parámetro para la consulta de conteo
      stmt.setInt(1, idUsuario);

      // Ejecutar la consulta de conteo
      ResultSet resultSet = stmt.executeQuery();

      // Obtener el resultado del conteo
      if (resultSet.next()) {
        contadorJuegos = resultSet.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Ahora, si hay juegos jugados, los obtenemos
    if (contadorJuegos > 0) {
      String sqlJuegos = SQL_SELECT_JUEGOS_JUGADOS;

      try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sqlJuegos)) {

        // Establecer el parámetro del id_usuario
        stmt.setInt(1, idUsuario);

        // Ejecutar la consulta para obtener los juegos jugados
        var resultSet = stmt.executeQuery();

        // Recorrer los resultados y crear objetos Juego
        while (resultSet.next()) {
          Juego juego = new Juego();
          juego.setNombre(resultSet.getString("titulo"));
          juego.setGenero(resultSet.getString("generos"));
          juego.setTiempo_jugado(resultSet.getString("tiempo_jugado"));

          // Cambiar esta línea para obtener la imagen como un arreglo de bytes
          byte[] imagenBytes = resultSet.getBytes("imagen_principal");
          juego.setImagen(imagenBytes);

          juegos.add(juego);
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return juegos;
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
  void flechaAtrasPressed(MouseEvent event) throws IOException {
    VentanaUtil.abrirVentana(HOME, "Inicio", STYLES, null, event);
  }

  @FXML
  void lblCambiarInfoPressed(MouseEvent event) throws IOException {
    VentanaUtil.abrirVentana(CAMBIAR_INFO_PERSONAL, "Cambiar Información", STYLES, null, event);
  }

  @FXML
  void lblCerrarSesionPressed(MouseEvent event) throws IOException {
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
      VentanaUtil.abrirVentana(LOGIN, "Login", STYLES, null, event);
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
          try {
            VentanaUtil.abrirVentana(LOGIN, "Login", STYLES, null, event);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
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
    return 42;
  }

}
