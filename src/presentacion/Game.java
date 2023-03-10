package presentacion;

import javax.imageio.ImageIO;
import javax.swing.*;

import logica.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Game extends JPanel {
    private final Juego game;

    /**
     * Construye un nuevo objeto Game con la instancia de juego de Juego proporcionada.
     *
     * @param game la instancia de juego de Juego que se representará
     * @throws IOException si ocurre un error de E/S (Entrada/Salida)
     */
    public Game(Juego game) throws IOException {
        this.game = game;
    }

    /**
     * Representa el estado actual del juego en la pantalla utilizando el contexto de gráficos dado.
     *
     * @param g el contexto de gráficos a utilizar para la representación
     */
    public void paintComponent(Graphics g) {
        if (game != null) {
            g.setColor(new Color(50, 50, 50));
            g.fillRect(0, 0, Juego.ANCHO_PANTALLA, Juego.ALTURA_PANTALLA);

            if (!game.isGameOver()) {
                // Dibuja las paredes y paredes de aire
                dibujarMuro(g, game.getlistaPared());
                dibujarMuroAire(g, game.getListaMuroAires());
                // Dibuja la comida y los power-ups
                dibujarComida(g, game.getListaComidas());
                dibujarComida(g, game.getFrutaActual());
                dibujarPoder(g, game.getPowerList());
                // Dibuja los fantasmas y al jugador
                dibujarFantasmas(g, game.getListaFantasmas(), game.getJugador());
                dibujarJugador(g, game.getJugador());
                // Dibuja la información del juego (puntuación, vidas, etc.)
                dibujarInformacion(g);
                // Si el juego está en pausa, dibuja un mensaje de pausa
                if (game.isPaused()) {
                    dibujarPausa(g);
                }
            } else {
                // Si el juego ha terminado, dibuja el mensaje de fin del juego
                drawGameOver(g);
            }
        }
    }

    /**
     * Dibuja el mensaje de fin del juego en la pantalla.
     *
     * @param g el contexto de gráficos a utilizar para la representación
     */
    public void drawGameOver(Graphics g) {
        g.setColor(new Color(255, 255, 0));
        g.setFont(new Font("Lucida Console", Font.BOLD, 15));
        g.drawString("Segunda es todo :( ", 60, 280);

    }

    /**
     * Dibuja el jugador en la posición actual en la pantalla.
     *
     * @param g el contexto de gráficos a utilizar para la representación
     * @param p el jugador a dibujar
     */
    public void dibujarJugador(Graphics g, Jugador p) {
        try {
            BufferedImage img = ImageIO.read(new File("src/recursos/buhofinal.png"));
            g.drawImage(img, p.getX() + 1, p.getY() + 1, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dibuja la comida en la pantalla.
     *
     * @param g           el contexto de gráficos a utilizar para la representación
     * @param listaComida la lista de comidas a dibujar
     */
    public void dibujarComida(Graphics g, ArrayList<Comida> listaComida) {
        g.setColor(new Color(255, 255, 255));
        for (Comida dot : listaComida) {
            int posX = dot.getColumna() * 20;
            int posY = dot.getFila() * 20 + 50;
            g.fillOval(posX + 7, posY + 7, 6, 6);
        }
    }

    /**
     * Método que se encarga de dibujar una fruta en la pantalla.
     *
     * @param g     objeto Graphics utilizado para dibujar la fruta
     * @param fruta objeto de tipo Fruta que contiene la información de la fruta a dibujar
     */
    public void dibujarComida(Graphics g, Fruta fruta) {
        if (fruta != null) {
            try {
                // Cargar la imagen de la fruta
                Image imagenFruta = ImageIO.read(new File("src/recursos/cocacola.png"));

                int posX = fruta.getColumna() * 20;
                int posY = fruta.getFila() * 20 + 50;

                // Dibujar la imagen de la fruta en la posición calculada
                g.drawImage(imagenFruta, posX, posY, null);
            } catch (IOException e) {
                // Manejar la excepción si no se puede cargar la imagen
                e.printStackTrace();
            }
        }
    }

    /**
     * Este método recibe un objeto Graphics y una lista de objetos Poder.
     * Se encarga de dibujar en el objeto Graphics la imagen de cada objeto Poder en la posición indicada.
     *
     * @param g:            objeto Graphics donde se dibujará la imagen del poder.
     * @param listaPoderes: lista de objetos Poder que se dibujarán.
     */
    public void dibujarPoder(Graphics g, ArrayList<Poder> listaPoderes) {
        try {
            // Cargar la imagen del poder
            Image imagenPoder = ImageIO.read(new File("src/recursos/poliburguer.png"));

            g.setColor(new Color(255, 255, 255));
            for (Poder power : listaPoderes) {
                int posX = power.getColumna() * 20;
                int posY = power.getFila() * 20 + 50;

                // Dibujar la imagen del poder en la posición calculada
                g.drawImage(imagenPoder, posX, posY, null);
            }
        } catch (IOException e) {
            // Manejar la excepción si no se puede cargar la imagen
            e.printStackTrace();
        }
    }


    /**
     * Crea un HashMap que asocia cada subclase de Fantasma con su imagen correspondiente almacenada en un archivo de
     * imagen ubicado en la carpeta "recursos".
     * Las imágenes se cargan utilizando la función ImageIO.read() y se almacenan en un objeto BufferedImage.
     * Posteriormente, se añaden al HashMap mediante la función put().
     */
    HashMap<Class<? extends Fantasma>, BufferedImage> mapaDeImagenes = new HashMap<>();

    {
        BufferedImage blinkyImg = ImageIO.read(new File("src/recursos/blinky.png"));
        BufferedImage pinkyImg = ImageIO.read(new File("src/recursos/pinky.png"));
        BufferedImage clydeImg = ImageIO.read(new File("src/recursos/clyde.png"));
        BufferedImage inkyImg = ImageIO.read(new File("src/recursos/inky.png"));
        mapaDeImagenes.put(Blinky.class, blinkyImg);
        mapaDeImagenes.put(Pinky.class, pinkyImg);
        mapaDeImagenes.put(Clyde.class, clydeImg);
        mapaDeImagenes.put(Inky.class, inkyImg);
    }


    /**
     * Dibuja los fantasmas en la pantalla, ya sea en su forma normal o en su forma "asustada".
     *
     * @param g              El objeto Graphics que se utiliza para dibujar en la pantalla.
     * @param listaFantasmas Una lista de objetos Fantasma que se deben dibujar.
     * @param jugador        El objeto Jugador que se utiliza para determinar si los fantasmas deben estar "asustados".
     *                       Si el jugador se está moviendo, los fantasmas estarán asustados, de lo contrario estarán en su forma normal.
     */
    public void dibujarFantasmas(Graphics g, ArrayList<Fantasma> listaFantasmas, Jugador jugador) {
        for (Fantasma p : listaFantasmas) {
            if (jugador.jugadorTienePoder()) {
                try {
                    BufferedImage img = ImageIO.read(new File("src/recursos/asustado.png"));
                    g.drawImage(img, p.getX() + 1, p.getY() + 1, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Obtenga la imagen correspondiente al tipo de fantasma
                BufferedImage img = mapaDeImagenes.get(p.getClass());

                // Dibuje la imagen en las coordenadas del fantasma
                g.drawImage(img, p.getX(), p.getY(), null);
            }
        }
    }

    /**
     * Dibuja los muros en la pantalla, utilizando un objeto Graphics.
     *
     * @param g          El objeto Graphics que se utiliza para dibujar en la pantalla.
     * @param listaMuros Una lista de objetos Muro que se deben dibujar.
     *                   Cada objeto Muro tiene una fila y una columna que determinan su posición en la pantalla.
     *                   El color del muro es establecido a través de un objeto Color.
     */
    public void dibujarMuro(Graphics g, ArrayList<Muro> listaMuros) {
        g.setColor(new Color(70, 150, 233));
        for (Muro wall : listaMuros) {
            int posX = wall.getColumna() * 20;
            int posY = wall.getFila() * 20 + 50;
            g.fillRect(posX, posY, 19, 19);
        }
    }

    /**
     * Dibuja los caminos vacíos en la pantalla, utilizando un objeto Graphics.
     *
     * @param g            El objeto Graphics que se utiliza para dibujar en la pantalla.
     * @param listaMuroAires Una lista de objetos Camino que se deben dibujar.
     *                     Cada objeto Camino tiene una fila y una columna que determinan su posición en la pantalla.
     *                     El color del camino es establecido a través de un objeto Color.
     */
    public void dibujarMuroAire(Graphics g, ArrayList<MuroAire> listaMuroAires) {
        g.setColor(new Color(115, 115, 115));
        for (MuroAire wall : listaMuroAires) {
            int posX = wall.getColumna() * 20;
            int posY = wall.getFila() * 20 + 50;
            g.fillRect(posX, posY, 20, 20);
        }
    }


    /**
     * Dibuja la pantalla de pausa en la pantalla, utilizando un objeto Graphics.
     *
     * @param g El objeto Graphics que se utiliza para dibujar en la pantalla.
     *          La pantalla de pausa muestra un mensaje indicando al usuario que presione la tecla 'P' para continuar el juego.
     *          La fuente y el color del mensaje son establecidos a través de objetos Font y Color.
     */
    public void dibujarPausa(Graphics g) {
        g.setColor(new Color(255, 255, 0));
        g.setFont(new Font("Lucida Console", Font.BOLD, 15));
        g.drawString("Press 'P' para continuar ", 60, 280);
    }

    /**
     * Dibuja la información del juego en la pantalla, utilizando un objeto Graphics.
     *
     * @param g El objeto Graphics que se utiliza para dibujar en la pantalla.
     *          La información incluye el nivel actual, la cantidad de intentos que le quedan al jugador y su puntaje actual.
     *          La fuente y el color de la información son establecidos a través de objetos Font y Color.
     */
    public void dibujarInformacion(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Lucida Console", Font.BOLD, 14));
        g.drawString("Nivel: " + game.getNivel(), 20, 35);
        g.drawString("Intentos: " + game.getLives(), 120, 35);
        g.drawString("Puntaje: " + game.getPlayerScore(), 240, 35);
    }
}
