package logica;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Jugador implements Comestible {
    private int x;
    private int y;
    private int initX;
    private int initY;
    private Direcciones direccion;
    private Boolean tienePoder;
    private Boolean golpeaPared;
    private Rectangle hitBox;

    /**
     * Constructor de la clase Jugador
     *
     * @param x coordenada x inicial del jugador
     * @param y coordenada y inicial del jugador
     */
    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        hitBox = new Rectangle(x, y, 20, 20);
        direccion = Direcciones.IZQUIERDA;
        tienePoder = false;
        golpeaPared = false;
    }

    /**
     * Reinicia la posición del objeto a su posición inicial.
     * El objeto volverá a la posición en la que se creó por primera vez,
     * y se actualizará su hitbox en consecuencia.
     */
    public void reiniciar() {
        x = initX;
        y = initY;
        hitBox = new Rectangle(x, y, 20, 20);
    }

    /**
     * Mueve el personaje en la dirección actual a una velocidad constante.
     * Actualiza la posición del hitBox correspondiente a la nueva posición del personaje.
     */
    public void mover() {
        int velocidad = 2;
        switch (direccion) {
            case ARRIBA -> y -= velocidad;
            case ABAJO -> y += velocidad;
            case IZQUIERDA -> x -= velocidad;
            case DERECHA -> x += velocidad;
        }
        hitBox = new Rectangle(x, y, 20, 20);
    }



    /**
     * Método que cambia la dirección del jugador
     *
     * @param direcciones objeto de la clase Direcciones
     */
    public void cambiarDireccion(Direcciones direcciones) {
        golpeaPared = false;
        direccion = direcciones;
    }

    /**
     * Método que devuelve la hitbox del jugador
     *
     * @return objeto de la clase Rectangle que representa la hitbox del jugador
     */
    public Rectangle getHitbox() {
        return hitBox;
    }

    /**
     * Método que verifica si el jugador ha comido un objeto comestible
     *
     * @param comestible objeto que implementa la interfaz Comestible
     * @return verdadero si ha comido el objeto, falso en caso contrario
     */
    public boolean come(Comestible comestible) {
        return hitBox.intersects(comestible.getHitbox());
    }

    /**
     * Método que devuelve la coordenada x del jugador
     *
     * @return coordenada x del jugador
     */
    public int getX() {
        return x;
    }

    /**
     * Método que devuelve la coordenada y del jugador
     *
     * @return coordenada y del jugador
     */
    public int getY() {
        return y;
    }

    /**
     * Método que devuelve la dirección actual del jugador
     *
     * @return objeto de la clase Direcciones que representa la dirección actual del jugador
     */
    public Direcciones getDireccion() {
        return this.direccion;
    }

    /**
     * Método que devuelve si el jugador está golpeando una pared
     *
     * @return verdadero si está golpeando una pared, falso en caso contrario
     */
    public Boolean estaGolpeandoPared() {
        return golpeaPared;
    }

    /**
     * Método que establece si el jugador está golpeando una pared
     *
     * @param golpeaPared verdadero si está golpeando una pared, falso en caso contrario
     */
    public void setGolpeaPared(Boolean golpeaPared) {
        this.golpeaPared = golpeaPared;
    }

    /**
     * Método que devuelve si el jugador está en movimiento
     *
     * @return verdadero si está en movimiento, falso en caso contrario
     */
    public boolean jugadorTienePoder() {
        return tienePoder;
    }

    /**
     * Activa el poder temporalmente durante 5 segundos y luego lo desactiva.
     */
    public void setPoder() {
        tienePoder = true;
        Timer timer = new Timer();
        TimerTask poder = new TimerTask() {
            public void run() {
                tienePoder = false;

            }
        };
        timer.schedule(poder, 5000);
    }

}
