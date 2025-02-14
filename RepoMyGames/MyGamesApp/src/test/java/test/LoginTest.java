package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.LoginController;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

/**
 * Clase de prueba para verificar el comportamiento del login de usuario.
 */
@ExtendWith(ApplicationExtension.class)
class LoginTest {

  private Stage stage;

  /**
   * Método que se ejecuta antes de cada prueba para cargar la interfaz de login y
   * asociar el controlador.
   *
   * @param stage La ventana principal de la aplicación.
   */
  @Start
  public void iniciar(Stage stage) {
    this.stage = stage;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
      Parent mainNode = loader.load();
      LoginController controller = loader.getController();

      assertNotNull(controller, "El controlador no se inicializó correctamente");

      stage.setScene(new Scene(mainNode));
      stage.show();
      stage.toFront();
    } catch (IOException e) {
      fail("No se pudo cargar el archivo FXML.");
    }
  }

  /**
   * Método que se ejecuta antes de cada prueba para configurar el entorno
   * necesario.
   *
   * @throws Exception Si ocurre un error durante la configuración.
   */
  @BeforeEach
  public void configurar() throws Exception {
    FxToolkit.setupStage(s -> {
      s.toFront();
    });
  }

  /**
   * Método que se ejecuta después de cada prueba para limpiar el entorno.
   *
   * @throws Exception Si ocurre un error durante la limpieza.
   */
  @AfterEach
  public void limpiar() throws Exception {
    FxToolkit.hideStage();
  }

  /**
   * Prueba para verificar que el texto del botón de login es correcto. Se asegura
   * de que el texto del botón diga "Iniciar Sesión".
   */
  @Test
  void testTextoBotonLogin() {
    FxAssert.verifyThat("#btnLogin", LabeledMatchers.hasText("Iniciar Sesión"));
  }

  /**
   * Prueba para verificar el comportamiento de login con credenciales válidas. Se
   * asegura de que el login redirige correctamente a la siguiente pantalla.
   *
   * @param fxRobot Objeto que simula las acciones del usuario.
   */
  @Test
  void testLoginConCredencialesValidas(FxRobot fxRobot) {
    // Ingresar usuario y contraseña correctos
    fxRobot.clickOn("#txtUsuario").write("UsuarioTest2");
    fxRobot.clickOn("#txtPasswordOculto").write("Password123!");
    fxRobot.clickOn("#btnLogin");

    // Esperar para que cargue el home
    fxRobot.sleep(20000);

    // Intentar obtener un nodo de la nueva pantalla
    boolean nuevoElementoPresente = fxRobot.lookup("#lblMasPopulares").tryQuery().isPresent();

    // Verificar si el nuevo elemento está presente
    assertTrue(nuevoElementoPresente, "El login no cambió correctamente de pantalla.");
  }

  /**
   * Prueba para verificar el comportamiento de login con credenciales inválidas.
   * Se asegura de que aparezca una alerta con el mensaje de "Usuario o contraseña
   * incorrectos".
   *
   * @param fxRobot Objeto que simula las acciones del usuario.
   */
  @Test
  void testLoginConCredencialesInvalidas(FxRobot fxRobot) {
    // Ingresar usuario y contraseña incorrectos
	    this.stage = stage;
	    try {
	      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
	      Parent mainNode = loader.load();
	      LoginController controller = loader.getController();

	      assertNotNull(controller, "El controlador no se inicializó correctamente");

	      stage.setScene(new Scene(mainNode));
	      stage.show();
	      stage.toFront();
	    } catch (IOException e) {
	      fail("No se pudo cargar el archivo FXML.");
	    }
	fxRobot.clickOn("#txtUsuario").write("usuario_incorrecto");
    fxRobot.clickOn("#txtPasswordOculto").write("contraseña_incorrecta");
    fxRobot.clickOn("#btnLogin");

    // Esperar la alerta de error
    fxRobot.sleep(1000);

    // Verificar que la alerta de error esté visible
    FxAssert.verifyThat(".alert", NodeMatchers.isVisible());
    FxAssert.verifyThat(".alert .content", LabeledMatchers.hasText("Usuario o contraseña incorrectos"));
  }

  /**
   * Prueba para verificar el comportamiento de mostrar y ocultar la contraseña.
   * Se asegura de que el campo de contraseña visible se muestre al hacer clic en
   * el botón del ojo.
   *
   * @param fxRobot Objeto que simula las acciones del usuario.
   */
  @Test
  void testMostrarOcultarContraseña(FxRobot fxRobot) {
    // Verificar que el campo de la contraseña oculta es visible inicialmente
    assertTrue(fxRobot.lookup("#txtPasswordOculto").queryTextInputControl().isVisible());

    // Hacer clic en el botón para mostrar la contraseña
    fxRobot.clickOn("#btnOjoPassword");
    fxRobot.sleep(500);

    // Verificar que el campo de contraseña visible ahora es visible
    assertTrue(fxRobot.lookup("#txtPassword").queryTextInputControl().isVisible());
  }

  /**
   * Prueba para simular el cierre de la ventana. Se verifica que la ventana se
   * cierre correctamente.
   *
   * @param fxRobot Objeto que simula las acciones del usuario.
   */
  @Test
  void testCerrarVentana(FxRobot fxRobot) {
    // Hacer clic en el botón de cerrar ventana
    fxRobot.clickOn("#imgClose");
    fxRobot.sleep(1000);

    // Verificar que la ventana esté cerrada
    assertFalse(stage.isShowing(), "La ventana debería cerrarse.");
  }

  /**
   * Prueba para simular la minimización de la ventana. Se verifica que la ventana
   * se minimice correctamente.
   *
   * @param fxRobot Objeto que simula las acciones del usuario.
   */
  @Test
  void testMinimizarVentana(FxRobot fxRobot) {
    // Hacer clic en el botón de minimizar ventana
    fxRobot.clickOn("#imgMinimizar");
    fxRobot.sleep(1000);

    // Verificar que la ventana esté minimizada
    assertTrue(stage.isIconified(), "La ventana debería minimizarse.");
  }
}
