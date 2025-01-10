package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

public class HomeController {

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private TextField barraBusqueda;

  @FXML
  private ImageView btnMenu;

  @FXML
  private ImageView btnUser;

  @FXML
  private HBox buscador;

  @FXML
  private HBox contJuegos1;

  @FXML
  private ScrollPane scrollpane;

  private double mousePressedX;

  @FXML
  public void initialize() {
    // Deshabilitar barras de desplazamiento
    scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // Manejar el arrastre horizontal
    contJuegos1.setOnMousePressed(this::onMousePressed);
    contJuegos1.setOnMouseDragged(this::onMouseDragged);
  }

  // Método para mover la ventana
  public void moverVentana(Stage stage) {
    // Obtén el monitor que quieres usar
    Screen screen = Screen.getPrimary(); 
    Rectangle2D bounds = screen.getVisualBounds();

    // Ajusta el desplazamiento según lo desees
    double desplazamientoX = 300;
    double desplazamientoY = 100;

    // Establecer la nueva posición de la ventana en el monitor
    stage.setX(bounds.getMinX() + desplazamientoX);
    stage.setY(bounds.getMinY() + desplazamientoY);
  }

  private void onMousePressed(MouseEvent event) {
    mousePressedX = event.getSceneX(); 
  }

  private void onMouseDragged(MouseEvent event) {
    double deltaX = mousePressedX - event.getSceneX();
    mousePressedX = event.getSceneX();

    // Desplazamos el contenido del ScrollPane
    double newHvalue = scrollpane.getHvalue() + deltaX * 8 / contJuegos1.getWidth();
    scrollpane.setHvalue(Math.max(0, Math.min(1, newHvalue))); 
  }

  // Método para inicializar el Stage, asegurando que la ventana se mueva
  public void inicializar(Stage stage) {
    // Mover la ventana cuando se inicializa el controlador
    moverVentana(stage);
  }
}