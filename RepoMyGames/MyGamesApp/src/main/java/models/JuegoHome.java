package models;

import java.util.List;

public class JuegoHome {

	@Override
	public String toString() {
		return "JuegoHome [imagenFondo=" + imagenFondo + ", titulo=" + titulo + ", idJuego=" + idJuego + ", plataformas="
				+ plataformas + "]";
	}

	String imagenFondo;
	String titulo;
	int idJuego;
	List<String> plataformas;

	public JuegoHome(String imagenFondo, String titulo, int idJuego, List<String> plataformas) {
		this.imagenFondo = imagenFondo;
		this.titulo = titulo;
		this.idJuego = idJuego;
		this.plataformas = plataformas;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the imagenFondo
	 */
	public String getImagenFondo() {
		return imagenFondo;
	}

	/**
	 * @return the idJuego
	 */
	public int getIdJuego() {
		return idJuego;
	}

	/**
	 * @return the plataformas
	 */
	public List<String> getPlataformas() {
		return plataformas;
	}

	/**
	 * @param imagenFondo the imagenFondo to set
	 */
	public void setImagenFondo(String imagenFondo) {
		this.imagenFondo = imagenFondo;
	}

	/**
	 * @param idJuego the idJuego to set
	 */
	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	/**
	 * @param plataformas the plataformas to set
	 */
	public void setPlataformas(List<String> plataformas) {
		this.plataformas = plataformas;
	}

}
