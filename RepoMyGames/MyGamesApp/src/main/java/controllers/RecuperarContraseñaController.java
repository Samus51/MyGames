package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RecuperarContraseñaController {

  @FXML
  private ImageView imgFlechaAtras;

  @FXML
  private Pane paginaFondo;

  @FXML
  private TextField txtCorreo;

  @FXML
  void imgFlechaAtrasPressed(MouseEvent event) {
      try {
          // Obtener el Stage de la ventana actual (Recuperar Contraseña)
          Stage ventanaRecuperarContraseña = (Stage) ((Node) event.getSource()).getScene().getWindow();

          // Cargar el archivo FXML de la ventana principal
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VentanaPrincipal.fxml"));
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
          ventanaRecuperarContraseña.close();

      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  @FXML
  void btnEnviarPressed(MouseEvent event) {
  	 try {
       // Obtener el Stage de la ventana actual (Recuperar Contraseña)
       Stage ventanaRecuperarContraseña = (Stage) ((Node) event.getSource()).getScene().getWindow();

       // Cargar el archivo FXML de la ventana principal
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecuperarContraseñaParte2.fxml"));
       Pane root = loader.load();

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
       ventanaRecuperarContraseña.close();

   } catch (IOException e) {
       e.printStackTrace();
   }
}
  }


