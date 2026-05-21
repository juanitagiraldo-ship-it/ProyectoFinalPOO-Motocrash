package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Clase abstracta base para todas las entidades del juego.
 * Define atributos comunes como posición, tamaño, sprite y estado activo.
 * Todas las entidades del juego heredan de esta clase.
 *
 * @author Alexandra Giraldo Ramirez
 * @version 1.0
 */
public abstract class Entity {

    /** Dirección nula, entidad sin movimiento. */
    protected static final int DIR_NONE  = 0;
    /** Dirección izquierda. */
    protected static final int DIR_LEFT  = 1;
    /** Dirección arriba. */
    protected static final int DIR_UP    = 2;
    /** Dirección derecha. */
    protected static final int DIR_RIGHT = 3;
    /** Dirección abajo. */
    protected static final int DIR_DOWN  = 4;

    private int x;
    private int y;
    private int widht;
    private int height;
    private BufferedImage sprite;
    private boolean active;
    private int direction;

    /**
     * Constructor principal de la entidad.
     *
     * @param x      posición horizontal inicial en píxeles
     * @param y      posición vertical inicial en píxeles
     * @param sprite imagen del sprite asociado a la entidad
     */
    public Entity(int x, int y, BufferedImage sprite) {
        this.x      = x;
        this.y      = y;
        this.sprite = sprite;

        if (sprite != null) {
            this.widht  = sprite.getWidth();
            this.height = sprite.getHeight();
        }

        this.active    = true;
        this.direction = DIR_NONE;
    }

    /**
     * Actualiza el estado de la entidad en cada frame del juego.
     * Cada subclase implementa su propia lógica de actualización.
     */
    public abstract void update();

    /**
     * Retorna el área de colisión de la entidad.
     *
     * @return rectángulo que representa la hitbox de la entidad
     */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, widht, height);
    }

    /**
     * Retorna una hitbox futura para predecir colisiones.
     *
     * @param pX posición horizontal futura
     * @param pY posición vertical futura
     * @return rectángulo en la posición predicha
     */
    public Rectangle getPredictionHitBox(int pX, int pY) {
        return new Rectangle(pX, pY, widht, height);
    }

    /**
     * Carga una imagen desde la carpeta de recursos del proyecto.
     *
     * @param nombre nombre del archivo de imagen incluyendo extensión
     * @return imagen cargada como {@code BufferedImage}, o {@code null} si no se encuentra
     */
    public static BufferedImage uploadImage(String nombre) {
        try {
            InputStream is = Entity.class.getResourceAsStream("/images/" + nombre);
            if (is == null) {
                System.err.println("Imagen no encontrada: " + nombre);
                return null;
            }
            return ImageIO.read(is);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + nombre);
            return null;
        }
    }

    /**
     * Retorna la posición horizontal de la entidad.
     *
     * @return coordenada X en píxeles
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la posición horizontal de la entidad.
     *
     * @param x nueva coordenada X en píxeles
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna la posición vertical de la entidad.
     *
     * @return coordenada Y en píxeles
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la posición vertical de la entidad.
     *
     * @param y nueva coordenada Y en píxeles
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retorna el ancho de la entidad.
     *
     * @return ancho en píxeles
     */
    public int getWidht() {
        return widht;
    }

    /**
     * Establece el ancho de la entidad.
     *
     * @param widht nuevo ancho en píxeles
     */
    public void setWidht(int widht) {
        this.widht = widht;
    }

    /**
     * Retorna la altura de la entidad.
     *
     * @return altura en píxeles
     */
    public int getHeight() {
        return height;
    }

    /**
     * Establece la altura de la entidad.
     *
     * @param height nueva altura en píxeles
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Retorna el sprite actual de la entidad.
     *
     * @return imagen del sprite
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    /**
     * Establece el sprite de la entidad y actualiza su tamaño.
     *
     * @param sprite nueva imagen del sprite
     */
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            this.widht  = sprite.getWidth();
            this.height = sprite.getHeight();
        }
    }

    /**
     * Indica si la entidad está activa en el juego.
     *
     * @return {@code true} si está activa, {@code false} si fue eliminada
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activa o desactiva la entidad.
     *
     * @param active {@code true} para activar, {@code false} para desactivar
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Retorna la dirección actual de movimiento de la entidad.
     *
     * @return constante de dirección {@code DIR_NONE}, {@code DIR_LEFT}, etc.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Establece la dirección de movimiento de la entidad.
     *
     * @param direction constante de dirección
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
}