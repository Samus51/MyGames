package utils;

import org.json.JSONObject;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorAPI {

  private static final String API_URL_TEMPLATE = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=new&page=%d";
  private static final String API_URL_1JUEGO = "https://api.rawg.io/api/games/%d?key=fcc27fd8089c4a42a452702e7f522258";

  // Obtener juegos y sus IDs
  public static Map<Integer, List<String>> getJuegosIDsYPlataformas(int pages) {
    Map<Integer, List<String>> gameData = new HashMap<>();

    for (int i = 1; i <= pages; i++) {
      try {
        String apiUrl = String.format(API_URL_TEMPLATE, i);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          StringBuilder response = new StringBuilder();
          String line;

          while ((line = in.readLine()) != null) {
            response.append(line);
          }
          in.close();

          JSONObject json = new JSONObject(response.toString());
          JSONArray results = json.getJSONArray("results");

          for (int j = 0; j < results.length(); j++) {
            JSONObject game = results.getJSONObject(j);
            int gameId = game.optInt("id", -1);

            if (gameId != -1) {
              JSONArray platformsArray = game.optJSONArray("platforms");
              List<String> platforms = new ArrayList<>();

              if (platformsArray != null) {
                for (int k = 0; k < platformsArray.length(); k++) {
                  JSONObject platformObj = platformsArray.getJSONObject(k);
                  String platformName = platformObj.optJSONObject("platform").optString("name", "Desconocido");
                  platforms.add(platformName);
                }
              }

              gameData.put(gameId, platforms);
            }
          }
        } else {
          System.out.println("Error al conectar con la API: " + connection.getResponseCode());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return gameData;
  }

  // Obtener detalles de un juego por su ID
  public static void obtenerDetallesJuego(int gameId) {
    List<String> generos = new ArrayList<String>();
    try {
      String detallesUrl = String.format(API_URL_1JUEGO, gameId);
      URL url = new URL(detallesUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      if (connection.getResponseCode() == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        // Obtener datos del juego
        String name = json.optString("name", "Nombre no disponible");
        String released = json.optString("released", "Fecha no disponible");
        String description = json.optString("description", "Descripción no disponible");
        String descripcionBuena = obtenerDescripcionIngles(description);
        String descripcionLimpia = limpiarDescripcion(descripcionBuena);
        String playtime = json.optString("playtime", "PlayTime no disponible");

        // Obtener géneros
        JSONArray genres = json.optJSONArray("genres");
        if (genres != null && genres.length() > 0) {
          // Recorre todos los géneros
          for (int i = 0; i < genres.length(); i++) {
            String nombreGenero = genres.getJSONObject(i).optString("name", "Género no disponible");
            generos.add(nombreGenero);
          }
        } else {
          System.out.println("Género no disponible");
        }

        // Obtener desarrolladores
        JSONArray developers = json.optJSONArray("developers");
        if (developers != null && developers.length() > 0) {
          String nombreDev = developers.getJSONObject(0).optString("name", "Desarrollador no disponible");
        } else {
          System.out.println("Desarrollador no disponible");
        }

        // Obtener todas las plataformas
        StringBuilder platformsList = new StringBuilder();
        if (json.has("platforms") && json.get("platforms") instanceof JSONArray) {
          JSONArray platforms = json.getJSONArray("platforms");
          for (int k = 0; k < platforms.length(); k++) {
            JSONObject platformObj = platforms.getJSONObject(k).getJSONObject("platform");
            String platformName = platformObj.optString("name", "Plataforma desconocida");
            platformsList.append(platformName);
            if (k < platforms.length() - 1) {
              platformsList.append(", ");
            }
          }
        } else {
          platformsList.append("Plataforma no disponible");
        }

        // Imprimir datos del juego
        System.out.println("Nombre: " + name);
        System.out.println("Fecha de lanzamiento: " + released);
        System.out.println("Plataformas: " + platformsList.toString());
        System.out.println("Descripción: " + descripcionLimpia);
        System.out.println("Generos: " + generos.toString());
        System.out.println("Tiempo de Juego: " + playtime + " H");
        System.out.println("-----------------------");
      } else {
        System.out.println("Error al obtener detalles del juego");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    Map<Integer, List<String>> gameIds = getJuegosIDsYPlataformas(1);

    long endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;

    System.out.println("TIEMPO: " + elapsedTime + " ms");
  }

  public static Map<String, List<String>> obtenerPrimeraCapturaYPlataformas(int gameId) {
    Map<String, List<String>> capturaYPlataformas = new HashMap<>();
    try {
      String detallesUrl = String.format(API_URL_1JUEGO, gameId);
      URL url = new URL(detallesUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      if (connection.getResponseCode() == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
          response.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());

        // Obtener captura de pantalla
        String foto = json.optString("background_image", "Imagen no disponible");

        // Obtener plataformas asociadas
        JSONArray platformsArray = json.optJSONArray("platforms");
        List<String> plataformas = new ArrayList<>();

        if (platformsArray != null) {
          for (int i = 0; i < platformsArray.length(); i++) {
            JSONObject platformObj = platformsArray.getJSONObject(i);
            String platformName = platformObj.optJSONObject("platform").optString("name", "Desconocido");
            plataformas.add(platformName);
          }
        }

        // Asociar la captura con las plataformas
        capturaYPlataformas.put(foto, plataformas);

      } else {
        System.out.println("Error al conectar con la API: " + connection.getResponseCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return capturaYPlataformas;
  }

  public static String obtenerDescripcionIngles(String descripcionCompleta) {
    String descripcionIngles = "";
    try {
      // Reemplazar caracteres escapados

      StringBuilder descripcionBuilder = new StringBuilder();
      String inicioIngles = "<p>";
      String finIngles = "</p>";

      int inicio = descripcionCompleta.indexOf(inicioIngles);
      while (inicio != -1) {
        int fin = descripcionCompleta.indexOf(finIngles, inicio);
        if (fin != -1) {
          String parrafo = descripcionCompleta.substring(inicio + inicioIngles.length(), fin).trim();
          // Agregar cada párrafo de la descripción
          descripcionBuilder.append(parrafo).append("\n");
          inicio = descripcionCompleta.indexOf(inicioIngles, fin);
        } else {
          inicio = -1;
        }
      }

      descripcionIngles = descripcionBuilder.toString().trim();
      descripcionIngles = descripcionIngles.replace("<br />", "\n");

    } catch (Exception e) {
      e.printStackTrace();
      descripcionIngles = "Error al procesar la descripción.";
    }
    return descripcionIngles;
  }

  public static String limpiarDescripcion(String descripcionCompleta) {
    String descripcionLimpia = "";
    try {
      // Reemplazar caracteres escapados
      descripcionLimpia = descripcionCompleta.replace("\\u003C", "<") // Reemplazar etiquetas abiertas
          .replace("\\u003E", ">") // Reemplazar etiquetas cerradas
          .replace("\\n", "\n") // Reemplazar saltos de línea
          .replace("&#39;", "'") // Reemplazar comillas simples
          .replace("<br />", "\n"); // Reemplazar etiquetas de salto de línea

    } catch (Exception e) {
      e.printStackTrace();
      descripcionLimpia = "Error al procesar la descripción.";
    }
    return descripcionLimpia;
  }

}
