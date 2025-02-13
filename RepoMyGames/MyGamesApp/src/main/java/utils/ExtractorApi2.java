package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import models.JuegoHome;
import models.JuegoPachorra;

public class ExtractorApi2 {

	private static final String API_KEY = "fcc27fd8089c4a42a452702e7f522258";
	private static final String API_URL_SEARCH = "https://api.rawg.io/api/games?key=" + API_KEY + "&search=%s";
	private static final String API_URL_GAME_DETAILS = "https://api.rawg.io/api/games/%d?key=" + API_KEY;
	private static final String API_URL_SEARCH_BUSQUEDA = "https://api.rawg.io/api/games?key=" + API_KEY
			+ "&search=%s&page_size=12";
	private static final String API_URL_GENRE = "https://api.rawg.io/api/games?key=" + API_KEY + "&genres=%s";
	private static final String API_URL_PLATAFORMA = "https://api.rawg.io/api/games/%d?key=fcc27fd8089c4a42a452702e7f522258&platforms=%d";

	private static final String API_URL_TEMPLATE_NEW = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=-released&released=2025-01-01&ratings=4&page=%d";
	private static final String API_URL_TEMPLATE = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&page_size=9&ordering=new&page=%d";
	private static final String API_URL_1JUEGO = "https://api.rawg.io/api/games/%d?key=fcc27fd8089c4a42a452702e7f522258";

	private static final String API_URL_TEMPLATE_SEARCH = "https://api.rawg.io/api/games?key=fcc27fd8089c4a42a452702e7f522258&search=%s";

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

	public static JuegoPachorra buscarJuegoPorNombre(String nombreJuego) {
		try {
			// Construir URL de búsqueda
			if (nombreJuego.toLowerCase().equals("label")) {
				return null;
			}

			String apiUrl = String.format(API_URL_SEARCH, URLEncoder.encode(nombreJuego, "UTF-8"));
			HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() != 200) {
				System.out.println("Error en la conexión: " + connection.getResponseCode());
				return null;
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

			if (results == null || results.isEmpty()) {
				System.out.println("No se encontraron resultados.");
				return new JuegoPachorra("Imagen no disponible", new ArrayList<>(), "No encontrado", "Fecha no disponible", -1,
						-1, new ArrayList<>(), new ArrayList<>(), -1, "Desconocido", new ArrayList<>(),
						"Descripción no disponible");
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

			String pegiNormal = "Desconocido";
			if (game.has("esrb_rating") && game.optJSONObject("esrb_rating") != null) {
				pegiNormal = game.optJSONObject("esrb_rating").optString("name", "Desconocido");
			}

			List<String> generos = new ArrayList<>();
			JSONArray generosArray = game.optJSONArray("genres");
			if (generosArray != null) {
				for (int i = 0; i < generosArray.length(); i++) {
					generos.add(generosArray.getJSONObject(i).optString("name", "Desconocido"));
				}
			}

			if (idJuego == -1) {
				System.out.println("No se pudo obtener el ID del juego.");
				return new JuegoPachorra(imagenPrincipal, capturas, titulo, lanzamiento, idJuego, metacritic, platforms,
						generos, playtime, pegiNormal, new ArrayList<>(), "Descripción no disponible");
			}

			// Obtener detalles del juego con el ID
			String detailsUrl = String.format(API_URL_GAME_DETAILS, idJuego);
			connection = (HttpURLConnection) new URL(detailsUrl).openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() != 200) {
				System.out.println("Error en la conexión: " + connection.getResponseCode());
				return new JuegoPachorra(imagenPrincipal, capturas, titulo, lanzamiento, idJuego, metacritic, platforms,
						generos, playtime, pegiNormal, new ArrayList<>(), "Descripción no disponible");
			}

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			response = new StringBuilder();
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();

			JSONObject gameDetails = new JSONObject(response.toString());

			List<String> devs = new ArrayList<>();
			JSONArray devsArray = gameDetails.optJSONArray("developers");
			if (devsArray != null) {
				for (int i = 0; i < devsArray.length(); i++) {
					devs.add(devsArray.getJSONObject(i).optString("name", "Desconocido"));
				}
			}

			String descripcion = ExtractorApi2
					.obtenerDescripcionIngles(gameDetails.optString("description", "Descripción no disponible"));

			return new JuegoPachorra(imagenPrincipal, capturas, titulo, lanzamiento, idJuego, metacritic, platforms, generos,
					playtime, pegiNormal, devs, descripcion);

		} catch (Exception e) {
			e.printStackTrace();
			return new JuegoPachorra("Imagen no disponible", new ArrayList<>(), "No encontrado", "Fecha no disponible", -1,
					-1, new ArrayList<>(), new ArrayList<>(), -1, "Desconocido", new ArrayList<>(), "Descripción no disponible");
		}
	}

	public static List<JuegoHome> buscarJuegosPorPlataformaPadre(String plataformaPadre, int indice) {
		List<JuegoHome> juegos = new ArrayList<>();
		String apiUrl = "";

		try {
			// Seleccionar la plataforma según el parámetro
			String plataformas = switch (plataformaPadre.toLowerCase()) {
			case "playstation" -> String.join(",",
					Arrays.stream(IdsPlataformas.consolasPlayStation).mapToObj(String::valueOf).toArray(String[]::new));
			case "xbox" ->
				String.join(",", Arrays.stream(IdsPlataformas.consolasXbox).mapToObj(String::valueOf).toArray(String[]::new));
			case "pc" ->
				String.join(",", Arrays.stream(IdsPlataformas.consolasPC).mapToObj(String::valueOf).toArray(String[]::new));
			case "retro" ->
				String.join(",", Arrays.stream(IdsPlataformas.consolasRetro).mapToObj(String::valueOf).toArray(String[]::new));
			case "nintendo" -> String.join(",",
					Arrays.stream(IdsPlataformas.consolasNintendo).mapToObj(String::valueOf).toArray(String[]::new));
			default -> throw new IllegalArgumentException("Plataforma desconocida");
			};

			// Construcción correcta de la URL con paginación
			apiUrl = String.format("https://api.rawg.io/api/games?key=%s&platforms=%s&page=%d", API_KEY, plataformas,
					(indice / 12 + 1));

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

}
