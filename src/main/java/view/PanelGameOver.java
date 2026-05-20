package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de Game Over del juego MotoCrash.
 * Muestra los resultados de la partida finalizada: nombre del jugador,
 * puntaje obtenido, tiempo jugado y el Top 3 del historial global.
 * Permite volver al menú principal para jugar de nuevo.
 *
 * @author Juan Esteban Rivera Duque, Alexandra Giraldo Ramirez
 * @version 1.0
 */
public class PanelGameOver extends JPanel {

    private MainWindow mainWindow;

    /**
     * Constructor del panel de Game Over.
     *
     * @param mainWindow referencia a la ventana principal para navegación
     */
    public PanelGameOver(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 40, 20));
    }

    /**
     * Actualiza y muestra los resultados de la partida finalizada.
     * Reconstruye todos los componentes con los datos nuevos.
     *
     * @param nombreJugador nombre del jugador que terminó la partida
     * @param puntaje       puntaje final obtenido
     * @param segundos      tiempo total jugado en segundos
     * @param top3          lista con el Top 3 de jugadores, cada entrada es {nombre, puntaje}
     */
    public void actualizar(String nombreJugador, int puntaje, int segundos, List<String[]> top3) {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill  = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("GAME OVER", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 42));
        titulo.setForeground(new Color(220, 50, 50));
        gbc.insets = new Insets(30, 60, 5, 60);
        add(titulo, gbc);

        // Nombre jugador
        JLabel lblNombre = new JLabel(nombreJugador, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(Color.YELLOW);
        gbc.insets = new Insets(5, 60, 20, 60);
        add(lblNombre, gbc);

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(60, 120, 60));
        gbc.insets = new Insets(0, 40, 20, 40);
        add(sep, gbc);

        // Puntaje y tiempo
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBackground(new Color(20, 55, 25));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        statsPanel.add(crearStat("PUNTAJE", String.valueOf(puntaje)));
        statsPanel.add(crearStat("TIEMPO", formatTime(segundos)));
        gbc.insets = new Insets(0, 40, 20, 40);
        add(statsPanel, gbc);

        // Top 3
        JLabel lblTop = new JLabel("   TOP 3", SwingConstants.CENTER);
        lblTop.setFont(new Font("Arial Black", Font.BOLD, 16));
        lblTop.setForeground(Color.YELLOW);
        gbc.insets = new Insets(5, 60, 8, 60);
        add(lblTop, gbc);

        String[] medallas = {"1.", "2.", "3."};
        Color[]  colores  = {
                new Color(255, 215, 0),
                new Color(192, 192, 192),
                new Color(205, 127, 50)
        };

        gbc.insets = new Insets(3, 40, 3, 40);
        for (int i = 0; i < top3.size(); i++) {
            String[] entrada = top3.get(i);
            JPanel fila = new JPanel(new BorderLayout(10, 0));
            fila.setBackground(new Color(20, 55, 25));
            fila.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

            JLabel medalla = new JLabel(medallas[i] + "  " + entrada[0]);
            medalla.setFont(new Font("Arial", Font.BOLD, 14));
            medalla.setForeground(colores[i]);

            JLabel pts = new JLabel(entrada[1] + " pts", SwingConstants.RIGHT);
            pts.setFont(new Font("Arial", Font.BOLD, 14));
            pts.setForeground(colores[i]);

            fila.add(medalla, BorderLayout.WEST);
            fila.add(pts,     BorderLayout.EAST);
            add(fila, gbc);
        }

        // Separador
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(60, 120, 60));
        gbc.insets = new Insets(15, 40, 15, 40);
        add(sep2, gbc);

        // Botón volver
        JButton btnVolver = new JButton("↩  VOLVER AL INICIO");
        btnVolver.setPreferredSize(new Dimension(300, 50));
        btnVolver.setBackground(new Color(40, 160, 40));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 15));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> mainWindow.volverAlInicio());
        gbc.insets = new Insets(0, 60, 30, 60);
        add(btnVolver, gbc);

        revalidate();
        repaint();
    }

    /**
     * Crea un panel con título y valor para mostrar estadísticas.
     *
     * @param titulo etiqueta descriptiva de la estadística
     * @param valor  valor a mostrar
     * @return panel con la estadística formateada
     */
    private JPanel crearStat(String titulo, String valor) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(new Color(20, 55, 25));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setForeground(new Color(150, 210, 150));

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setForeground(Color.WHITE);

        panel.add(lblTitulo);
        panel.add(lblValor);
        return panel;
    }

    /**
     * Formatea segundos en formato MM:SS.
     *
     * @param segundos tiempo en segundos
     * @return cadena con formato {@code MM:SS}
     */
    private String formatTime(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }
}