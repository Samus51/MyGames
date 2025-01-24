package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controlador de JuegoInfo
 */
public class JuegoInfoController {

  // Constantes
  // Styles
  private static final String STYLES = "/styles.css";
  // Pantallas
  private static final String HOME = "/views/Home.fxml";

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnAnadirJuego;

  @FXML
  private Button btnAnadirJuegoAnadirListaNoJugado;

  @FXML
  private Button btnAnadirJuegoJugadoAnanidoLista;

  @FXML
  private Button btnAnadirJuegoJugadoSinAnadir;

  @FXML
  private Button btnAnadirListaDeseos;

  @FXML
  private Button btnAnadirListaDeseosAnadirListaNoJugado;

  @FXML
  private Button btnAnadirListaDeseosJugadoSinAnadir;

  @FXML
  private Button btnEliminarJuegoAnadirJuego;

  @FXML
  private Button btnEliminarJuegoAnadirJuegoJugado;

  @FXML
  private Button btnEliminarListaDeseosJugadoAnanidoLista;

  @FXML
  private Button btnJugadoAnadirJuegoJugado;

  @FXML
  private Button btnJugadoJugadoAnanidoLista;

  @FXML
  private Button btnJugadoJugadoSinAnadir;

  @FXML
  private Button btnNoJugado;

  @FXML
  private Button btnNoJugadoAnadirJuego;

  @FXML
  private Button btnNoJugadoAnadirListaNoJugado;

  @FXML
  private ImageView imgCerrar;

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private ImageView imgJuego;

  @FXML
  private ImageView imgJuego2;

  @FXML
  private ImageView imgJuego3;

  @FXML
  private ImageView imgJuego4;

  @FXML
  private ImageView imgJuego5;

  @FXML
  private ImageView imgJuego6;

  @FXML
  private ImageView imgMinimizar;

  @FXML
  private ImageView imgPegi;

  @FXML
  private Label lblDesarrolladoresVacio;

  @FXML
  private Label lblDescripcionVacio;

  @FXML
  private Label lblFechaLanzamientoVacio;

  @FXML
  private Label lblGenerosVacio;

  @FXML
  private Label lblMetaScoreVacio;

  @FXML
  private Label lblPlataformasVacio;

  @FXML
  private Label lblTiempoJugadoVacio;

  @FXML
  private Label lblTitulo;

  @FXML
  private VBox menuAnadirJuego;

  @FXML
  private VBox menuAnadirJuegoJugado;

  @FXML
  private VBox menuAnadirListaNoJugado;

  @FXML
  private VBox menuGeneral;

  @FXML
  private VBox menuJugadoAnanidoLista;

  @FXML
  private VBox menuJugadoSinAnadir;

  @FXML
  private TextField txtComentarios;

  @FXML
  void btnAnadirJuegoPressed(MouseEvent event) {

  }

  @FXML
  void btnAnadirListaPressed(MouseEvent event) {

  }

  @FXML
  void btnEliminarJuegoPressed(MouseEvent event) {

  }

  @FXML
  void btnEliminarListaPressed(MouseEvent event) {

  }

  @FXML
  void btnJugadoPressed(MouseEvent event) {

  }

  @FXML
  void btnNoJugadoPressed(MouseEvent event) {

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
  void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
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
