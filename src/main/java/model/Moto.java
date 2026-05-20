package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Representa la moto del jugador en el juego MotoCrash.
 * Gestiona la posición, movimiento entre carriles, vidas,
 * puntaje y efectos de power-ups.
 *
 * @author Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class Moto extends Entity {

    /** Posición vertical fija de la moto en pantalla. */
    private static final int Y_FIXED       = 520;
    /** Número de vidas iniciales del jugador. */
    private static final int INITIAL_LIVES = 3;
    /** Ancho del sprite de la moto en píxeles. */
    private static final int MOTO_WIDTH    = 40;
    /** Altura del sprite de la moto en píxeles. */
    private static final int MOTO_HEIGHT   = 70;

    /** Posiciones X de cada carril, coinciden con Obstacle y PowerUp. */
    private static final int[] LANES = {4, 53, 102};

    private int score;
    private int lives;
    private int currentLane;
    private int speed;
    private boolean enter;
    private boolean paused;
    private boolean shielded     = false;
    private int     shieldFrames = 0;
    private boolean turboActivo  = false;
    private int     turboFrames  = 0;

    /**
     * Constructor por defecto.
     * Inicializa la moto en el carril central con 3 vidas y puntaje 0.
     */
    public Moto() {
        super(53, Y_FIXED, cargarImagenEscalada());
        this.score       = 0;
        this.lives       = INITIAL_LIVES;
        this.currentLane = 1;
        this.speed       = 12;
        this.enter       = false;
        this.paused      = false;
    }

    /**
     * Carga y escala una imagen aleatoria de moto desde los recursos.
     *
     * @return imagen escalada de la moto
     */
    private static BufferedImage cargarImagenEscalada() {
        String nombre   = "moto" + (new Random().nextInt(4) + 1) + ".png";
        BufferedImage original = uploadImage(nombre);
        if (original == null) return null;
        Image escalada  = original.getScaledInstance(MOTO_WIDTH, MOTO_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage resultado = new BufferedImage(MOTO_WIDTH, MOTO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        resultado.getGraphics().drawImage(escalada, 0, 0, null);
        return resultado;
    }

    /**
     * Mueve la moto al carril izquierdo si no está en el primero.
     */
    public void leftMove()  {
        if (currentLane > 0) currentLane--;
    }

    /**
     * Mueve la moto al carril derecho si no está en el último.
     */
    public void rightMove() {
        if (currentLane < 2) currentLane++;
    }

    /**
     * Alterna entre pausa y reanudación del juego.
     */
    public void togglePause() {
        this.paused = !this.paused;
    }

    /**
     * Actualiza la posición de la moto y los contadores de power-ups.
     * Solo se ejecuta si el juego ha iniciado y no está en pausa.
     */
    @Override
    public void update() {
        if (!enter || paused) return;

        int targetX = LANES[currentLane];
        int velocidadActual = turboActivo ? speed * 2 : speed;
        if      (getX() < targetX) setX(Math.min(getX() + velocidadActual, targetX));
        else if (getX() > targetX) setX(Math.max(getX() - velocidadActual, targetX));

        score += turboActivo ? 3 : 1;

        if (shielded) {
            shieldFrames--;
            if (shieldFrames <= 0) shielded = false;
        }

        if (turboActivo) {
            turboFrames--;
            if (turboFrames <= 0) turboActivo = false;
        }
    }

    /**
     * Resta una vida al jugador al colisionar con un obstáculo.
     * Si tiene escudo activo, lo absorbe sin perder vida.
     */
    public void loseLife() {
        if (shielded) {
            shielded     = false;
            shieldFrames = 0;
            return;
        }
        if (lives > 0) lives--;
    }

    /**
     * Aplica el efecto del power-up recogido.
     *
     * @param type tipo de power-up: {@code 0} escudo, {@code 1} turbo
     */
    public void applyPowerUp(int type) {
        if (type == PowerUp.TYPE_SHIELD) {
            shielded     = true;
            shieldFrames = 250;
        } else {
            turboActivo  = true;
            turboFrames  = 150;
            shielded     = true;
            shieldFrames = 150;
        }
    }

    /**
     * Resetea todos los atributos de la moto para una nueva partida.
     */
    public void resetear() {
        score        = 0;
        lives        = INITIAL_LIVES;
        currentLane  = 1;
        enter        = false;
        paused       = false;
        shielded     = false;
        shieldFrames = 0;
        turboActivo  = false;
        turboFrames  = 0;
        setX(53);
        setY(Y_FIXED);
    }

    /**
     * Indica si el turbo está activo.
     *
     * @return {@code true} si el turbo está activo
     */
    public boolean isTurboActivo() {
        return turboActivo;
    }

    /**
     * Indica si el escudo está activo.
     *
     * @return {@code true} si el escudo está activo
     */
    public boolean isShielded()  {
        return shielded;
    }

    /**
     * Indica si el juego está en pausa.
     *
     * @return {@code true} si está pausado
     */
    public boolean isPaused()    {
        return paused;
    }

    /**
     * Indica si el jugador ha presionado Enter para iniciar.
     *
     * @return {@code true} si el juego ha comenzado
     */
    public boolean isEnter()     {
        return enter;
    }

    /**
     * Establece el estado de inicio del juego.
     *
     * @param enter {@code true} para iniciar el juego
     */
    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    /**
     * Retorna el puntaje actual del jugador.
     *
     * @return puntaje acumulado
     */
    public int getScore() {
        return score;
    }

    /**
     * Retorna las vidas restantes del jugador.
     *
     * @return número de vidas
     */
    public int getLives() {
        return lives;
    }
}