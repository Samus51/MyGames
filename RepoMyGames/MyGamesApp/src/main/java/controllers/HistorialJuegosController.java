package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.Conector;
import models.Juego;
import models.Usuario;
import utils.VentanaUtil;

/**
 * Controlador de HistorialJuegos
 */
public class HistorialJuegosController implements Initializable {

	// Constantes
	// SQL
	private static final String SQL_ELIMINAR_CUENTA = "DELETE FROM usuarios WHERE id_usuario = ?";
	// Styles
	private static final String STYLES = "/styles.css";
	// Pantallas
	private static final String LOGIN = "/views/Login.fxml";
	private static final String JUEGO = "/views/Juego.fxml";
	private static final String CAMBIAR_INFO_PERSONAL = "/views/CambiarInfoPersonal.fxml";
	private static final String HOME = "/views/Home.fxml";

	@FXML
	private BorderPane VentanaPrincipal;

	@FXML
	private ImageView imgCerrar;

	@FXML
	private ImageView imgFlechaAtras;

	@FXML
	private ImageView imgMinimizar;

	@FXML
	private Label lblGenero;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblTiempoJuego;

	@FXML
	private VBox listaJuegos;

	@FXML
	private BorderPane panelLogo;

	List<Juego> recentlyPlayed;

	private Usuario usuarioActivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		recentlyPlayed = new ArrayList<>(getRecentlyPlayed());

		try {
			for (Juego juego : recentlyPlayed) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource(JUEGO));

				HBox hBox = fxmlLoader.load();
				JuegoController juegoController = fxmlLoader.getController();
				juegoController.setData(juego);

				listaJuegos.getChildren().add(hBox);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Juego> getRecentlyPlayed() {
		List<Juego> ls = new ArrayList<>();

		Juego juego1 = new Juego();
		juego1.setNombre("Juego 1");
		juego1.setImagen("/Logo.png");
		juego1.setGenero("Accion");
		juego1.setTiempo_jugado("23");
		ls.add(juego1);

		Juego juego2 = new Juego();
		juego2.setImagen("/Logo.png");
		juego2.setNombre("Juego 2");
		juego2.setGenero("Accion");
		juego2.setTiempo_jugado("23");
		ls.add(juego2);

		Juego juego3 = new Juego();
		juego3.setImagen("/Logo.png");
		juego3.setNombre("Juego 3");
		juego3.setGenero("Accion");
		juego3.setTiempo_jugado("23");
		ls.add(juego3);

		Juego juego4 = new Juego();
		juego4.setImagen("/Logo.png");
		juego4.setNombre("Juego 4");
		juego4.setGenero("Accion");
		juego4.setTiempo_jugado("23");
		ls.add(juego4);

		Juego juego5 = new Juego();
		juego5.setImagen("/Logo.png");
		juego5.setNombre("Juego 5");
		juego5.setGenero("Accion");
		juego5.setTiempo_jugado("23");
		ls.add(juego5);

		Juego juego6 = new Juego();
		juego6.setImagen("/Logo.png");
		juego6.setNombre("Juego 6");
		juego6.setGenero("Accion");
		juego6.setTiempo_jugado("23");
		ls.add(juego6);

		Juego juego7 = new Juego();
		juego7.setImagen("/Logo.png");
		juego7.setNombre("Juego 7");
		juego7.setGenero("Accion");
		juego7.setTiempo_jugado("23");
		ls.add(juego7);

		return ls;
	}

	@FXML
	void minimizarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.setIconified(true);
	}

	@FXML
	void cerrarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.close();
	}

	@FXML
	void flechaAtrasPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(HOME, "Inicio", STYLES, null, event);
	}

	@FXML
	void lblCambiarInfoPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(CAMBIAR_INFO_PERSONAL, "Cambiar Información", STYLES, null, event);
	}

	@FXML
	void lblCerrarSesionPressed(MouseEvent event) throws IOException {
		// Crear la alerta de tipo confirmación
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Cerrar sesión");
		alerta.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
		alerta.setContentText("Confirma si deseas salir de tu cuenta actual.");

		// Personalizar los botones de la alerta
		ButtonType botonConfirmar = new ButtonType("Aceptar");
		ButtonType botonCancelar = new ButtonType("Cancelar");

		// Establecer los botones personalizados
		alerta.getButtonTypes().setAll(botonConfirmar, botonCancelar);

		// Mostrar la alerta y manejar la respuesta de forma sencilla
		alerta.showAndWait();

		// Verificar cuál fue el botón presionado
		if (alerta.getResult() == botonConfirmar) {
			VentanaUtil.abrirVentana(LOGIN, "Login", STYLES, null, event);
		} else {
			alerta.close();
		}
	}

	@FXML
	void lblEliminarCuentaPressed(MouseEvent event) {
		// Crear la alerta de tipo confirmación
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Eliminar Cuenta");
		alerta.setHeaderText("¿Estás seguro de que deseas eliminar tu cuenta?");
		alerta.setContentText("Esta acción es irreversible. Si aceptas, perderás todos tus datos.");

		// Personalizar los botones de la alerta
		ButtonType botonConfirmar = new ButtonType("Aceptar");
		ButtonType botonCancelar = new ButtonType("Cancelar");

		// Establecer los botones personalizados
		alerta.getButtonTypes().setAll(botonConfirmar, botonCancelar);

		// Mostrar la alerta y manejar la respuesta
		alerta.showAndWait().ifPresent(new Consumer<ButtonType>() {
			@Override
			public void accept(ButtonType respuesta) {
				if (respuesta == botonConfirmar) {
					eliminarCuenta();
					try {
						VentanaUtil.abrirVentana(LOGIN, "Login", STYLES, null, event);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Si el usuario cancela
					alerta.close();
				}
			}
		});
	}

	private void eliminarCuenta() {
		// Establecemos la consulta SQL
		String sql = SQL_ELIMINAR_CUENTA;

		// Suponiendo que el id_usuario lo obtienes desde una sesión activa
		int idUsuario = obtenerIdUsuario();

		try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			// Establecer el valor del parámetro en la consulta (id_usuario)
			stmt.setInt(1, idUsuario);

			// Ejecutar la eliminación
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int obtenerIdUsuario() {
		if (usuarioActivo != null) {
			return usuarioActivo.getIdUsuario();
		}
		return -1;
	}

}
