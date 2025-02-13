package controllers;

import java.io.IOException;
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
import models.JuegoPachorra;
import utils.ExtractorApi2;
import utils.VentanaUtil;

/**
 * Controlador de JuegoInfo
 */
public class JuegoInfoController {
	String tituloJuego;

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

	private void cargarJuegoInfo() {
		// Simula la carga del juego
		JuegoPachorra juegoACargar = ExtractorApi2.buscarJuegoPorNombre(this.tituloJuego);

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
	void btnAnadirJuegoPressed(MouseEvent event) {

	}

	@FXML
	void btnAnadirListaPressed(MouseEvent event) {

	}

	@FXML
	void btnEliminarJuegoPressed(MouseEvent event) {

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

	/**
	 * Metodo para abrir una nueva ventana y cerrar la actual
	 * 
	 * @param fxml Ventana fxml
	 */

}
