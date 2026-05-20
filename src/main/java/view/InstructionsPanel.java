package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de instrucciones del juego MotoCrash.
 * Muestra las reglas del juego, los controles disponibles
 * y los efectos de cada power-up. Permite navegar al juego
 * o volver al menú principal.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class InstructionsPanel extends JPanel {

    /**
     * Constructor del panel de instrucciones.
     * Inicializa y organiza todas las reglas y controles del juego.
     *
     * @param ventana referencia a la ventana principal para navegación
     */
    public InstructionsPanel(MainWindow ventana) {
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 40, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill  = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 40, 5, 40);

        // Título
        JLabel titulo = new JLabel("INSTRUCCIONES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setForeground(Color.YELLOW);
        gbc.insets = new Insets(25, 40, 15, 40);
        add(titulo, gbc);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(60, 120, 60));
        gbc.insets = new Insets(0, 40, 15, 40);
        add(sep, gbc);

        // Reglas
        String[][] reglas = {
                {"   MOVIMIENTO",  "Flechas ← → o A / D para cambiar de carril"},
                {"   PAUSA",       "SPACE para pausar y reanudar el juego"},
                {"   ENEMIGOS",    "Evita los carros — cada choque quita una vida"},
                {"   VIDAS",       "Empiezas con 3 vidas. Sin vidas = Game Over"},
                {"   ESCUDO",      "Recoge el escudo azul para absorber un golpe"},
                {"   TURBO",       "Recoge el turbo amarillo: velocidad x2 + invencible"},
                {"   PUNTAJE",     "Avanzar suma puntos. El turbo da puntos extra"},
                {"   DIFICULTAD",  "Cada 300 puntos los carros van más rápido"},
        };

        gbc.insets = new Insets(4, 40, 4, 40);
        for (String[] regla : reglas) {
            JPanel fila = new JPanel(new BorderLayout(10, 0));
            fila.setBackground(new Color(20, 55, 25));
            fila.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

            JLabel icono = new JLabel(regla[0]);
            icono.setFont(new Font("Arial", Font.BOLD, 13));
            icono.setForeground(Color.YELLOW);
            icono.setPreferredSize(new Dimension(120, 20));

            JLabel desc = new JLabel(regla[1]);
            desc.setFont(new Font("Arial", Font.PLAIN, 12));
            desc.setForeground(new Color(200, 230, 200));

            fila.add(icono, BorderLayout.WEST);
            fila.add(desc,  BorderLayout.CENTER);
            add(fila, gbc);
        }

        // Separador
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(60, 120, 60));
        gbc.insets = new Insets(15, 40, 10, 40);
        add(sep2, gbc);

        // Botón jugar
        JButton btnJugar = crearBoton("   JUGAR AHORA", new Color(40, 160, 40));
        btnJugar.addActionListener(e -> ventana.changePanel("NOMBRE"));
        gbc.insets = new Insets(5, 60, 5, 60);
        add(btnJugar, gbc);

        // Botón volver
        JButton btnVolver = crearBoton("  VOLVER AL MENÚ", new Color(30, 100, 30));
        btnVolver.addActionListener(e -> ventana.changePanel("MENU"));
        add(btnVolver, gbc);
    }

    /**
     * Crea un botón con estilo personalizado para la interfaz.
     *
     * @param texto texto del botón
     * @param color color de fondo del botón
     * @return botón configurado con el estilo del juego
     */
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}