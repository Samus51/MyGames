package models;

import java.util.List;

public class JuegoBD {
  private int idJuego;
  private String titulo;
  private String descripcion;
  private String fechaLanzamiento;
  private boolean creadoPorUsuario;
  private int idUsuario;
  private int tiempoJugado;
  private String desarrolladores;
  private byte[] imagenPrincipal;
  private byte[] imagenSecundaria;
  private byte[] imagenTerciaria;  // Se renombró para que coincida con el nombre de columna
  private byte[] imagenCuarta;
  private byte[] imagenQuinta;
  private int pegi;
  private String generos;
  private List<String> plataformas;

  // Constructor completo
  public JuegoBD(int idJuego, String titulo, String descripcion, String fechaLanzamiento, boolean creadoPorUsuario,
                 int idUsuario, int tiempoJugado, String desarrolladores, byte[] imagenPrincipal, byte[] imagenSecundaria,
                 byte[] imagenTerciaria, byte[] imagenCuarta, byte[] imagenQuinta, int pegi, String generos,
                 List<String> plataformas) {
    this.idJuego = idJuego;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fechaLanzamiento = fechaLanzamiento;
    this.creadoPorUsuario = creadoPorUsuario;
    this.idUsuario = idUsuario;
    this.tiempoJugado = tiempoJugado;
    this.desarrolladores = desarrolladores;
    this.imagenPrincipal = imagenPrincipal;
    this.imagenSecundaria = imagenSecundaria;
    this.imagenTerciaria = imagenTerciaria;
    this.imagenCuarta = imagenCuarta;
    this.imagenQuinta = imagenQuinta;
    this.pegi = pegi;
    this.generos = generos;
    this.plataformas = plataformas;
  }

  // Constructor reducido para inicializar solo algunos campos
  public JuegoBD(int idJuego, String titulo, List<String> plataformas, byte[] imagenPrincipal) {
    this.idJuego = idJuego;
    this.titulo = titulo;
    this.plataformas = plataformas;
    this.imagenPrincipal = imagenPrincipal;
  }

  // Getters y Setters
  public int getIdJuego() {
    return idJuego;
  }

  public void setIdJuego(int idJuego) {
    this.idJuego = idJuego;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFechaLanzamiento() {
    return fechaLanzamiento;
  }

  public void setFechaLanzamiento(String fechaLanzamiento) {
    this.fechaLanzamiento = fechaLanzamiento;
  }

  public boolean isCreadoPorUsuario() {
    return creadoPorUsuario;
  }

  public void setCreadoPorUsuario(boolean creadoPorUsuario) {
    this.creadoPorUsuario = creadoPorUsuario;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getTiempoJugado() {
    return tiempoJugado;
  }

  public void setTiempoJugado(int tiempoJugado) {
    this.tiempoJugado = tiempoJugado;
  }

  public String getDesarrolladores() {
    return desarrolladores;
  }

  public void setDesarrolladores(String desarrolladores) {
    this.desarrolladores = desarrolladores;
  }

  public byte[] getImagenPrincipal() {
    return imagenPrincipal;
  }

  public void setImagenPrincipal(byte[] imagenPrincipal) {
    this.imagenPrincipal = imagenPrincipal;
  }

  public byte[] getImagenSecundaria() {
    return imagenSecundaria;
  }

  public void setImagenSecundaria(byte[] imagenSecundaria) {
    this.imagenSecundaria = imagenSecundaria;
  }

  public byte[] getImagenTerciaria() {
    return imagenTerciaria;
  }

  public void setImagenTerciaria(byte[] imagenTerciaria) {
    this.imagenTerciaria = imagenTerciaria;
  }

  public byte[] getImagenCuarta() {
    return imagenCuarta;
  }

  public void setImagenCuarta(byte[] imagenCuarta) {
    this.imagenCuarta = imagenCuarta;
  }

  public byte[] getImagenQuinta() {
    return imagenQuinta;
  }

  public void setImagenQuinta(byte[] imagenQuinta) {
    this.imagenQuinta = imagenQuinta;
  }

  public int getPegi() {
    return pegi;
  }

  public void setPegi(int pegi) {
    this.pegi = pegi;
  }

  public String getGeneros() {
    return generos;
  }

  public void setGeneros(String generos) {
    this.generos = generos;
  }

  public List<String> getPlataformas() {
    return plataformas;
  }

  public void setPlataformas(List<String> plataformas) {
    this.plataformas = plataformas;
  }
}
