package controllers;

import java.io.ByteArrayInputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Juego;

/**
 * Controlador de Juego
 */
public class JuegoController {

  @FXML
  private ImageView imgJuego;

  @FXML
  private Label lblNombre;

  @FXML
  private Label lblGenero;

  @FXML
  private Label lblTiempoJuego;

  public void setData(Juego juego) {
    // Convertir el arreglo de bytes de la imagen a un InputStream
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(juego.getImagen());

    // Crear una imagen a partir del InputStream
    Image image = new Image(byteArrayInputStream);

    // Establecer la imagen en el ImageView
    imgJuego.setImage(image);

    // Establecer los valores de los labels
    lblNombre.setText(juego.getNombre());
    lblGenero.setText(juego.getGenero());
    lblTiempoJuego.setText(juego.getTiempo_jugado());
  }

}
