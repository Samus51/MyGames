package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.JuegoBD;
import models.JuegoHome;
import utils.ExtractorAPI;
import utils.ExtractorApi2;
import utils.ImagenBDUtils;
import utils.VentanaUtil;

public class CreadosUsuarioController {

	private static final String PANEL_JUEGO_INFO_BD = "/views/JuegoInfoBD.fxml";

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

	private static final String PANEL_ADD_JUEGO = "/views/JuegoAnadir.fxml";

	private int currentPage = 0; // Página actual
	private final int juegosPorPagina = 12; // Número de juegos por página
	private int totalJuegos; // Total de juegos disponibles
	private List<JuegoBD> todosLosJuegos; // Lista de todos los juegos cargados

	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private ImageView btnCerrar, btnMenu, btnMinimizar, btnUser, imgCargarJuegosAdelante, imgCargarJuegosAtras;
	@FXML
	private Label btnGeneroSalida, btnGeneros, btnGenerosMenuPlataformas, btnPlataformas, btnPlataformasMenuGeneros,
			btnPlataformasSalida;
	@FXML
	private HBox buscador, contBusqueda, contJuegos1, contJuegos2, contJuegos3, contJuegos4, contPlataformas12,
			contPlataformas121, contPlataformas13, contPlataformas14, contPlataformas22, contPlataformas221,
			contPlataformas23, contPlataformas24, contPlataformas5, contPlataformas51, contPlataformas6,
			contPlataformas7;
	@FXML
	private VBox contInfo12, contInfo121, contInfo13, contInfo14, contInfo22, contInfo221, contInfo23, contInfo24,
			contInfo5, contInfo51, contInfo6, contInfo7, juegoSolo1, juegoSolo10, juegoSolo11, juegoSolo12, juegoSolo2,
			juegoSolo3, juegoSolo4, juegoSolo5, juegoSolo6, juegoSolo7, juegoSolo8, juegoSolo9, menuFondito,
			menuGeneral, menuGeneros, menuPlataformas;
	@FXML
	private StackPane contMenuPadre;
	@FXML
	private ScrollPane scrollHorizontalJuego, scrollHorizontalJuego1, scrollHorizontalJuego2, scrollHorizontalJuego3,
			scrollJuegosVertical, scrollMenu;
	private Map<VBox, JuegoBD> vboxToJuegoMap;

	public void initialize() {
		// Cargar los juegos desde la base de datos
		todosLosJuegos = cargarJuegos();
		totalJuegos = todosLosJuegos.size();

		// Cargar los juegos en la primera página
		cargarJuegosEnPantalla(currentPage);
	}

