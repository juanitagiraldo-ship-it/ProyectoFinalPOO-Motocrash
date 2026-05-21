package model;

import java.awt.image.BufferedImage;

/**
 * Representa la carretera del juego MotoCrash.
 * Gestiona el desplazamiento vertical continuo de la imagen de fondo
 * para simular el movimiento hacia adelante de la moto.
 *
 * @author Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class Road {

    private int y1;
    private int y2;
    private int speed;
    private BufferedImage backgroundImage;

    /**
     * Constructor que inicializa la carretera con una velocidad dada.
     * Carga la imagen de fondo y posiciona las dos copias para el scroll continuo.
     *
     * @param initialSpeed velocidad inicial de desplazamiento en píxeles por frame
     */
    public Road(int initialSpeed) {
        this.backgroundImage = Entity.uploadImage("carretera.png");
        this.speed = initialSpeed;
        this.y1    = 0;
        this.y2    = -backgroundImage.getHeight();
    }

    /**
     * Actualiza la posición vertical de las dos copias de la carretera.
     * Cuando una copia sale de pantalla, se reposiciona arriba de la otra
     * para crear el efecto de scroll infinito.
     */
    public void update() {
        y1 += speed;
        y2 += speed;

        if (y1 >= backgroundImage.getHeight()) {
            y1 = y2 - backgroundImage.getHeight();
        }
        if (y2 >= backgroundImage.getHeight()) {
            y2 = y1 - backgroundImage.getHeight();
        }
    }

    /**
     * Cambia la velocidad de desplazamiento de la carretera.
     *
     * @param newSpeed nueva velocidad en píxeles por frame
     */
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * Retorna la posición vertical de la primera copia de la carretera.
     *
     * @return coordenada Y de la primera imagen
     */
    public int getY1() {
        return y1;
    }

    /**
     * Establece la posición vertical de la primera copia.
     *
     * @param y1 nueva coordenada Y
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * Retorna la posición vertical de la segunda copia de la carretera.
     *
     * @return coordenada Y de la segunda imagen
     */
    public int getY2() {
        return y2;
    }

    /**
     * Establece la posición vertical de la segunda copia.
     *
     * @param y2 nueva coordenada Y
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
     * Retorna la imagen de fondo de la carretera.
     *
     * @return imagen de la carretera
     */
    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Establece una nueva imagen de fondo para la carretera.
     *
     * @param backgroundImage nueva imagen de fondo
     */
    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}