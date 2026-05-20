package view;

import controller.SoundManager;
import model.Moto;
import model.Road;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal del juego MotoCrash.
 * Gestiona la navegación entre pantallas usando {@code CardLayout},
 * mantiene el historial de partidas y el Top 3 de jugadores.
 * También controla la reproducción del sonido de inicio.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class MainWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel;
    private PanelGameOver panelGameOver;
    private Moto moto;
    private Road road;
    private String nombreJugador = "Jugador";
    private SoundManager soundManager = new SoundManager();

    /** Historial de partidas: cada entrada es {nombre, puntaje}. */
    private List<String[]> historial = new ArrayList<>();

    /**
     * Constructor que inicializa la ventana y todos los paneles del juego.
     * Registra los paneles en el {@code CardLayout} y reproduce el sonido de inicio.
     */
    public MainWindow() {
        setTitle("MotoCrash");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout    = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        moto = new Moto();
        road = new Road(5);

        WelcomePanel      welcome       = new WelcomePanel(this);
        InstructionsPanel instrucciones = new InstructionsPanel(this);
        PlayerNamePanel   nombre        = new PlayerNamePanel(this);
        gamePanel                       = new GamePanel(moto, road, this);
        panelGameOver                   = new PanelGameOver(this);

        mainContainer.add(welcome,       "MENU");
        mainContainer.add(instrucciones, "REGLAS");
        mainContainer.add(nombre,        "NOMBRE");
        mainContainer.add(gamePanel,     "JUEGO");
        mainContainer.add(panelGameOver, "GAMEOVER");

        add(mainContainer);
        setVisible(true);

        soundManager.playLoop("inicio.wav");
    }

    /**
     * Establece el nombre del jugador actual.
     *
     * @param nombre nombre ingresado por el jugador
     */
    public void setNombreJugador(String nombre) {
        this.nombreJugador = nombre;
    }

    /**
     * Retorna el nombre del jugador actual.
     *
     * @return nombre del jugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * Muestra la pantalla de Game Over con los resultados de la partida.
     * Guarda la partida en el historial, actualiza el Top 3 y navega
     * a la pantalla de Game Over.
     *
     * @param puntaje  puntaje obtenido en la partida
     * @param segundos tiempo jugado en segundos
     */
    public void mostrarGameOver(int puntaje, int segundos) {
        historial.add(new String[]{nombreJugador, String.valueOf(puntaje)});
        historial.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        List<String[]> top3 = historial.subList(0, Math.min(3, historial.size()));
        panelGameOver.actualizar(nombreJugador, puntaje, segundos, top3);
        cardLayout.show(mainContainer, "GAMEOVER");
    }

    /**
     * Resetea el juego y vuelve al menú principal.
     * Crea nuevas instancias de moto y carretera, y reproduce
     * el sonido de inicio nuevamente.
     */
    public void volverAlInicio() {
        moto.resetear();
        road = new Road(5);

        mainContainer.remove(gamePanel);
        gamePanel = new GamePanel(moto, road, this);
        mainContainer.add(gamePanel, "JUEGO");

        cardLayout.show(mainContainer, "MENU");
        soundManager.playLoop("inicio.wav");
    }

    /**
     * Navega al panel indicado por su nombre.
     * Si el destino es el juego, detiene el sonido de inicio
     * e inicia el hilo del juego.
     *
     * @param name nombre del panel destino
     */
    public void changePanel(String name) {
        cardLayout.show(mainContainer, name);
        if (name.equals("JUEGO")) {
            soundManager.stopInicio();
            gamePanel.iniciarJuego();
        }
    }
}