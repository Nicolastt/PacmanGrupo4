package logica;

import java.awt.*;

public class Fruta implements Comestible {
    private int fila;
    private int columna;
    private int indice;
    private Rectangle hitBox;

    /**
     * Constructor de la clase Fruta.
     *
     * @param indice    índice de la posición en la que se encuentra la fruta en el laberinto.
     * @param laberinto objeto Laberinto que contiene la información del laberinto.
     */
    public Fruta(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
        hitBox = new Rectangle(columna * 20 + 3, fila * 20 + 50 + 3, 14, 14);
    }

    /**
     * Método que retorna el hitbox de la fruta.
     *
     * @return hitBox objeto Rectangle que representa el hitbox de la fruta.
     */
    public Rectangle getHitbox() {
        return hitBox;
    }


    /**
     * Método que retorna la fila en la que se encuentra la fruta.
     *
     * @return fila número entero que representa la fila en la que se encuentra la fruta.
     */
    public int getFila() {
        return fila;
    }


    /**
     * Método que retorna la columna en la que se encuentra la fruta.
     *
     * @return columna número entero que representa la columna en la que se encuentra la fruta.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Método que retorna el índice de la posición en la que se encuentra la fruta en el laberinto.
     *
     * @return indice número entero que representa el índice de la posición en la que se encuentra la fruta en el laberinto.
     */
    public int getIndice() {
        return indice;
    }
}
