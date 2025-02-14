package models;

public class Juego {
  private byte[] imagen;  
  private String nombre;
  private String genero;
  private String tiempo_jugado;

  // Getter y setter para la imagen
  public byte[] getImagen() {
    return imagen;
  }

  public void setImagen(byte[] imagen) {
    this.imagen = imagen;
  }

  // Getter y setter para el nombre
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  // Getter y setter para el género
  public String getGenero() {
    return genero;
  }

  public void setGenero(String genero) {
    this.genero = genero;
  }

  // Getter y setter para el tiempo jugado
  public String getTiempo_jugado() {
    return tiempo_jugado;
  }

  public void setTiempo_jugado(String tiempo_jugado) {
    this.tiempo_jugado = tiempo_jugado;
  }
}
