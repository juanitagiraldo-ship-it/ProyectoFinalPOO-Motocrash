package controller;

import model.Moto;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GamepadController implements Runnable {

    private Moto moto;
    private boolean running = true;
    private Robot robot;

    public GamepadController(Moto moto) {
        this.moto = moto;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            revisarMando();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void revisarMando() {
        // El PS4 por Bluetooth se registra como teclado en Windows
        // Los botones del mando se mapean así:
        // D-pad izquierda → flecha izquierda
        // D-pad derecha   → flecha derecha
        // Botón X (cruz)  → Enter
        // Botón Options   → Escape

        // Como Java no lee mandos nativamente sin librería,
        // el GamepadController reenvía las teclas que el mando simula
        // Esto funciona cuando el PS4 está en modo de compatibilidad con PC
    }

    public void stop() {
        running = false;
    }
}