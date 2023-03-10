package logica;

import java.awt.*;

public class Comida implements Comestible {
    private int fila;
    private int columna;
    private int indice;
    private Rectangle hitBox;

    /**
     * Constructor de la clase Comida. Recibe como parámetros el índice de la celda donde se ubica la comida y el laberinto actual.
     * A partir del índice se calcula la fila y la columna en el laberinto y se crea un rectángulo de hitbox en la ubicación correspondiente.
     *
     * @param indice    el índice de la celda donde se ubica la comida
     * @param laberinto el laberinto actual
     */
    public Comida(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
        hitBox = new Rectangle(columna * 20 + 5, fila * 20 + 50 + 5, 10, 10);
    }

    /**
     * Retorna la fila donde se ubica la comida en el laberinto.
     *
     * @return la fila donde se ubica la comida
     */
    public int getFila() {
        return fila;
    }


    /**
     * Retorna la columna donde se ubica la comida en el laberinto.
     *
     * @return la columna donde se ubica la comida
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Retorna el índice de la celda donde se ubica la comida en el laberinto.
     *
     * @return el índice de la celda donde se ubica la comida
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Retorna el rectángulo de hitbox de la comida para detectar colisiones con el Juego.
     *
     * @return el rectángulo de hitbox de la comida
     */
    public Rectangle getHitbox() {
        return hitBox;
    }
}
