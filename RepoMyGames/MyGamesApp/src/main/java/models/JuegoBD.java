package models;

import java.util.List;

public class JuegoBD {

  String imagenFondo;
  String titulo;
  int idJuego;
  List<String> plataformas;
  byte[] imagenPrincipal;
  byte[] imagenSecundaria;
  byte[] imagenTerciaria;
  byte[] imagenCuarta;
  byte[] imagenQuinta;

  public JuegoBD(String imagenFondo, String titulo, int idJuego, List<String> plataformas, byte[] imagenPrincipal,
      byte[] imagenSecundaria, byte[] imagenTerciaria, byte[] imagenCuarta, byte[] imagenQuinta) {
    this.imagenFondo = imagenFondo;
    this.titulo = titulo;
    this.idJuego = idJuego;
    this.plataformas = plataformas;
    this.imagenPrincipal = imagenPrincipal;
    this.imagenSecundaria = imagenSecundaria;
    this.imagenTerciaria = imagenTerciaria;
    this.imagenCuarta = imagenCuarta;
    this.imagenQuinta = imagenQuinta;
  }

  @Override
  public String toString() {
    return "JuegoHome [imagenFondo=" + imagenFondo + ", titulo=" + titulo + ", idJuego=" + idJuego + ", plataformas="
        + plataformas + "]";
  }

  // Getters y Setters
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getImagenFondo() {
    return imagenFondo;
  }

  public void setImagenFondo(String imagenFondo) {
    this.imagenFondo = imagenFondo;
  }

  public int getIdJuego() {
    return idJuego;
  }

  public void setIdJuego(int idJuego) {
    this.idJuego = idJuego;
  }

  public List<String> getPlataformas() {
    return plataformas;
  }

  public void setPlataformas(List<String> plataformas) {
    this.plataformas = plataformas;
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
}
