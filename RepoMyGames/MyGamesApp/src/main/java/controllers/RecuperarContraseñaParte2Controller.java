package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RecuperarContraseñaParte2Controller {

    @FXML
    private Button btnTogglePassword;

    @FXML
    private ImageView imgTogglePassword;

    @FXML
    private Pane paginaFondo;

    @FXML
    private TextField txtConfirmarPassword;

    @FXML
    private TextField txtConfirmarPassword1;

    @FXML
    private PasswordField txtConfirmarPasswordOculto;

    @FXML
    private PasswordField txtConfirmarPasswordOculto1;

    // Variable para controlar la visibilidad de las contraseñas
    private boolean isPasswordVisible = false;

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        // Cambiar la visibilidad de los campos
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            // Mostrar contraseñas en texto
            txtConfirmarPasswordOculto.setVisible(false);
            txtConfirmarPassword.setVisible(true);
            txtConfirmarPassword.setText(txtConfirmarPasswordOculto.getText());

            txtConfirmarPasswordOculto1.setVisible(false);
            txtConfirmarPassword1.setVisible(true);
            txtConfirmarPassword1.setText(txtConfirmarPasswordOculto1.getText());

            // Cambiar la imagen del botón
            imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegroTachado.png")));
        } else {
            // Volver a ocultar contraseñas
            txtConfirmarPasswordOculto.setVisible(true);
            txtConfirmarPassword.setVisible(false);
            txtConfirmarPasswordOculto.setText(txtConfirmarPassword.getText());

            txtConfirmarPasswordOculto1.setVisible(true);
            txtConfirmarPassword1.setVisible(false);
            txtConfirmarPasswordOculto1.setText(txtConfirmarPassword1.getText());

            // Cambiar la imagen del botón
            imgTogglePassword.setImage(new Image(getClass().getResourceAsStream("/ojoNegro.png")));
        }
    }
}
