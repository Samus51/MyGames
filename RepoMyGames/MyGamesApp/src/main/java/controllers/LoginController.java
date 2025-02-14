package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.mindrot.jbcrypt.BCrypt;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import models.Usuario;
import utils.VentanaUtil;

/**
 * Controlador de Login
 */
public class LoginController {

	// Constantes
	// SQL
	private static final String SQL_USUARIO = "Select * from usuarios where nombre = ?";
	// Styles
	private static final String STYLES = "/styles.css";
	// Pantallas
	private static final String REGISTRO = "/views/Registro.fxml";
	private static final String RECUPERAR_CONTRASENA = "/views/RecuperarContrasena.fxml";
	private static final String HOME = "/views/Home.fxml";
	// Fotos
	private static final String OJO_NEGRO = "/ojoNegro.png";
	private static final String OJO_NEGRO_TACHADO = "/ojoNegroTachado.png";

	@FXML
	private BorderPane VentanaPrincipal, panelLogo;

	@FXML
	private Button btnLogin, btnOjoPassword;

	@FXML
	private ImageView imgClose, imgLogo, imgMinimizar, imgOjoPassword, cargando;

	@FXML
	private Label lblCrearCuenta, lblUser, lblUser1;

	@FXML
	private Circle logoClip;

	@FXML
	private TextField txtPassword, txtPasswordLimpio, txtUsuario;

	@FXML
	private PasswordField txtPasswordOculto;

	private boolean esPasswordVisible = false;

	@FXML
	void initialize() {
		addZoomEffect(imgLogo);
		cargando.setVisible(false);
	}

	@FXML
	void btnLoginPressed(MouseEvent event) {
		String user = txtUsuario.getText();
		String password = txtPassword.getText().isEmpty() ? txtPasswordOculto.getText() : txtPassword.getText();

		Connection cone = Conector.conectar();
		try (PreparedStatement st = cone.prepareStatement(SQL_USUARIO)) {
			st.setString(1, user);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				String storedHash = rs.getString("contrasena");

				// Comparar la contraseña ingresada con el hash almacenado
				if (BCrypt.checkpw(password, storedHash)) {
					int id = rs.getInt("id_usuario");
					Usuario usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"),
							rs.getString("email"), storedHash, Arrays.asList(rs.getString("generos").split(",")));

					btnLogin.setText("");
					cargando.setVisible(true);
					btnLogin.setDisable(true);

					Task<Void> task = new Task<Void>() {
						protected Void call() throws Exception {
							Thread.sleep(5000);
							return null;
						}

						protected void succeeded() {
							try {

								// Insertar en la biblioteca
								String insertBibliotecaSQL = "INSERT INTO biblioteca (id_usuario) VALUES (?)";
								try (PreparedStatement st2 = cone.prepareStatement(insertBibliotecaSQL)) {
									st2.setInt(1, id);
									st2.executeUpdate();
								} catch (SQLException e) {
									e.printStackTrace();
								}

								// Abrir la ventana home
								VentanaUtil.abrirVentana(HOME, "Home", STYLES, controller -> {
									HomeController homeController = (HomeController) controller;
									homeController.setUsuario(usuario);
								}, event);

								System.out.println(usuario.toString());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						protected void failed() {
							mostrarError("Hubo un error al procesar la solicitud.");
						}
					};
					new Thread(task).start();
				} else {
					mostrarError("Usuario o contraseña incorrectos");
				}
			} else {
				mostrarError("Usuario o contraseña incorrectos");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void mostrarPassword() {
		// Cambiar la visibilidad de los campos
		esPasswordVisible = !esPasswordVisible;

		if (esPasswordVisible) {
			txtPasswordOculto.setVisible(false);
			txtPassword.setVisible(true);
			txtPassword.setText(txtPasswordOculto.getText());
			imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO_TACHADO)));
		} else {
			txtPasswordOculto.setVisible(true);
			txtPassword.setVisible(false);
			txtPasswordOculto.setText(txtPassword.getText());
			imgOjoPassword.setImage(new Image(getClass().getResourceAsStream(OJO_NEGRO)));
		}
	}

	@FXML
	void crearCuentaPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(REGISTRO, "Registro", STYLES, null, event);
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
	void lblRecuperarContrasenaPressed(MouseEvent event) throws IOException {
		VentanaUtil.abrirVentana(RECUPERAR_CONTRASENA, "Recuperar Contraseña", STYLES, null, event);
	}

	// Método para agregar efecto de zoom a un ImageView
	private void addZoomEffect(ImageView imageView) {
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

	private void mostrarError(String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle("Login");
		alerta.setContentText(mensaje);
		alerta.setHeaderText("Error de Login");
		alerta.showAndWait();

		btnLogin.setText("Iniciar sesión");
		cargando.setVisible(false);
		btnLogin.setDisable(false);
	}

}