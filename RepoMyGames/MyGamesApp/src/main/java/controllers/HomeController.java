package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	@FXML
	private BorderPane VentanaPrincipal;

	@FXML
	private Label btnGeneroSalida;

	@FXML
	private Label btnGeneros;

	@FXML
	private Label btnGenerosMenuPlataformas;

	@FXML
	private ImageView btnGenerosSalida;

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
	private StackPane contMenuPadre;

	@FXML
	private VBox menuGeneral;

	@FXML
	private VBox menuGeneros;

	@FXML
	private VBox menuPlataformas;

	@FXML
	private ScrollPane scrollMenu;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private ScrollPane scrollpane1;

	@FXML
	private ScrollPane scrollpane2;

	@FXML
	private ScrollPane scrollpane3;

	private double mousePressedX;
	private double mousePressedY;
	private HBox contenedorActivo = null;

	@FXML
	public void initialize() {
		scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollpane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollpane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollpane3.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// Manejar el arrastre horizontal
		contJuegos1.setOnMousePressed(this::onMousePressed);
		contJuegos1.setOnMouseDragged(this::onMouseDragged);

		contJuegos2.setOnMousePressed(this::onMousePressed);
		contJuegos2.setOnMouseDragged(this::onMouseDragged);

		contJuegos3.setOnMousePressed(this::onMousePressed);
		contJuegos3.setOnMouseDragged(this::onMouseDragged);

		contJuegos4.setOnMousePressed(this::onMousePressed);
		contJuegos4.setOnMouseDragged(this::onMouseDragged);

		scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);
	}

	// Método para mover la ventana
	public void moverVentana(Stage stage) {
		// Cogemos la Pantalla
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		// Ajustamos el desplazamiento
		double desplazamientoX = 300; // Distancia horizontal
		double desplazamientoY = 100; // Distancia vertical

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
				scrollPaneActivo = scrollpane;
			} else if (contenedorActivo == contJuegos2) {
				scrollPaneActivo = scrollpane1;
			} else if (contenedorActivo == contJuegos3) {
				scrollPaneActivo = scrollpane2;
			} else if (contenedorActivo == contJuegos4) {
				scrollPaneActivo = scrollpane3;
			}

			if (scrollPaneActivo != null) {
				// Calculamos el nuevo valor de desplazamiento en función del movimiento del
				// ratón
				double newHvalue = scrollPaneActivo.getHvalue() + deltaX * 8 / contenedorActivo.getWidth();
				// Aseguramos que el valor se mantenga dentro de los límites (0 a 1)
				scrollPaneActivo.setHvalue(Math.max(0, Math.min(1, newHvalue)));
				scrollPaneActivo.setVvalue(0); // Establecemos el valor vertical en 0 para restringirlo.

			}
		}
	}

	// Método para mover la ventana
	public void moverVentanaMenu(Stage stage) {
		// Obtén el monitor que quieres usar
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		// Ajusta el desplazamiento según lo desees
		double desplazamientoX = 300; // Distancia horizontal
		double desplazamientoY = 100; // Distancia vertical

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

	@FXML
	void btnMenuPressed(MouseEvent event) {

		if (menuGeneral.isVisible()) {
			menuGeneral.setVisible(false);
		} else {
			menuGeneral.setVisible(true);
		}

		if (menuGeneros.isVisible()) {
			menuGeneros.setVisible(false);
			menuGeneral.setVisible(false);

		}
		if (menuPlataformas.isVisible()) {
			menuPlataformas.setVisible(false);
			menuGeneral.setVisible(false);

		}

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
	void btnUserPressed(MouseEvent event) {
		abrirNuevaVentana(PANEL_USER);
	}
}