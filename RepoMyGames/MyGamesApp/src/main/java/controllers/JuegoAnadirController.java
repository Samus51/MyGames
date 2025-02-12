package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdbc.Conector;
import utils.VentanaUtil;

public class JuegoAnadirController {

	private static final String STYLES = "/styles.css";
	private static final String HOME = "/views/Home.fxml";
	private static final String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private Button btnAnadirJuego;
	@FXML
	private TextField txtFechaLanzamiento, txtDesarrolladores, txtDescripcion, txtTiempoJugado, txtTitulo;
	@FXML
	private ImageView img1, img2, img3, img4, img5;
	@FXML
	private ImageView imgCerrar, imgFlechaAtras, imgMinimizar;
	@FXML
	private Label lblMetaScoreVacio, lblTitulo;
	@FXML
	private CheckComboBox<String> lstGeneros, lstPlataformas;
	@FXML
	private ComboBox<String> lstPegi;

	private File archivoImagenPrincipal, archivoImagenSecundaria, archivoImagenTercera, archivoImagenCuarta,
			archivoImagenQuinta;

	@FXML
	public void initialize() {
		lstGeneros.getItems().addAll("Action", "Adventure", "RPG", "Casual", "Arcade", "Indie", "Strategy", "Simulation",
				"Sports", "Racing", "Fighting", "Shooter", "Puzzle", "Massively Multiplayer", "Card");

		lstPlataformas.getItems().addAll("PC", "macOS", "Linux", "PlayStation 5", "PlayStation 4", "PlayStation 3",
				"PlayStation 2", "PlayStation", "PS Vita", "PSP", "Nintendo Switch", "Nintendo 3DS", "Nintendo DS",
				"Nintendo DSi", "Wii U", "Wii", "Nintendo 64", "Xbox One", "Xbox Series S/X", "Xbox 360", "Xbox", "iOS",
				"Android", "GameCube", "Game Boy Advance", "Game Boy Color", "Game Boy", "SNES", "NES", "Classic Macintosh",
				"Apple II", "Commodore / Amiga", "Atari 7800", "Atari 5200", "Atari 2600", "Atari Flashback", "Atari 8-bit",
				"Atari ST", "Atari Lynx", "Atari XEGS", "Genesis", "SEGA Saturn", "SEGA CD", "SEGA 32X", "SEGA Master System",
				"Dreamcast", "3DO", "Jaguar", "Game Gear", "Neo Geo");

		lstPegi.getItems().addAll("3", "7", "12", "16", "18");
	}

	@FXML
	void btnCerrarPressed(MouseEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}

	@FXML
	void btnMinimizarPressed(MouseEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	void btnUserPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(PANEL_USER, "Usuario", STYLES, null, event);
	}

