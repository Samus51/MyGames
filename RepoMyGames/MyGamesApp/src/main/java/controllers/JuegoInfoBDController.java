package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.JuegoBD;
import models.Usuario;
import utils.VentanaUtil;

/**
 * Controlador de JuegoInfo
 */
public class JuegoInfoBDController {
  String tituloJuego;

  private JuegoBD juego;
  private Usuario usuario;
  Connection conn;

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
    if (juego != null) {
      // Mostrar datos del juego en los controles correspondientes
      if (juego.getDescripcion() != null) {
        lblDescripcionVacio.setText(juego.getDescripcion());
      }
      if (juego.getDesarrolladores() != null) {
        lblDesarrolladoresVacio.setText(juego.getDesarrolladores());
      }
      if (juego.getPlataformas() != null) {
        lblPlataformasVacio.setText(juego.getPlataformas().toString());
      }
      if (juego.getFechaLanzamiento() != null) {
        lblFechaLanzamientoVacio.setText(juego.getFechaLanzamiento());
      }
      if (juego.getGeneros() != null) {
        lblGenerosVacio.setText(juego.getGeneros());
      }

      if (juego.getTiempoJugado() >= 0) {
        lblTiempoJugadoVacio.setText(juego.getTiempoJugado() + " Horas");
      }

      // Para las imágenes
      if (juego.getImagenSecundaria() != null) {
        Image imgSecundaria = new Image(new ByteArrayInputStream(juego.getImagenSecundaria()));
        imgJuego2.setImage(imgSecundaria);
      }
      if (juego.getImagenTerciaria() != null) {
        Image imgTerciaria = new Image(new ByteArrayInputStream(juego.getImagenTerciaria()));
        imgJuego3.setImage(imgTerciaria);
      }
      if (juego.getImagenCuarta() != null) {
        Image imgCuarta = new Image(new ByteArrayInputStream(juego.getImagenCuarta()));
        imgJuego4.setImage(imgCuarta);
      }
      if (juego.getImagenQuinta() != null) {
        Image imgQuinta = new Image(new ByteArrayInputStream(juego.getImagenQuinta()));
        imgJuego5.setImage(imgQuinta);
      }
    }
  }

  @FXML
  void btnAnadirJuegoPressed(MouseEvent event) throws ParseException {
    if (juego == null) {
      VentanaUtil.mostrarAlerta("Error", "No se ha seleccionado ningún juego.");
      return;
    }

    // Recuperar datos del juego
    String titulo = juego.getTitulo();
    String descripcion = juego.getDescripcion();
    String fechaLanzamientoString = juego.getFechaLanzamiento();
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    java.sql.Date sqlFechaLanzamiento = null;
    if (fechaLanzamientoString != null && !fechaLanzamientoString.trim().isEmpty()) {
      try {
        java.util.Date utilFechaLanzamiento = formato.parse(fechaLanzamientoString);
        sqlFechaLanzamiento = new java.sql.Date(utilFechaLanzamiento.getTime());
      } catch (ParseException e) {
        VentanaUtil.mostrarAlerta("Error de fecha", "Formato de fecha incorrecto.");
        return;
      }
    }

    int idUsuario = HomeController.getUsuario().getIdUsuario();
    int tiempoJugado = juego.getTiempoJugado();
    String desarrolladores = juego.getDesarrolladores() != null ? juego.getDesarrolladores() : "Desconocido";
    String pegi = juego.getPegi() != null ? juego.getPegi() : "Desconocido";

    // Manejo de imágenes
    byte[] imagenPrincipal = juego.getImagenPrincipal();
    byte[] imagenSecundaria = juego.getImagenSecundaria();
    byte[] imagenTercera = juego.getImagenTerciaria();
    byte[] imagenCuarta = juego.getImagenCuarta();
    byte[] imagenQuinta = juego.getImagenQuinta();

    String generos = juego.getGeneros() != null ? String.join(",", juego.getGeneros()) : "Desconocido";
    String plataformas = juego.getPlataformas() != null ? String.join(",", juego.getPlataformas()) : "Desconocido";

    String checkExistenciaJuegoSQL = "SELECT id_juego FROM juegos WHERE titulo = ?";
    String checkExistenciaBibliotecaSQL = "SELECT 1 FROM biblioteca WHERE id_usuario = ? AND id_juego = ?";

    try (Connection conn = Conector.conectar();
        PreparedStatement stExistenciaJuego = conn.prepareStatement(checkExistenciaJuegoSQL)) {

      stExistenciaJuego.setString(1, titulo);
      try (ResultSet rsJuego = stExistenciaJuego.executeQuery()) {

        if (rsJuego.next()) {
          int idJuegoExistente = rsJuego.getInt("id_juego");

          // Verificar si ya está en la biblioteca
          try (PreparedStatement stExistenciaBiblioteca = conn.prepareStatement(checkExistenciaBibliotecaSQL)) {
            stExistenciaBiblioteca.setInt(1, idUsuario);
            stExistenciaBiblioteca.setInt(2, idJuegoExistente);

            try (ResultSet rsBiblioteca = stExistenciaBiblioteca.executeQuery()) {
              if (rsBiblioteca.next()) {
                // Juego ya en la biblioteca, mostrar el menú 'menuAnadirJuego'
                VentanaUtil.mostrarAlerta("Juego ya en Biblioteca", "Este juego ya está en tu biblioteca.");
                mostrarMenu(menuAnadirJuego);
                return;
              }
            }
          }

          // Agregar el juego a la biblioteca si no está allí
          String insertBibliotecaSQL = "INSERT INTO biblioteca (id_usuario, id_juego, fecha_adquisicion) VALUES (?, ?, ?)";
          try (PreparedStatement stInsertBiblioteca = conn.prepareStatement(insertBibliotecaSQL)) {
            stInsertBiblioteca.setInt(1, idUsuario);
            stInsertBiblioteca.setInt(2, idJuegoExistente);
            stInsertBiblioteca.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            stInsertBiblioteca.executeUpdate();
            VentanaUtil.mostrarAlerta("Juego Añadido", "El juego ha sido añadido a tu biblioteca.");
            mostrarMenu(menuAnadirJuego);
          }

        } else {
          VentanaUtil.mostrarAlerta("Juego no encontrado", "Este juego no está en la base de datos.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      VentanaUtil.mostrarAlerta("Error", "Ocurrió un error al añadir el juego.");
    }
  }

  @FXML
  void btnEliminarJuegoPressed(MouseEvent event) {
    // Verificar si el juego está cargado en la pantalla
    if (juego == null || juego.getTitulo() == null || juego.getTitulo().isEmpty()) {
      VentanaUtil.mostrarAlerta("Error", "No se ha seleccionado ningún juego.");
      return;
    }

    // Obtener el título del juego que se está mostrando
    String tituloJuegoBorrar = juego.getTitulo();

    // Consulta SQL para obtener el ID del juego basado en el título
    String selectJuegoSQLBorrado = "SELECT id_juego FROM juegos WHERE titulo = ?";

    try (Connection conn = Conector.conectar(); PreparedStatement st = conn.prepareStatement(selectJuegoSQLBorrado)) {

      // Establecer el título del juego en la consulta SQL
      st.setString(1, tituloJuegoBorrar);
      try (ResultSet rs = st.executeQuery()) {

        // Verificar si el juego existe en la base de datos
        if (rs.next()) {
          int idJuego = rs.getInt("id_juego");

          // Eliminar el juego de la biblioteca
          String deleteBibliotecaSQL = "DELETE FROM biblioteca WHERE id_usuario = ? AND id_juego = ?";
          try (PreparedStatement stDelete = conn.prepareStatement(deleteBibliotecaSQL)) {
            stDelete.setInt(1, HomeController.getUsuario().getIdUsuario());
            stDelete.setInt(2, idJuego);
            stDelete.executeUpdate();

            // Mostrar mensaje y actualizar la interfaz
            VentanaUtil.mostrarAlerta("Juego Eliminado", "El juego ha sido eliminado de la biblioteca.");
            mostrarMenu(menuGeneral);
          }
        } else {
          VentanaUtil.mostrarAlerta("Error", "El juego no se encuentra en la base de datos.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      VentanaUtil.mostrarAlerta("Error al Eliminar Juego", "Ocurrió un error al eliminar el juego de la biblioteca.");
    }
  }

  @FXML
  void btnAnadirListaPressed(MouseEvent event) {
  }

  @FXML
  void btnEliminarListaPressed(MouseEvent event) {
  }

  @FXML
  void btnNoJugadoPressed(MouseEvent event) {
    if (juego == null || juego.getTitulo() == null || juego.getTitulo().isEmpty()) {
      VentanaUtil.mostrarAlerta("Error", "No se ha seleccionado ningún juego.");
      return;
    }

    int idUsuario = HomeController.getUsuario().getIdUsuario();
    String tituloJuego = juego.getTitulo();

    // Consulta SQL para verificar si el juego ya está marcado como jugado por el
    // usuario
    String checkJuegoJugadoSQL = "SELECT 1 FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";

    try (Connection conn = Conector.conectar();
        PreparedStatement stCheck = conn.prepareStatement(checkJuegoJugadoSQL)) {
      stCheck.setInt(1, idUsuario);
      stCheck.setInt(2, juego.getIdJuego());

      try (ResultSet rs = stCheck.executeQuery()) {
        if (rs.next()) {
          // El juego ya está marcado como jugado, no hacer nada
          VentanaUtil.mostrarAlerta("Juego ya marcado como jugado", "Este juego ya está marcado como jugado.");
        } else {
          // El juego no está marcado como jugado, insertarlo en la tabla
          String insertJuegoJugadoSQL = "INSERT INTO juegos_jugados (id_usuario, id_juego, fecha_jugado) VALUES (?, ?, ?)";
          try (PreparedStatement stInsert = conn.prepareStatement(insertJuegoJugadoSQL)) {
            stInsert.setInt(1, idUsuario);
            stInsert.setInt(2, juego.getIdJuego());
            stInsert.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            stInsert.executeUpdate();
            VentanaUtil.mostrarAlerta("Juego marcado como jugado", "El juego ha sido marcado como jugado.");
            mostrarMenu(menuJugadoSinAnadir);
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      VentanaUtil.mostrarAlerta("Error", "Ocurrió un error al marcar el juego como jugado.");
    }
  }

  @FXML
  void btnJugadoPressed(MouseEvent event) {
    if (juego == null || juego.getTitulo() == null || juego.getTitulo().isEmpty()) {
      VentanaUtil.mostrarAlerta("Error", "No se ha seleccionado ningún juego.");
      return;
    }

    int idUsuario = HomeController.getUsuario().getIdUsuario();
    String tituloJuego = juego.getTitulo();

    // Consulta SQL para verificar si el juego está marcado como jugado
    String checkJuegoJugadoSQL = "SELECT 1 FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";

    try (Connection conn = Conector.conectar();
        PreparedStatement stCheck = conn.prepareStatement(checkJuegoJugadoSQL)) {
      stCheck.setInt(1, idUsuario);
      stCheck.setInt(2, juego.getIdJuego()); // Asegúrate de que el objeto `juego` tenga el idJuego

      try (ResultSet rs = stCheck.executeQuery()) {
        if (rs.next()) {
          // El juego está marcado como jugado, eliminarlo de la tabla
          String deleteJuegoJugadoSQL = "DELETE FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";
          try (PreparedStatement stDelete = conn.prepareStatement(deleteJuegoJugadoSQL)) {
            stDelete.setInt(1, idUsuario);
            stDelete.setInt(2, juego.getIdJuego());
            stDelete.executeUpdate();
            VentanaUtil.mostrarAlerta("Juego marcado como no jugado",
                "El juego ha sido eliminado de la lista de juegos jugados.");
            mostrarMenu(menuGeneral);
          }
        } else {
          // El juego no está marcado como jugado, no hacer nada
          VentanaUtil.mostrarAlerta("Juego no jugado", "Este juego no está marcado como jugado.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      VentanaUtil.mostrarAlerta("Error", "Ocurrió un error al marcar el juego como no jugado.");
    }
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
    this.juego = juego;

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

      if (juego.getPegi() != null) {
        switch (juego.getPegi()) {
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

      System.out.println("PEGI del juego: " + juego.getPegi());

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

  private void ocultarTodosLosMenus() {
    VBox[] menus = { menuGeneral, menuAnadirJuego, menuAnadirJuegoJugado, menuAnadirListaNoJugado,
        menuJugadoAnanidoLista, menuJugadoSinAnadir };

    for (VBox menu : menus) {
      if (menu != null) {
        menu.setVisible(false);
      }
    }
  }

  private void mostrarMenu(VBox menu) {
    ocultarTodosLosMenus();
    if (menu != null) {
      menu.setVisible(true);
      menu.setDisable(false);
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

  public void setUsuario(Usuario usuario) {
    if (usuario.equals(null)) {
      return;
    }
    this.usuario = usuario;
  }

  public Usuario getUsuario() {
    return usuario;

  }

}
