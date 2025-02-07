package models;

import java.util.List;

public class JuegoPachorra {
	private String imagenPrincipal;
	private List<String> capturasImagenes;
	private String titulo;
	private String fechaLanzamiento;
	private int idJuego;
	private int playtime;
	private int metacriticScore;
	List<String> plataformas;
	private List<String> generos;
	private int tiempo_jugado;
	private String pegi;
	private List<String> devs;

	/**
	 * @return the imagenPrincipal
	 */
	public String getImagenPrincipal() {
		return imagenPrincipal;
	}

	@Override
	public String toString() {
		return "JuegoPachorra [imagenPrincipal=" + imagenPrincipal + ", capturasImagenes=" + capturasImagenes + ", titulo="
				+ titulo + ", fechaLanzamiento=" + fechaLanzamiento + ", idJuego=" + idJuego + ", metacriticScore="
				+ metacriticScore + ", plataformas=" + plataformas + ", generos=" + generos + ", tiempo_jugado=" + tiempo_jugado
				+ ", pegi=" + pegi + ", devs=" + devs + ", descripcion=" + descripcion + "]";
	}

	public JuegoPachorra(String imagenPrincipal, List<String> capturasImagenes, String titulo, String fechaLanzamiento,
			int idJuego, int metacriticScore, List<String> plataformas, List<String> generos, int tiempo_jugado, String pegi,
			List<String> devs, String descripcion) {
		this.imagenPrincipal = imagenPrincipal;
		this.capturasImagenes = capturasImagenes;
		this.titulo = titulo;
		this.fechaLanzamiento = fechaLanzamiento;
		this.idJuego = idJuego;
		this.metacriticScore = metacriticScore;
		this.plataformas = plataformas;
		this.generos = generos;
		this.tiempo_jugado = tiempo_jugado;
		this.pegi = pegi;
		this.devs = devs;
		this.descripcion = descripcion;
	}

	/**
	 * @return the capturasImagenes
	 */
	public List<String> getCapturasImagenes() {
		return capturasImagenes;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @return the fechaLanzamiento
	 */
	public String getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	/**
	 * @return the idJuego
	 */
	public int getIdJuego() {
		return idJuego;
	}

	/**
	 * @return the metacriticScore
	 */
	public int getMetacriticScore() {
		return metacriticScore;
	}

	/**
	 * @return the plataformas
	 */
	public List<String> getPlataformas() {
		return plataformas;
	}

	/**
	 * @return the generos
	 */
	public List<String> getGeneros() {
		return generos;
	}

	/**
	 * @return the tiempo_jugado
	 */
	public int getTiempo_jugado() {
		return tiempo_jugado;
	}

	/**
	 * @return the pegi
	 */
	public String getPegi() {
		return pegi;
	}

	/**
	 * @return the devs
	 */
	public List<String> getDevs() {
		return devs;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param imagenPrincipal the imagenPrincipal to set
	 */
	public void setImagenPrincipal(String imagenPrincipal) {
		this.imagenPrincipal = imagenPrincipal;
	}

	/**
	 * @param capturasImagenes the capturasImagenes to set
	 */
	public void setCapturasImagenes(List<String> capturasImagenes) {
		this.capturasImagenes = capturasImagenes;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @param fechaLanzamiento the fechaLanzamiento to set
	 */
	public void setFechaLanzamiento(String fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	/**
	 * @param idJuego the idJuego to set
	 */
	public void setIdJuego(int idJuego) {
		this.idJuego = idJuego;
	}

	/**
	 * @param metacriticScore the metacriticScore to set
	 */
	public void setMetacriticScore(int metacriticScore) {
		this.metacriticScore = metacriticScore;
	}

	/**
	 * @param plataformas the plataformas to set
	 */
	public void setPlataformas(List<String> plataformas) {
		this.plataformas = plataformas;
	}

	/**
	 * @param generos the generos to set
	 */
	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}

	/**
	 * @param tiempo_jugado the tiempo_jugado to set
	 */
	public void setTiempo_jugado(int tiempo_jugado) {
		this.tiempo_jugado = tiempo_jugado;
	}

	/**
	 * @param pegi the pegi to set
	 */
	public void setPegi(String pegi) {
		this.pegi = pegi;
	}

	/**
	 * @param devs the devs to set
	 */
	public void setDevs(List<String> devs) {
		this.devs = devs;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	private String descripcion;

}
