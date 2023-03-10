package logica;

import java.awt.*;

public class Poder implements Comestible {
    private int fila;
    private int columna;
    private int indice;
    private Rectangle hitBox;

    /**
     * Constructor de la clase Poder.
     *
     * @param indice    indice de la posición del poder en el laberinto.
     * @param laberinto instancia de la clase Laberinto que contiene el laberinto donde se encuentra el poder.
     */
    public Poder(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
        hitBox = new Rectangle(columna * 20 + 3, fila * 20 + 50 + 3, 14, 14);
    }

    /**
     * Devuelve la fila del objeto en el laberinto.
     *
     * @return un entero que indica la fila en la que se encuentra el objeto.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Devuelve la columna del objeto en el laberinto.
     *
     * @return un entero que indica la columna en la que se encuentra el objeto.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve el índice del objeto en la lista de celdas del laberinto.
     *
     * @return un entero que indica el índice del objeto en la lista de celdas del laberinto.
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Devuelve el objeto Rectangle que representa el área de colisión del objeto.
     *
     * @return el objeto Rectangle que representa el área de colisión del objeto.
     */
    public Rectangle getHitbox() {
        return hitBox;
    }
}

