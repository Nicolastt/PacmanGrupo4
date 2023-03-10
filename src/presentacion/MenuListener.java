package presentacion;

import ucd.comp2011j.engine.MenuCommands;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuListener implements MenuCommands, KeyListener {
    private boolean about;
    private boolean exit;
    private boolean high;
    private boolean menu;
    private boolean newGame;


    /**
     * El método keyTyped se llama cuando se pulsa y suelta una tecla. En este caso,
     * el método verifica si la tecla pulsada es A, X, H, M o N, y establece una
     * bandera correspondiente a true para indicar que se ha presionado esa tecla.
     *
     * @param e El objeto KeyEvent que representa el evento de teclado.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a') {
            about = true;
        } else if (e.getKeyChar() == 'X' || e.getKeyChar() == 'x') {
            exit = true;
        } else if (e.getKeyChar() == 'H' || e.getKeyChar() == 'h') {
            high = true;
        } else if (e.getKeyChar() == 'M' || e.getKeyChar() == 'm') {
            menu = true;
        } else if (e.getKeyChar() == 'N' || e.getKeyChar() == 'n') {
            newGame = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Devuelve un valor booleano que indica si el botón de "Nuevo Juego" ha sido presionado.
     *
     * @return true si el botón de "Nuevo Juego" ha sido presionado, false en caso contrario.
     */
    @Override
    public boolean hasPressedNewGame() {
        return newGame;
    }

    /**
     * Devuelve un valor booleano que indica si el botón de "Acerca de" ha sido presionado.
     *
     * @return true si el botón de "Acerca de" ha sido presionado, false en caso contrario.
     */
    @Override
    public boolean hasPressedAboutScreen() {
        return about;
    }

    /**
     * Devuelve un valor booleano que indica si el botón de "Puntajes Altos" ha sido presionado.
     *
     * @return true si el botón de "Puntajes Altos" ha sido presionado, false en caso contrario.
     */
    @Override
    public boolean hasPressedHighScoreScreen() {
        return high;
    }

    /**
     * Devuelve un valor booleano que indica si el botón de "Menú" ha sido presionado.
     *
     * @return true si el botón de "Menú" ha sido presionado, false en caso contrario.
     */
    @Override
    public boolean hasPressedMenu() {
        return menu;
    }

    /**
     * Devuelve un valor booleano que indica si el botón de "Salir" ha sido presionado.
     *
     * @return true si el botón de "Salir" ha sido presionado, false en caso contrario.
     */
    @Override
    public boolean hasPressedExit() {
        return exit;
    }

    /**
     * Resetea las variables de teclado de selección de menú a su estado inicial.
     */
    @Override
    public void resetKeyPresses() {
        menu = false;
        about = false;
        newGame = false;
        high = false;
        exit = false;
    }
}
