package controller;

import view.MainWindow;

import javax.swing.*;

/**
 * Clase principal del juego MotoCrash.
 * Punto de entrada de la aplicación. Lanza la ventana principal
 * en el hilo de despacho de eventos de Swing para garantizar
 * la seguridad en el manejo de la interfaz gráfica.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de la aplicación.
     * Inicializa la ventana principal del juego usando
     * {@code SwingUtilities.invokeLater} para ejecutarse
     * en el hilo de eventos de Swing.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }
}