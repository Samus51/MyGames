package utils;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import models.JuegoBD;

public class ImagenBDUtils {

  // 378, 199, Contenedores Info y Plataformas
  public static void asignarImagenes(HBox hbox, List<JuegoBD> juegos) {
    int imageIndex = 0;

    // Lista de nombres de consolas retro
    String[] consolasRetro = { "NES", "SNES", "Nintendo 64", "GameCube", "Game Boy", "Game Boy Color",
        "Game Boy Advance", "Nintendo DS", "Nintendo 3DS", "SEGA Genesis", "SEGA Saturn", "SEGA Dreamcast",
        "Atari 2600", "Atari 5200", "Atari 7800", "Neo Geo" };

    for (Node node : hbox.getChildren()) {
      if (node instanceof VBox) {
        VBox vbox = (VBox) node;

        // Obtener el juego correspondiente
        if (imageIndex < juegos.size()) {
          JuegoBD juego = juegos.get(imageIndex);

          // Buscar dentro del VBox el contenedor de información (contInfo)
          for (Node vboxChild : vbox.getChildren()) {
            // Asignar la captura al ImageView principal
            if (vboxChild instanceof ImageView) {
              ImageView imageView = (ImageView) vboxChild;

              byte[] imagenPrincipal = juego.getImagenPrincipal(); // Obtener la imagen principal del juego
              if (imagenPrincipal != null && imagenPrincipal.length > 0) {
                // Cargar la imagen desde el array de bytes
                try {
                  Image image = new Image(new ByteArrayInputStream(imagenPrincipal));
                  imageView.setImage(image);
                } catch (IllegalArgumentException e) {
                  // Si hay un error al cargar la imagen, usar una imagen predeterminada
                  imageView.setImage(new Image("Logo.png"));
                }
              } else {
                // Si la imagen es null o vacía, establecer una imagen predeterminada
                imageView.setImage(new Image("Logo.png"));
              }

              imageView.setPreserveRatio(false);
              imageView.setSmooth(true);

              // Aplicar esquinas redondeadas
              double cornerRadius = 20;
              Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
              clip.setArcWidth(cornerRadius);
              clip.setArcHeight(cornerRadius);
              imageView.setClip(clip);

              imageView.setFitWidth(imageView.getFitWidth());
              imageView.setFitHeight(imageView.getFitHeight());
            }

            // Buscar el contInfo para asignar imágenes a las 5 capturas dentro del HBox
            if (vboxChild instanceof VBox) {
              VBox contInfo = (VBox) vboxChild;

              for (Node contInfoChild : contInfo.getChildren()) {
                // Si encontramos el HBox con las imágenes
                if (contInfoChild instanceof HBox) {
                  HBox contenedorImagenes = (HBox) contInfoChild;

                  // Lista de logos por defecto (para asegurarnos de que siempre haya 5 imágenes)
                  String[] logosPorDefecto = { "imgLogos/logoPlay.png", "imgLogos/logoPc.png", "imgLogos/logoXbox.png",
                      "imgLogos/logoNintendo.png", "imgLogos/logoRetro.png" };

                  // Mapa de versiones en color de los logos si la plataforma está presente en el
                  // juego
                  Map<String, String> logosColor = Map.of("Playstation", "imgLogos/logoPlayColor.png", "Pc",
                      "imgLogos/logoPcColor.png", "Xbox", "imgLogos/logoXboxColor.png", "Nintendo",
                      "imgLogos/logoNintendoColor.png", "Retro", "imgLogos/logoRetroColor.png");

                  // Índice para recorrer las imágenes dentro del HBox
                  int index = 0;

                  // Verificar si alguna plataforma del juego es retro
                  boolean esConsolaRetro = false;
                  for (String plataforma : juego.getPlataformas()) {
                    for (String consolaRetro : consolasRetro) {
                      if (plataforma.toLowerCase().contains(consolaRetro.toLowerCase())) {
                        esConsolaRetro = true;
                        break;
                      }
                    }
                    if (esConsolaRetro)
                      break;
                  }

                  // Recorrer las imágenes dentro del contenedor
                  for (Node imagenNode2 : contenedorImagenes.getChildren()) {
                    if (imagenNode2 instanceof ImageView imageView2) {
                      // Definir la plataforma asociada a esta imagen en base a su posición
                      String plataformaClave = switch (index) {
                      case 0 -> "Playstation";
                      case 1 -> "Pc";
                      case 2 -> "Xbox";
                      case 3 -> "Nintendo";
                      case 4 -> "Retro";
                      default -> "Retro";
                      };

                      // Imagen por defecto
                      String logoPath = logosPorDefecto[index];

                      // Verificar si alguna plataforma del juego contiene la palabra clave
                      boolean tienePlataforma = false;
                      for (String plataforma : juego.getPlataformas()) {
                        if (plataforma.toLowerCase().contains(plataformaClave.toLowerCase())) {
                          tienePlataforma = true;
                          break;
                        }
                      }

                      // Si el juego tiene una plataforma retro, usar el logo retro
                      if (esConsolaRetro && plataformaClave.equals("Retro")) {
                        logoPath = logosColor.get("Retro");
                      } else if (tienePlataforma) {
                        logoPath = logosColor.get(plataformaClave);
                      }

                      try {
                        // Intentar cargar la imagen
                        imageView2.setImage(new Image(logoPath));
                      } catch (IllegalArgumentException e) {
                        // Si la URL es inválida, cargar la imagen por defecto
                        imageView2.setImage(new Image("imgLogos/Logo.png"));
                      }

                      imageView2.setPreserveRatio(false);
                      imageView2.setSmooth(true);

                      // Aplicar esquinas redondeadas
                      double cornerRadius2 = 10;
                      Rectangle clip2 = new Rectangle(imageView2.getFitWidth(), imageView2.getFitHeight());
                      clip2.setArcWidth(cornerRadius2);
                      clip2.setArcHeight(cornerRadius2);
                      imageView2.setClip(clip2);

                      imageView2.setFitWidth(imageView2.getFitWidth());
                      imageView2.setFitHeight(imageView2.getFitHeight());

                      index++;
                    }
                  }
                }

                // Asignar el título al Label independiente
                if (contInfoChild instanceof Label) {
                  Label tituloLabel = (Label) contInfoChild;

                  // Asignar el título del juego
                  tituloLabel.setText(juego.getTitulo());
                }
              }
            }
          }
        }

        imageIndex++;
      }
    }
  }
}
