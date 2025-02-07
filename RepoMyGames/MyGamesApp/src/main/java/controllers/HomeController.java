package controllers;

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

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.JuegoHome;
import utils.ExtractorAPI;

public class HomeController {

	private final static String PANEL_USER = "/views/CambiarInfoPersonal.fxml";

	private static final String PANEL_JUEGO_INFO = "/views/JuegoInfo.fxml";

	private static final String STYLES = "/styles.css";

	private static final String PANEL_HOME_BUSQUEDA = "/views/HomeBusqueda.fxml";

	@FXML
	private BorderPane VentanaPrincipal;

	@FXML
	private Pane panelFondo;

	@FXML
	private Label btnGeneroSalida;

	@FXML
	private Label btnGeneros;

	@FXML
	private TextField txtBusqueda;

	@FXML
	private static Label titulo;

	@FXML
	private Label btnGenerosMenuPlataformas;

	@FXML
	private ImageView btnGenerosSalida;

	@FXML
	private ImageView btnMinimizar;

	@FXML
	private ImageView btnCerrar;

	@FXML
	private ImageView imgLupa;

	@FXML
	private Label btnPlataformas;

	@FXML
	private ImageView btnMenu;

	@FXML
	private Label btnPlataformasMenuGeneros;

	@FXML
	private Label btnPlataformasSalida;

	@FXML
	private ImageView btnUser;

	@FXML
	private HBox buscador;

	@FXML
	private HBox contBusqueda;

	@FXML
	private HBox contJuegos1;

	@FXML
	private HBox contJuegos2;

	@FXML
	private HBox contJuegos3;

	@FXML
	private HBox contJuegos4;

	@FXML
	private HBox contPlataformas;

	@FXML
	private HBox contPlataformas1;

	@FXML
	private StackPane contMenuPadre;

	@FXML
	private VBox menuGeneral;

	@FXML
	private VBox contInfo;

	@FXML
	private VBox menuFondito;

	@FXML
	private VBox menuGeneros;

	@FXML
	private VBox menuPlataformas;

	@FXML
	private ScrollPane scrollMenu;

	@FXML
	private ScrollPane scrollJuegosVertical;

	@FXML
	private ScrollPane scrollHorizontalJuego;

	@FXML
	private ScrollPane scrollHorizontalJuego1;

	@FXML
	private ScrollPane scrollHorizontalJuego2;

	@FXML
	private ScrollPane scrollHorizontalJuego3;

	private double mousePressedX;
	private double mousePressedY;
	private HBox contenedorActivo = null;

	@FXML
	public void initialize() {
		scrollHorizontalJuego.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// Manejar el arrastre horizontal
		contJuegos1.setOnMousePressed(this::onMousePressed);
		contJuegos1.setOnMouseDragged(this::onMouseDragged);

		contJuegos2.setOnMousePressed(this::onMousePressed);
		contJuegos2.setOnMouseDragged(this::onMouseDragged);

		contJuegos3.setOnMousePressed(this::onMousePressed);
		contJuegos3.setOnMouseDragged(this::onMouseDragged);

		contJuegos4.setOnMousePressed(this::onMousePressed);
		contJuegos4.setOnMouseDragged(this::onMouseDragged);

		contJuegos4.setOnMousePressed(this::onMousePressed);
		contJuegos4.setOnMouseDragged(this::onMouseDragged);

		scrollJuegosVertical.setOnMousePressed(this::onMousePressed);
		scrollJuegosVertical.setOnMousePressed(this::onMousePressed);

		scrollMenu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollMenu.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollHorizontalJuego.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);

		List<JuegoHome> capturasPlataformas = cargarJuegos("Popular");
		List<JuegoHome> capturasPlataformas2 = cargarJuegos("Nuevos");

