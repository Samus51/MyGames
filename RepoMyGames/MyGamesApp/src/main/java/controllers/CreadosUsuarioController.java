package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.JuegoBD;
import utils.ImagenBDUtils;
import utils.VentanaUtil;

public class CreadosUsuarioController {

  private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";
  private static final String PANEL_JUEGO_INFO_BD = "/views/JuegoInfoBD.fxml";
  private static final String STYLES = "/styles.css";

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private ImageView btnCerrar;

  @FXML
  private Label btnGeneroSalida;

  @FXML
  private Label btnGeneros;

  @FXML
  private Label btnGenerosMenuPlataformas;

  @FXML
  private ImageView btnMenu;

  @FXML
  private ImageView btnMinimizar;

  @FXML
  private Label btnPlataformas;

  @FXML
  private Label btnPlataformasMenuGeneros;

  @FXML
  private Label btnPlataformasSalida;

  @FXML
  private ImageView btnUser;

  @FXML
  private HBox contBusqueda;

  @FXML
  private VBox contInfo12;

  @FXML
  private VBox contInfo121;

  @FXML
  private VBox contInfo13;

  @FXML
  private VBox contInfo14;

  @FXML
  private VBox contInfo22;

  @FXML
  private VBox contInfo221;

  @FXML
  private VBox contInfo23;

  @FXML
  private VBox contInfo24;

  @FXML
  private VBox contInfo5;

  @FXML
  private VBox contInfo51;

  @FXML
  private VBox contInfo6;

  @FXML
  private VBox contInfo7;

  @FXML
  private HBox contJuegos1;

  @FXML
  private HBox contJuegos2;

  @FXML
  private HBox contJuegos3;

  @FXML
  private HBox contJuegos4;

  @FXML
  private StackPane contMenuPadre;

  @FXML
  private HBox contPlataformas12;

  @FXML
  private HBox contPlataformas121;

  @FXML
  private HBox contPlataformas13;

  @FXML
  private HBox contPlataformas14;

  @FXML
  private HBox contPlataformas22;

  @FXML
  private HBox contPlataformas221;

  @FXML
  private HBox contPlataformas23;

  @FXML
  private HBox contPlataformas24;

  @FXML
  private HBox contPlataformas5;

  @FXML
  private HBox contPlataformas51;

  @FXML
  private HBox contPlataformas6;

  @FXML
  private HBox contPlataformas7;

  @FXML
  private ImageView imgCargarJuegosAtras;

  @FXML
  private ImageView imgCargarJuegosAdelante;

  @FXML
  private VBox juegoSolo1;

  @FXML
  private VBox juegoSolo2;

  @FXML
  private VBox juegoSolo31;

  @FXML
  private VBox juegoSolo311;

  @FXML
  private VBox juegoSolo32;

  @FXML
  private VBox juegoSolo33;

  @FXML
  private VBox juegoSolo41;

  @FXML
  private VBox juegoSolo411;

  @FXML
  private VBox juegoSolo42;

  @FXML
  private VBox juegoSolo43;

  @FXML
  private VBox juegoSolo7;

  @FXML
  private VBox juegoSolo71;

  @FXML
  private VBox menuFondito;

  @FXML
  private VBox menuGeneral;

  @FXML
  private VBox menuGeneros;

  @FXML
  private VBox menuPlataformas;

  @FXML
  private ScrollPane scrollJuegosVertical;

  @FXML
  private ScrollPane scrollMenu;

  @FXML
  public void initialize() {
    List<JuegoBD> capturasPlataformas = cargarJuegos("Popular");
    List<JuegoBD> capturasPlataformas2 = cargarJuegos("Nuevos");

    ImagenBDUtils.asignarImagenes(contJuegos1, capturasPlataformas);
    ImagenBDUtils.asignarImagenes(contJuegos2, capturasPlataformas2);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón de cerrar
   * 
   * @param event
   */
  void btnCerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }

  @FXML
  void btnGenerosMenuPlataformasPressed(MouseEvent event) {
    cambiarMenu(menuGeneros, menuPlataformas, menuGeneral, true);
  }

  @FXML
  void btnGenerosPressed(MouseEvent event) {
    cambiarMenu(menuGeneros, menuPlataformas, menuGeneral, false);
  }

  @FXML
  void btnGenerosSalidaPressed(MouseEvent event) {
    cambiarMenu(menuGeneral, menuGeneros, null, false);
  }

