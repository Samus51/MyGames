package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.ExtractorAPI;

public class HomeController {

  private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

  @FXML
  private BorderPane VentanaPrincipal;

<<<<<<< HEAD
  @FXML
  private Pane panelFondo;
=======
	@FXML
	private Pane panelFondo;

	@FXML
	private Label btnGeneroSalida;
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

  @FXML
  private Label btnGeneroSalida;

  @FXML
  private Label btnGeneros;

  @FXML
  private Label btnGenerosMenuPlataformas;

  @FXML
  private ImageView btnGenerosSalida;

  @FXML
  private ImageView btnMinimizar;

  @FXML
  private ImageView btnCerrar;

  @FXML
  private Label btnPlataformas;

  @FXML
  private ImageView btnMenu;

  @FXML
  private Label btnPlataformasMenuGeneros;

  @FXML
  private Label btnPlataformasSalida;

  @FXML
  private ImageView btnUser;

  @FXML
  private HBox buscador;

  @FXML
  private HBox contBusqueda;

  @FXML
  private HBox contJuegos1;

  @FXML
  private HBox contJuegos2;

  @FXML
  private HBox contJuegos3;

  @FXML
  private HBox contJuegos4;

  @FXML
  private HBox contPlataformas;

  @FXML
  private HBox contPlataformas1;

  @FXML
  private StackPane contMenuPadre;

  @FXML
  private VBox menuGeneral;

  @FXML
  private VBox contInfo;

  @FXML
  private VBox menuFondito;

  @FXML
  private VBox menuGeneros;

  @FXML
  private VBox menuPlataformas;

  @FXML
  private ScrollPane scrollMenu;

  @FXML
  private ScrollPane scrollJuegosVertical;

  @FXML
  private ScrollPane scrollHorizontalJuego;

  @FXML
  private ScrollPane scrollHorizontalJuego1;

  @FXML
  private ScrollPane scrollHorizontalJuego2;

  @FXML
  private ScrollPane scrollHorizontalJuego3;

  private double mousePressedX;
  private double mousePressedY;
  private HBox contenedorActivo = null;

  @FXML
  public void initialize() {
    scrollHorizontalJuego.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    // Manejar el arrastre horizontal
    contJuegos1.setOnMousePressed(this::onMousePressed);
    contJuegos1.setOnMouseDragged(this::onMouseDragged);

<<<<<<< HEAD
    contJuegos2.setOnMousePressed(this::onMousePressed);
    contJuegos2.setOnMouseDragged(this::onMouseDragged);
=======
		List<String> imagenes = cargarJuegos();
		asignarImagenes(contJuegos2, imagenes);
	}
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

<<<<<<< HEAD
    contJuegos3.setOnMousePressed(this::onMousePressed);
    contJuegos3.setOnMouseDragged(this::onMouseDragged);
=======
	public static void asignarImagenes(HBox hbox, List<String> imagePaths) {
		for (int i = 0; i < hbox.getChildren().size(); i++) {
			if (hbox.getChildren().get(i) instanceof ImageView) {
				ImageView imageView = (ImageView) hbox.getChildren().get(i);
				if (i < imagePaths.size()) {
					imageView.setImage(new Image(imagePaths.get(i)));
					imageView.setPreserveRatio(false);
					imageView.setSmooth(true);
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

<<<<<<< HEAD
    contJuegos4.setOnMousePressed(this::onMousePressed);
    contJuegos4.setOnMouseDragged(this::onMouseDragged);
=======
					double cornerRadius = 20;
					Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
					clip.setArcWidth(cornerRadius);
					clip.setArcHeight(cornerRadius);
					imageView.setClip(clip);
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

<<<<<<< HEAD
    contJuegos4.setOnMousePressed(this::onMousePressed);
    contJuegos4.setOnMouseDragged(this::onMouseDragged);
=======
					imageView.setFitWidth(imageView.getFitWidth());
					imageView.setFitHeight(imageView.getFitHeight());
				}
			}
		}
	}

	private static List<String> cargarJuegos() {
		List<Integer> gameIds = ExtractorAPI.getJuegosIDs(1);
		List<String> capturas = new ArrayList<String>();
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

    scrollJuegosVertical.setOnMousePressed(this::onMousePressed);
    scrollJuegosVertical.setOnMousePressed(this::onMousePressed);

<<<<<<< HEAD
    scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
=======
		}

