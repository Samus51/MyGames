package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
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
import javafx.stage.Stage;
import models.JuegoHome;
import utils.ExtractorAPI;
import utils.ExtractorApi2;
import utils.ImagenUtils;
import utils.VentanaUtil;

public class GenerosController {
	private static String ultimoGeneroBuscado = "";

	/**
	 * @return the ultimoGeneroBuscado
	 */
	public static String getUltimoGeneroBuscado() {
		return ultimoGeneroBuscado;
	}

	/**
	 * @param ultimoGeneroBuscado the ultimoGeneroBuscado to set
	 */
	public static void setUltimoGeneroBuscado(String ultimoGeneroBuscado) {

		if (ultimoGeneroBuscado.isBlank()) {
			return;
		}

		GenerosController.ultimoGeneroBuscado = ultimoGeneroBuscado;
	}

	List<JuegoHome> juegosBuscados = new ArrayList<JuegoHome>();

	/**
	 * @param juegosPasados the juegosBuscados to set
	 */
	public void setJuegosBuscados(List<JuegoHome> juegosPasados) {
		System.out.println("Ultimo Genero Buscado: " + ultimoGeneroBuscado);
		this.juegosBuscados = juegosPasados;
		System.out.println(juegosPasados.toString());

		// Dividir la lista en 4 partes intentando que cada parte tenga 3 juegos
		int total = juegosPasados.size();
		int chunkSize = 3; 

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
	private static final String PANEL_PLATAFORMAS = "/views/Plataformas.fxml";
	private static final String PANEL_GENEROS = "/views/Generos.fxml";
	private static final String PANEL_RECOMENDADOS = "/views/Recomendados.fxml";
	private static final String PANEL_CREADOS_USUARIO = "/views/CreadosUsuario.fxml";
	private static final String PANEL_BIBLIIOTECA = "/views/Biblioteca.fxml";
	private static final String PANEL_DESEADOS = "/views/Whishlist.fxml";
	private static final String PANEL_ADD_JUEGOS = "/views/JuegoAnadir.fxml";

	private static int indice = 0;

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
	private ImageView btnGenerosSalida, imgCerrar, btnMinimizar, btnCerrar, imgLupa, btnMenu, btnUser, imgCargarJuegos,
			imgCargarJuegosAtras;

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

						// Aquí se pasa el usuario al controlador
						VentanaUtil.abrirVentana(PANEL_JUEGO_INFO, "Juego Info", STYLES, controller -> {
							JuegoInfoController juegoInfoController = (JuegoInfoController) controller;
							juegoInfoController.setUsuario(HomeController.getUsuario());
							juegoInfoController.setTituloJuego(tituloJuego);
						}, event);
					}
				}
			}
		}
	}

	@FXML
	void imgLupaPressed(MouseEvent event) throws IOException {
		String texto = txtBusqueda.getText();
		System.out.println(texto + " -------");

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegoPorNombreBarra(texto, 0);
		System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_HOME_BUSQUEDA, "Búsqueda", STYLES, controller -> {
			((HomeBusquedaController) controller).setJuegosBuscados(juegosCargar);
		}, event);

	}

	@FXML
	void imgCerrarPressed(MouseEvent event) throws IOException {

		VentanaUtil.abrirVentana(PANEL_HOME, "Home", STYLES, null, event);
	}

	@FXML
	void lblGenerosPressed(MouseEvent event) throws IOException {
		Label labelClicado = (Label) event.getSource();
		String genero = labelClicado.getText().toLowerCase().replace(" ", "-");
		System.out.println(genero + " -------");

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegosPorGenero(genero, 0);
		System.out.println("JUEGOS CARGADOS: " + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_GENEROS, "Géneros", STYLES, controller -> {
			((GenerosController) controller).setJuegosBuscados(juegosCargar);
		}, event);
	}

	@FXML
	void lblPlataformasPressed(MouseEvent event) throws IOException {
		System.out.println("BUSQUEDA DE PLATAFORMAS");
		Label labelClicado = (Label) event.getSource();
		String plataformaPadre = labelClicado.getText().toLowerCase().replace(" ", "-");
		System.out.println(plataformaPadre + " -------");

		List<JuegoHome> juegosCargar = ExtractorApi2.buscarJuegosPorPlataformaPadre(plataformaPadre, 0);
		System.out.println("JUEGOS CARGADOS: " + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_PLATAFORMAS, "Plataformas", STYLES, controller -> {
			PlataformasController plataformasController = (PlataformasController) controller;
			PlataformasController.setUltimaPlataformaBuscada(plataformaPadre);
			plataformasController.setJuegosBuscados(juegosCargar);
		}, event);

	}

	@FXML
	void imgCargarJuegosPressed(MouseEvent event) throws IOException {

		indice += 12;
		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegosPorGenero(ultimoGeneroBuscado, indice);
		System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());

		if (juegosCargar.size() == 0) {
			return;
		}

		VentanaUtil.abrirVentana(PANEL_GENEROS, "Géneros", STYLES, controller -> {
			GenerosController genController = (GenerosController) controller;
			GenerosController.setUltimoGeneroBuscado(ultimoGeneroBuscado);
			genController.setJuegosBuscados(juegosCargar);
		}, event);
		System.out.println(indice);

	}

	@FXML
	void imgCargarJuegoAtrasPressed(MouseEvent event) throws IOException {

		System.out.println(indice);

		if (indice <= 0) {
			System.out.println(indice);
			return;
		}

		indice -= 12;

		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegosPorGenero(ultimoGeneroBuscado, indice);
		System.out.println("JUEGOS CARGADOS:" + juegosCargar.size());

		VentanaUtil.abrirVentana(PANEL_PLATAFORMAS, "Géneros", STYLES, controller -> {
			((GenerosController) controller).setJuegosBuscados(juegosCargar);
		}, event);
	}

	@FXML
	void recomendadosPressed(MouseEvent event) throws IOException {
		System.out.println("Recomendados");
		VentanaUtil.abrirVentana(PANEL_RECOMENDADOS, "Recomendados", STYLES, null, event);

	}

	@FXML
	void creadosPorUsuarioPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_CREADOS_USUARIO, "Recomendados", STYLES, null, event);

	}

	@FXML
	void listaDeseadosPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_DESEADOS, "Añadir Juego", STYLES, null, event);
	}

	@FXML
	void anadirJuegoPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_ADD_JUEGOS, "Añadir Juego", STYLES, null, event);

	}

	@FXML
	void tusJuegosPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_BIBLIIOTECA, "Biblioteca", STYLES, null, event);

	}

}
