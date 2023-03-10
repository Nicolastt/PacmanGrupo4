package logica;

public class Coordenadas {
    private int fila;
    private int columna;
    private int indice;

    /**
     * Constructor de la clase Coordenadas que recibe la fila, columna y número de columnas de la matriz.
     * Calcula el índice correspondiente a partir de la fila y columna.
     *
     * @param fila        la fila de la celda.
     * @param columna     la columna de la celda.
     * @param numColumnas el número de columnas de la matriz.
     */
    public Coordenadas(int fila, int columna, int numColumnas) {
        this.fila = fila;
        this.columna = columna;
        this.indice = columna + fila * numColumnas;
    }

    /**
     * Constructor de la clase Coordenadas que recibe el índice de la celda y el número de columnas de la matriz.
     * Calcula la fila y columna correspondiente a partir del índice.
     *
     * @param indice      el índice de la celda en una matriz unidimensional.
     * @param numColumnas el número de columnas de la matriz.
     */
    public Coordenadas(int indice, int numColumnas) {
        this.indice = indice;
        this.fila = indice / numColumnas;
        this.columna = indice % numColumnas;
    }

    /**
     * Método estático que devuelve las coordenadas correspondientes al índice dado.
     *
     * @param i           el índice de la celda en una matriz unidimensional.
     * @param numColumnas el número de columnas de la matriz.
     * @return las coordenadas correspondientes al índice dado.
     */
    public static Coordenadas getCordenadas(int i, int numColumnas) {
        return new Coordenadas(i, numColumnas);
    }

    /**
     * Método estático que devuelve el índice correspondiente a las coordenadas dadas.
     *
     * @param fila        la fila de la celda.
     * @param columna     la columna de la celda.
     * @param numColumnas el número de columnas de la matriz.
     * @return el índice correspondiente a las coordenadas dadas.
     */
    public static Coordenadas getIndice(int fila, int columna, int numColumnas) {
        return new Coordenadas(fila, columna, numColumnas);
    }

    /**
     * Devuelve la fila de la celda.
     *
     * @return la fila de la celda.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Devuelve la columna de la celda.
     *
     * @return la columna de la celda.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve el índice de la celda en una matriz unidimensional.
     *
     * @return el índice de la celda en una matriz unidimensional.
     */
    public int getIndice() {
        return indice;
    }
}
