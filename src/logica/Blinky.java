package logica;

import java.util.ArrayList;
import java.util.Collections;

public class Blinky extends Fantasma {


    /**
     * Constructor de la clase Blinky.
     *
     * @param x            Coordenada X inicial del fantasma.
     * @param y            Coordenada Y inicial del fantasma.
     * @param actLaberinto Laberinto actual en el que se encuentra el fantasma.
     * @param jugador      Jugador que controla Pac-Man.
     */
    public Blinky(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        super(x, y, actLaberinto, jugador);
    }


    /**
     * Obtiene la siguiente dirección en la que debe moverse el fantasma.
     *
     * @param perseguir Indica si el fantasma debe perseguir al jugador o alejarse de él.
     */
    public void getSiguienteDireccion(boolean perseguir) {
        {
            int velocidad = 2;
            ArrayList<Direcciones> todasDirecciones = new ArrayList<>();
            ArrayList<Direcciones> direccionCorrecta = new ArrayList<>();
            ArrayList<Opcion> todasOpciones = new ArrayList<>();
            todasDirecciones.add(Direcciones.ARRIBA);
            todasDirecciones.add(Direcciones.ABAJO);
            todasDirecciones.add(Direcciones.IZQUIERDA);
            todasDirecciones.add(Direcciones.DERECHA);

            // Filtra las direcciones que no chocan con paredes
            for (Direcciones direccione : todasDirecciones) {
                if (!golpeaPared(direccione)) {
                    direccionCorrecta.add(direccione);
                }
            }
            todasDirecciones = direccionCorrecta;

            // Si solo hay una dirección posible, se mueve en esa dirección
            if (todasDirecciones.size() == 1) {
                dirección = todasDirecciones.get(0);
            }


            // Calcula la distancia a cada una de las direcciones posibles
            for (Direcciones todasDireccione : todasDirecciones) {
                int postempoX, postempY;
                double distancia;
                switch (todasDireccione) {
                    case ARRIBA -> {
                        postempoX = x;
                        postempY = y - velocidad;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.ARRIBA, distancia));
                    }
                    case ABAJO -> {
                        postempoX = x;
                        postempY = y + velocidad;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.ABAJO, distancia));
                    }
                    case IZQUIERDA -> {
                        postempoX = x - velocidad;
                        postempY = y;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.IZQUIERDA, distancia));
                    }
                    case DERECHA -> {
                        postempoX = x + velocidad;
                        postempY = y;
                        distancia = Math.abs(Math.pow(postempoX - jugador.getX(), 2) + Math.pow(postempY - jugador.getY(), 2));
                        todasOpciones.add(new Opcion(Direcciones.DERECHA, distancia));
                    }
                }
            }

            /**
             * Ordena todasOpciones de menor a mayor distancia y establece la dirección a seguir dependiendo si se está persiguiendo al jugador o no.
             * Si se está persiguiendo al jugador, se elige la dirección con menor distancia.
             * Si no se está persiguiendo al jugador, se elige la dirección con mayor distancia.
             * @param perseguir un booleano que indica si se está persiguiendo al jugador o no.
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