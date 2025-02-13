package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.JuegoPachorra;
import models.Usuario;
import utils.ExtractorApi2;
import utils.VentanaUtil;

/**
 * Controlador de JuegoInfo
 */
public class JuegoInfoController {
	String tituloJuego;
	JuegoPachorra juego;

	/**
	 * @return the tituloJuego
	 */
	public String getTituloJuego() {
		return tituloJuego;
	}

	public void setTituloJuego(String tituloJuego) {
		this.tituloJuego = tituloJuego;
		if (lblTitulo != null) {
			lblTitulo.setText(tituloJuego);
		}
		System.out.println("Juego de Info: " + tituloJuego);
		cargarJuegoInfo();

	}

	// Constantes
	// Styles
	private static final String STYLES = "/styles.css";
	// Pantallas
	private static final String HOME = "/views/Home.fxml";

//Contenedores principales
	@FXML
	private BorderPane VentanaPrincipal;
	@FXML
	private VBox vboxPrincipal, menuGeneral, menuAnadirJuego, menuAnadirJuegoJugado, menuAnadirListaNoJugado,
			menuJugadoAnanidoLista, menuJugadoSinAnadir;

//Botones
	@FXML
	private Button btnAnadirJuego, btnAnadirJuegoAnadirListaNoJugado, btnAnadirJuegoJugadoAnanidoLista,
			btnAnadirJuegoJugadoSinAnadir, btnAnadirListaDeseos, btnAnadirListaDeseosAnadirListaNoJugado,
			btnAnadirListaDeseosJugadoSinAnadir, btnEliminarJuegoAnadirJuego, btnEliminarJuegoAnadirJuegoJugado,
			btnEliminarListaDeseosJugadoAnanidoLista, btnJugadoAnadirJuegoJugado, btnJugadoJugadoAnanidoLista,
			btnJugadoJugadoSinAnadir, btnNoJugado, btnNoJugadoAnadirJuego, btnNoJugadoAnadirListaNoJugado;

//Imágenes
	@FXML
	private ImageView imgCerrar, imgFlechaAtras, imgJuego, imgJuego2, imgJuego3, imgJuego4, imgJuego5, imgJuego6,
			imgMinimizar, imgPegi;

//Labels
	@FXML
	private Label lblDesarrolladoresVacio, lblDescripcionVacio, lblFechaLanzamientoVacio, lblGenerosVacio,
			lblMetaScoreVacio, lblPlataformasVacio, lblTiempoJugadoVacio, lblTitulo;

	@FXML
	private TextField txtComentarios;
	private Usuario usuario;

	private void cargarJuegoInfo() {
		// Simula la carga del juego
		JuegoPachorra juegoACargar = ExtractorApi2.buscarJuegoPorNombre(this.tituloJuego);
		juego = juegoACargar;
		if (juegoACargar != null) {
			// Actualiza la interfaz con la información del juego, solo si los datos no son
			// nulos

			if (juegoACargar.getPegi() != null) {
				switch (juegoACargar.getPegi()) {
				case "Mature":
					imgPegi.setImage(new Image("imgPegi/pegi16.png"));
					break;
				case "Adult Only":
					imgPegi.setImage(new Image("imgPegi/pegi19.png"));
					break;
				case "Everyone":
					imgPegi.setImage(new Image("imgPegi/pegi3.png"));
					break;
				case "Teen":
					imgPegi.setImage(new Image("imgPegi/pegi12.png"));
					break;
				case "Everyone 10+":
					imgPegi.setImage(new Image("imgPegi/pegi7.png"));
					break;
				default:
					// Código por defecto si no coincide con ningún caso
					break;
				}
			}

		}

		if (juegoACargar.getImagenPrincipal() != null) {
			imgJuego.setImage(new Image(juegoACargar.getImagenPrincipal()));
		}
		if (juegoACargar.getDescripcion() != null) {
			lblDescripcionVacio.setText(juegoACargar.getDescripcion());
		}
		if (juegoACargar.getDevs() != null) {
			lblDesarrolladoresVacio.setText(juegoACargar.getDevs().toString());
		}
		if (juegoACargar.getPlataformas() != null) {
			lblPlataformasVacio.setText(juegoACargar.getPlataformas().toString());
		}
		if (juegoACargar.getFechaLanzamiento() != null) {
			lblFechaLanzamientoVacio.setText(juegoACargar.getFechaLanzamiento());
		}
		if (juegoACargar.getGeneros() != null) {
			lblGenerosVacio.setText(juegoACargar.getGeneros().toString());
		}
		if (juegoACargar.getMetacriticScore() >= 0) {
			lblMetaScoreVacio.setText(String.valueOf(juegoACargar.getMetacriticScore()));
		}
		if (juegoACargar.getTiempo_jugado() >= 0) {
			lblTiempoJugadoVacio.setText(juegoACargar.getTiempo_jugado() + " Horas");
		}

		List<String> imagenes = juegoACargar.getCapturasImagenes();
		if (imagenes != null && imagenes.size() >= 5) {
			imgJuego2.setImage(new Image(imagenes.get(1)));
			imgJuego3.setImage(new Image(imagenes.get(2)));
			imgJuego4.setImage(new Image(imagenes.get(3)));
			imgJuego5.setImage(new Image(imagenes.get(4)));
		}

		{
			// Si no se encuentra el juego, muestra un mensaje de error
			System.out.println("Juego no encontrado");
		}
	}

