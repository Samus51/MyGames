package controllers;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.JuegoHome;
import utils.ExtractorAPI;
import utils.ExtractorApi2;
import utils.ImagenUtils;
import utils.VentanaUtil;

public class HomeController {

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	private static final String PANEL_JUEGO_INFO = "/views/JuegoInfo.fxml";

	private static final String STYLES = "/styles.css";

	private static final String PANEL_HOME_BUSQUEDA = "/views/HomeBusqueda.fxml";

	private static final String PANEL_GENEROS = "/views/Generos.fxml";

	private static final String PANEL_PLATAFORMAS = "/views/Plataformas.fxml";

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
	void btnPlataformasPressed(MouseEvent event) {
		cambiarMenu(menuPlataformas, menuGeneros, menuGeneral, false);
	}

	@FXML
	void btnPlataformasSalidaPressed(MouseEvent event) {
		cambiarMenu(menuGeneral, menuPlataformas, menuGeneros, false);
	}

	@FXML
	void btnPlataformasMenuGenerosPressed(MouseEvent event) {
		cambiarMenu(menuPlataformas, menuGeneros, menuGeneral, true);
	}

	@FXML
	void btnMenuPressed(MouseEvent event) {
		menuGeneral.setVisible(!menuGeneral.isVisible());
		menuGeneros.setVisible(false);
		menuPlataformas.setVisible(false);
	}

	@FXML
	void btnUserPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_USER, "Usuario", STYLES, null, event);
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

	@FXML
	void juegoSoloPressed(MouseEvent event) throws IOException {
		VBox vbox = (VBox) event.getSource();

		for (Node childNode : vbox.getChildren()) {
			if (childNode instanceof VBox innerVBox) {
				for (Node innerNode : innerVBox.getChildren()) {
					if (innerNode instanceof Label tituloLabel) {
						String tituloJuego = tituloLabel.getText();
						System.out.println("Título del juego: " + tituloJuego);

						VentanaUtil.abrirVentana(PANEL_JUEGO_INFO, "Juego Info", STYLES, controller -> {
							((JuegoInfoController) controller).setTituloJuego(tituloJuego);
						}, event);
					}
				}
			}
		}
	}

	@FXML
	void lblAddJuegoPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_ADD_JUEGO, "Añadir Juego", STYLES, null, event);
	}

	@FXML
	void imgLupaPressed(MouseEvent event) throws IOException {
		String texto = txtBusqueda.getText();
		System.out.println(texto + " -------");

		// Obtén el controlador de la ventana y pasa el texto
		VentanaUtil.abrirVentana(PANEL_HOME_BUSQUEDA, "Búsqueda", STYLES, controller -> {
			HomeBusquedaController homeController = (HomeBusquedaController) controller;
			HomeBusquedaController.setUltimoTextoBusqueda(texto);

			List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegoPorNombreBarra(texto, 0);
			System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());
			homeController.setJuegosBuscados(juegosCargar);
		}, event);
	}

	@FXML
	void lblGenerosPressed(MouseEvent event) throws IOException {
		Label labelClicado = (Label) event.getSource();
		String genero = labelClicado.getText().toLowerCase().replace(" ", "-");
		System.out.println(genero + " -------");

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegosPorGenero(genero, 0);
		System.out.println("JUEGOS CARGADOS: " + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_GENEROS, "Géneros", STYLES, controller -> {
			GenerosController genController = (GenerosController) controller;
			GenerosController.setUltimoGeneroBuscado(genero);
			genController.setJuegosBuscados(juegosCargar);
		}, event);

	}

	@FXML
	void lblPlataformasPressed(MouseEvent event) throws IOException {
		System.out.println("Busqueda de plataformas");
		Label labelClicado = (Label) event.getSource();
		String plataformaPadre = labelClicado.getText().toLowerCase().replace(" ", "-");
		System.out.println(plataformaPadre + " -------");

		List<JuegoHome> juegosCargar = ExtractorApi2.buscarJuegosPorPlataformaPadre(plataformaPadre, 0);
		System.out.println("JUEGOS CARGADOS: " + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_PLATAFORMAS, "Plataformas", STYLES, controller -> {
			PlataformasController platController = (PlataformasController) controller;
			PlataformasController.setUltimaPlataformaBuscada(plataformaPadre);
			platController.setJuegosBuscados(juegosCargar);
		}, event);
		System.out.println(PlataformasController.getUltimaPlataformaBuscada());

	}

}
