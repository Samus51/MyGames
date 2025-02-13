package models;

import java.util.List;

public class Usuario {
	private int idUsuario;
	private String nombreUsuario;
	private String email;
	private String contrasena;
	private List<String> generosPreferidos;

	public Usuario(int idUsuario, String nombreUsuario, String email, String contrasena,
			List<String> generosPreferidos) {
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contrasena = contrasena;
		this.generosPreferidos = generosPreferidos;
	}

	// Métodos getter y setter para el ID
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", email=" + email
				+ ", contrasena=" + contrasena + ", generosPreferidos=" + generosPreferidos + "]";
	}

// Métodos getter y setter para el resto de los campos
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<String> getGenerosPreferidos() {
		return generosPreferidos;
	}

	public void setGenerosPreferidos(List<String> generosPreferidos) {
		this.generosPreferidos = generosPreferidos;
	}
}
