package logica;

public class MuroAire {
    private int fila;
    private int columna;
    private int indice;

    /**
     * Constructor de la clase Camino.
     *
     * @param indice    el índice de la casilla en el laberinto.
     * @param laberinto el laberinto en el que se encuentra la casilla.
     */
    public MuroAire(int indice, Laberinto laberinto) {
        this.indice = indice;
        // Obtiene la fila y columna de la casilla a partir de su índice en el laberinto.
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
    }

    /**
     * Obtiene la fila de la casilla en el laberinto.
     *
     * @return la fila de la casilla en el laberinto.
     */
    public int getFila() {
        return fila;
    }


    /**
     * Obtiene la columna de la casilla en el laberinto.
     *
     * @return la columna de la casilla en el laberinto.
     */
    public int getColumna() {
        return columna;
    }


}
