package models;

public class Juego {
  private String imagen;
  private String nombre;
  private String genero;
  private String tiempo_jugado;

  public String getImagen() {
    return imagen;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  public String getTiempo_jugado() {
    return tiempo_jugado;
  }

  public void setTiempo_jugado(String tiempo_jugado) {
    this.tiempo_jugado = tiempo_jugado;
  }
}
