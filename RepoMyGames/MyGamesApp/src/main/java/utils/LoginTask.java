package utils;

import java.util.function.Consumer;

import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import models.Usuario;

public class LoginTask extends Task<Void> {
	private final Usuario usuario;
	private final Consumer<Usuario> onSuccess;
	private final Runnable onFailure;
	private final MouseEvent event;

	public LoginTask(Usuario usuario, Consumer<Usuario> onSuccess, Runnable onFailure, MouseEvent event) {
		this.usuario = usuario;
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
		this.event = event;
	}

	@Override
	protected Void call() throws Exception {
		// Simulación de espera
		Thread.sleep(5000);
		return null;
	}

	@Override
	protected void succeeded() {
		onSuccess.accept(usuario); // Llamamos a la función onSuccess pasando el usuario
	}

	@Override
	protected void failed() {
		onFailure.run(); // Si falla, se ejecuta onFailure
	}
}
