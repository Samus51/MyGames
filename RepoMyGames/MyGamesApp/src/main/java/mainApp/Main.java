package mainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      // Cargar el archivo FXML
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
      Pane root = loader.load();
      primaryStage.setMaximized(true);
      primaryStage.initStyle(StageStyle.UNDECORATED);

      Screen screen = Screen.getPrimary();
      Rectangle2D bounds = screen.getVisualBounds();

      Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());

      // Cargar la hoja de estilos
      scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

      // Establecer la escena y mostrar la ventana
      primaryStage.setTitle("MyGames");
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
