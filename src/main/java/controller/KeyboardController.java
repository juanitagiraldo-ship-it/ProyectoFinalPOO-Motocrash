package controller;

import model.Moto;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controlador de teclado del juego MotoCrash.
 * Captura las teclas presionadas por el jugador y las traduce
 * en acciones sobre la moto. Soporta flechas y teclas WASD.
 *
 * @author Juan Esteban Rivera Duque
 * @version 1.0
 */
public class KeyboardController extends KeyAdapter {

    private Moto moto;

    /**
     * Constructor del controlador de teclado.
     *
     * @param moto instancia de la moto que será controlada
     */
    public KeyboardController(Moto moto) {
        this.moto = moto;
    }

    /**
     * Procesa las teclas presionadas y ejecuta la acción correspondiente.
     * <ul>
     *   <li>{@code ENTER} — inicia el juego</li>
     *   <li>{@code SPACE} — pausa o reanuda el juego</li>
     *   <li>{@code LEFT} / {@code A} — mueve la moto al carril izquierdo</li>
     *   <li>{@code RIGHT} / {@code D} — mueve la moto al carril derecho</li>
     * </ul>
     *
     * @param e evento de teclado capturado
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER && !moto.isEnter()) {
            moto.setEnter(true);
        }

        if (key == KeyEvent.VK_SPACE && moto.isEnter()) {
            moto.togglePause();
        }

        if (moto.isEnter() && !moto.isPaused()) {
            if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) moto.leftMove();
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) moto.rightMove();
        }
    }
}