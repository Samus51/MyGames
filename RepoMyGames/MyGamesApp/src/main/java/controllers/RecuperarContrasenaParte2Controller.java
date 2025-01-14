package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdbc.Conector;

public class RecuperarContrasenaParte2Controller {

	private static final String SQL_CODIGO = "Select codigo_seguridad from usuarios where email = ?";

	private static final String SQL_PASSWORDS = "UPDATE usuarios set contrasena = ? where email = ?";

	private String email;

	public void setEmail(String email) {
		this.email = email;
	}

  @FXML
  private BorderPane VentanaPrincipal;

	
	@FXML
	private TextField txtCodigo;

	@FXML
	private Button btnTogglePassword;

	@FXML
	private ImageView imgClose;

	@FXML
	private ImageView imgFlechaAtras;

	@FXML
	private ImageView imgMinimizar;

	@FXML
	private ImageView imgTogglePassword;

	@FXML
	private Pane paginaFondo;

	@FXML
	private TextField txtConfirmarPassword;

	@FXML
	private PasswordField txtConfirmarPasswordOculto;

	@FXML
	private TextField txtPassword;

	@FXML
	private PasswordField txtPasswordOculto;

	@FXML
	void imgFlechaAtrasPressed(MouseEvent event) {
		try {
			// Obtener el Stage de la ventana actual (Recuperar Contraseña)
			Stage ventanaRecuperarContrasena = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Cargar el archivo FXML de la ventana principal
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			BorderPane root = loader.load();

			// Crear una nueva escena con el root cargado
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

			// Crear un nuevo Stage (ventana) para la "Ventana Principal"
			Stage nuevaVentana = new Stage();
			nuevaVentana.setTitle("Ventana Principal");
			nuevaVentana.setScene(scene);

			// Maximizar la ventana
			nuevaVentana.setMaximized(true);
			nuevaVentana.setResizable(false);
			nuevaVentana.initStyle(StageStyle.UNDECORATED);

			// Mostrar la nueva ventana
			nuevaVentana.show();

			// Cerrar la ventana actual (Recuperar Contraseña)
			ventanaRecuperarContrasena.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	// Variable para controlar la visibilidad de las contraseñas
	private boolean isPasswordVisible = false;

	@FXML
	private void togglePasswordVisibility() {
		// Cambiar la visibilidad de los campos
		isPasswordVisible = !isPasswordVisible;

		if (isPasswordVisible) {
			txtPasswordOculto.setVisible(false);
			txtPassword.setVisible(true);
			txtPassword.setText(txtPasswordOculto.getText());

			txtConfirmarPasswordOculto.setVisible(false);
			txtConfirmarPassword.setVisible(true);
			txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());

			imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
		} else {
			txtPasswordOculto.setVisible(true);
			txtPassword.setVisible(false);
			txtPasswordOculto.setText(txtPassword.getText());

			txtConfirmarPasswordOculto.setVisible(true);
			txtConfirmarPassword.setVisible(false);
			txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

			imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
		}
	}

	@FXML
	void btnEnviarPressed(MouseEvent event) {
		String codigo = txtCodigo.getText();
		String emailUser = email;
		String password = txtPassword.getText().isEmpty() ? txtPasswordOculto.getText() : txtPassword.getText();
		String passwordConfirmar = txtConfirmarPassword.getText().isEmpty() ? txtConfirmarPasswordOculto.getText()
				: txtConfirmarPassword.getText();
		String codigoOriginal = "";

		if (!password.equals(passwordConfirmar)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Creación de usuario");
			alert.setHeaderText("Error de contraseña");
			alert.setContentText("La contraseña debe ser igual a la contraseña de confirmación");
			alert.showAndWait();
			return;
		}

		if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,}$")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Creación de usuario");
			alert.setHeaderText("Error de formato de contraseña");
			alert.setContentText(
					"La contraseña debe tener al menos 8 caracteres, incluir una letra mayúscula, una minúscula, un número y un carácter especial.");
			alert.showAndWait();
			return;
		}

		try (Connection cone = Conector.conectar();
				PreparedStatement st = cone.prepareStatement(SQL_CODIGO);
				PreparedStatement st2 = cone.prepareStatement(SQL_PASSWORDS)) {

			st.setString(1, emailUser);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				codigoOriginal = rs.getString("codigo_seguridad");
			}
			if (codigo.equals(codigoOriginal)) {
				Alert alerta2 = new Alert(AlertType.INFORMATION);
				alerta2.setTitle("Recuperacion de Cuenta");
				alerta2.setHeaderText("Codigo Correcto");
				alerta2.setContentText("Ha podido recuperar su cuenta");
				alerta2.showAndWait();

			} else {
				Alert alerta2 = new Alert(AlertType.ERROR);
				alerta2.setTitle("Recuperacion de Cuenta");
				alerta2.setHeaderText("Codigo Incorrecto");
				alerta2.setContentText("El codigo de seguridad no es el correctoo o no existe");
				alerta2.showAndWait();

			}

			st2.setString(1, password);
			st2.setString(2, email);
			st2.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		abrirNuevaVentana("/views/Login.fxml");

		
	}

	
	
  private void abrirNuevaVentana(String fxml) {
    try {
      // Obtener el Stage de la ventana principal
      Stage ventanaPrincipal = (Stage) VentanaPrincipal.getScene().getWindow();

      // Cargar el archivo FXML de la nueva ventana
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
      Pane root = loader.load();

      // Crear una nueva escena con el root cargado
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      // Crear un nuevo Stage
      Stage nuevaVentana = new Stage();
      nuevaVentana.setScene(scene);

      // Maximizar la ventana
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);

      // Mostrar la nueva ventana
      nuevaVentana.show();

      // Cerrar la ventana principal
      ventanaPrincipal.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
	
	
	
	
	
	@FXML
	void flechaAtrasPressed(MouseEvent event) {
		try {
			// Obtener el Stage de la ventana principal y cerrarla
			Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Cargar el nuevo archivo FXML (el que contiene la vista "Crear Cuenta")
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			BorderPane root = loader.load();

			// Crear una nueva escena con el root cargado
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

			// Crear un nuevo Stage (ventana) para la "Crear Cuenta"
			Stage nuevaVentana = new Stage();
			nuevaVentana.setTitle("Crear Cuenta");
			nuevaVentana.setScene(scene);

			// Maximizar la ventana
			nuevaVentana.setMaximized(true);
			nuevaVentana.setResizable(false);
			nuevaVentana.initStyle(StageStyle.UNDECORATED);

			// Mostrar la nueva ventana
			nuevaVentana.show();

			// Transición de desvanecimiento para la primera ventana
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), nuevaVentana.getScene().getRoot());
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);

			ventanaPrincipal.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
