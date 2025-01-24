package utils;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginTask extends Task<Void> {
  private final Node eventSource;
  private final Button btnLogin;
  private final Node cargando;

  public LoginTask(Node eventSource, Button btnLogin, Node cargando) {
    this.eventSource = eventSource;
    this.btnLogin = btnLogin;
    this.cargando = cargando;
  }

  @Override
  protected Void call() throws Exception {
    Thread.sleep(5000);
    return null;
  }

  @Override
  protected void succeeded() {
    try {
      Stage ventanaPrincipal = (Stage) eventSource.getScene().getWindow();

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
      BorderPane root = loader.load();

      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      Stage nuevaVentana = new Stage();
      nuevaVentana.setTitle("Home");
      nuevaVentana.setScene(scene);
      nuevaVentana.setMaximized(true);
      nuevaVentana.setResizable(false);
      nuevaVentana.initStyle(StageStyle.UNDECORATED);
      nuevaVentana.show();

      ventanaPrincipal.close();
    } catch (IOException e) {
      e.printStackTrace();
      failed();
    }
  }

  @Override
  protected void failed() {
    // Mostrar alerta de error
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Login");
    alerta.setContentText("Hubo un error al procesar la solicitud.");
    alerta.setHeaderText("Error de Login");
    alerta.showAndWait();

    // Restaurar la UI
    restaurarUI();
  }

  private void restaurarUI() {
    btnLogin.setText("Iniciar sesión");
    btnLogin.setDisable(false);
    cargando.setVisible(false);
  }
}
