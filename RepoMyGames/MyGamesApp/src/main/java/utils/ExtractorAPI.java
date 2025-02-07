package utils;

import org.json.JSONObject;

import models.JuegoPachorra;
import models.JuegoHome;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorAPI {

  private static final String API_KEY = "fcc27fd8089c4a42a452702e7f522258";
  private static final String API_URL_SEARCH = "https://api.rawg.io/api/games?key=" + API_KEY + "&search=%s";
  private static final String API_URL_GAME_DETAILS = "https://api.rawg.io/api/games/%d?key=" + API_KEY;
  private static final String API_URL_SEARCH_BUSQUEDA = "https://api.rawg.io/api/games?key=" + API_KEY + "&search=%s&page_size=12";

  private static final String API_URL_TEMPLATE_NEW = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=-released&released=2025-01-01&ratings=4&page=%d";
	private static final String API_URL_TEMPLATE = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=new&page=%d";
	private static final String API_URL_1JUEGO = "https://api.rawg.io/api/games/%d?key=fcc27fd8089c4a42a452702e7f522258";

	private static final String API_URL_TEMPLATE_SEARCH = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&search=%s";

  
  
	
	
	public static List<JuegoHome> buscarJuegoPorNombreBarra(String nombreJuego) {
    List<JuegoHome> juegos = new ArrayList<>();

    try {
        String apiUrl = String.format(API_URL_SEARCH_BUSQUEDA, URLEncoder.encode(nombreJuego, "UTF-8"));
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Error en la conexión: " + connection.getResponseCode());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        JSONArray results = json.optJSONArray("results");

        if (results == null || results.length() == 0) {
            System.out.println("No se encontraron resultados.");
            return juegos;
        }

        for (int i = 0; i < results.length(); i++) {
            JSONObject game = results.getJSONObject(i);
            int idJuego = game.optInt("id", -1);
            String titulo = game.optString("name", "No encontrado");
            String imagenPrincipal = game.optString("background_image", "Imagen no disponible");

            List<String> platforms = new ArrayList<>();
            JSONArray plataformasArray = game.optJSONArray("platforms");
            if (plataformasArray != null) {
                for (int j = 0; j < plataformasArray.length(); j++) {
                    JSONObject obj = plataformasArray.getJSONObject(j).optJSONObject("platform");
                    platforms.add(obj != null ? obj.optString("name", "Desconocido") : "Desconocido");
                }
            }

            juegos.add(new JuegoHome(imagenPrincipal, titulo, idJuego, platforms));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return juegos;
}

	
	
	
	
	
	
	
	
	public static JuegoPachorra buscarJuegoPorNombre(String nombreJuego) {
    try {
        // Construir URL de búsqueda
        String apiUrl = String.format(API_URL_SEARCH, URLEncoder.encode(nombreJuego, "UTF-8"));
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        
        if (connection.getResponseCode() != 200) {
            throw new IOException("Error en la conexión: " + connection.getResponseCode());
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        JSONObject json = new JSONObject(response.toString());
        JSONArray results = json.optJSONArray("results");

        if (results == null || results.length() == 0) {
            System.out.println("No se encontraron resultados.");
            return null;
        }
        
        JSONObject game = results.getJSONObject(0);
        int idJuego = game.optInt("id", -1);
        	
        String titulo = game.optString("name", "No encontrado");
        String imagenPrincipal = game.optString("background_image", "Imagen no disponible");
        int metacritic = game.optInt("metacritic", -1);
        String lanzamiento = game.optString("released", "Fecha no disponible");
        int playtime = game.optInt("playtime", -1);

        
        List<String> platforms = new ArrayList<>();
        JSONArray plataformasArray = game.optJSONArray("platforms");
        if (plataformasArray != null) {
            for (int i = 0; i < plataformasArray.length(); i++) {
                JSONObject obj = plataformasArray.getJSONObject(i).optJSONObject("platform");
                platforms.add(obj != null ? obj.optString("name", "Desconocido") : "Desconocido");
            }
        }

        List<String> capturas = new ArrayList<>();
        JSONArray capturasArray = game.optJSONArray("short_screenshots");
        if (capturasArray != null) {
            for (int i = 0; i < capturasArray.length(); i++) {
                capturas.add(capturasArray.getJSONObject(i).optString("image", "Desconocido"));
            }
        }

        List<String> pegi = new ArrayList<>();
        if (game.has("esrb_rating")) {
            pegi.add(game.optJSONObject("esrb_rating").optString("name", "Desconocido"));
        }
        String pegiNormal = pegi.getFirst();
        List<String> generos = new ArrayList<>();
        JSONArray generosArray = game.optJSONArray("genres");
        if (generosArray != null) {
            for (int i = 0; i < generosArray.length(); i++) {
                generos.add(generosArray.getJSONObject(i).optString("name", "Desconocido"));
            }
        }
        
        
        
        
        
        if (idJuego == -1) {
            System.out.println("No se pudo obtener el ID del juego.");
            return null;
        }

        // Obtener detalles del juego con el ID
        String detailsUrl = String.format(API_URL_GAME_DETAILS, idJuego);
        connection = (HttpURLConnection) new URL(detailsUrl).openConnection();
        connection.setRequestMethod("GET");
        
        if (connection.getResponseCode() != 200) {
            throw new IOException("Error en la conexión: " + connection.getResponseCode());
        }
        
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        response = new StringBuilder();
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        JSONObject gameDetails = new JSONObject(response.toString());

        // Extraer información relevante

        

        List<String> devs = new ArrayList<>();
        JSONArray devsArray = gameDetails.optJSONArray("developers");
        if (devsArray != null) {
            for (int i = 0; i < devsArray.length(); i++) {
                devs.add(devsArray.getJSONObject(i).optString("name", "Desconocido"));
            }
        }
        String des = gameDetails.optString("description", "Descripción no disponible");
        String descripcion = obtenerDescripcionIngles(des);
        return new JuegoPachorra(imagenPrincipal, capturas, titulo, lanzamiento, idJuego, metacritic, platforms, generos, playtime, pegiNormal, devs, descripcion);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

	// Obtener juegos y sus IDs
	public static List<JuegoHome> getJuegosIDsYPlataformas(int pages, String modo) {
		List<JuegoHome> juegosBasicos = new ArrayList<>();

		for (int i = 1; i <= pages; i++) {
			try {
				// Definir la URL de la API dependiendo del modo
				String apiUrl;
				if ("Popular".equals(modo)) {
					apiUrl = String.format(API_URL_TEMPLATE, i); // Para los nuevos lanzamientos
				} else {
					apiUrl = String.format(API_URL_TEMPLATE_NEW, i); // Para los nuevos lanzamientos
				}

				// Establecer la conexión con la URL
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				// Si la respuesta es exitosa
				if (connection.getResponseCode() == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;

					while ((line = in.readLine()) != null) {
						response.append(line);
					}
					in.close();

					// Procesar la respuesta JSON
					JSONObject json = new JSONObject(response.toString());
					JSONArray results = json.getJSONArray("results");

					for (int j = 0; j < results.length(); j++) {
						JSONObject game = results.getJSONObject(j);
						int gameId = game.optInt("id", -1);
						String title = game.optString("name", "No encontrado");

						if (gameId != -1) {
							// Extraer plataformas del juego
							JSONArray platformsArray = game.optJSONArray("platforms");
							List<String> platforms = new ArrayList<>();

							if (platformsArray != null) {
								for (int k = 0; k < platformsArray.length(); k++) {
									JSONObject platformObj = platformsArray.getJSONObject(k);
									String platformName = platformObj.optJSONObject("platform").optString("name", "Desconocido");
									platforms.add(platformName);
								}
							}

							// Crear objeto JuegoHome y agregarlo a la lista
							JuegoHome juego = new JuegoHome(null, title, gameId, platforms);
							juegosBasicos.add(juego);
						}
					}
				} else {
					System.out.println("Error al conectar con la API: " + connection.getResponseCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return juegosBasicos;
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
		
		List<JuegoHome> mecago = buscarJuegoPorNombreBarra("Grand Theft Auto V");
		for(JuegoHome jue: mecago) {
			System.out.println(jue.toString());
		}
	}

	public static List<JuegoHome> obtenerPrimeraCapturaYPlataformas(List<JuegoHome> juegosSinCompletar) {
		List<JuegoHome> juegosConImagenes = new ArrayList<>();

		try {
			// Recorrer todos los juegos de la lista
			for (JuegoHome juego : juegosSinCompletar) {
				int gameId = juego.getIdJuego();
				String title = juego.getTitulo();
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

					// Crear un objeto JuegoHome con la imagen de fondo y las plataformas y añadirlo
					// a la lista
					juegosConImagenes.add(new JuegoHome(foto, title, gameId, plataformas));

				} else {
					System.out.println(
							"Error al conectar con la API para el juego con ID " + gameId + ": " + connection.getResponseCode());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return juegosConImagenes;
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
