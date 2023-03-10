package presentacion;

import ucd.comp2011j.engine.ScoreKeeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Puntaje extends JPanel {
    private ScoreKeeper score;
    private Image imagenFondo;

    /**
     * Constructor de la clase Puntaje.
     * Carga la imagen del fondo del marcador de puntajes.
     *
     * @param score ScoreKeeper que se utilizará para obtener los puntajes.
     */
    public Puntaje(ScoreKeeper score) {
        this.score = score;
        try {
            imagenFondo = ImageIO.read(new File("src/recursos/scoreboard.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pinta el componente gráfico del panel de puntuaciones, incluyendo el fondo, el texto y las puntuaciones obtenidas.
     *
     * @param g el objeto Graphics utilizado para pintar el componente gráfico
     */
    public void paintComponent(Graphics g) {
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), null);
        g.setColor(new Color(40, 64, 110));
        Font font = new Font("Arial", Font.BOLD, 30);

        font = new Font("Lucida Console", Font.BOLD, 17);
        g.setFont(font);
        int y = 150;
        for (ucd.comp2011j.engine.Score s : score.getScores()) {
            g.drawString("- " + s.getName() + ": " + s.getScore(), 65, y);
            y += 25;
        }
    }
}
