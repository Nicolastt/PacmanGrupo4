package logica;

import java.util.Arrays;

public class Pinky extends Fantasma {

    /**
     * Constructor de la clase Pinky.
     *
     * @param x            Coordenada X inicial del fantasma.
     * @param y            Coordenada Y inicial del fantasma.
     * @param actLaberinto Laberinto actual en el que se encuentra el fantasma.
     * @param jugador      Jugador que controla Pac-Man.
     */
    public Pinky(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }

    /**
     * Método para obtener la siguiente dirección del movimiento del fantasma Clyde.
     *
     * @param perseguir variable booleana que indica si el fantasma está persiguiendo al jugador o no.
     */
    @Override
    public void getSiguienteDireccion(boolean perseguir) {
        // Obtener la celda actual del jugador
        int filaJugador = jugador.getY() / 20;
        int columnaJugador = jugador.getX() / 20;

        // Calcular la posición objetivo
        int filaObjetivo, columnaObjetivo;
        if (perseguir) {
            // Si se persigue al jugador, se toma la celda del jugador como objetivo
            filaObjetivo = filaJugador;
            columnaObjetivo = columnaJugador;
        } else {
            // Si no se persigue al jugador, se toma la celda de la esquina superior izquierda como objetivo
            filaObjetivo = 0;
            columnaObjetivo = 0;
        }

        // Calcular la distancia a cada celda adyacente
        Opcion[] opciones = new Opcion[4];
        opciones[0] = new Opcion(Direcciones.ARRIBA, calcularDistancia(filaObjetivo, columnaObjetivo, x, y - 20));
        opciones[1] = new Opcion(Direcciones.ABAJO, calcularDistancia(filaObjetivo, columnaObjetivo, x, y + 20));
        opciones[2] = new Opcion(Direcciones.IZQUIERDA, calcularDistancia(filaObjetivo, columnaObjetivo, x - 20, y));
        opciones[3] = new Opcion(Direcciones.DERECHA, calcularDistancia(filaObjetivo, columnaObjetivo, x + 20, y));

        // Ordenar las opciones de menor a mayor distancia
        Arrays.sort(opciones);

        // Elegir la dirección correspondiente a la celda más cercana que no choque con una pared
        for (int i = 0; i < opciones.length; i++) {
            if (!golpeaPared(opciones[i].direccion)) {
                dirección = opciones[i].direccion;
                return;
            }
        }
    }

    /**
     * Calcula la distancia euclidiana entre una celda objetivo y una posición actual.
     *
     * @param fila    la fila de la celda objetivo.
     * @param columna la columna de la celda objetivo.
     * @param x       la coordenada x de la posición actual.
     * @param y       la coordenada y de la posición actual.
     * @return la distancia euclidiana entre la celda objetivo y la posición actual.
     */
    private double calcularDistancia(int fila, int columna, int x, int y) {
        double deltaX = columna * 20 - x / 20 * 20;
        double deltaY = fila * 20 - y / 20 * 20;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
