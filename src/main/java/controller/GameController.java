package controller;

import model.Moto;
import model.Obstacle;
import model.PowerUp;
import model.Road;
import view.GamePanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controlador principal del juego MotoCrash.
 * Gestiona el bucle del juego, la generación de obstáculos y power-ups,
 * la detección de colisiones y el incremento progresivo de dificultad.
 * Implementa {@code Runnable} para ejecutarse en un hilo independiente.
 *
 * @author Juan Esteban Rivera Duque
 * @version 1.0
 */
public class GameController implements Runnable {

    private Moto moto;
    private Road road;
    private List<Obstacle> obstacles;
    private List<PowerUp>  powerUps;
    private GamePanel gamePanel;
    private SoundManager soundManager;

    private boolean playing         = true;
    private int     globalSpeed     = 8;
    private int     obstacleCounter = 0;
    private int     powerUpCounter  = 0;
    private int     gameSeconds     = 0;
    private int     frameCount      = 0;
    private int     nivelVelocidad  = 0;

    /** Frames por segundo del juego. */
    private static final int FPS = 50;

    /**
     * Constructor del controlador del juego.
     *
     * @param moto      instancia de la moto del jugador
     * @param road      instancia de la carretera
     * @param gamePanel panel de juego donde se renderiza todo
     */
    public GameController(Moto moto, Road road, GamePanel gamePanel) {
        this.moto         = moto;
        this.road         = road;
        this.gamePanel    = gamePanel;
        this.obstacles    = new ArrayList<>();
        this.powerUps     = new ArrayList<>();
        this.soundManager = new SoundManager();
    }

    /**
     * Bucle principal del juego.
     * Reproduce la música de fondo, actualiza la lógica cada 20ms
     * y notifica al panel para redibujar.
     * Al terminar notifica el Game Over.
     */
    @Override
    public void run() {
        soundManager.playLoop("fondo.wav");
        while (playing) {
            updateLogic();
            gamePanel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        soundManager.stopFondo();
        soundManager.play("gameover.wav");
        gamePanel.showGameOver(gameSeconds);
    }

    /**
     * Actualiza toda la lógica del juego en cada frame.
     * Mueve la carretera, genera obstáculos y power-ups,
     * y gestiona colisiones cuando el juego está activo.
     */
    private void updateLogic() {
        road.update();
        moto.update();

        obstacleCounter++;
        if (obstacleCounter > 60) {
            obstacles.add(new Obstacle(globalSpeed));
            obstacleCounter = 0;
        }

        if (moto.isEnter() && !moto.isPaused()) {
            frameCount++;
            if (frameCount >= FPS) {
                frameCount = 0;
                gameSeconds++;
            }

            int nivelActual = moto.getScore() / 300;
            if (nivelActual > nivelVelocidad && globalSpeed < 25) {
                nivelVelocidad = nivelActual;
                globalSpeed += 2;
                road.setSpeed(globalSpeed);
            }

            powerUpCounter++;
            if (powerUpCounter > 250) {
                powerUps.add(new PowerUp());
                powerUpCounter = 0;
            }
        }

        updateEntities();

        if (moto.isEnter() && !moto.isPaused()) {
            checkCollisions();
        }
    }

    /**
     * Actualiza y elimina las entidades inactivas de las listas.
     */
    private void updateEntities() {
        Iterator<Obstacle> itO = obstacles.iterator();
        while (itO.hasNext()) {
            Obstacle o = itO.next();
            o.update();
            if (!o.isActive()) itO.remove();
        }

        Iterator<PowerUp> itP = powerUps.iterator();
        while (itP.hasNext()) {
            PowerUp p = itP.next();
            p.update();
            if (!p.isActive()) itP.remove();
        }
    }

    /**
     * Detecta colisiones entre la moto y los obstáculos o power-ups.
     * Una colisión con obstáculo resta una vida.
     * Una colisión con power-up aplica su efecto y lo elimina.
     */
    private void checkCollisions() {
        for (Obstacle o : obstacles) {
            if (!o.isCollided() && moto.getHitbox().intersects(o.getHitbox())) {
                o.setCollided(true);
                soundManager.play("colision.wav");
                moto.loseLife();
                if (moto.getLives() <= 0) {
                    playing = false;
                    return;
                }
            }
        }

        Iterator<PowerUp> itP = powerUps.iterator();
        while (itP.hasNext()) {
            PowerUp p = itP.next();
            java.awt.Rectangle pBox = new java.awt.Rectangle(
                    p.getX() - 5, p.getY() - 5,
                    p.getWidht() + 10, p.getHeight() + 10
            );
            if (moto.getHitbox().intersects(pBox)) {
                moto.applyPowerUp(p.getType());
                itP.remove();
            }
        }
    }

    /**
     * Retorna la lista de obstáculos activos.
     *
     * @return lista de obstáculos
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Retorna la lista de power-ups activos.
     *
     * @return lista de power-ups
     */
    public List<PowerUp> getPowerUps()   {
        return powerUps;
    }

    /**
     * Retorna el tiempo transcurrido en la partida actual.
     *
     * @return segundos jugados
     */
    public int getGameSeconds()          {
        return gameSeconds;
    }

    /**
     * Indica si el juego está en curso.
     *
     * @return {@code true} si el juego sigue activo
     */
    public boolean isPlaying()           {
        return playing;
    }
}