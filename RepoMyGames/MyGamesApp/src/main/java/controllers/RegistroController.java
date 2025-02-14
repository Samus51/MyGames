package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.controlsfx.control.CheckComboBox;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdbc.Conector;
import models.EnviarCorreo;
import utils.VentanaUtil;

/**
 * Controlador de Registro
 */
public class RegistroController {

	// Constantes
	// SQL
	private static final String SQL_USUARIO_COMPROBACION = "Select * from usuarios where email = ?";
	private static final String SQL_USUARIO_INSERT = "INSERT INTO usuarios (nombre, email, fecha_registro, contrasena, codigo_seguridad, generos) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	// Correo
	private static final String EMAIL_FROM = "soportemygames@gmail.com";
	private static String PASSWORD_FROM = "cmol lytj vnub uanm";
	// Pantallas
	private static final String STYLES = "/styles.css";
	private static final String LOGIN = "/views/Login.fxml";
	// Fotos
	private static final String OJO_NEGRO = "/ojoNegro.png";
	private static final String OJO_NEGRO_TACHADO = "/ojoNegroTachado.png";

	@FXML
//ImageView
	private ImageView imgFlechaAtras, imgLogo, imgOjoPassword, imgMinimizar, imgCerrar;

//TextField y PasswordField
	@FXML
	private TextField txtUser, txtEmail, txtConfirmarPassword;
	@FXML
	private PasswordField txtConfirmarPasswordOculto;

//Labels
	@FXML
	private Label lblUser, lblUser2, lblPassword, lblConfirmarPassword;
	@FXML
	public TextField txtPassword;
	@FXML
	public PasswordField txtPasswordOculto;

//Buttons
	@FXML
	private Button btnCrearCuenta, btnOjoPassword;

//ComboBox y CheckComboBox
	@FXML
	private ComboBox<?> cbxGenero;
	@FXML
	CheckComboBox<String> lstGeneros;

//Contenedores y Paneles
	@FXML
	private BorderPane VentanaPrincipal, panelLogo;

//Círculos y Formatos Gráficos
	@FXML
	private Circle logoClip;

	public boolean esPasswordVisible = false;

