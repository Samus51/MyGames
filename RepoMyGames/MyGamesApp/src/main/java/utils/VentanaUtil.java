package utils;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.mindrot.jbcrypt.BCrypt;

import controllers.HomeController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainApp.Main;
import models.Usuario;

public class VentanaUtil {

	public static String getContent(String userName) {
		return "<html>"
				+ "<body style='font-family: Arial, sans-serif; background-color: #f4f7fc; text-align: center; padding: 30px;'>"
				+ "<div style='background-color: #ffffff; padding: 40px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;'>"
				+ "<h1 style='color: #333; font-size: 26px; margin-bottom: 20px;'>¡Bienvenido a MyGames!</h1>"
				+ "<img src='https://raw.githubusercontent.com/Samus51/MyGames/main/LogoMyGames.png' "
				+ "alt='Logo MyGames' style='width: 250px; height: auto; margin: 20px auto; display: block;'>"
				+ "<p style='font-size: 18px; color: #555; margin-top: 20px;'>Hola <strong style='color: #4CAF50;'>" + userName
				+ "</strong>,</p>"
				+ "<p style='font-size: 16px; color: #555;'>Tu cuenta ha sido creada exitosamente. Ahora puedes acceder y disfrutar de todo lo que MyGames tiene para ofrecerte.</p>"
				+ "<p style='font-size: 16px; color: #555; margin-top: 20px;'>¡Estamos emocionados de tenerte en nuestra comunidad de jugadores!</p>"
				+ "<p style='font-size: 14px; color: #888; margin-top: 20px;'>Si tienes alguna pregunta, no dudes en contactarnos. ¡Gracias por unirte a nuestra comunidad!</p>"
				+ "<hr style='border: 0; border-top: 1px solid #eee; margin: 30px 0;'>"
				+ "<p style='font-size: 14px; color: #888;'>Equipo MyGames</p>" + "</div>" + "</body>" + "</html>";

	}

	public static void abrirVentana(String fxmlPath, String titulo, String stylesheet,
			Consumer<Object> controladorConsumer, MouseEvent event) throws IOException {
// Obtener la ventana actual
		Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(VentanaUtil.class.getResource(fxmlPath));
		Pane root = loader.load();

		Object controller = loader.getController();
		System.out.println("Controlador cargado: " + controller);

		if (controladorConsumer != null && controller != null) {
			controladorConsumer.accept(controller);
		}

		Scene scene = new Scene(root);
		if (stylesheet != null) {
			scene.getStylesheets().add(VentanaUtil.class.getResource(stylesheet).toExternalForm());
		}

		Stage nuevaVentana = new Stage();
		nuevaVentana.setTitle(titulo);
		nuevaVentana.setScene(scene);
		nuevaVentana.setMaximized(true);
		nuevaVentana.setResizable(false);
		nuevaVentana.initStyle(StageStyle.UNDECORATED);
		nuevaVentana.show();

// Cerrar la ventana actual
		ventanaActual.close();

		System.out.println("Ventana cerrada y nueva ventana abierta");
	}

	public static void abrirVentanaHome(String fxmlPath, String titulo, String stylesheet, Usuario usuario,
			MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(VentanaUtil.class.getResource(fxmlPath));
		BorderPane root = loader.load();

		HomeController homeController = loader.getController();
		homeController.setUsuario(usuario);
		System.out.println("ELUSER: " + homeController.getUsuario().toString());

		if (stylesheet != null) {
			root.getStylesheets().add(VentanaUtil.class.getResource(stylesheet).toExternalForm());
		}

		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(titulo);
		stage.setScene(scene);
		stage.show();
	}

	private static Stage obtenerVentana(MouseEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static boolean validarDatos(String email, String contrasena, String contrasenaConfirmacion,
			List<String> generosPreferidos) {
		if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
			mostrarAlerta("Error de formato de email", "El formato de email no es válido.");
			return false;
		}

		if (!contrasena.equals(contrasenaConfirmacion)) {
			mostrarAlerta("Error de contraseña", "Las contraseñas no coinciden.");
			return false;
		}

		if (!contrasena.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$")) {
			mostrarAlerta("Error de formato de contraseña", "La contraseña debe cumplir ciertos requisitos.");
			return false;
		}

		if (generosPreferidos == null || generosPreferidos.isEmpty()) {
			mostrarAlerta("Selección de géneros favoritos", "Debe seleccionar al menos un género.");
			return false;
		}

		return true;
	}

	public static void mostrarAlerta(String titulo, String mensaje) {
		// Usamos Platform.runLater() para asegurarnos de que se ejecuta en el hilo de
		// JavaFX
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(titulo);
			alert.setContentText(mensaje);
			alert.showAndWait();
		});
	}

	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static String generarContenidoCorreo(String codigo) {
		return "<html>"
				+ "<body style='font-family: Arial, sans-serif; background-color: #f4f4f9; text-align: center; padding: 30px;'>"
				+ "<div style='background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto;'>"
				+ "<h1 style='color: #333; font-size: 24px;'>¡Recupera tu cuenta en MyGames!</h1>"
				+ "<img src='https://raw.githubusercontent.com/Samus51/MyGames/main/LogoMyGames.png' "
				+ "alt='Logo MyGames' style='width: 200px; margin: 20px auto;'>"
				+ "<p style='font-size: 18px; color: #555;'>Tu código de seguridad es:</p>"
				+ "<div style='background-color: blue; color: white; font-size: 24px; font-weight: bold; padding: 15px 25px; border-radius: 5px; display: inline-block;'>"
				+ codigo + "</div>"
				+ "<p style='font-size: 16px; color: #555; margin-top: 20px;'>Por favor, no compartas este código con nadie.</p>"
				+ "<p style='font-size: 14px; color: #888;'>Si no solicitaste este código, por favor ignora este mensaje.</p>"
				+ "<p style='font-size: 16px; color: #333; margin-top: 30px;'>¡Gracias por ser parte de MyGames!</p>"
				+ "<hr style='border: 0; border-top: 1px solid #eee; margin: 30px 0;'>"
				+ "<p style='font-size: 12px; color: #aaa;'>Equipo MyGames</p>" + "</div>" + "</body>" + "</html>";
	}

}