		return capturas;

	}
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

    scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    menuGeneros.setMinHeight(0);
    menuPlataformas.setMinHeight(0);
    menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);

    Map<String, List<String>> capturasPlataformas = cargarJuegos();
    asignarImagenes(contJuegos1, capturasPlataformas, contPlataformas);
    asignarImagenes(contJuegos2, capturasPlataformas, contPlataformas1);

  }

  // 378, 199, Contenedors Info y Plataformas
  public static void asignarImagenes(HBox hbox, Map<String, List<String>> capturaYPlataformas, HBox contPlataformeros) {
    int imageIndex = 0;

    for (Node node : hbox.getChildren()) {
      if (node instanceof VBox) {
        VBox vbox = (VBox) node;

        // Recorrer los hijos del VBox para buscar ImageViews
        for (Node vboxChild : vbox.getChildren()) {

          if (vboxChild instanceof ImageView && imageIndex < capturaYPlataformas.size()) {
            ImageView imageView = (ImageView) vboxChild;

            // Obtener la captura de pantalla correspondiente
            String captura = (String) capturaYPlataformas.keySet().toArray()[imageIndex];
            List<String> plataformas = capturaYPlataformas.get(captura);

            // Asignar imagen
            imageView.setImage(new Image(captura));
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);

            // Aplicar esquinas redondeadas
            double cornerRadius = 20;
            Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
            clip.setArcWidth(cornerRadius);
            clip.setArcHeight(cornerRadius);
            imageView.setClip(clip);

            imageView.setFitWidth(imageView.getFitWidth());
            imageView.setFitHeight(imageView.getFitHeight());

            // Mostrar plataformas (como texto adicional o alguna representación)
            if (vbox.getChildren().size() > 1 && vbox.getChildren().get(1) instanceof Label) {
              Label platformLabel = (Label) vbox.getChildren().get(1);
              platformLabel.setText(String.join(", ", plataformas));
            }

            imageIndex++;
          }
          if (vboxChild instanceof VBox) {
            for (Node vboxChild2 : vbox.getChildren()) {

            }

          }
        }
      }
    }
  }

  private static Map<String, List<String>> cargarJuegos() {
    Map<Integer, List<String>> gameIdsYPlataformas = ExtractorAPI.getJuegosIDsYPlataformas(1);

    Map<String, List<String>> capturaYPlataformas = new HashMap<>();

    for (Map.Entry<Integer, List<String>> entry : gameIdsYPlataformas.entrySet()) {
      int gameId = entry.getKey();
      List<String> plataformas = entry.getValue();

      try {
        Map<String, List<String>> capturasJuego = ExtractorAPI.obtenerPrimeraCapturaYPlataformas(gameId);
        for (Map.Entry<String, List<String>> capturaEntry : capturasJuego.entrySet()) {
          String captura = capturaEntry.getKey();
          capturaYPlataformas.put(captura, plataformas);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return capturaYPlataformas;
  }

  // Método para mover la ventana
  public void moverVentana(Stage stage) {
    // Cogemos la Pantalla
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    // Ajustamos el desplazamiento
    double desplazamientoX = 300;
    double desplazamientoY = 100;

    // Establecer la nueva posición de la ventana en el monitor
    stage.setX(bounds.getMinX() + desplazamientoX);
    stage.setY(bounds.getMinY() + desplazamientoY);
  }

//Método que se ejecuta cuando se presiona el ratón en el contenedor de juegos
  private void onMousePressed(MouseEvent event) {
    // Detectamos cuál contenedor está siendo presionado y lo asignamos a la
    // variable
    if (event.getSource() == contJuegos1) {
      contenedorActivo = contJuegos1;
    } else if (event.getSource() == contJuegos2) {
      contenedorActivo = contJuegos2;
    } else if (event.getSource() == contJuegos3) {
      contenedorActivo = contJuegos3;
    } else if (event.getSource() == contJuegos4) {
      contenedorActivo = contJuegos4;
    }

    // Guardamos la posición inicial del mouse
    if (contenedorActivo != null) {
      mousePressedX = event.getSceneX();
    }
  }

//Método que se ejecuta cuando el ratón se mueve para arrastrar el contenido
  private void onMouseDragged(MouseEvent event) {
    // Verificamos si hay un contenedor activo
    if (contenedorActivo != null) {
      double deltaX = mousePressedX - event.getSceneX();
      mousePressedX = event.getSceneX();

      // Calculamos el desplazamiento solo para el contenedor activo
      ScrollPane scrollPaneActivo = null;
      if (contenedorActivo == contJuegos1) {
        scrollPaneActivo = scrollHorizontalJuego;
      }
      if (contenedorActivo == contJuegos2) {
        scrollPaneActivo = scrollHorizontalJuego1;
      }
      if (contenedorActivo == contJuegos3) {
        scrollPaneActivo = scrollHorizontalJuego2;
      }
      if (contenedorActivo == contJuegos4) {
        scrollPaneActivo = scrollHorizontalJuego3;
      }

      if (scrollPaneActivo != null) {
        // Calculamos el nuevo valor de desplazamiento en función del movimiento del
        // ratón
        double newHvalue = scrollPaneActivo.getHvalue() + deltaX * 2 / contenedorActivo.getWidth();
        // Aseguramos que el valor se mantenga dentro de los límites (0 a 1)
        scrollPaneActivo.setHvalue(Math.max(0, Math.min(1, newHvalue)));
        scrollPaneActivo.setVvalue(0);

      }
    }
  }

  // Método para mover la ventana
  public void moverVentanaMenu(Stage stage) {
    // Obtén el monitor que quieres usar
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

<<<<<<< HEAD
    // Ajusta el desplazamiento según lo desees
    double desplazamientoX = 300;
    double desplazamientoY = 100;
=======
		// Ajusta el desplazamiento según lo desees
		double desplazamientoX = 300;
		double desplazamientoY = 100;
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

    // Establecer la nueva posición de la ventana en el monitor
    stage.setX(bounds.getMinX() + desplazamientoX);
    stage.setY(bounds.getMinY() + desplazamientoY);
  }

  private void onMousePressedMenu(MouseEvent event) {
    mousePressedY = event.getSceneY();
  }

  private void onMouseDraggedMenu(MouseEvent event) {
    double deltaY = mousePressedY - event.getSceneY();
    mousePressedY = event.getSceneY();

    // Desplazamos el contenido del ScrollPane
    double newVvalue = scrollMenu.getVvalue() + deltaY * 8 / contMenuPadre.getHeight();
    scrollMenu.setVvalue(Math.max(0, Math.min(1, newVvalue)));
  }

  @FXML
  void btnGenerosMenuPlataformasPressed(MouseEvent event) {
    menuGeneral.setVisible(false);
    menuGeneros.setVisible(true);
    menuPlataformas.setVisible(false);

    menuGeneros.setMinHeight(Region.USE_COMPUTED_SIZE);
    menuPlataformas.setMinHeight(0);
    menuGeneral.setMinHeight(0);

    scrollMenu.setHvalue(0);
    scrollMenu.setVvalue(0);
  }

  @FXML
  void btnGenerosPressed(MouseEvent event) {
    menuGeneral.setVisible(false);
    menuGeneros.setVisible(true);
    menuPlataformas.setVisible(false);

    menuGeneros.setMinHeight(Region.USE_COMPUTED_SIZE);
    menuPlataformas.setMinHeight(0);
    menuGeneral.setMinHeight(0);
  }

  @FXML
  void btnGenerosSalidaPressed(MouseEvent event) {
    menuGeneral.setVisible(true);
    menuGeneros.setVisible(false);

    menuGeneros.setMinHeight(0);
    menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);
  }

  @FXML
  void btnPlataformasPressed(MouseEvent event) {
    menuGeneral.setVisible(false);
    menuPlataformas.setVisible(true);
    menuGeneros.setVisible(false);

    menuGeneros.setMinHeight(0);
    menuPlataformas.setMinHeight(Region.USE_COMPUTED_SIZE);
    menuGeneral.setMinHeight(0);
  }

  @FXML
  void btnPlataformasSalidaPressed(MouseEvent event) {
    menuGeneral.setVisible(true);
    menuPlataformas.setVisible(false);
    menuGeneros.setVisible(false);

    menuGeneros.setMinHeight(0);
    menuPlataformas.setMinHeight(0);
    menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);
  }

  @FXML
  void btnPlataformasMenuGenerosPressed(MouseEvent event) {
    menuGeneral.setVisible(false);
    menuGeneros.setVisible(false);
    menuPlataformas.setVisible(true);

    menuGeneros.setMinHeight(0);
    menuPlataformas.setMinHeight(Region.USE_COMPUTED_SIZE);
    menuGeneral.setMinHeight(0);

    scrollMenu.setHvalue(0);
    scrollMenu.setVvalue(0);
  }

<<<<<<< HEAD
  @FXML
  void btnMenuPressed(MouseEvent event) {
    if (menuGeneral.isVisible()) {
      // Ocultar menú y restaurar estilos originales (transparente)
      menuGeneral.setVisible(false);
      menuFondito.setStyle("-fx-background-image: none;" + // Sin imagen de fondo
          "-fx-background-color: transparent;" // Color de fondo transparente
      );
    } else {
      // Mostrar menú y aplicar estilo con opacidad
      menuGeneral.setVisible(true);
      menuFondito.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, "
          + "rgba(0, 0, 255, 0.3), rgba(0, 0, 0, 0.3));");
    }
=======
	@FXML
	void btnMenuPressed(MouseEvent event) {
	    if (menuGeneral.isVisible()) {
	        // Ocultar menú y restaurar estilos originales (transparente)
	        menuGeneral.setVisible(false);
	        contMenuPadre.setStyle(
	            "-fx-background-image: none;" + // Sin imagen de fondo
	            "-fx-background-color: transparent;" // Color de fondo transparente
	        );
	    } else {
	        // Mostrar menú y aplicar estilo con opacidad
	        menuGeneral.setVisible(true);
	        contMenuPadre.setStyle(
	            "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, " +
	            "rgba(0, 0, 255, 0.3), rgba(0, 0, 0, 0.3));"
	        );
	    }
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

<<<<<<< HEAD
    // Asegúrate de que otros menús estén ocultos
    menuGeneros.setVisible(false);
    menuPlataformas.setVisible(false);
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
=======
	    // Asegúrate de que otros menús estén ocultos
	    menuGeneros.setVisible(false);
	    menuPlataformas.setVisible(false);
	}
>>>>>>> branch 'main' of https://github.com/Samus51/MyGames.git

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
  void btnUserPressed(MouseEvent event) {
    abrirNuevaVentana(PANEL_USER);
  }

  @FXML
  void btnMinimizarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.setIconified(true);
  }

  @FXML
  void btnCerrarPressed(MouseEvent event) {
    Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
    ventanaPrincipal.close();
  }

}