package controllers;

import java.io.IOException;
import java.util.ArrayList;
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

public class GenerosYPlataformasController {

	List<JuegoHome> juegosBuscados = new ArrayList<JuegoHome>();

	/**
	 * @param juegosPasados the juegosBuscados to set
	 */
	public void setJuegosBuscados(List<JuegoHome> juegosPasados) {
		this.juegosBuscados = juegosPasados;
		System.out.println(juegosPasados.toString());

		// Dividir la lista en 4 partes intentando que cada parte tenga 3 juegos
		int total = juegosPasados.size();
		int chunkSize = 3; // Cada lista tendrá 3 juegos como máximo

		// Crear las listas
		List<JuegoHome> lista1 = new ArrayList<>();
		List<JuegoHome> lista2 = new ArrayList<>();
		List<JuegoHome> lista3 = new ArrayList<>();
		List<JuegoHome> lista4 = new ArrayList<>();

		// Llenar las listas hasta el tamaño de chunkSize (3 juegos por lista)
		for (int i = 0; i < total; i++) {
			if (i < chunkSize) {
				lista1.add(juegosPasados.get(i));
			} else if (i < chunkSize * 2) {
				lista2.add(juegosPasados.get(i));
			} else if (i < chunkSize * 3) {
				lista3.add(juegosPasados.get(i));
			} else {
				lista4.add(juegosPasados.get(i));
			}
		}

		// Asignar las imágenes solo a los contenedores que tienen juegos
		if (!lista1.isEmpty()) {
			ImagenUtils.asignarImagenes(contJuegos1, lista1);
			contJuegos1.setVisible(true);
		} else {
			contJuegos1.setVisible(false);
		}

		if (!lista2.isEmpty()) {
			ImagenUtils.asignarImagenes(contJuegos2, lista2);
			contJuegos2.setVisible(true);
		} else {
			contJuegos2.setVisible(false);
		}

		if (!lista3.isEmpty()) {
			ImagenUtils.asignarImagenes(contJuegos3, lista3);
			contJuegos3.setVisible(true);
		} else {
			contJuegos3.setVisible(false);
		}

		if (!lista4.isEmpty()) {
			ImagenUtils.asignarImagenes(contJuegos4, lista4);
			contJuegos4.setVisible(true);
		} else {
			contJuegos4.setVisible(false);
		}
	}

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	private static final String PANEL_JUEGO_INFO = "/views/JuegoInfo.fxml";

	private static final String STYLES = "/styles.css";

	private static final String PANEL_HOME = "/views/Home.fxml";

	private static final String PANEL_HOME_BUSQUEDA = "/views/HomeBusqueda.fxml";

	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private Pane panelFondo;

	@FXML
	private Label btnGeneroSalida, btnGeneros, btnGenerosMenuPlataformas, btnPlataformas, btnPlataformasMenuGeneros,
			btnPlataformasSalida;
	@FXML
	private static Label titulo;

	@FXML
	private ImageView btnGenerosSalida, imgCerrar, btnMinimizar, btnCerrar, imgLupa, btnMenu, btnUser;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private HBox buscador, contBusqueda, contJuegos1, contJuegos2, contJuegos3, contJuegos4, contPlataformas,
			contPlataformas1;

	@FXML
	private StackPane contMenuPadre;

	@FXML
	private VBox menuGeneral, contInfo, menuFondito, menuGeneros, menuPlataformas;

	@FXML
	private ScrollPane scrollMenu, scrollJuegosVertical, scrollHorizontalJuego, scrollHorizontalJuego1,
			scrollHorizontalJuego2, scrollHorizontalJuego3;

	@FXML
	public void initialize() {
		System.out.println("SIZE JUEGOS VENTANA: " + juegosBuscados.size());
		scrollHorizontalJuego.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);

	}

	private static List<JuegoHome> cargarJuegos(String modo) {
		List<JuegoHome> juegosIncompletos = ExtractorAPI.getJuegosIDsYPlataformas(1, modo);

		List<JuegoHome> juegos = ExtractorAPI.obtenerPrimeraCapturaYPlataformas(juegosIncompletos);

		return juegos;
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
	void imgLupaPressed(MouseEvent event) throws IOException {
		String texto = txtBusqueda.getText();
		System.out.println(texto + " -------");
		FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_HOME_BUSQUEDA));
		Pane root = loader.load();

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegoPorNombreBarra(texto);
		System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());
		GenerosYPlataformasController controller = loader.getController();
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

	@FXML
	void imgCerrarPressed(MouseEvent event) {

		abrirNuevaVentana(PANEL_HOME);
	}

	@FXML
	void lblGeneroPressed(MouseEvent event) {

		System.out.println("BOF");
	}

}