	@FXML
	void btnCerrarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.close();
	}

	@FXML
	void btnMenuPressed(MouseEvent event) {
		menuGeneral.setVisible(!menuGeneral.isVisible());
		menuGeneros.setVisible(false);
		menuPlataformas.setVisible(false);
	}

	@FXML
	void btnMinimizarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.setIconified(true);
	}

	@FXML
	void btnUserPressed(MouseEvent event) {
		try {
			VentanaUtil.abrirVentana(PANEL_USER, "Usuario", STYLES, null, event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void imgCargarJuegosAtrasPressed(MouseEvent event) {
		if (currentPage > 0) {
			currentPage--;
			cargarJuegosEnPantalla(currentPage);
		}
	}

	@FXML
	void imgCargarJuegosAdelantePressed(MouseEvent event) {
		if ((currentPage + 1) * juegosPorPagina < totalJuegos) {
			currentPage++;
			cargarJuegosEnPantalla(currentPage);
		}
	}

	@FXML
	void juegoSoloPressed(MouseEvent event) {
		VBox juegoSeleccionadoVBox = (VBox) event.getSource();

		// Obtener el juego asociado al VBox que se ha clickeado
		JuegoBD juegoSeleccionado = vboxToJuegoMap.get(juegoSeleccionadoVBox);

		if (juegoSeleccionado != null) {
			// Abrir la ventana de información del juego (panel de información)
			try {
				VentanaUtil.abrirVentana(PANEL_JUEGO_INFO_BD, "Información del Juego", STYLES, controller -> {
					if (controller instanceof JuegoInfoBDController) {
						JuegoInfoBDController juegoInfoController = (JuegoInfoBDController) controller;
						// Establecer el juego seleccionado en el controlador de la nueva ventana
						juegoInfoController.setJuegoSeleccionado(juegoSeleccionado);
					}
				}, event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<JuegoBD> cargarJuegos() {
		List<JuegoBD> juegos = new ArrayList<>();
		String query = "SELECT id_juego, titulo, plataformas, generos, tiempo_jugado, descripcion, imagen_principal, "
				+ "imagen_secundaria, imagen_tercera, imagen_cuarta, imagen_quinta, pegi, fecha_lanzamiento, creado_por_usuario, desarrolladores "
				+ "FROM juegos WHERE creado_por_usuario = 1";

		try (Connection conn = Conector.conectar();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int idJuego = rs.getInt("id_juego");
				String titulo = rs.getString("titulo");
				String plataformasStr = rs.getString("plataformas");
				String generos = rs.getString("generos");
				String descripcion = rs.getString("descripcion");
				String fechaLanzamiento = rs.getString("fecha_lanzamiento");
				int creadoPorUsuario = rs.getInt("creado_por_usuario");

				// Manejo de tiempos jugados (puede ser nulo, en tal caso asignamos un valor por
				// defecto)
				int tiempoJugado = rs.getInt("tiempo_jugado");

				// Manejo de PEGI (puede ser nulo)
				String pegi = rs.getString("pegi");
				if (rs.wasNull()) {
					pegi = "-1";
				}

				// Obtener los desarrolladores (nuevamente puede ser nulo)
				String desarrolladores = rs.getString("desarrolladores");
				if (desarrolladores == null) {
					desarrolladores = "Desarrollador desconocido";
				}

				List<String> plataformas = parsePlataformas(plataformasStr);

				// Obtener las imágenes como bytes
				byte[] imagenPrincipal = rs.getBytes("imagen_principal");
				byte[] imagenSecundaria = rs.getBytes("imagen_secundaria");
				byte[] imagenTercera = rs.getBytes("imagen_tercera");
				byte[] imagenCuarta = rs.getBytes("imagen_cuarta");
				byte[] imagenQuinta = rs.getBytes("imagen_quinta");

				// Crear una nueva instancia de JuegoBD con los datos obtenidos
				JuegoBD juego = new JuegoBD(idJuego, titulo, descripcion, fechaLanzamiento, creadoPorUsuario == 1,
						tiempoJugado, desarrolladores, imagenPrincipal, imagenSecundaria, imagenTercera, imagenCuarta,
						imagenQuinta, pegi, generos, plataformas);

				// Agregar el juego a la lista
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
			String[] plataformasArray = plataformasStr.split(",");
			for (String plataforma : plataformasArray) {
				plataformas.add(plataforma.trim());
			}
		}
		return plataformas;
	}

	private void cargarJuegosEnPantalla(int page) {
		// Obtener un subconjunto de juegos para la página actual
		int startIndex = page * juegosPorPagina;
		int endIndex = Math.min(startIndex + juegosPorPagina, totalJuegos);
		List<JuegoBD> juegosPagina = todosLosJuegos.subList(startIndex, endIndex);

		// Crear un arreglo de VBox para los juegos
		VBox[] juegosVbox = { juegoSolo1, juegoSolo2, juegoSolo3, juegoSolo4, juegoSolo5, juegoSolo6, juegoSolo7,
				juegoSolo8, juegoSolo9, juegoSolo10, juegoSolo11, juegoSolo12 };

		// Crear un mapa para asociar cada VBox con un juego
		Map<VBox, JuegoBD> vboxToJuegoMap = new HashMap<>();

		// Asignar las imágenes a los VBox y asociarlos con los juegos
		for (int i = 0; i < juegosPagina.size(); i++) {
			JuegoBD juego = juegosPagina.get(i);
			ImagenBDUtils.asignarImagenes(juegosVbox[i], List.of(juego));
			juegosVbox[i].setVisible(true);

			// Asociar el VBox con el juego
			vboxToJuegoMap.put(juegosVbox[i], juego);
		}

		// Ocultar los VBox restantes si hay menos de 12 juegos en la página
		for (int i = juegosPagina.size(); i < juegosVbox.length; i++) {
			juegosVbox[i].setVisible(false);
		}

		// Asignar el mapa de VBox a un campo para usarlo en el evento de clic
		this.vboxToJuegoMap = vboxToJuegoMap;
	}

	@FXML
	void flechaAtrasPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_HOME, "Inicio", STYLES, null, event);
	}
}