	@FXML
	void btnAnadirJuegoPressed(MouseEvent event) throws ParseException {
		String titulo = juego.getTitulo();
		String descripcion = juego.getDescripcion();
		String fechaLanzamientoString = juego.getFechaLanzamiento();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilFechaLanzamiento = formato.parse(fechaLanzamientoString);
		java.sql.Date sqlFechaLanzamiento = new java.sql.Date(utilFechaLanzamiento.getTime());

		boolean creadoPorUsuario = false;
		int idUsuario = usuario.getIdUsuario();
		int tiempoJugado = juego.getTiempo_jugado();
		String desarrolladores = lblDesarrolladoresVacio.getText();
		String pegi = juego.getPegi();
		String url1 = juego.getImagenPrincipal();
		String url2 = juego.getCapturasImagenes().get(0);
		String url3 = juego.getCapturasImagenes().get(1);
		String url4 = juego.getCapturasImagenes().get(2);
		String url5 = juego.getCapturasImagenes().get(3);
		String generos = String.join(",", juego.getGeneros());
		String plataformas = String.join(",", juego.getPlataformas());

		String checkExistenciaJuegoSQL = "SELECT id_juego FROM juegos WHERE titulo = ?";
		String checkExistenciaBibliotecaSQL = "SELECT id_biblioteca FROM biblioteca WHERE id_usuario = ? AND id_juego = ?";

		try (Connection cone = Conector.conectar();
				PreparedStatement stExistenciaJuego = cone.prepareStatement(checkExistenciaJuegoSQL)) {
			stExistenciaJuego.setString(1, titulo);
			ResultSet rsJuego = stExistenciaJuego.executeQuery();

			if (rsJuego.next()) {
				int idJuegoExistente = rsJuego.getInt("id_juego");

				// Verificar si el juego ya está en la biblioteca del usuario
				try (PreparedStatement stExistenciaBiblioteca = cone.prepareStatement(checkExistenciaBibliotecaSQL)) {
					stExistenciaBiblioteca.setInt(1, idUsuario);
					stExistenciaBiblioteca.setInt(2, idJuegoExistente);
					ResultSet rsBiblioteca = stExistenciaBiblioteca.executeQuery();

					if (rsBiblioteca.next()) {
						// El juego ya existe en la biblioteca
						VentanaUtil.mostrarAlerta("Juego ya en Biblioteca", "Este juego ya está en tu biblioteca.");
					} else {
						// Agregar el juego a la biblioteca
						String insertBibliotecaSQL = "INSERT INTO biblioteca (id_usuario, id_juego, fecha_adquisicion) VALUES (?, ?, ?)";
						try (PreparedStatement stInsertBiblioteca = cone.prepareStatement(insertBibliotecaSQL)) {
							stInsertBiblioteca.setInt(1, idUsuario);
							stInsertBiblioteca.setInt(2, idJuegoExistente);
							stInsertBiblioteca.setDate(3, new java.sql.Date(System.currentTimeMillis()));

							stInsertBiblioteca.executeUpdate();
							VentanaUtil.mostrarAlerta("Juego Añadido a la Biblioteca",
									"El juego ha sido añadido a tu biblioteca.");
						}
					}
				}
			} else {
				// Si el juego no existe, insertarlo en la base de datos
				String insertJuegoSQL = "INSERT INTO juegos (titulo, descripcion, fecha_lanzamiento, creado_por_usuario, id_usuario, tiempo_jugado, desarrolladores, pegi, url_1, url_2, url_3, url_4, url_5, generos, plataformas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement stInsertJuego = cone.prepareStatement(insertJuegoSQL)) {
					stInsertJuego.setString(1, titulo);
					stInsertJuego.setString(2, descripcion);
					stInsertJuego.setDate(3, sqlFechaLanzamiento);
					stInsertJuego.setBoolean(4, creadoPorUsuario);
					stInsertJuego.setInt(5, idUsuario);
					stInsertJuego.setInt(6, tiempoJugado);
					stInsertJuego.setString(7, desarrolladores);
					stInsertJuego.setString(8, pegi);
					stInsertJuego.setString(9, url1);
					stInsertJuego.setString(10, url2);
					stInsertJuego.setString(11, url3);
					stInsertJuego.setString(12, url4);
					stInsertJuego.setString(13, url5);
					stInsertJuego.setString(14, generos);
					stInsertJuego.setString(15, plataformas);

					stInsertJuego.executeUpdate();

					// Obtener el id del juego insertado
					String selectIdJuegoSQL = "SELECT LAST_INSERT_ID()";
					try (PreparedStatement stSelect = cone.prepareStatement(selectIdJuegoSQL);
							ResultSet rs = stSelect.executeQuery()) {

						if (rs.next()) {
							int idJuego = rs.getInt(1);

							// Insertar el juego en la biblioteca
							String insertBibliotecaSQL = "INSERT INTO biblioteca (id_usuario, id_juego, fecha_adquisicion) VALUES (?, ?, ?)";
							try (PreparedStatement stInsertBiblioteca = cone.prepareStatement(insertBibliotecaSQL)) {
								stInsertBiblioteca.setInt(1, idUsuario);
								stInsertBiblioteca.setInt(2, idJuego);
								stInsertBiblioteca.setDate(3, new java.sql.Date(System.currentTimeMillis()));

								stInsertBiblioteca.executeUpdate();
							}
						}
					}
					VentanaUtil.mostrarAlerta("Juego Añadido", "El juego ha sido añadido correctamente.");
					btnAnadirJuego.setText("Juego ya añadido");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			VentanaUtil.mostrarAlerta("Error al Añadir Juego", "Ocurrió un error al añadir el juego.");
		}
	}

	@FXML
	void btnAnadirListaPressed(MouseEvent event) {

	}

	@FXML
	void btnEliminarJuegoPressed(MouseEvent event) {
		String tituloJuegoBorrar = tituloJuego;

		String selectJuegoSQLBorrado = "SELECT id_juego FROM juegos WHERE titulo = ?";
		try (Connection cone = Conector.conectar();
				PreparedStatement st = cone.prepareStatement(selectJuegoSQLBorrado)) {

			st.setString(1, tituloJuegoBorrar);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				int idJuego = rs.getInt("id_juego");

				String deleteBibliotecaSQL = "DELETE FROM biblioteca WHERE id_usuario = ? AND id_juego = ?";
				try (PreparedStatement stDelete = cone.prepareStatement(deleteBibliotecaSQL)) {
					stDelete.setInt(1, usuario.getIdUsuario());
					stDelete.setInt(2, idJuego);
					stDelete.executeUpdate();
					VentanaUtil.mostrarAlerta("Juego Eliminado", "El juego ha sido eliminado de la biblioteca.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			VentanaUtil.mostrarAlerta("Error al Eliminar Juego",
					"Ocurrió un error al eliminar el juego de la biblioteca.");
		}
	}

	@FXML
	void btnEliminarListaPressed(MouseEvent event) {

	}

	@FXML
	void btnJugadoPressed(MouseEvent event) {

	}

	@FXML
	void btnNoJugadoPressed(MouseEvent event) {

	}

	@FXML
	void cerrarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.close();
	}

	@FXML
	void flechaAtrasPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(HOME, "Juego Info", STYLES, null, event);

	}

	@FXML
	void minimizarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.setIconified(true);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;

	}
	/**
	 * Metodo para abrir una nueva ventana y cerrar la actual
	 * 
	 * @param fxml Ventana fxml
	 */

}
