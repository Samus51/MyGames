package utils;

import java.io.IOException;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaUtil {
    public static void abrirVentana(String fxmlPath, String titulo, String stylesheet, Consumer<Object> controladorConsumer, MouseEvent event) throws IOException {
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

        obtenerVentana(event).close();
        System.out.println("Ventana cerrada");
    }

    private static Stage obtenerVentana(MouseEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
