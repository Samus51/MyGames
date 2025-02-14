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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class LoginTest {

  private Stage stage;

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

  @BeforeEach
  public void configurar() throws Exception {
    FxToolkit.setupStage(s -> {
      s.toFront();
    });
  }

  @AfterEach
  public void limpiar() throws Exception {
    FxToolkit.hideStage();
  }

  @Test
  void probarTextoBotonLogin() {
    FxAssert.verifyThat("#btnLogin", LabeledMatchers.hasText("Iniciar Sesión"));
  }

  @Test
  void probarLoginConCredencialesValidas(FxRobot fxRobot) {
    // Ingresar usuario y contraseña correctos
    fxRobot.clickOn("#txtUsuario").write("UsuarioTest");
    fxRobot.clickOn("#txtPasswordOculto").write("Password123!");
    fxRobot.clickOn("#btnLogin");

    // Esperar un momento para que la transición ocurra
    fxRobot.sleep(20000);

    // Intentar obtener un nodo de la nueva pantalla
    boolean nuevoElementoPresente = fxRobot.lookup("#lblMasPopularas").tryQuery().isPresent();

    // Verificar si el nuevo elemento está presente
    assertTrue(nuevoElementoPresente, "El login no cambió correctamente de pantalla.");
  }

  @Test
  void probarLoginConCredencialesInvalidas(FxRobot fxRobot) {
    // Ingresar usuario y contraseña incorrectos
    fxRobot.clickOn("#txtUsuario").write("usuario_incorrecto");
    fxRobot.clickOn("#txtPasswordOculto").write("contraseña_incorrecta");
    fxRobot.clickOn("#btnLogin");

    // Esperar la alerta de error
    fxRobot.sleep(1000);

    // Verificar que la alerta de error esté visible
    FxAssert.verifyThat(".alert", NodeMatchers.isVisible());
    FxAssert.verifyThat(".alert .content", LabeledMatchers.hasText("Usuario o contraseña incorrectos"));
  }

  @Test
  void probarMostrarOcultarContraseña(FxRobot fxRobot) {
    // Verificar que el campo de la contraseña oculta es visible inicialmente
    assertTrue(fxRobot.lookup("#txtPasswordOculto").queryTextInputControl().isVisible());

    // Hacer clic en el botón para mostrar la contraseña
    fxRobot.clickOn("#btnOjoPassword");

    // Esperar un poco para que se muestre la contraseña
    fxRobot.sleep(500);

    // Verificar que el campo de contraseña visible ahora es visible
    assertTrue(fxRobot.lookup("#txtPassword").queryTextInputControl().isVisible());
  }

  @Test
  void probarCerrarVentana(FxRobot fxRobot) {
    // Hacer clic en el botón de cerrar ventana
    fxRobot.clickOn("#imgClose");

    // Esperar un segundo
    fxRobot.sleep(1000);

    // Verificar que la ventana esté cerrada
    assertFalse(stage.isShowing(), "La ventana debería cerrarse.");
  }

  @Test
  void probarMinimizarVentana(FxRobot fxRobot) {
    // Hacer clic en el botón de minimizar ventana
    fxRobot.clickOn("#imgMinimizar");

    // Esperar un segundo
    fxRobot.sleep(1000);

    // Verificar que la ventana esté minimizada
    assertTrue(stage.isIconified(), "La ventana debería minimizarse.");
  }
}