  @FXML
  void btnMenuPressed(MouseEvent event) {
    menuGeneral.setVisible(!menuGeneral.isVisible());
    menuGeneros.setVisible(false);
    menuPlataformas.setVisible(false);
  }

  @FXML
  /**
   * Método para cuando se pulse el botón minimizar
   * 
   * @param event
   */
  void btnMinimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  void btnPlataformasMenuGenerosPressed(MouseEvent event) {
    cambiarMenu(menuPlataformas, menuGeneros, menuGeneral, true);
  }

  @FXML
  void btnPlataformasPressed(MouseEvent event) {
    cambiarMenu(menuPlataformas, menuGeneros, menuGeneral, false);
  }

  @FXML
  void btnPlataformasSalidaPressed(MouseEvent event) {
    cambiarMenu(menuGeneral, menuPlataformas, menuGeneros, false);
  }

  @FXML
  void btnUserPressed(MouseEvent event) {
    try {
      // Usando VentanaUtil para abrir la ventana de usuario
      VentanaUtil.abrirVentana(PANEL_USER, "Usuario", STYLES, null, event);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void imgCargarJuegosAtrasPressed(MouseEvent event) {

  }

  @FXML
  void imgCargarJuegosAdelantePressed(MouseEvent event) {

  }

  @FXML
  void juegoSoloPressed(MouseEvent event) throws IOException {
    VBox vbox = (VBox) event.getSource();

    for (Node childNode : vbox.getChildren()) {
      if (childNode instanceof VBox innerVBox) {
        for (Node innerNode : innerVBox.getChildren()) {
          if (innerNode instanceof Label tituloLabel) {
            String tituloJuego = tituloLabel.getText();
            System.out.println("Título del juego: " + tituloJuego);

            VentanaUtil.abrirVentana(PANEL_JUEGO_INFO_BD, "Juego Info", STYLES, controller -> {
              ((JuegoInfoBDController) controller).setTituloJuego(tituloJuego);
            }, event);
          }
        }
      }
    }
  }

  private List<JuegoBD> cargarJuegos(String modo) {
    List<JuegoBD> juegos = new ArrayList<>();

    // Consulta para cargar los juegos desde la base de datos
    String query = "SELECT id_juego, titulo, plataformas, imagen_principal, imagen_secundaria, imagen_tercera, imagen_cuarta, imagen_quinta "
        + "FROM juegos WHERE creado_por_usuario = 1";

    try (Connection conn = Conector.conectar();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int idJuego = rs.getInt("id_juego");
        String titulo = rs.getString("titulo");
        String plataformas = rs.getString("plataformas");

        // Cargando las imágenes desde la base de datos (en formato BLOB)
        byte[] imagenPrincipal = rs.getBytes("imagen_principal");
        byte[] imagenSecundaria = rs.getBytes("imagen_secundaria");
        byte[] imagenTerciaria = rs.getBytes("imagen_tercera");
        byte[] imagenCuarta = rs.getBytes("imagen_cuarta");
        byte[] imagenQuinta = rs.getBytes("imagen_quinta");

        JuegoBD juego = new JuegoBD(null, titulo, idJuego, parsePlataformas(plataformas), imagenPrincipal,
            imagenSecundaria, imagenTerciaria, imagenCuarta, imagenQuinta);
        juegos.add(juego);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return juegos;
  }

  private List<String> parsePlataformas(String plataformasStr) {
    List<String> plataformas = new ArrayList<>();
    if (plataformasStr != null && !plataformasStr.isEmpty()) {
      for (String plataforma : plataformasStr.split(",")) {
        plataformas.add(plataforma.trim());
      }
    }
    return plataformas;
  }

  private void cambiarMenu(Region mostrar, Region ocultar1, Region ocultar2, boolean resetScroll) {
    menuGeneral.setVisible(mostrar == menuGeneral);
    menuGeneros.setVisible(mostrar == menuGeneros);
    menuPlataformas.setVisible(mostrar == menuPlataformas);

    menuGeneros.setMinHeight(mostrar == menuGeneros ? Region.USE_COMPUTED_SIZE : 0);
    menuPlataformas.setMinHeight(mostrar == menuPlataformas ? Region.USE_COMPUTED_SIZE : 0);
    menuGeneral.setMinHeight(mostrar == menuGeneral ? Region.USE_COMPUTED_SIZE : 0);

    if (resetScroll) {
      scrollMenu.setHvalue(0);
      scrollMenu.setVvalue(0);
    }
  }
}
