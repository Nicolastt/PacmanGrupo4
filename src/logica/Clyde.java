package logica;

import java.util.ArrayList;
import java.util.Collections;

public class Clyde extends Fantasma {
    /**
     * Constructor de la clase Clyde.
     *
     * @param x            Coordenada X inicial del fantasma.
     * @param y            Coordenada Y inicial del fantasma.
     * @param actLaberinto Laberinto actual en el que se encuentra el fantasma.
     * @param jugador      Jugador que controla Pac-Man.
     */
    public Clyde(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }
    private final int velocidad = 2;

    /**
     * Método para obtener la siguiente dirección del movimiento del fantasma Clyde.
     *
     * @param perseguir variable booleana que indica si el fantasma está persiguiendo al jugador o no.
     */
    @Override
    public void getSiguienteDireccion(boolean perseguir) {
        {
            {

                ArrayList<Direcciones> todasDirecciones = new ArrayList<>();
                ArrayList<Direcciones> direccionCorrecta = new ArrayList<>();
                ArrayList<Opcion> todasOpciones = new ArrayList<>();
                todasDirecciones.add(Direcciones.ARRIBA);
                todasDirecciones.add(Direcciones.ABAJO);
                todasDirecciones.add(Direcciones.IZQUIERDA);
                todasDirecciones.add(Direcciones.DERECHA);


                // Se verifica si la dirección no golpea una pared
                for (Direcciones todasDireccione : todasDirecciones) {
                    if (!golpeaPared(todasDireccione)) {
                        direccionCorrecta.add(todasDireccione);
                    }
                }
                todasDirecciones = direccionCorrecta;

                // Si solo hay una dirección disponible, se toma esa
                if (todasDirecciones.size() == 1) {
                    dirección = todasDirecciones.get(0);
                }

                // Se crea una lista de todas las opciones disponibles junto con su distancia al jugador
                for (Direcciones todasDireccione : todasDirecciones) {
                    int postempoX, postempY;
                    double distancia;
                    switch (todasDireccione) {
                        case ARRIBA -> {
                            postempoX = x;
                            postempY = y - velocidad;
                            distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 100) + Math.pow(postempY - jugador.getY(), 20));
                            todasOpciones.add(new Opcion(Direcciones.ARRIBA, distancia));
                        }
                        case ABAJO -> {
                            postempoX = x;
                            postempY = y + velocidad;
                            distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 40) + Math.pow(postempY - jugador.getY(), 20));
                            todasOpciones.add(new Opcion(Direcciones.ABAJO, distancia));
                        }
                        case IZQUIERDA -> {
                            postempoX = x - velocidad;
                            postempY = y;
                            distancia = Math.abs(Math.pow(postempoX - jugador.getY(), 10) + Math.pow(postempY - jugador.getY(), 40));
                            todasOpciones.add(new Opcion(Direcciones.IZQUIERDA, distancia));
                        }
                        case DERECHA -> {
                            postempoX = x + velocidad;
                            postempY = y;
                            distancia = Math.abs(Math.pow(postempoX - jugador.getY(), 60) + Math.pow(postempY - jugador.getY(), 10));
                            todasOpciones.add(new Opcion(Direcciones.DERECHA, distancia));
                        }
                    }
                }

                /*
                 * Ordena todasOpciones de menor a mayor distancia y establece la dirección a seguir dependiendo si se está persiguiendo al jugador o no.
                 * Si se está persiguiendo al jugador, se elige la dirección con menor distancia.
                 * Si no se está persiguiendo al jugador, se elige la dirección con mayor distancia.
                 */
                Collections.sort(todasOpciones);
                if (perseguir) {
                    dirección = todasOpciones.get(0).direccion;
                } else {
                    dirección = todasOpciones.get(todasOpciones.size() - 1).direccion;
                }
            }
        }
    }
}

