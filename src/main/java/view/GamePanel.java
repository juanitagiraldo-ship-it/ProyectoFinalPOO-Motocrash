package view;

import controller.GameController;
import controller.GamepadController;
import controller.KeyboardController;
import model.Moto;
import model.Obstacle;
import model.PowerUp;
import model.Road;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal del juego MotoCrash.
 * Se encarga de renderizar todos los elementos visuales del juego:
 * carretera, moto, obstáculos, power-ups y el HUD con puntaje,
 * vidas y tiempo. Gestiona el inicio del hilo del juego y
 * el controlador de teclado y mando.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class GamePanel extends JPanel {

    private Moto moto;
    private Road road;
    private GameController gameController;
    private MainWindow mainWindow;

    /**
     * Constructor del panel de juego.
     * Registra el controlador de teclado y el listener de ratón
     * para recuperar el foco cuando sea necesario.
     *
     * @param moto       instancia de la moto del jugador
     * @param road       instancia de la carretera
     * @param mainWindow referencia a la ventana principal para navegación
     */
    public GamePanel(Moto moto, Road road, MainWindow mainWindow) {
        this.moto       = moto;
        this.road       = road;
        this.mainWindow = mainWindow;

        setFocusable(true);
        addKeyListener(new KeyboardController(moto));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });
    }

    /**
     * Inicia el hilo del juego y el hilo del mando.
     * Debe llamarse cuando el panel ya es visible en pantalla
     * para que {@code requestFocusInWindow} funcione correctamente.
     */
    public void iniciarJuego() {
        gameController = new GameController(moto, road, this);
        Thread hiloJuego = new Thread(gameController);
        hiloJuego.setDaemon(true);
        hiloJuego.start();

        GamepadController gamepad = new GamepadController(moto);
        Thread hiloGamepad = new Thread(gamepad);
        hiloGamepad.setDaemon(true);
        hiloGamepad.start();

        requestFocusInWindow();
    }

    /**
     * Navega a la pantalla de Game Over cuando el jugador pierde todas las vidas.
     * Se ejecuta en el hilo de eventos de Swing.
     *
     * @param segundos tiempo total jugado en segundos
     */
    public void showGameOver(int segundos) {
        SwingUtilities.invokeLater(() -> {
            mainWindow.mostrarGameOver(moto.getScore(), segundos);
        });
    }

    /**
     * Renderiza todos los elementos visuales del juego.
     * Dibuja el fondo degradado, la carretera, los obstáculos,
     * los power-ups, la moto y el HUD.
     *
     * @param g contexto gráfico de Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        GradientPaint fondo = new GradientPaint(
                0, 0, new Color(15, 40, 20),
                0, getHeight(), new Color(30, 70, 35)
        );
        g2d.setPaint(fondo);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        int xRoad = (getWidth() - road.getBackgroundImage().getWidth()) / 2;

        g.drawImage(road.getBackgroundImage(), xRoad, road.getY1(), null);
        g.drawImage(road.getBackgroundImage(), xRoad, road.getY2(), null);

        if (gameController != null) {
            for (Obstacle o : gameController.getObstacles()) {
                if (o.getSprite() != null)
                    g.drawImage(o.getSprite(), xRoad + o.getX(), o.getY(), null);
            }
            for (PowerUp p : gameController.getPowerUps()) {
                if (p.getSprite() != null)
                    g.drawImage(p.getSprite(), xRoad + p.getX(), p.getY(), null);
            }
        }

        g.drawImage(moto.getSprite(), xRoad + moto.getX(), moto.getY(), null);

        // HUD
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.fillRect(0, 70, getWidth(), 2);

        g.setFont(new Font("Arial", Font.BOLD, 16));

        g.setColor(Color.YELLOW);
        g.drawString("PUNTAJE", 15, 22);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(moto.getScore()), 15, 48);

        g.setColor(Color.YELLOW);
        g.drawString("VIDAS", 200, 22);
        drawVidas(g, moto.getLives(), 185, 48);

        int seg = gameController != null ? gameController.getGameSeconds() : 0;
        g.setColor(Color.YELLOW);
        g.drawString("TIEMPO", 360, 22);
        g.setColor(Color.WHITE);
        g.drawString(formatTime(seg), 360, 48);

        if (moto.isTurboActivo()) {
            g.setColor(new Color(255, 200, 0));
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.drawString("  TURBO ACTIVO", 150, 65);
        } else if (moto.isShielded()) {
            g.setColor(new Color(0, 180, 255));
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.drawString("  ESCUDO ACTIVO", 150, 65);
        }

        if (!moto.isEnter()) {
            drawOverlay(g, "PRESIONA ENTER PARA EMPEZAR");
        } else if (moto.isPaused()) {
            drawOverlay(g, "PAUSA  —  ESPACIO para continuar");
        }
    }

    /**
     * Dibuja corazones representando las vidas restantes del jugador.
     *
     * @param g     contexto gráfico
     * @param vidas número de vidas a dibujar
     * @param x     posición horizontal inicial
     * @param y     posición vertical inicial
     */
    private void drawVidas(Graphics g, int vidas, int x, int y) {
        for (int i = 0; i < vidas; i++) {
            g.setColor(Color.RED);
            int bx = x + i * 22;
            g.fillOval(bx, y - 12, 10, 10);
            g.fillOval(bx + 6, y - 12, 10, 10);
            int[] px = {bx, bx + 16, bx + 8};
            int[] py = {y - 5, y - 5, y + 5};
            g.fillPolygon(px, py, 3);
        }
    }

    /**
     * Formatea segundos en formato MM:SS.
     *
     * @param segundos tiempo en segundos
     * @return cadena con formato {@code MM:SS}
     */
    private String formatTime(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    /**
     * Dibuja un overlay semitransparente con un mensaje centrado.
     * Se usa para los mensajes de inicio y pausa.
     *
     * @param g    contexto gráfico
     * @param text mensaje a mostrar
     */
    private void drawOverlay(Graphics g, String text) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 300, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(text)) / 2;
        g.drawString(text, textX, 340);
    }
}