package presentacion;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerListener implements KeyListener {
    private boolean izquierda;
    private boolean derecha;
    private boolean arriba;
    private boolean abajo;
    private boolean pausa;
    private MenuListener menuListener;

    /**
     * Devuelve verdadero si el usuario está presionando la tecla de la flecha izquierda
     *
     * @return true si el usuario está presionando la tecla de la flecha izquierda, de lo contrario false.
     */
    public boolean estaPresionandoIzquierda() {
        return izquierda;
    }

    /**
     * Devuelve verdadero si el usuario está presionando la tecla de la flecha derecha
     *
     * @return true si el usuario está presionando la tecla de la flecha derecha, de lo contrario false.
     */
    public boolean estaPresionandoDerecha() {
        return derecha;
    }

    /**
     * Devuelve verdadero si el usuario está presionando la tecla de la flecha arriba
     *
     * @return true si el usuario está presionando la tecla de la flecha arriba, de lo contrario false.
     */
    public boolean estaPresionandoArriba() {
        return arriba;
    }

    /**
     * Devuelve verdadero si el usuario está presionando la tecla de la flecha abajo
     *
     * @return true si el usuario está presionando la tecla de la flecha abajo, de lo contrario false.
     */
    public boolean estaPresionandoAbajo() {
        return abajo;
    }

    /**
     * Devuelve verdadero si el usuario ha presionado la tecla de pausa
     *
     * @return true si el usuario ha presionado la tecla de pausa, de lo contrario false.
     */
    public boolean hasPressedPause() {
        return pausa;
    }


    /**
     * Invocado cuando se presiona y se suelta una tecla del teclado.
     * En este caso, establece la variable de pausa en verdadero si la tecla presionada es 'P' o 'p'.
     *
     * @param e un objeto KeyEvent que contiene información sobre la tecla que se presionó
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
            pausa = true;
        }
       
    }

    /**
     * Se activa cuando se presiona una tecla.
     * Si se presiona una tecla de dirección, se actualiza la variable correspondiente.
     * Si se presiona la tecla de pausa, se actualiza la variable pausa.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            arriba = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            abajo = true;
        }
    }

    /**
     * Se ejecuta cuando se suelta una tecla.
     * Si la tecla liberada es una tecla de dirección, se marca la dirección correspondiente como no presionada.
     *
     * @param e el objeto KeyEvent que representa la tecla liberada
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierda = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derecha = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            arriba = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            abajo = false;
        }
    }

    /**
     * Método que resetea la variable pausa a false.
     */
    public void resetPause() {
        pausa = false;
    }

}
