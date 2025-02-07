package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.Conector;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoAnadirController {

  private static final String STYLES = "/styles.css";
  private static final String HOME = "/views/Home.fxml";
  private static final String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

  @FXML
  private BorderPane VentanaPrincipal;
  @FXML
  private Button btnAnadirJuego;
  @FXML
  private TextField txtFechaLanzamiento, txtDesarrolladores, txtDescripcion, txtTiempoJugado, txtTitulo;
  @FXML
  private ImageView img1, img2, img3, img4, img5;
  @FXML
  private ImageView imgCerrar, imgFlechaAtras, imgMinimizar;
  @FXML
  private Label lblMetaScoreVacio, lblTitulo;
  @FXML
  private CheckComboBox<String> lstGeneros, lstPlataformas;
  @FXML
  private CheckComboBox<String> lstPegi;

  private File archivoImagenPrincipal, archivoImagenSecundaria, archivoImagenTercera, archivoImagenCuarta,
      archivoImagenQuinta;

  @FXML
  public void initialize() {
    lstGeneros.getItems().addAll("Acción", "Aventura", "RPG", "Casual", "Arcade", "Indie", "Estrategia", "Simulación",
        "Deportes", "Carreras", "Lucha", "Disparos", "Puzzle", "Multijugador masivo");
    lstPlataformas.getItems().addAll("PC", "PlayStation 5", "Xbox Series X", "Nintendo Switch", "Android", "iOS");
    lstPegi.getItems().addAll("3", "7", "12", "16", "18");
  }

  @FXML
  void btnCerrarPressed(MouseEvent event) {
    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
  }

  @FXML
  void btnMinimizarPressed(MouseEvent event) {
    ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
  }

  @FXML
  void btnUserPressed(MouseEvent event) {
    abrirNuevaVentana(PANEL_USER);
  }

  @FXML
  void flechaAtrasPressed(MouseEvent event) {
    abrirNuevaVentana(HOME);
  }

  @FXML
  void guardarJuego(MouseEvent event) {
    String titulo = txtTitulo.getText().trim();
    String descripcion = txtDescripcion.getText().trim();
    String desarrolladores = txtDesarrolladores.getText().trim();
    String fechaLanzamiento = txtFechaLanzamiento.getText().trim();
    List<String> generos = new ArrayList<>(lstGeneros.getCheckModel().getCheckedItems());
    List<String> plataformas = new ArrayList<>(lstPlataformas.getCheckModel().getCheckedItems());
    int tiempoJugado = txtTiempoJugado.getText().trim().isEmpty() ? 0
        : Integer.parseInt(txtTiempoJugado.getText().trim());
    int pegi = lstPegi.getCheckModel().getCheckedItems().isEmpty() ? 0
        : Integer.parseInt(lstPegi.getCheckModel().getCheckedItems().get(0));
    int idUsuario = obtenerIdUsuarioActual();

    if (titulo.isEmpty() || desarrolladores.isEmpty() || fechaLanzamiento.isEmpty() || generos.isEmpty()
        || plataformas.isEmpty()) {
      mostrarAlerta("Error", "Todos los campos deben estar completos", Alert.AlertType.ERROR);
      return;
    }

    try (Connection conn = Conector.conectar()) {
      conn.setAutoCommit(false);
      int idDesarrollador = obtenerIdDesarrollador(conn, desarrolladores);
      int idJuego = insertarJuego(conn, titulo, descripcion, fechaLanzamiento, idDesarrollador, idUsuario, tiempoJugado,
          desarrolladores, pegi);
      asociarPlataformas(conn, idJuego, plataformas);
      conn.commit();
      mostrarAlerta("Éxito", "Juego guardado correctamente", Alert.AlertType.INFORMATION);
    } catch (SQLException e) {
      e.printStackTrace();
      mostrarAlerta("Error", "No se pudo guardar el juego", Alert.AlertType.ERROR);
    }
  }

  private int obtenerIdDesarrollador(Connection conn, String desarrolladores) throws SQLException {
    String query = "SELECT id_desarrollador FROM desarrolladores WHERE nombre = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, desarrolladores);
      ResultSet rs = stmt.executeQuery();
      if (rs.next())
        return rs.getInt("id_desarrollador");
    }
    query = "INSERT INTO desarrolladores (nombre) VALUES (?)";
    try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, desarrolladores);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      return rs.next() ? rs.getInt(1) : -1;
    }
  }

  private int insertarJuego(Connection conn, String titulo, String descripcion, String fechaLanzamiento,
      int idDesarrollador, int idUsuario, int tiempoJugado, String desarrolladores, int pegi) throws SQLException {
    String query = "INSERT INTO juegos (titulo, descripcion, fecha_lanzamiento, id_desarrollador, creado_por_usuario, id_usuario, tiempo_jugado, creadores, pegi) VALUES (?, ?, ?, ?, 1, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, titulo);
      stmt.setString(2, descripcion);
      stmt.setString(3, fechaLanzamiento);
      stmt.setInt(4, idDesarrollador);
      stmt.setInt(5, idUsuario);
      stmt.setInt(6, tiempoJugado);
      stmt.setString(7, desarrolladores);
      stmt.setInt(8, pegi);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      return rs.next() ? rs.getInt(1) : -1;
    }
  }

  private void asociarPlataformas(Connection conn, int idJuego, List<String> plataformas) throws SQLException {
    String query = "INSERT INTO juegos_plataformas (id_juego, id_plataforma) VALUES (?, (SELECT id_plataforma FROM plataformas WHERE nombre = ?))";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
      for (String plataforma : plataformas) {
        stmt.setInt(1, idJuego);
        stmt.setString(2, plataforma);
        stmt.executeUpdate();
      }
    }
  }

  private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
    Alert alert = new Alert(tipo);
    alert.setTitle(titulo);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void abrirNuevaVentana(String fxml) {
    try {
      Stage stage = new Stage();
      stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
      stage.initStyle(StageStyle.DECORATED);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private int obtenerIdUsuarioActual() {
    return 29;
  }
}
