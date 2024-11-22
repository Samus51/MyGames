package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class VentanaPrincipalController implements Initializable{

  @FXML
  private BorderPane VentanaPrincipal;

  @FXML
  private Button btnLogin;

  @FXML
  private ImageView imgLogo;

  @FXML
  private ImageView imgOjo;

  @FXML
  private Label lblUser;

  @FXML
  private Label lblUser1;

  @FXML
  private BorderPane panelLogo;

  @FXML
  private TextField txtPasswordClear;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Image img = new Image(getClass().getResourceAsStream("/Logo.png"));
    imgLogo.setImage(img);
  }

}
