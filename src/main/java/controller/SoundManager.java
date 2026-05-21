package controller;

import javax.sound.sampled.*;
import java.io.InputStream;

/**
 * Gestor de sonidos del juego MotoCrash.
 * Permite reproducir efectos de sonido una sola vez y música
 * en bucle continuo. Maneja los clips de inicio y fondo
 * para poder detenerlos cuando sea necesario.
 *
 * @author Juan Esteban Rivera Duque
 * @version 1.0
 */
public class SoundManager {

    private Clip clipFondo;
    private Clip clipInicio;

    /**
     * Reproduce un archivo de sonido una sola vez.
     *
     * @param nombre nombre del archivo de sonido incluyendo extensión
     */
    public void play(String nombre) {
        try {
            InputStream is = getClass().getResourceAsStream("/sounds/" + nombre);
            if (is == null) {
                System.err.println("Sonido no encontrado: " + nombre);
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    new java.io.BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error reproduciendo: " + nombre);
        }
    }

    /**
     * Reproduce un archivo de sonido en bucle continuo.
     * Convierte el audio a formato PCM para garantizar compatibilidad.
     * Guarda referencia al clip para poder detenerlo después.
     *
     * @param nombre nombre del archivo de sonido incluyendo extensión
     */
    public void playLoop(String nombre) {
        try {
            InputStream is = getClass().getResourceAsStream("/sounds/" + nombre);
            if (is == null) {
                System.err.println("Sonido no encontrado: " + nombre);
                return;
            }
            AudioInputStream audioOriginal = AudioSystem.getAudioInputStream(
                    new java.io.BufferedInputStream(is));

            AudioFormat formatoBase = audioOriginal.getFormat();
            AudioFormat formatoPCM  = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    formatoBase.getSampleRate(),
                    16,
                    formatoBase.getChannels(),
                    formatoBase.getChannels() * 2,
                    formatoBase.getSampleRate(),
                    false
            );
            AudioInputStream audioPCM = AudioSystem.getAudioInputStream(formatoPCM, audioOriginal);

            Clip clip = AudioSystem.getClip();
            clip.open(audioPCM);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            if (nombre.equals("inicio.wav")) clipInicio = clip;
            if (nombre.equals("fondo.wav"))  clipFondo  = clip;

        } catch (Exception e) {
            System.err.println("Error reproduciendo loop: " + nombre + " - " + e.getMessage());
        }
    }

    /**
     * Detiene el sonido de inicio si está reproduciéndose.
     */
    public void stopInicio() {
        if (clipInicio != null && clipInicio.isRunning()) clipInicio.stop();
    }

    /**
     * Detiene la música de fondo si está reproduciéndose.
     */
    public void stopFondo() {
        if (clipFondo != null && clipFondo.isRunning()) clipFondo.stop();
    }

    /**
     * Detiene todos los sonidos activos.
     */
    public void stopAll() {
        stopInicio();
        stopFondo();
    }
}