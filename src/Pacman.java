import logica.Juego;
import presentacion.*;
import presentacion.Menu;
import ucd.comp2011j.engine.GameManager;
import ucd.comp2011j.engine.ScoreKeeper;

import javax.swing.*;

/**
 * La clase Pacman es la clase principal del juego que inicia la aplicación.
 * En el método main, se crea la pantalla del menú y se establecen sus propiedades,
 * se agregan los listeners para capturar las acciones del usuario en el menú y en el juego.
 * Se crea un objeto Juego y un objeto ScoreKeeper, y se crean las instancias de las clases Menu,
 * Instrucciones, Puntaje y Game. Finalmente, se crea un objeto GameManager y se le pasan como
 * parámetros las instancias de las clases mencionadas anteriormente junto con el objeto Juego y
 * el JFrame de la pantalla del menú. Se hace visible la pantalla del menú y se inicia el juego
 * con el método run() del objeto GameManager.
 */
public class Pacman {
    public static void main(String[] args) throws Exception {
        JFrame pantallaMenu = new JFrame();
        pantallaMenu.setSize(Juego.ANCHO_PANTALLA, Juego.ALTURA_PANTALLA);
        pantallaMenu.setResizable(false);
        pantallaMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaMenu.setTitle("PACMAN");
        pantallaMenu.setLocationRelativeTo(null);


        PlayerListener playerListener = new PlayerListener();
        pantallaMenu.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        pantallaMenu.addKeyListener(menuListener);

        Juego pacman = new Juego(playerListener);
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");

        Menu menu = new Menu();
        Instrucciones instrucciones = new Instrucciones();
        Puntaje puntaje = new Puntaje(scoreKeeper);
        Game game = new Game(pacman);

        GameManager juego = new GameManager(pacman, pantallaMenu, menuListener, menu, instrucciones, puntaje, game, scoreKeeper);
        pantallaMenu.setVisible(true);

        juego.run();
    }
}
