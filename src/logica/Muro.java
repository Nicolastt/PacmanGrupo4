package logica;

public class Muro {
    private int fila;
    private int columna;
    private int indice;

    /**
     * Constructor de la clase Muro que recibe un índice y un objeto Laberinto.
     *
     * @param indice    el índice del muro en el array del laberinto.
     * @param laberinto el objeto Laberinto en el que se encuentra el muro.
     */
    public Muro(int indice, Laberinto laberinto) {
        this.indice = indice;
        this.fila = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getFila();
        this.columna = Coordenadas.getCordenadas(indice, laberinto.getColumnas()).getColumna();
    }

    /**
     * Método que devuelve la fila en la que se encuentra el muro.
     *
     * @return la fila del muro.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Método que devuelve la columna en la que se encuentra el muro.
     *
     * @return la columna del muro.
     */
    public int getColumna() {
        return columna;
    }


    /**
     * Método que devuelve el índice del muro.
     *
     * @return el índice del muro.
     */
    public int getIndice() {
        return indice;
    }
}