		asignarImagenes(contJuegos1, capturasPlataformas);
		asignarImagenes(contJuegos2, capturasPlataformas2);

	}

	// 378, 199, Contenedors Info y Plataformas
	public static void asignarImagenes(HBox hbox, List<JuegoHome> capturaYPlataformas) {
		int imageIndex = 0;

		for (Node node : hbox.getChildren()) {
			if (node instanceof VBox) {
				VBox vbox = (VBox) node;

				// Obtener el juego correspondiente
				if (imageIndex < capturaYPlataformas.size()) {
					JuegoHome juego = capturaYPlataformas.get(imageIndex);

					// Buscar dentro del VBox el contenedor de información (contInfo)
					for (Node vboxChild : vbox.getChildren()) {
						// Asignar la captura al ImageView principal
						if (vboxChild instanceof ImageView) {
							ImageView imageView = (ImageView) vboxChild;
							String captura = juego.getImagenFondo();

							try {
								// Intentar cargar la imagen
								imageView.setImage(new Image(captura));
							} catch (IllegalArgumentException e) {
								// Si la URL es inválida, cargar la imagen por defecto
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

											// Si el juego tiene una plataforma que coincide, usar la versión en color
											if (tienePlataforma) {
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
											double cornerRadius = 10;
											Rectangle clip = new Rectangle(imageView2.getFitWidth(), imageView2.getFitHeight());
											clip.setArcWidth(cornerRadius);
											clip.setArcHeight(cornerRadius);
											imageView2.setClip(clip);

											imageView2.setFitWidth(imageView2.getFitWidth());
											imageView2.setFitHeight(imageView2.getFitHeight());

											index++; // Pasamos a la siguiente imagen
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

				imageIndex++; // Pasar al siguiente juego
			}
		}
	}

	private static List<JuegoHome> cargarJuegos(String modo) {
		List<JuegoHome> juegosIncompletos = ExtractorAPI.getJuegosIDsYPlataformas(1, modo);

		List<JuegoHome> juegos = ExtractorAPI.obtenerPrimeraCapturaYPlataformas(juegosIncompletos);

		return juegos;
	}

	// Método para mover la ventana
	public void moverVentana(Stage stage) {
		// Cogemos la Pantalla
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		// Ajustamos el desplazamiento
		double desplazamientoX = 300;
		double desplazamientoY = 100;

		// Establecer la nueva posición de la ventana en el monitor
		stage.setX(bounds.getMinX() + desplazamientoX);
		stage.setY(bounds.getMinY() + desplazamientoY);
	}

//Método que se ejecuta cuando se presiona el ratón en el contenedor de juegos
	private void onMousePressed(MouseEvent event) {
		// Detectamos cuál contenedor está siendo presionado y lo asignamos a la
		// variable
		if (event.getSource() == contJuegos1) {
			contenedorActivo = contJuegos1;
		} else if (event.getSource() == contJuegos2) {
			contenedorActivo = contJuegos2;
		} else if (event.getSource() == contJuegos3) {
			contenedorActivo = contJuegos3;
		} else if (event.getSource() == contJuegos4) {
			contenedorActivo = contJuegos4;
		}

		// Guardamos la posición inicial del mouse
		if (contenedorActivo != null) {
			mousePressedX = event.getSceneX();
		}
	}

//Método que se ejecuta cuando el ratón se mueve para arrastrar el contenido
	private void onMouseDragged(MouseEvent event) {
		// Verificamos si hay un contenedor activo
		if (contenedorActivo != null) {
			double deltaX = mousePressedX - event.getSceneX();
			mousePressedX = event.getSceneX();

			// Calculamos el desplazamiento solo para el contenedor activo
			ScrollPane scrollPaneActivo = null;
			if (contenedorActivo == contJuegos1) {
				scrollPaneActivo = scrollHorizontalJuego;
			}
			if (contenedorActivo == contJuegos2) {
				scrollPaneActivo = scrollHorizontalJuego1;
			}
			if (contenedorActivo == contJuegos3) {
				scrollPaneActivo = scrollHorizontalJuego2;
			}
			if (contenedorActivo == contJuegos4) {
				scrollPaneActivo = scrollHorizontalJuego3;
			}

			if (scrollPaneActivo != null) {
				// Calculamos el nuevo valor de desplazamiento en función del movimiento del
				// ratón
				double newHvalue = scrollPaneActivo.getHvalue() + deltaX * 2 / contenedorActivo.getWidth();
				// Aseguramos que el valor se mantenga dentro de los límites (0 a 1)
				scrollPaneActivo.setHvalue(Math.max(0, Math.min(1, newHvalue)));
				scrollPaneActivo.setVvalue(0);

			}
		}
	}

	// Método para mover la ventana
	public void moverVentanaMenu(Stage stage) {
		// Obtén el monitor que quieres usar
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		// Ajusta el desplazamiento según lo desees
		double desplazamientoX = 300;
		double desplazamientoY = 100;

		// Establecer la nueva posición de la ventana en el monitor
		stage.setX(bounds.getMinX() + desplazamientoX);
		stage.setY(bounds.getMinY() + desplazamientoY);
	}

	private void onMousePressedMenu(MouseEvent event) {
		mousePressedY = event.getSceneY();
	}

	private void onMouseDraggedMenu(MouseEvent event) {
		double deltaY = mousePressedY - event.getSceneY();
		mousePressedY = event.getSceneY();

		// Desplazamos el contenido del ScrollPane
		double newVvalue = scrollMenu.getVvalue() + deltaY * 8 / contMenuPadre.getHeight();
		scrollMenu.setVvalue(Math.max(0, Math.min(1, newVvalue)));
	}

	@FXML
	void btnGenerosMenuPlataformasPressed(MouseEvent event) {
		menuGeneral.setVisible(false);
		menuGeneros.setVisible(true);
		menuPlataformas.setVisible(false);

		menuGeneros.setMinHeight(Region.USE_COMPUTED_SIZE);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(0);

		scrollMenu.setHvalue(0);
		scrollMenu.setVvalue(0);
	}

	@FXML
	void btnGenerosPressed(MouseEvent event) {
		menuGeneral.setVisible(false);
		menuGeneros.setVisible(true);
		menuPlataformas.setVisible(false);

		menuGeneros.setMinHeight(Region.USE_COMPUTED_SIZE);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(0);
	}

	@FXML
	void btnGenerosSalidaPressed(MouseEvent event) {
		menuGeneral.setVisible(true);
		menuGeneros.setVisible(false);

		menuGeneros.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);
	}

	@FXML
	void btnPlataformasPressed(MouseEvent event) {
		menuGeneral.setVisible(false);
		menuPlataformas.setVisible(true);
		menuGeneros.setVisible(false);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(Region.USE_COMPUTED_SIZE);
		menuGeneral.setMinHeight(0);
	}

	@FXML
	void btnPlataformasSalidaPressed(MouseEvent event) {
		menuGeneral.setVisible(true);
		menuPlataformas.setVisible(false);
		menuGeneros.setVisible(false);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(0);
		menuGeneral.setMinHeight(Region.USE_COMPUTED_SIZE);
	}

	@FXML
	void btnPlataformasMenuGenerosPressed(MouseEvent event) {
		menuGeneral.setVisible(false);
		menuGeneros.setVisible(false);
		menuPlataformas.setVisible(true);

		menuGeneros.setMinHeight(0);
		menuPlataformas.setMinHeight(Region.USE_COMPUTED_SIZE);
		menuGeneral.setMinHeight(0);

		scrollMenu.setHvalue(0);
		scrollMenu.setVvalue(0);
	}

	@FXML
	void btnMenuPressed(MouseEvent event) {
		if (menuGeneral.isVisible()) {
			// Ocultar menú y restaurar estilos originales (transparente)
			menuGeneral.setVisible(false);
			// menuFondito.setStyle("-fx-background-image: none;" + // Sin imagen de fondo
			// "-fx-background-color: transparent;" // Color de fondo transparente
			// );
		} else {
			// Mostrar menú y aplicar estilo con opacidad
			menuGeneral.setVisible(true);
			// menuFondito.setStyle("-fx-background-color: linear-gradient(from 0% 0% to
			// 100% 100%, "
			// + "rgba(0, 0, 255, 0.3), rgba(0, 0, 0, 0.3));");
		}

		// Asegúrate de que otros menús estén ocultos
		menuGeneros.setVisible(false);
		menuPlataformas.setVisible(false);
	}

	public void abrirNuevaVentana(String fxml) {
		try {
			// Obtener el Stage de la ventana principal
			Stage ventanaPrincipal = (Stage) VentanaPrincipal.getScene().getWindow();

			// Cargar el archivo FXML de la nueva ventana
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Pane root = loader.load();

			// Crear una nueva escena con el root cargado
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

			// Crear un nuevo Stage
			Stage nuevaVentana = new Stage();
			nuevaVentana.setScene(scene);

			// Maximizar la ventana
			nuevaVentana.setMaximized(true);
			nuevaVentana.setResizable(false);
			nuevaVentana.initStyle(StageStyle.UNDECORATED);

			// Mostrar la nueva ventana
			nuevaVentana.show();

			// Cerrar la ventana principal
			ventanaPrincipal.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void btnUserPressed(MouseEvent event) {
		abrirNuevaVentana(PANEL_USER);
	}

	@FXML
	void btnMinimizarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.setIconified(true);
	}

	@FXML
	void btnCerrarPressed(MouseEvent event) {
		Stage ventanaPrincipal = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ventanaPrincipal.close();
	}

	private Stage obtenerVentana(MouseEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	@FXML
	void juegoSoloPressed(MouseEvent event) throws IOException {
		VBox vbox = (VBox) event.getSource();

		for (Node childNode : vbox.getChildren()) {
			if (childNode instanceof VBox) {
				VBox innerVBox = (VBox) childNode;

				for (Node innerNode : innerVBox.getChildren()) {
					if (innerNode instanceof Label) {
						Label tituloLabel = (Label) innerNode;

						FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_JUEGO_INFO));
						Pane root = loader.load();

						// Verifica que el controlador esté cargado
						JuegoInfoController controller = loader.getController();
						System.out.println("Controlador cargado: " + controller);

						// Ahora pasa el título al controlador
						String tituloJuego = tituloLabel.getText();
						System.out.println("Título del juego: " + tituloJuego);
						controller.setTituloJuego(tituloJuego);

						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

						Stage nuevaVentana = new Stage();
						nuevaVentana.setTitle("Juego Info");
						nuevaVentana.setScene(scene);
						nuevaVentana.setMaximized(true);
						nuevaVentana.setResizable(false);
						nuevaVentana.initStyle(StageStyle.UNDECORATED);

						nuevaVentana.show();
						obtenerVentana(event).close();
						System.out.println("Ventana cerrada");
					}
				}
			}
		}
	}

	@FXML
	void imgLupaPressed(MouseEvent event) throws IOException {
		System.out.println("HOLAAA");
		String texto = txtBusqueda.getText();
		System.out.println(texto+" -------");
		FXMLLoader loader = new FXMLLoader(getClass().getResource(PANEL_HOME_BUSQUEDA));
		Pane root = loader.load();

		
		System.out.println();
		List<JuegoHome> juegosCargar = ExtractorAPI.buscarJuegoPorNombreBarra(texto);
		System.out.println("JUEGOS CARGADOS:" +juegosCargar.size());
		HomeBusquedaController controller = loader.getController();
		System.out.println("Controlador cargado: " + controller);

		// Ahora pasa el título al controlador
		controller.setJuegosBuscados(juegosCargar);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(STYLES).toExternalForm());

		Stage nuevaVentana = new Stage();
		nuevaVentana.setTitle("Busqueda");
		nuevaVentana.setScene(scene);
		nuevaVentana.setMaximized(true);
		nuevaVentana.setResizable(false);
		nuevaVentana.initStyle(StageStyle.UNDECORATED);

		nuevaVentana.show();
		obtenerVentana(event).close();
		System.out.println("Ventana cerrada");

		

	}

}
