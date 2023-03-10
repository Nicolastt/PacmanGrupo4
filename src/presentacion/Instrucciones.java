package presentacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Instrucciones extends JPanel {
    private Image imagenFondo;

    /**
     * La variable imagenFondo almacena la imagen de fondo utilizada para mostrar las instrucciones del juego.
     * La imagen se carga desde un archivo en el sistema utilizando la clase ImageIO.
     */
    public Instrucciones() {
        try {
            imagenFondo = ImageIO.read(new File("src/recursos/instrucciones.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * El método paintComponent se utiliza para dibujar la imagen de fondo en la pantalla.
     *
     * @param g El objeto Graphics que se utiliza para dibujar en la pantalla.
     *          La imagen de fondo se dibuja en la posición (0,0) con el tamaño del componente actual.
     *          Se utiliza el método drawImage de Graphics para dibujar la imagen de fondo.
     */
    public void paintComponent(Graphics g) {

        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), null);

    }
}
