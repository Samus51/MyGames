package controllers;

import java.util.List;

public class Usuario {
	private String nombreUsuario;
	private String email;
	private String contraseña;
	private List<String> generosPreferidos;

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the contraseña
	 */
	public String getContraseña() {
		return contraseña;
	}

	/**
	 * @return the generosPreferidos
	 */
	public List<String> getGenerosPreferidos() {
		return generosPreferidos;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param contraseña the contraseña to set
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	/**
	 * @param generosPreferidos the generosPreferidos to set
	 */
	public void setGenerosPreferidos(List<String> generosPreferidos) {
		this.generosPreferidos = generosPreferidos;
	}

	public Usuario(String nombreUsuario, String email, String contraseña, List<String> generosPreferidos) {
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.generosPreferidos = generosPreferidos;
	}

}
