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
import utils.ImagenBDUtils;
import utils.VentanaUtil;

public class CreadosUsuarioController {

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";
	private static final String PANEL_JUEGO_INFO_BD = "/views/JuegoInfoBD.fxml";
	private static final String STYLES = "/styles.css";

	private int currentPage = 0; // Página actual
	private final int juegosPorPagina = 12; // Número de juegos por página
	private int totalJuegos; // Total de juegos disponibles
	private List<JuegoBD> todosLosJuegos; // Lista de todos los juegos cargados

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
	private HBox buscador;

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
	private ImageView imgCargarJuegosAdelante;

	@FXML
	private ImageView imgCargarJuegosAtras;

	@FXML
	private VBox juegoSolo1;

	@FXML
	private VBox juegoSolo10;

	@FXML
	private VBox juegoSolo11;

	@FXML
	private VBox juegoSolo12;

	@FXML
	private VBox juegoSolo2;

	@FXML
	private VBox juegoSolo3;

	@FXML
	private VBox juegoSolo4;

	@FXML
	private VBox juegoSolo5;

	@FXML
	private VBox juegoSolo6;

	@FXML
	private VBox juegoSolo7;

	@FXML
	private VBox juegoSolo8;

	@FXML
	private VBox juegoSolo9;

	@FXML
	private VBox menuFondito;

	@FXML
	private VBox menuGeneral;

	@FXML
	private VBox menuGeneros;

	@FXML
	private VBox menuPlataformas;

	@FXML
	private ScrollPane scrollHorizontalJuego;

	@FXML
	private ScrollPane scrollHorizontalJuego1;

	@FXML
	private ScrollPane scrollHorizontalJuego2;

	@FXML
	private ScrollPane scrollHorizontalJuego3;

	@FXML
	private ScrollPane scrollJuegosVertical;

	@FXML
	private ScrollPane scrollMenu;
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
			VentanaUtil.abrirVentana(PANEL_USER, "Usuario", STYLES, null, event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void imgCargarJuegosAtrasPressed(MouseEvent event) {
		if (currentPage > 0) {
			currentPage--; // Retroceder una página
			cargarJuegosEnPantalla(currentPage);
		}
	}

	@FXML
	void imgCargarJuegosAdelantePressed(MouseEvent event) {
		if ((currentPage + 1) * juegosPorPagina < totalJuegos) {
			currentPage++; // Avanzar una página
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
				JuegoBD juego = new JuegoBD(idJuego, titulo, descripcion, fechaLanzamiento, creadoPorUsuario == 1, tiempoJugado,
						desarrolladores, imagenPrincipal, imagenSecundaria, imagenTercera, imagenCuarta, imagenQuinta, pegi,
						generos, plataformas);

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

	private void cambiarMenu(VBox menuActivo, VBox menuInactivo1, VBox menuInactivo2, boolean activar) {
		if (activar) {
			menuActivo.setVisible(true);
			menuInactivo1.setVisible(false);
			menuInactivo2.setVisible(false);
		} else {
			menuActivo.setVisible(false);
			menuInactivo1.setVisible(true);
			menuInactivo2.setVisible(true);
		}
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

}
