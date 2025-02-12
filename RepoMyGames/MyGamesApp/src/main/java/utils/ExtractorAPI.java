package utils;

import org.json.JSONObject;

import models.JuegoPachorra;
import models.JuegoHome;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExtractorAPI {

	private static final String API_KEY = "fcc27fd8089c4a42a452702e7f522258";
	private static final String API_URL_SEARCH = "https://api.rawg.io/api/games?key=" + API_KEY + "&search=%s";
	private static final String API_URL_GAME_DETAILS = "https://api.rawg.io/api/games/%d?key=" + API_KEY;
	private static final String API_URL_SEARCH_BUSQUEDA = "https://api.rawg.io/api/games?key=" + API_KEY
			+ "&search=%s&page_size=12";
	private static final String API_URL_GENRE = "https://api.rawg.io/api/games?key=" + API_KEY + "&genres=%s";

	private static final String API_URL_TEMPLATE_NEW = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=-released&released=2025-01-01&ratings=4&page=%d";
	private static final String API_URL_TEMPLATE = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=new&page=%d";
	private static final String API_URL_1JUEGO = "https://api.rawg.io/api/games/%d?key=fcc27fd8089c4a42a452702e7f522258";

	private static final String API_URL_TEMPLATE_SEARCH = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&search=%s";

	public static List<JuegoHome> buscarJuegoPorNombreBarra(String nombreJuego, int offset) {
		List<JuegoHome> juegos = new ArrayList<>();
		try {
			String apiUrl = String.format(API_URL_SEARCH_BUSQUEDA, URLEncoder.encode(nombreJuego, "UTF-8")) + "&page="
					+ (offset / 12 + 1);
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

	public static List<JuegoHome> buscarJuegosPorGenero(String genero) {
		if (genero.equals("rpg")) {
			genero = "5";
		}
		List<JuegoHome> juegos = new ArrayList<>();

		try {

			String apiUrl = String.format(API_URL_GENRE, URLEncoder.encode(genero, "UTF-8"));
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
									String platformName = platformObj.optJSONObject("platform").optString("name",
											"Desconocido");
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
				String descripcionBuena = ExtractorApi2.obtenerDescripcionIngles(description);
				String descripcionLimpia = ExtractorApi2.limpiarDescripcion(descripcionBuena);
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

		String apiKey = "fcc27fd8089c4a42a452702e7f522258";
		String baseUrl = "https://api.rawg.io/api/genres?key=" + apiKey;

		try {
			URL url = new URL(baseUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Procesar JSON
				JSONObject jsonResponse = new JSONObject(response.toString());
				JSONArray genres = jsonResponse.getJSONArray("results");

				System.out.println("Géneros disponibles:");
				for (int i = 0; i < genres.length(); i++) {
					JSONObject genre = genres.getJSONObject(i);
					System.out.println("- " + genre.getString("name") + " (ID: " + genre.getInt("id") + ")");
				}
			} else {
				System.out.println("Error en la solicitud: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
							String platformName = platformObj.optJSONObject("platform").optString("name",
									"Desconocido");
							plataformas.add(platformName);
						}
					}

					// Crear un objeto JuegoHome con la imagen de fondo y las plataformas y añadirlo
					// a la lista
					juegosConImagenes.add(new JuegoHome(foto, title, gameId, plataformas));

				} else {
					System.out.println("Error al conectar con la API para el juego con ID " + gameId + ": "
							+ connection.getResponseCode());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return juegosConImagenes;
	}

}
