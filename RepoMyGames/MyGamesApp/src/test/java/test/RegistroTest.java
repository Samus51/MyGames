package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;

import controllers.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.VentanaUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class RegistroTest extends ApplicationTest {

	private RegistroController controller;
	private Stage stage;

	@Start
	public void iniciar(Stage stage) {
		this.stage = stage;
		try {
			// Cargar el archivo FXML y asociar el controlador
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Registro.fxml"));
			Parent mainNode = loader.load();
			controller = loader.getController();
			stage.setScene(new Scene(mainNode));
			stage.show();
			stage.toFront();
		} catch (IOException e) {
			fail("No se pudo cargar el archivo FXML.");
		}
	}

	@BeforeEach
	void setUp() {
		// Inicializar cosas que se necesiten antes de cada test
	}

	@Test
	void testValidarDatos() {
		// Email inválido
		boolean resultadoEmailInvalido = VentanaUtil.validarDatos("correo_invalido", "Password123!", "Password123!",
				Arrays.asList("Action"));
		assertFalse(resultadoEmailInvalido, "Debe fallar con un email inválido");

		// Contraseñas no coinciden
		boolean resultadoContraseniasNoCoinciden = VentanaUtil.validarDatos("test@email.com", "password1", "password2",
				Arrays.asList("RPG"));
		assertFalse(resultadoContraseniasNoCoinciden, "Debe fallar si las contraseñas no coinciden");

		// Lista de géneros vacía
		boolean resultadoListaGenerosVacia = VentanaUtil.validarDatos("test@email.com", "Password123!", "Password123!",
				Arrays.asList());
		assertFalse(resultadoListaGenerosVacia, "Debe fallar si no se selecciona ningún género");

		// Caso válido
		boolean resultadoValido = VentanaUtil.validarDatos("test@email.com", "Password123!", "Password123!",
				Arrays.asList("Action", "Adventure"));
		assertTrue(resultadoValido, "Debe pasar si los datos son correctos");
	}

	@Test
	void testCrearUsuario() {
		// Datos de prueba
		String nombre = "UsuarioTest";
		String email = "test@email.com";
		String password = "Password123!";
		List<String> generos = Arrays.asList("Action", "Adventure");

		// Intentar crear el usuario (esto dependería de una base de datos real)
		boolean resultado = controller.crearUsuario(nombre, email, password, generos);

		// Verificar que el usuario no fue creado (porque no hay conexión real)
		assertTrue(resultado, "El usuario se ha creado correctamente");
	}

	@Test
	void testGetGenerosIdsByNames() {
		// Simulación de nombres de géneros
		List<String> generos = Arrays.asList("Action", "RPG", "Strategy");

		// Verificar que no devuelva una lista vacía
		List<Integer> ids = controller.getGenerosIdsByNames(generos);
		assertNotNull(ids, "La lista de IDs no debe ser null");
		assertFalse(ids.isEmpty(), "Debe haber al menos un ID en la lista");
	}

	@Test
	void testMostrarPassword() {
		// Verificar que los controles no son nulos
		assertNotNull(controller, "El controlador no debería ser nulo");
		assertNotNull(controller.txtPasswordOculto, "El campo de contraseña oculta no debe ser nulo");
		assertNotNull(controller.txtPassword, "El campo de contraseña visible no debe ser nulo");

		// Verificamos la visibilidad inicial de los campos de contraseña
		assertTrue(controller.txtPasswordOculto.isVisible(),
				"El campo de la contraseña oculta debe ser visible inicialmente");
		assertFalse(controller.txtPassword.isVisible(), "El campo de la contraseña visible debe estar oculto inicialmente");

		// Llamamos al método para mostrar la contraseña
		controller.mostrarPassword();

		// Verificar que la contraseña ahora es visible
		assertTrue(controller.esPasswordVisible, "La contraseña debe mostrarse después de llamar a mostrarPassword()");
		assertFalse(controller.txtPasswordOculto.isVisible(),
				"El campo de la contraseña oculta debe estar oculto después de mostrar la contraseña");
		assertTrue(controller.txtPassword.isVisible(),
				"El campo de la contraseña visible debe estar visible después de mostrar la contraseña");

		// Llamamos de nuevo al método para ocultar la contraseña
		controller.mostrarPassword();

		// Verificar que la contraseña ahora es oculta nuevamente
		assertFalse(controller.esPasswordVisible,
				"La contraseña debe ocultarse después de llamar a mostrarPassword() otra vez");
		assertTrue(controller.txtPasswordOculto.isVisible(),
				"El campo de la contraseña oculta debe estar visible después de ocultar la contraseña");
		assertFalse(controller.txtPassword.isVisible(),
				"El campo de la contraseña visible debe estar oculto después de ocultar la contraseña");
	}

	@Test
	void testCerrarVentana(FxRobot fxRobot) {
		// Simula un clic en el botón de cerrar ventana
		fxRobot.clickOn("#imgCerrar");

		// Espera un poco para asegurarte de que la acción de cierre se complete
		fxRobot.sleep(1000);

		// Verifica que la ventana esté cerrada
		assertFalse(stage.isShowing(), "La ventana debería cerrarse.");
	}

	@Test
	void testMinimizarVentana(FxRobot fxRobot) {
		// Simula un clic en el botón de minimizar ventana
		fxRobot.clickOn("#imgMinimizar");

		// Espera un poco para asegurarte de que la acción de minimizar se complete
		fxRobot.sleep(1000);

		// Verifica que la ventana esté minimizada
		assertTrue(stage.isIconified(), "La ventana debería minimizarse.");
	}
}
