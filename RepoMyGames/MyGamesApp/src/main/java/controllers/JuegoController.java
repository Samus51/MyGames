package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Juego;

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
    Image image = new Image(getClass().getResourceAsStream(juego.getImagen()));
    imgJuego.setImage(image);
    lblNombre.setText(juego.getNombre());
    lblGenero.setText(juego.getGenero());
    lblTiempoJuego.setText(juego.getTiempo_jugado());
  }
}
