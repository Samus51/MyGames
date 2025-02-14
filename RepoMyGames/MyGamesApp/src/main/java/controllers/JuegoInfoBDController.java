package controllers;

import java.io.IOException;
import java.util.List;
import java.io.ByteArrayInputStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.JuegoBD;
import models.JuegoPachorra;
import utils.ExtractorAPI;
import utils.ExtractorApi2;
import utils.VentanaUtil;

/**
 * Controlador de JuegoInfo
 */
public class JuegoInfoBDController {
  String tituloJuego;

  private JuegoBD juegoSeleccionado;

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
  private VBox vboxPrincipal;

  private void cargarJuegoInfo() {
    if (juegoSeleccionado != null) {
      // Mostrar datos del juego en los controles correspondientes
      if (juegoSeleccionado.getDescripcion() != null) {
        lblDescripcionVacio.setText(juegoSeleccionado.getDescripcion());
      }
      if (juegoSeleccionado.getDesarrolladores() != null) {
        lblDesarrolladoresVacio.setText(juegoSeleccionado.getDesarrolladores());
      }
      if (juegoSeleccionado.getPlataformas() != null) {
        lblPlataformasVacio.setText(juegoSeleccionado.getPlataformas().toString());
      }
      if (juegoSeleccionado.getFechaLanzamiento() != null) {
        lblFechaLanzamientoVacio.setText(juegoSeleccionado.getFechaLanzamiento());
      }
      if (juegoSeleccionado.getGeneros() != null) {
        lblGenerosVacio.setText(juegoSeleccionado.getGeneros());
      }

      if (juegoSeleccionado.getTiempoJugado() >= 0) {
        lblTiempoJugadoVacio.setText(juegoSeleccionado.getTiempoJugado() + " Horas");
      }

      // Para las imágenes
      if (juegoSeleccionado.getImagenSecundaria() != null) {
        Image imgSecundaria = new Image(new ByteArrayInputStream(juegoSeleccionado.getImagenSecundaria()));
        imgJuego2.setImage(imgSecundaria);
      }
      if (juegoSeleccionado.getImagenTerciaria() != null) {
        Image imgTerciaria = new Image(new ByteArrayInputStream(juegoSeleccionado.getImagenTerciaria()));
        imgJuego3.setImage(imgTerciaria);
      }
      if (juegoSeleccionado.getImagenCuarta() != null) {
        Image imgCuarta = new Image(new ByteArrayInputStream(juegoSeleccionado.getImagenCuarta()));
        imgJuego4.setImage(imgCuarta);
      }
      if (juegoSeleccionado.getImagenQuinta() != null) {
        Image imgQuinta = new Image(new ByteArrayInputStream(juegoSeleccionado.getImagenQuinta()));
        imgJuego5.setImage(imgQuinta);
      }
    }
  }

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
  void flechaAtrasPressed(MouseEvent event) throws IOException {
    VentanaUtil.abrirVentana(HOME, "Juego Info", STYLES, null, event);

  }

  @FXML
  void minimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  public void setJuegoSeleccionado(JuegoBD juego) {
    this.juegoSeleccionado = juego;

    // Actualizar la interfaz con los datos del juego
    if (juego != null) {
      // Mostrar el título
      lblTitulo.setText(juego.getTitulo());

      // Mostrar la imagen principal si está disponible
      if (juego.getImagenPrincipal() != null) {
        Image img = new Image(new ByteArrayInputStream(juego.getImagenPrincipal()));
        imgJuego.setImage(img);
      }

      // Mostrar la descripción del juego
      if (juego.getDescripcion() != null) {
        lblDescripcionVacio.setText(juego.getDescripcion());
      }

      // Mostrar los desarrolladores si están disponibles
      if (juego.getDesarrolladores() != null) {
        lblDesarrolladoresVacio.setText(juego.getDesarrolladores());
      }

      // Mostrar las plataformas si están disponibles
      if (juego.getPlataformas() != null && !juego.getPlataformas().isEmpty()) {
        lblPlataformasVacio.setText(String.join(", ", juego.getPlataformas()));
      }

      // Mostrar la fecha de lanzamiento si está disponible
      if (juego.getFechaLanzamiento() != null) {
        lblFechaLanzamientoVacio.setText(juego.getFechaLanzamiento());
      }

      // Mostrar los géneros si están disponibles
      if (juego.getGeneros() != null) {
        lblGenerosVacio.setText(juego.getGeneros());
      }

      // Mostrar el tiempo jugado si es mayor o igual a 0
      if (juego.getTiempoJugado() >= 0) {
        lblTiempoJugadoVacio.setText(juego.getTiempoJugado() + " Horas");
      }

      if (juegoSeleccionado.getPegi() != null) {
        switch (juegoSeleccionado.getPegi()) {
        case "16":
          imgPegi.setImage(new Image("imgPegi/pegi16.png"));
          break;
        case "18":
          imgPegi.setImage(new Image("imgPegi/pegi18.png"));
          break;
        case "3":
          imgPegi.setImage(new Image("imgPegi/pegi3.png"));
          break;
        case "12":
          imgPegi.setImage(new Image("imgPegi/pegi12.png"));
          break;
        case "7":
          imgPegi.setImage(new Image("imgPegi/pegi7.png"));
          break;
        default:
          break;
        }
      }
      
      System.out.println("PEGI del juego: " + juegoSeleccionado.getPegi());


      // Mostrar las imágenes adicionales (secundarias, terciarias, etc.)
      if (juego.getImagenSecundaria() != null) {
        imgJuego2.setImage(new Image(new ByteArrayInputStream(juego.getImagenSecundaria())));
      }
      if (juego.getImagenTerciaria() != null) {
        imgJuego3.setImage(new Image(new ByteArrayInputStream(juego.getImagenTerciaria())));
      }
      if (juego.getImagenCuarta() != null) {
        imgJuego4.setImage(new Image(new ByteArrayInputStream(juego.getImagenCuarta())));
      }
      if (juego.getImagenQuinta() != null) {
        imgJuego5.setImage(new Image(new ByteArrayInputStream(juego.getImagenQuinta())));
      }
    }
  }

  /**
   * @return the tituloJuego
   */
  public String getTituloJuego() {
    return tituloJuego;
  }

  public void setTituloJuego(String tituloJuego) {
    this.tituloJuego = tituloJuego;
    if (lblTitulo != null) {
      lblTitulo.setText(tituloJuego);
    }
    System.out.println("Juego de Info: " + tituloJuego);
    cargarJuegoInfo();

  }

}
