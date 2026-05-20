package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de bienvenida del juego MotoCrash.
 * Muestra el logo de la UAM, el título del juego, los botones
 * de navegación, los integrantes del equipo y el docente.
 * Es la primera pantalla que ve el jugador al iniciar la aplicación.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class WelcomePanel extends JPanel {

    /**
     * Constructor del panel de bienvenida.
     * Inicializa y organiza todos los componentes visuales.
     *
     * @param mainWindow referencia a la ventana principal para navegación
     */
    public WelcomePanel(MainWindow mainWindow) {
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 40, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill  = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 40, 8, 40);

        // Logo UAM
        ImageIcon logoIcon    = new ImageIcon(getClass().getResource("/images/logoUAM.png"));
        Image     logoEscalado = logoIcon.getImage().getScaledInstance(250, 80, Image.SCALE_SMOOTH);
        JLabel    logoLabel   = new JLabel(new ImageIcon(logoEscalado), SwingConstants.CENTER);
        gbc.insets = new Insets(30, 40, 5, 40);
        add(logoLabel, gbc);

        // Título
        JLabel title = new JLabel("MOTOCRASH", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 48));
        title.setForeground(Color.YELLOW);
        gbc.insets = new Insets(0, 40, 5, 40);
        add(title, gbc);

        // Subtítulo materia
        JLabel materia = new JLabel("Programación Orientada a Objetos", SwingConstants.CENTER);
        materia.setFont(new Font("Arial", Font.ITALIC, 13));
        materia.setForeground(new Color(180, 220, 180));
        gbc.insets = new Insets(0, 40, 25, 40);
        add(materia, gbc);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(60, 120, 60));
        gbc.insets = new Insets(0, 40, 20, 40);
        add(sep, gbc);

        // Botón Iniciar
        JButton btnIniciar = crearBoton("   INICIAR JUEGO", new Color(40, 160, 40));
        btnIniciar.addActionListener(e -> mainWindow.changePanel("NOMBRE"));
        gbc.insets = new Insets(5, 60, 5, 60);
        add(btnIniciar, gbc);

        // Botón Instrucciones
        JButton btnInstrucciones = crearBoton("   INSTRUCCIONES", new Color(30, 100, 30));
        btnInstrucciones.addActionListener(e -> mainWindow.changePanel("REGLAS"));
        add(btnInstrucciones, gbc);

        // Integrantes
        gbc.insets = new Insets(25, 40, 5, 40);
        JLabel lblEquipo = new JLabel("Equipo de desarrollo:", SwingConstants.CENTER);
        lblEquipo.setFont(new Font("Arial", Font.BOLD, 12));
        lblEquipo.setForeground(new Color(150, 210, 150));
        add(lblEquipo, gbc);

        String[] integrantes = {"Alexandra Giraldo Ramirez", "Juan Esteban Rivera Duque"};
        gbc.insets = new Insets(2, 40, 2, 40);
        for (String nombre : integrantes) {
            JLabel lbl = new JLabel("• " + nombre, SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.PLAIN, 12));
            lbl.setForeground(new Color(200, 230, 200));
            add(lbl, gbc);
        }

        gbc.insets = new Insets(20, 40, 10, 40);
        JLabel docente = new JLabel("Docente: Diana Henao", SwingConstants.CENTER);
        docente.setFont(new Font("Arial", Font.ITALIC, 11));
        docente.setForeground(new Color(130, 180, 130));
        add(docente, gbc);
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