	@FXML
	/**
	 * Método para cuando se pulse el ojo en la contraseña
	 */
	public void mostrarPassword() {
		// Cambiar la visibilidad de los campos
		esPasswordVisible = !esPasswordVisible;

		if (esPasswordVisible) {
			txtPasswordOculto.setVisible(false);
			txtPassword.setVisible(true);
			txtPassword.setText(txtPasswordOculto.getText());

			txtConfirmarPasswordOculto.setVisible(false);
			txtConfirmarPassword.setVisible(true);
			txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());

			imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO_TACHADO)));
		} else {
			txtPasswordOculto.setVisible(true);
			txtPassword.setVisible(false);
			txtPasswordOculto.setText(txtPassword.getText());

			txtConfirmarPasswordOculto.setVisible(true);
			txtConfirmarPassword.setVisible(false);
			txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

			imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO)));
		}
	}

	public boolean crearUsuario(String userName, String email, String contrasena, List<String> generosPreferidos) {
		Connection cone = Conector.conectar();
		try (PreparedStatement st = cone.prepareStatement(SQL_USUARIO_COMPROBACION);
				PreparedStatement st2 = cone.prepareStatement(SQL_USUARIO_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

			// Comprobar si el usuario ya existe
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return false; // Usuario ya existe
			}

			// Insertar el usuario
			st2.setString(1, userName);
			st2.setString(2, email);
			st2.setDate(3, new Date(System.currentTimeMillis()));
			st2.setString(4, contrasena);
			st2.setString(5, "");

			// Concatenar géneros en una sola cadena
			String generosStr = String.join(",", generosPreferidos);
			st2.setString(6, generosStr);

			int result = st2.executeUpdate();
			if (result == 1) {
				return true; // Usuario creado con éxito
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false; // En caso de error
	}

	@FXML
	public void initialize() {
		// Crear la lista de géneros
		ObservableList<String> generos = FXCollections.observableArrayList("Action", "Adventure",
				"RPG (Role-Playing Games)", "Casual", "Arcade", "Massively Multiplayer", "Family", "Educational", "Indie",
				"Strategy", "Simulation", "Platform", "Sports", "Board Games", "Shooter", "Puzzle", "Racing", "Fighting",
				"Card Games");

		// Configurar el CheckComboBox con la lista de géneros
		lstGeneros.getItems().addAll(generos);
		// Zoom del logo al pasar por encima
		efectoZoom(imgLogo);

	}

	@FXML
	void btnCrearCuentaPressed(MouseEvent event) throws IOException, MessagingException {
		String userName = txtUser.getText();
		String email = txtEmail.getText();
		String contrasena = txtPassword.getText();
		String contrasenaConfirmacion = txtConfirmarPassword.getText();

		if (VentanaUtil.validarDatos(email, contrasena, contrasenaConfirmacion, getListGeneros())) {
			List<String> generosPreferidos = getListGeneros();

			// Hash de la contraseña
			String contrasenaHash = null;
			try {
				contrasenaHash = VentanaUtil.hashPassword(contrasena);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Crear el usuario con el hash de la contraseña
			crearUsuario(userName, email, contrasenaHash, generosPreferidos);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usuario Creado");
			alert.setHeaderText("Creación de Usuario con éxito");
			alert.setContentText("El usuario ha sido creado correctamente.");
			alert.showAndWait();
			flechaAtrasPressed(event);
			String subject = "Creación de Cuenta";

			EnviarCorreo enviarCorreo = new EnviarCorreo();
			enviarCorreo.enviarCorreo(EMAIL_FROM, PASSWORD_FROM, email, subject, VentanaUtil.getContent(userName));
		}
	}

	@FXML
	/**
	 * Método para cuando se pulse la flecha atrás
	 * 
	 * @param event
	 */
	void flechaAtrasPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(LOGIN, "Login", STYLES, null, event);
	}

	@FXML
	/**
	 * Método para cuando se pulse el botón minimizar
	 * 
	 * @param event
	 */
	void minimizarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.setIconified(true);
	}

	@FXML
	/**
	 * Método para cuando se pulse el botón de cerrar
	 * 
	 * @param event
	 */
	void cerrarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.close();
	}

	public List<String> getListGeneros() {
		return new ArrayList<>(lstGeneros.getCheckModel().getCheckedItems());
	}

	public List<Integer> getGenerosIdsByNames(List<String> nombresGeneros) {
		if (nombresGeneros == null || nombresGeneros.isEmpty()) {
			System.out.println("No hay géneros proporcionados.");
			return new ArrayList<>();
		}

		Map<String, Integer> generosMap = cargarGenerosDeBaseDeDatos();
		List<Integer> generosIds = new ArrayList<>();

		for (String genero : nombresGeneros) {
			Integer id = generosMap.get(genero);
			if (id != null) {
				generosIds.add(id);
			} else {
				System.out.println("Género no encontrado en la base de datos: " + genero);
			}
		}

		return generosIds;
	}

	private Map<String, Integer> cargarGenerosDeBaseDeDatos() {
		Map<String, Integer> generosMap = new HashMap<>();
		String sql = "SELECT nombre, id_genero FROM generos";

		try (Connection cone = Conector.conectar();
				PreparedStatement st = cone.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				generosMap.put(rs.getString("nombre"), rs.getInt("id_genero"));
			}

		} catch (SQLException e) {
			System.err.println("Error al cargar los géneros: " + e.getMessage());
		}

		return generosMap;
	}

	/**
	 * Método para agregar efecto de zoom a un ImageView
	 * 
	 * @param imageView Imagen
	 */
	private void efectoZoom(ImageView imageView) {
		imageView.setOnMouseEntered(event -> {
			ScaleTransition zoomIn = new ScaleTransition(Duration.millis(200), imageView);
			zoomIn.setToX(1.2);
			zoomIn.setToY(1.2);
			zoomIn.play();
		});

		imageView.setOnMouseExited(event -> {
			ScaleTransition zoomOut = new ScaleTransition(Duration.millis(200), imageView);
			zoomOut.setToX(1.0);
			zoomOut.setToY(1.0);
			zoomOut.play();
		});
	}

	/**
	 * Metodo para abrir una nueva ventana y cerrar la actual
	 * 
	 * @param fxml Ventana fxml
	 */
}
