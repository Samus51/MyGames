package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jdbc.Conector;
import models.JuegoHome;

public class MetodosSQL {
	public static int obtenerIdJuego(Connection conn, String nombreJuego) throws SQLException {
		String query = "SELECT id_juego FROM juegos WHERE titulo = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, nombreJuego);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id_juego");
			}
		}
		return -1;
	}

	public static boolean agregarJuegoAUsuario(Connection conn, int idUsuario, int idJuego) throws SQLException {
		String query = "INSERT INTO wishlist (id_usuario, id_juego, fecha_agregado) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idJuego);
			stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			return stmt.executeUpdate() > 0;
		}
	}

	public boolean juegoJugado(int idUsuario, int idJuego) {
		String query = "SELECT COUNT(*) FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";
		try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idJuego);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean juegoEnListaDeseados(int idUsuario, int idJuego) {
		String query = "SELECT COUNT(*) FROM wishlist WHERE id_usuario = ? AND id_juego = ?";
		try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idJuego);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Verificar si el juego ha sido jugado
	public static boolean verificarJuegoJugado(int idUsuario, int idJuego) throws SQLException {
		String sql = "SELECT 1 FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";
		try (Connection conn = Conector.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idJuego);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // Si existe un resultado, el juego ha sido jugado
			}
		}
	}

	// Verificar si el juego está en la biblioteca
	public static boolean verificarJuegoEnBiblioteca(int idUsuario, int idJuego) throws SQLException {
		String sql = "SELECT 1 FROM biblioteca WHERE id_usuario = ? AND id_juego = ?";
		try (Connection conn = Conector.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idJuego);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // Si existe un resultado, el juego está en la biblioteca
			}
		}
	}

	// Verificar si el juego está en la lista de deseos
	public static boolean verificarJuegoEnListaDeseos(int idUsuario, int idJuego) throws SQLException {
		String sql = "SELECT 1 FROM wishlist WHERE id_usuario = ? AND id_juego = ?";
		try (Connection conn = Conector.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idJuego);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // Si existe un resultado, el juego está en la lista de deseos
			}
		}
	}

	public static boolean comprobarJuego(Connection cone, String titulo) {
		String query = "SELECT COUNT(*) FROM juegos WHERE titulo = ?";

		try (PreparedStatement st = cone.prepareStatement(query)) {
			st.setString(1, titulo);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // Si el count es mayor a 0, el juego existe
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false; // Si hay error o no encuentra el juego, retorna false
	}

	public static boolean insertarJuegoJugado(int idUsuario, int idJuego) throws SQLException {
		String sql = "INSERT INTO juegos_jugados (id_usuario, id_juego, completado, fecha_jugado) VALUES (?, ?, 0, ?)";

		try (Connection conn = Conector.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idJuego);
			stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

			return stmt.executeUpdate() > 0;
		}
	}

	public static String obtenerGeneroAleatorio(List<String> generosUsuario) {
		if (generosUsuario == null || generosUsuario.isEmpty()) {
			return "Desconocido";
		}

		// Limpiamos los géneros eliminando comas innecesarias y recortamos espacios
		List<String> generosLimpios = generosUsuario.stream().map(g -> g.trim()) // Eliminamos espacios al principio y
																					// final
				.filter(g -> !g.isEmpty()) // Eliminamos los vacíos
				.toList();

		if (generosLimpios.isEmpty()) {
			return "Desconocido";
		}

		// Elegimos uno aleatorio
		Random random = new Random();
		return generosLimpios.get(random.nextInt(generosLimpios.size()));
	}

	public static List<JuegoHome> obtenerJuegosBiblioteca(Connection conn, int idUsuario) {
		List<JuegoHome> juegos = new ArrayList<>();
		String sql = "SELECT j.id_juego, j.titulo, j.url_1, j.plataformas " + "FROM juegos j "
				+ "JOIN biblioteca b ON j.id_juego = b.id_juego " + "WHERE b.id_usuario = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, idUsuario);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					int idJuego = rs.getInt("id_juego");
					String titulo = rs.getString("titulo");
					String imagenFondo = rs.getString("url_1");

					// Convertir las plataformas de String a List<String>
					List<String> plataformas = Arrays.asList(rs.getString("plataformas").split(","));

					JuegoHome juego = new JuegoHome(imagenFondo, titulo, idJuego, plataformas);
					juegos.add(juego);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return juegos;
	}

	public static List<JuegoHome> obtenerJuegosWhishlist(Connection conn, int idUsuario) {
		List<JuegoHome> juegos = new ArrayList<>();
		String sql = "SELECT j.id_juego, j.titulo, j.url_1, j.plataformas " + "FROM juegos j "
				+ "JOIN wishlist b ON j.id_juego = b.id_juego " + "WHERE b.id_usuario = ?";

		try (PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, idUsuario);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					int idJuego = rs.getInt("id_juego");
					String titulo = rs.getString("titulo");
					String imagenFondo = rs.getString("url_1");

					// Convertir las plataformas de String a List<String>
					List<String> plataformas = Arrays.asList(rs.getString("plataformas").split(","));

					JuegoHome juego = new JuegoHome(imagenFondo, titulo, idJuego, plataformas);
					juegos.add(juego);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return juegos;
	}

	public static void eliminarJuegoDeListaJugados(Connection conn, int usuarioId, int juegoId) throws SQLException {
		String query = "DELETE FROM juegos_jugados WHERE id_usuario = ? AND id_juego = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, usuarioId);
			stmt.setInt(2, juegoId);
			stmt.executeUpdate();
		}
	}

	public static void eliminarJuegoDeListaDeseos(Connection conn, int usuarioId, int juegoId) throws SQLException {
		String query = "DELETE FROM wishlist WHERE id_usuario = ? AND id_juego = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, usuarioId);
			stmt.setInt(2, juegoId);
			stmt.executeUpdate();
		}
	}

}
