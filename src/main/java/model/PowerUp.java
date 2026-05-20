package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Representa un power-up en el juego MotoCrash.
 * Los power-ups aparecen en carriles aleatorios y descienden
 * por la pantalla. Al ser recogidos por la moto aplican un efecto especial.
 * Existen dos tipos: escudo y turbo.
 *
 * @author Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class PowerUp extends Entity {

    /** Tipo escudo: absorbe un golpe de un obstáculo. */
    public static final int TYPE_SHIELD = 0;
    /** Tipo turbo: velocidad doble e invencibilidad por 3 segundos. */
    public static final int TYPE_TURBO  = 1;

    /** Ancho del sprite del power-up en píxeles. */
    private static final int PU_WIDTH  = 35;
    /** Altura del sprite del power-up en píxeles. */
    private static final int PU_HEIGHT = 35;

    /** Posiciones X de cada carril, coinciden con Moto y Obstacle. */
    private static final int[] LANES = {4, 53, 102};

    private int type;
    private int speed = 5;

    /**
     * Constructor que crea un power-up de tipo aleatorio en un carril aleatorio.
     */
    public PowerUp() {
        super(0, -50, null);
        Random rand = new Random();
        this.type = rand.nextInt(2);
        BufferedImage sprite = cargarEscalado(
                type == TYPE_SHIELD ? "powerup_escudo.png" : "powerup_turbo.png"
        );
        setSprite(sprite);
        if (sprite != null) {
            setWidht(sprite.getWidth());
            setHeight(sprite.getHeight());
        }
        setX(LANES[rand.nextInt(3)]);
    }

    /**
     * Carga y escala una imagen de power-up desde los recursos.
     *
     * @param nombre nombre del archivo de imagen
     * @return imagen escalada del power-up
     */
    private static BufferedImage cargarEscalado(String nombre) {
        BufferedImage original = uploadImage(nombre);
        if (original == null) return null;
        Image escalada = original.getScaledInstance(PU_WIDTH, PU_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage resultado = new BufferedImage(PU_WIDTH, PU_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        resultado.getGraphics().drawImage(escalada, 0, 0, null);
        return resultado;
    }

    /**
     * Actualiza la posición vertical del power-up.
     * Lo desactiva cuando sale de la pantalla.
     */
    @Override
    public void update() {
        setY(getY() + speed);
        if (getY() > 700) setActive(false);
    }

    /**
     * Retorna el tipo de power-up.
     *
     * @return {@code TYPE_SHIELD} o {@code TYPE_TURBO}
     */
    public int getType() {
        return type;
    }
}