	@FXML
	void flechaAtrasPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(HOME, "Inicio", STYLES, null, event);
	}

	@FXML
	void guardarJuego(MouseEvent event) throws IOException {
		String titulo = txtTitulo.getText().trim();
		String descripcion = txtDescripcion.getText().trim();
		String desarrolladores = txtDesarrolladores.getText().trim();
		String tiempoJugado = txtTiempoJugado.getText().trim();
		String fechaLanzamiento = txtFechaLanzamiento.getText().trim();
		List<String> generos = new ArrayList<>(lstGeneros.getCheckModel().getCheckedItems());
		List<String> plataformas = new ArrayList<>(lstPlataformas.getCheckModel().getCheckedItems());
		String pegi = lstPegi.getSelectionModel().getSelectedItem();
		int idUsuario = obtenerIdUsuarioActual();

		if (titulo.isEmpty() || desarrolladores.isEmpty() || fechaLanzamiento.isEmpty() || generos.isEmpty()
				|| plataformas.isEmpty() || pegi == null) {
			mostrarAlerta("Error", "Todos los campos deben estar completos", Alert.AlertType.ERROR);
			return;
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = Conector.conectar();
			conn.setAutoCommit(false);

			// Insertar juego en la base de datos
			String query = "INSERT INTO juegos (titulo, descripcion, fecha_lanzamiento, creado_por_usuario, id_usuario,tiempo_jugado,desarrolladores,imagen_principal, imagen_secundaria, imagen_tercera, imagen_cuarta, imagen_quinta, pegi, generos, plataformas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, titulo);
			stmt.setString(2, descripcion);
			stmt.setString(3, fechaLanzamiento);
			stmt.setInt(4, 1);
			stmt.setInt(5, idUsuario);
			stmt.setString(6, tiempoJugado);
			stmt.setString(7, desarrolladores);

			// Manejo de la imagen principal
			FileInputStream fis1 = null;
			FileInputStream fis2 = null;
			FileInputStream fis3 = null;
			FileInputStream fis4 = null;
			FileInputStream fis5 = null;

			try {
				if (archivoImagenPrincipal != null && archivoImagenPrincipal.exists() && archivoImagenPrincipal.isFile()) {
					fis1 = new FileInputStream(archivoImagenPrincipal);
					stmt.setBinaryStream(8, fis1, (int) archivoImagenPrincipal.length());
				} else {
					System.err.println("El archivo de imagen principal no se encuentra o no es válido.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de imagen principal: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			try {
				if (archivoImagenSecundaria != null && archivoImagenSecundaria.exists() && archivoImagenSecundaria.isFile()) {
					fis2 = new FileInputStream(archivoImagenSecundaria);
					stmt.setBinaryStream(9, fis2, (int) archivoImagenSecundaria.length());
				} else {
					System.err.println("El archivo de imagen secundaria no se encuentra o no es válido.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de imagen secundaria: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			try {
				if (archivoImagenTercera != null && archivoImagenTercera.exists() && archivoImagenTercera.isFile()) {
					fis3 = new FileInputStream(archivoImagenTercera);
					stmt.setBinaryStream(10, fis3, (int) archivoImagenTercera.length());
				} else {
					System.err.println("El archivo de imagen tercera no se encuentra o no es válido.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de imagen tercera: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			try {
				if (archivoImagenCuarta != null && archivoImagenCuarta.exists() && archivoImagenCuarta.isFile()) {
					fis4 = new FileInputStream(archivoImagenCuarta);
					stmt.setBinaryStream(11, fis4, (int) archivoImagenCuarta.length());
				} else {
					System.err.println("El archivo de imagen cuarta no se encuentra o no es válido.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de imagen cuarta: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			try {
				if (archivoImagenQuinta != null && archivoImagenQuinta.exists() && archivoImagenQuinta.isFile()) {
					fis5 = new FileInputStream(archivoImagenQuinta);
					stmt.setBinaryStream(12, fis5, (int) archivoImagenQuinta.length());
				} else {
					System.err.println("El archivo de imagen quinta no se encuentra o no es válido.");
					return;
				}
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de imagen sexta: " + e.getMessage());
				e.printStackTrace();
				return;
			}

			stmt.setInt(13, Integer.parseInt(pegi));

			stmt.setString(14, String.join(",", generos));
			stmt.setString(15, String.join(",", plataformas));

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();

			if (!rs.next()) {
				throw new SQLException("Error al obtener el ID del juego");
			}

			conn.commit();
			mostrarAlerta("Éxito", "Juego guardado correctamente", Alert.AlertType.INFORMATION);

			VentanaUtil.abrirVentana(HOME, "Home", STYLES, null, event);
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			mostrarAlerta("Error", "No se pudo guardar el juego", Alert.AlertType.ERROR);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	private int obtenerIdUsuarioActual() {
		return 29;
	}

	private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	// Método para cargar imágenes en ImageViews
	@FXML
	void cargarImagenArriba(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

		File archivoSeleccionado = fileChooser.showOpenDialog(null);

		if (archivoSeleccionado != null) {
			archivoImagenPrincipal = archivoSeleccionado;

			ImageView img = (ImageView) event.getSource();
			Image image = new Image(archivoImagenPrincipal.toURI().toString());

			img.setImage(image);

			// Ajustar tamaño correctamente para evitar distorsiones
			img.setFitWidth(img.getParent().getLayoutBounds().getWidth());
			img.setFitHeight(img.getParent().getLayoutBounds().getHeight());
			img.setPreserveRatio(true);
			img.setSmooth(true);
			img.setCache(true);
		} else {
			System.out.println("No se seleccionó ninguna imagen.");
		}
	}

	@FXML
	void cargarImagen(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

		File archivoSeleccionado = fileChooser.showOpenDialog(null);

		if (archivoSeleccionado != null) {
			ImageView img = (ImageView) event.getSource();
			Image image = new Image(archivoSeleccionado.toURI().toString());

			img.setImage(image);
			img.setPreserveRatio(true);
			img.setSmooth(true);
			img.setCache(true);

			// Ajustar tamaño correctamente sin distorsionar
			img.setFitWidth(img.getParent().getLayoutBounds().getWidth());
			img.setFitHeight(img.getParent().getLayoutBounds().getHeight());

			// Asignar archivo a la variable correspondiente
			if (img == img2) {
				archivoImagenSecundaria = archivoSeleccionado;
			} else if (img == img3) {
				archivoImagenTercera = archivoSeleccionado;
			} else if (img == img4) {
				archivoImagenCuarta = archivoSeleccionado;
			} else if (img == img5) {
				archivoImagenQuinta = archivoSeleccionado;
			}

		} else {
			System.out.println("No se seleccionó ninguna imagen.");
		}
	}

}
