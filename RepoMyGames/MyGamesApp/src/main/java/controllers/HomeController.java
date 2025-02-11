package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import models.JuegoHome;
import utils.ExtractorAPI;
import utils.ImagenUtils;

public class HomeController {

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	private static final String PANEL_JUEGO_INFO = "/views/JuegoInfo.fxml";

	private static final String STYLES = "/styles.css";

	private static final String PANEL_HOME_BUSQUEDA = "/views/HomeBusqueda.fxml";

	private static final String PANEL_ADD_JUEGO = "/views/JuegoAnadir.fxml";

//Contenedores
	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private Pane panelFondo;
	@FXML
	private StackPane contMenuPadre;
	@FXML
	private VBox menuGeneral, contInfo, menuFondito, menuGeneros, menuPlataformas;

//Labels
	@FXML
	private Label btnGeneroSalida, lblAddJuego, btnGeneros, btnGenerosMenuPlataformas, btnPlataformas,
			btnPlataformasMenuGeneros, btnPlataformasSalida;
	@FXML
	private static Label titulo;

//TextField
	@FXML
	private TextField txtBusqueda;

//ImageViews
	@FXML
	private ImageView btnGenerosSalida, btnMinimizar, btnCerrar, imgLupa, btnMenu, btnUser;

//HBoxes
	@FXML
	private HBox buscador, contBusqueda, contJuegos1, contJuegos2, contJuegos3, contJuegos4, contPlataformas,
			contPlataformas1;

//ScrollPanes
	@FXML
	private ScrollPane scrollMenu, scrollJuegosVertical, scrollHorizontalJuego, scrollHorizontalJuego1,
			scrollHorizontalJuego2, scrollHorizontalJuego3;

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

		contJuegos2.setOnMousePressed(this::onMousePressed);
		contJuegos2.setOnMouseDragged(this::onMouseDragged);

		contJuegos3.setOnMousePressed(this::onMousePressed);
		contJuegos3.setOnMouseDragged(this::onMouseDragged);

		contJuegos4.setOnMousePressed(this::onMousePressed);
		contJuegos4.setOnMouseDragged(this::onMouseDragged);

		contJuegos4.setOnMousePressed(this::onMousePressed);
		contJuegos4.setOnMouseDragged(this::onMouseDragged);

		scrollJuegosVertical.setOnMousePressed(this::onMousePressed);
		scrollJuegosVertical.setOnMousePressed(this::onMousePressed);

		scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);

		List<JuegoHome> capturasPlataformas = cargarJuegos("Popular");
		List<JuegoHome> capturasPlataformas2 = cargarJuegos("Nuevos");

		ImagenUtils.asignarImagenes(contJuegos1, capturasPlataformas);
		ImagenUtils.asignarImagenes(contJuegos2, capturasPlataformas2);

	}

	private static List<JuegoHome> cargarJuegos(String modo) {
		List<JuegoHome> juegosIncompletos = ExtractorAPI.getJuegosIDsYPlataformas(1, modo);

		List<JuegoHome> juegos = ExtractorAPI.obtenerPrimeraCapturaYPlataformas(juegosIncompletos);

		return juegos;
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

		// Ajusta el desplazamiento según lo desees
		double desplazamientoX = 300;
		double desplazamientoY = 100;

		// Establecer la nueva posición de la ventana en el monitor
		stage.setX(bounds.getMinX() + desplazamientoX);
		stage.setY(bounds.getMinY() + desplazamientoY);
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
	void lblGenerosPressed(MouseEvent event) {
		System.out.println("HEL");
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
			// Ocultar menú y restaurar estilos originales (transparente)
			menuGeneral.setVisible(false);
			// );
		} else {
			// Mostrar menú y aplicar estilo con opacidad
			menuGeneral.setVisible(true);
		}

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

	private Stage obtenerVentana(MouseEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	@FXML
	void juegoSoloPressed(MouseEvent event) throws IOException {
		VBox vbox = (VBox) event.getSource();

		for (Node childNode : vbox.getChildren()) {
			if (childNode instanceof VBox) {
				VBox innerVBox = (VBox) childNode;

				for (Node innerNode : innerVBox.getChildren()) {
					if (innerNode instanceof Label) {
						Label tituloLabel = (Label) innerNode;

						FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_JUEGO_INFO));
						Pane root = loader.load();

						// Verifica que el controlador esté cargado
						JuegoInfoController controller = loader.getController();
						System.out.println("Controlador cargado: " + controller);

						// Ahora pasa el título al controlador
						String tituloJuego = tituloLabel.getText();
						System.out.println("Título del juego: " + tituloJuego);
						controller.setTituloJuego(tituloJuego);

						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

						Stage nuevaVentana = new Stage();
						nuevaVentana.setTitle("Juego Info");
						nuevaVentana.setScene(scene);
						nuevaVentana.setMaximized(true);
						nuevaVentana.setResizable(false);
						nuevaVentana.initStyle(StageStyle.UNDECORATED);

						nuevaVentana.show();
						obtenerVentana(event).close();
						System.out.println("Ventana cerrada");
					}
				}
			}
		}
	}

	@FXML
	void lblAddJuegoPressed(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_ADD_JUEGO));
		Pane root = loader.load();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

		Stage nuevaVentana = new Stage();
		nuevaVentana.setTitle("Busqueda");
		nuevaVentana.setScene(scene);
		nuevaVentana.setMaximized(true);
		nuevaVentana.setResizable(false);
		nuevaVentana.initStyle(StageStyle.UNDECORATED);

		nuevaVentana.show();
		obtenerVentana(event).close();
		System.out.println("Ventana cerrada");

	}

	@FXML
	void imgLupaPressed(MouseEvent event) throws IOException {
		System.out.println("HOLAAA");
		String texto = txtBusqueda.getText();
		System.out.println(texto + " -------");
		FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_HOME_BUSQUEDA));
		Pane root = loader.load();

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegoPorNombreBarra(texto);
		System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());
		HomeBusquedaController controller = loader.getController();
		System.out.println("Controlador cargado: " + controller);

		// Ahora pasa el título al controlador
		controller.setJuegosBuscados(juegosCargar);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

		Stage nuevaVentana = new Stage();
		nuevaVentana.setTitle("Busqueda");
		nuevaVentana.setScene(scene);
		nuevaVentana.setMaximized(true);
		nuevaVentana.setResizable(false);
		nuevaVentana.initStyle(StageStyle.UNDECORATED);

		nuevaVentana.show();
		obtenerVentana(event).close();
		System.out.println("Ventana cerrada");

	}

}
