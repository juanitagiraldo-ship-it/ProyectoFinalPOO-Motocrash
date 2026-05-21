package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Representa un obstáculo (carro enemigo) en el juego MotoCrash.
 * Los obstáculos se generan en carriles aleatorios y descienden
 * por la pantalla a velocidad progresiva.
 *
 * @author Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class Obstacle extends Entity {

    /** Ancho del sprite del carro en píxeles. */
    private static final int CAR_WIDTH  = 40;
    /** Altura del sprite del carro en píxeles. */
    private static final int CAR_HEIGHT = 75;

    /** Posiciones X de cada carril, coinciden con Moto y PowerUp. */
    private static final int[] LANES = {4, 53, 102};

    private int speed;
    private boolean collided = false;

    /**
     * Constructor que crea un obstáculo en un carril aleatorio.
     *
     * @param speedGlobal velocidad global actual del juego
     */
    public Obstacle(int speedGlobal) {
        super(0, -120, cargarCocheEscalado());
        Random rand = new Random();
        setX(LANES[rand.nextInt(3)]);
        this.speed = speedGlobal + rand.nextInt(3);
    }

    /**
     * Carga y escala una imagen aleatoria de carro desde los recursos.
     *
     * @return imagen escalada del carro
     */
    private static BufferedImage cargarCocheEscalado() {
        String nombre = "coche" + (new Random().nextInt(5) + 1) + ".png";
        BufferedImage original = uploadImage(nombre);
        if (original == null) return null;
        Image escalada = original.getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage resultado = new BufferedImage(CAR_WIDTH, CAR_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        resultado.getGraphics().drawImage(escalada, 0, 0, null);
        return resultado;
    }

    /**
     * Actualiza la posición vertical del obstáculo.
     * Lo desactiva cuando sale de la pantalla.
     */
    @Override
    public void update() {
        setY(getY() + speed);
        if (getY() > 700) setActive(false);
    }

    /**
     * Indica si este obstáculo ya colisionó con la moto.
     *
     * @return {@code true} si ya hubo colisión
     */
    public boolean isCollided() {
        return collided;
    }

    /**
     * Establece el estado de colisión del obstáculo.
     *
     * @param c {@code true} si colisionó con la moto
     */
    public void setCollided(boolean c) {
        this.collided = c;
    }

    /**
     * Cambia la velocidad del obstáculo.
     *
     * @param speed nueva velocidad en píxeles por frame
     */
    public void setVelocidad(int speed) {
        this.speed = speed;
    }
}