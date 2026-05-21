package controller;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import model.Moto;

import java.util.Arrays;
import java.util.List;

/**
 * Controlador de mando para MotoCrash usando XInput (via JNA).
 * Funciona con cualquier mando Xbox o PS4 con DS4Windows activo.
 *
 * Mapeo de controles:
 *   D-pad izquierda / stick izquierdo → carril izquierdo
 *   D-pad derecha  / stick izquierdo  → carril derecho
 *   Botón A (Cruz en PS4)             → iniciar partida
 *   Botón Start (Options en PS4)      → pausar / reanudar
 */
public class GamepadController implements Runnable {

    private final Moto moto;
    private volatile boolean running = true;

    private static final int POLL_INTERVAL_MS  = 80;
    private static final long MOVE_COOLDOWN_MS = 200;
    private static final long PAUSE_COOLDOWN_MS = 400;
    private long lastMoveTime  = 0;
    private long lastPauseTime = 0;

    // Máscaras de botones XInput
    private static final int XINPUT_DPAD_UP    = 0x0001;
    private static final int XINPUT_DPAD_DOWN  = 0x0002;
    private static final int XINPUT_DPAD_LEFT  = 0x0004;
    private static final int XINPUT_DPAD_RIGHT = 0x0008;
    private static final int XINPUT_START      = 0x0010;
    private static final int XINPUT_A          = 0x1000;
    private static final short STICK_THRESHOLD = 16000;

    // ── JNA: interfaz con xinput1_4.dll ──────────────────────────────────

    public interface XInput extends Library {
        XInput INSTANCE = Native.load("xinput1_4", XInput.class);
        int XInputGetState(int dwUserIndex, XINPUT_STATE pState);
    }

    public static class XINPUT_GAMEPAD extends Structure {
        public short wButtons;
        public byte  bLeftTrigger;
        public byte  bRightTrigger;
        public short sThumbLX;
        public short sThumbLY;
        public short sThumbRX;
        public short sThumbRY;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("wButtons", "bLeftTrigger", "bRightTrigger",
                    "sThumbLX", "sThumbLY", "sThumbRX", "sThumbRY");
        }
    }

    public static class XINPUT_STATE extends Structure {
        public int           dwPacketNumber;
        public XINPUT_GAMEPAD Gamepad;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("dwPacketNumber", "Gamepad");
        }
    }

    // ─────────────────────────────────────────────────────────────────────

    public GamepadController(Moto moto) {
        this.moto = moto;
    }

    @Override
    public void run() {
        boolean mandoDetectado = false;
        XINPUT_STATE state = new XINPUT_STATE();

        // Esperar hasta detectar el mando (máximo 10 segundos)
        for (int i = 0; i < 100; i++) {
            try {
                if (XInput.INSTANCE.XInputGetState(0, state) == 0) {
                    mandoDetectado = true;
                    System.out.println("[GamepadController] Mando detectado via XInput.");
                    break;
                }
            } catch (Exception e) {
                System.err.println("[GamepadController] Error al inicializar XInput: " + e.getMessage());
                return;
            }
            try { Thread.sleep(100); } catch (InterruptedException e) { return; }
        }

        if (!mandoDetectado) {
            System.out.println("[GamepadController] No se detectó mando. Conecta el mando y asegúrate de tener DS4Windows abierto.");
            return;
        }

        while (running) {
            try {
                if (XInput.INSTANCE.XInputGetState(0, state) == 0) {
                    procesarEntrada(state.Gamepad);
                }
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("[GamepadController] Error leyendo mando: " + e.getMessage());
                break;
            }
        }
    }

    private void procesarEntrada(XINPUT_GAMEPAD gp) {
        int  botones = gp.wButtons & 0xFFFF;
        long ahora   = System.currentTimeMillis();

        // Iniciar partida con botón A
        if ((botones & XINPUT_A) != 0 && !moto.isEnter()) {
            moto.setEnter(true);
        }

        // Pausar con Start, con cooldown para evitar toggle rápido
        if ((botones & XINPUT_START) != 0 && moto.isEnter()) {
            if (ahora - lastPauseTime >= PAUSE_COOLDOWN_MS) {
                moto.togglePause();
                lastPauseTime = ahora;
            }
        }

        // Movimiento lateral
        if (moto.isEnter() && !moto.isPaused()) {
            if (ahora - lastMoveTime >= MOVE_COOLDOWN_MS) {
                boolean izquierda = (botones & XINPUT_DPAD_LEFT) != 0
                        || gp.sThumbLX < -STICK_THRESHOLD;
                boolean derecha   = (botones & XINPUT_DPAD_RIGHT) != 0
                        || gp.sThumbLX >  STICK_THRESHOLD;

                if (izquierda) {
                    moto.leftMove();
                    lastMoveTime = ahora;
                } else if (derecha) {
                    moto.rightMove();
                    lastMoveTime = ahora;
                }
            }
        }
    }

    public void stop() {
        running = false;
    }
}