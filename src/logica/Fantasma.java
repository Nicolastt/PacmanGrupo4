package logica;

import java.awt.*;

public abstract class Fantasma implements Comestible {
    // Atributos compartidos por los distintos tipos de fantasmas
    protected int x;
    protected int y;
    protected int initX;
    protected int initY;
    protected Rectangle hitBox;
    protected Direcciones dirección;
    protected Laberinto actLaberinto;
    protected Jugador jugador;
    protected boolean puedeMoverse;

    private double velocidad = 2;

    /**
     * Constructor de la clase Fantasma.
     *
     * @param x            coordenada X del Fantasma.
     * @param y            coordenada Y del Fantasma.
     * @param actLaberinto Laberinto en el que se mueve el Fantasma.
     * @param jugador      Jugador del juego.
     */
    public Fantasma(int x, int y, Laberinto actLaberinto, Jugador jugador) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.actLaberinto = actLaberinto;
        this.jugador = jugador;
        hitBox = new Rectangle(x, y, 20, 20);
        dirección = null;
        puedeMoverse = false;

    }

    /**
     * Reinicia la posición del Fantasma y su hitbox.
     */
    public void reiniciar() {
        puedeMoverse = false;
        x = initX;
        y = initY;
        hitBox = new Rectangle(x, y, 20, 20);

    }

    /**
     * Verifica si el Fantasma ha chocado con el Jugador.
     *
     * @param jugador Jugador del juego.
     * @return verdadero si el Fantasma ha chocado con el Jugador, falso en caso contrario.
     */
    public boolean comer(Jugador jugador) {
        return hitBox.intersects(jugador.getHitbox());
    }

    /**
     * Clase interna que representa una posible dirección para el Fantasma y su distancia al Jugador.
     */
    protected class Opcion implements Comparable<Opcion> {
        protected Direcciones direccion;
        protected double distancia;

        /**
         * Constructor de la clase Opcion.
         *
         * @param direccion Dirección posible para el Fantasma.
         * @param distancia Distancia al Jugador.
         */
        public Opcion(Direcciones direccion, double distancia) {
            this.direccion = direccion;
            this.distancia = distancia;
        }

        /**
         * Compara dos opciones de dirección para el Fantasma por su distancia al Jugador.
         *
         * @param opcion Opcion de dirección a comparar.
         * @return 1 si la distancia de esta opción es mayor que la de la opción dada, -1 si es menor, 0 si son iguales.
         */
        public int compareTo(Opcion opcion) {
            if (distancia > opcion.distancia) {
                return 1;

            } else if (distancia < opcion.distancia) {
                return -1;
            } else {
                return 0;
            }
        }

        /**
         * Representación en cadena de la opción de dirección.
         *
         * @return Dirección y distancia.
         */
        public String toString() {
            return direccion + " " + distancia;
        }
    }

    /**
     * Método para mover al personaje. Si no puede moverse, el método no hace nada.
     * Si el personaje está en una casilla completa (no en una intermedia), llama al método getSiguienteDireccion
     * para obtener la siguiente dirección del personaje.
     * Luego, mueve al personaje en la dirección actual y actualiza su hitbox.
     */
    public void mover() {
        if (!puedeMoverse) {
            return;
        }

        if ((x / 20.0) % 1 == 0 && ((y - 50) / 20.0) % 1 == 0) {
            getSiguienteDireccion(!jugador.jugadorTienePoder());
        }

        switch (dirección) {
            case ARRIBA -> y -= velocidad;
            case ABAJO -> y += velocidad;
            case IZQUIERDA -> x -= velocidad;
            case DERECHA -> x += velocidad;
        }
        hitBox = new Rectangle(x, y, 20, 20);
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para determinar la siguiente dirección del personaje.
     *
     * @param perseguir si es true, el personaje está en modo persecución; de lo contrario, está en modo huida.
     */
    public abstract void getSiguienteDireccion(boolean perseguir);

    /**
     * Comprueba si el personaje golpea una pared en una determinada dirección.
     *
     * @param direccion la dirección en la que se comprueba si hay una pared
     * @return true si el personaje golpea una pared en la dirección dada, false en caso contrario
     */
    public boolean golpeaPared(Direcciones direccion) {
        int fila, columna, indice;
        switch (direccion) {
            case ARRIBA -> {
                fila = (y - 1 - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
            }
            case ABAJO -> {
                fila = (y - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
            }
            case IZQUIERDA -> {
                fila = (y - 50) / 20;
                columna = (x - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
            }
            case DERECHA -> {
                fila = (y - 50) / 20;
                columna = x / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, actLaberinto.getColumnas()).getIndice();
                if (actLaberinto.getLaberinto().get(indice).equals("W")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Retorna el rectángulo que representa la hitbox del objeto.
     *
     * @return el rectángulo que representa la hitbox del objeto.
     */
    public Rectangle getHitbox() {
        return hitBox;
    }

    /**
     * Retorna la coordenada x actual del objeto.
     *
     * @return la coordenada x actual del objeto.
     */
    public int getX() {
        return x;
    }

    /**
     * Retorna la coordenada y actual del objeto.
     *
     * @return la coordenada y actual del objeto.
     */
    public int getY() {
        return y;
    }


    /**
     * Retorna la nueva dirección del objeto.
     *
     * @return la nueva dirección del objeto.
     */
    public Direcciones getNuevaDireccion() {
        return dirección;
    }


    /**
     * Establece si el objeto puede moverse o no.
     *
     * @param t true si el objeto puede moverse, false si no.
     */
    public void setMover(boolean t) {
        puedeMoverse = t;
    }

    /**
     * Establece la velocidad del objeto.
     *
     * @param velocidad la nueva velocidad del objeto.
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }
}
