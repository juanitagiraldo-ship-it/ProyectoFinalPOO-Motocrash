package view;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de ingreso del nombre del jugador en MotoCrash.
 * Se muestra antes de iniciar una partida y permite al jugador
 * ingresar su nombre para registrarlo en el historial y el Top 3.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class PlayerNamePanel extends JPanel {

    /**
     * Constructor del panel de ingreso de nombre.
     * Inicializa el campo de texto y los botones de navegación.
     *
     * @param mainWindow referencia a la ventana principal para navegación
     */
    public PlayerNamePanel(MainWindow mainWindow) {
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 40, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill  = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 60, 10, 60);

        // Título
        JLabel titulo = new JLabel("¿CÓMO TE LLAMAS?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 28));
        titulo.setForeground(Color.YELLOW);
        gbc.insets = new Insets(60, 60, 10, 60);
        add(titulo, gbc);

        JLabel sub = new JLabel("Ingresa tu nombre para continuar", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.ITALIC, 13));
        sub.setForeground(new Color(180, 220, 180));
        gbc.insets = new Insets(0, 60, 30, 60);
        add(sub, gbc);

        // Campo de texto
        JTextField campoNombre = new JTextField();
        campoNombre.setFont(new Font("Arial", Font.BOLD, 18));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.setPreferredSize(new Dimension(300, 45));
        campoNombre.setBackground(new Color(20, 55, 25));
        campoNombre.setForeground(Color.WHITE);
        campoNombre.setCaretColor(Color.YELLOW);
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 160, 60), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.insets = new Insets(0, 60, 20, 60);
        add(campoNombre, gbc);

        // Mensaje de error
        JLabel lblError = new JLabel(" ", SwingConstants.CENTER);
        lblError.setForeground(new Color(255, 80, 80));
        lblError.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.insets = new Insets(0, 60, 10, 60);
        add(lblError, gbc);

        // Botón jugar
        JButton btnJugar = new JButton("   ¡A JUGAR!");
        btnJugar.setPreferredSize(new Dimension(300, 50));
        btnJugar.setBackground(new Color(40, 160, 40));
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFont(new Font("Arial", Font.BOLD, 16));
        btnJugar.setFocusPainted(false);
        btnJugar.setBorderPainted(false);
        btnJugar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.insets = new Insets(5, 60, 5, 60);
        add(btnJugar, gbc);

        // Botón volver
        JButton btnVolver = new JButton("  VOLVER");
        btnVolver.setPreferredSize(new Dimension(300, 40));
        btnVolver.setBackground(new Color(30, 100, 30));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 13));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btnVolver, gbc);

        // Acciones
        btnJugar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            if (nombre.isEmpty()) {
                lblError.setText("  Debes ingresar tu nombre");
            } else {
                mainWindow.setNombreJugador(nombre);
                mainWindow.changePanel("JUEGO");
            }
        });

        campoNombre.addActionListener(e -> btnJugar.doClick());
        btnVolver.addActionListener(e -> mainWindow.changePanel("MENU"));
    }
}