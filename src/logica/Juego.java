package logica;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import presentacion.PlayerListener;
import ucd.comp2011j.engine.Game;

public class Juego implements Game {

    public static final int ALTURA_PANTALLA = 530 + 50;
    public static final int ANCHO_PANTALLA = 380;
    private static final int NO_NIVELES = 4;

    private final PlayerListener listener;
    private int vidasJugador;
    private int puntajeJugador;
    private int puntajeTemp = 0;
    private boolean pausado = true;
    private int nivelActual = 1;
    private final String[] nivel;
    private Laberinto curLaberinto;

    private Jugador jugador;
    private ArrayList<Fantasma> listaFantasmas;
    private ArrayList<Fantasma> listaFantasmasEnBase;
    private final ArrayList<Fantasma> listaFantasmasPorSalir = new ArrayList<>();
    private ArrayList<Comida> listaComidas;
    private ArrayList<Fruta> listaFrutas;
    private ArrayList<Poder> listaPoder;
    private ArrayList<Muro> listaMuros;
    private ArrayList<MuroAire> listaMuroAires;
    private Fruta frutaActual;
    private int curIndiceFrutas;
    private int contadorComerFantasma;

    private final Timer timer = new Timer();
    private boolean actualizarFruta = true;

    /**
     * Crea una nueva instancia de Juego y se le pasa un objeto PlayerListener para recibir eventos del jugador
     *
     * @param listener Objeto PlayerListener que recibe eventos del jugador
     */

    public Juego(PlayerListener listener) {
        this.listener = listener;
        this.nivel = getNiveltxt();
        startNewGame();
    }

    /**
     * Método que devuelve el puntaje actual del jugador.
     *
     * @return El puntaje actual del jugador.
     */
    @Override
    public int getPlayerScore() {
        return puntajeJugador;
    }

    /**
     * Método que devuelve el número de vidas restantes del jugador.
     *
     * @return El número de vidas restantes del jugador.
     */
    @Override
    public int getLives() {
        return vidasJugador;
    }


    /**
     * Método que actualiza el estado del juego.
     * Si el juego no está en pausa, se mueve el jugador, se ordenan los fantasmas,
     * se mueven los fantasmas, se come la fruta, se come el fantasma y se genera
     * una nueva fruta aleatoria.
     */
    @Override
    public void updateGame() {
        if (!isPaused()) {
            moverJugador();
            ordenarFantasmas();
            moverFantasmas();
            comer();
            comerFantasma();
            generarFrutaAleatoria();
        }
    }

    /**
     * Método que verifica si algún fantasma ha sido comido por el jugador.
     * Si un fantasma ha sido comido, se reinicia la posición del jugador y se
     * disminuye en uno el número de vidas restantes del jugador.
     */
    public void comerFantasma() {
        for (Fantasma g : listaFantasmas) {
            if (g.comer(jugador) && vidasJugador >=0) {
                jugador.reiniciar();
                vidasJugador -- ;
            }
        }
    }

    /**
     * Método que ordena los fantasmas y los saca de la base para moverlos.
     */
    public void ordenarFantasmas() {
        // Se crea un objeto Random para generar valores aleatorios.
        Random rand = new Random();
        int tiempoEspera = 0;
        // Si la cantidad de fantasmas en la base es igual a la cantidad de fantasmas totales,
        // se saca el primer fantasma de la base y se activa su movimiento.
        if (listaFantasmasEnBase.size() == listaFantasmas.size()) {
            listaFantasmasEnBase.get(0).setMover(true);
            listaFantasmasEnBase.remove(0);
        }
        // Se itera sobre la lista de fantasmas en la base.
        for (Fantasma fantasma : listaFantasmasEnBase) {
            // Se suma un valor aleatorio entre 500 y 2500 milisegundos al tiempo de espera.
            tiempoEspera += rand.nextInt(5 * 100, 50 * 100);
            // Se añade el fantasma a la lista de fantasmas por salir.
            listaFantasmasPorSalir.add(fantasma);
            // Se crea una tarea programada que se ejecutará después del tiempo de espera.
            TimerTask pend = new TimerTask() {
                public void run() {
                    // Se activa el movimiento del fantasma y se elimina de la lista de fantasmas por salir.
                    fantasma.setMover(true);
                    listaFantasmasPorSalir.remove(fantasma);
                }
            };
            // Se programa la tarea pendiente para que se ejecute después del tiempo de espera.
            timer.schedule(pend, tiempoEspera);
        }
        // Se eliminan todos los fantasmas de la base.
        listaFantasmasEnBase.clear();
    }

    /**
     * Mueve a cada uno de los fantasmas en la lista de fantasmas utilizando el método "mover()"
     */
    public void moverFantasmas() {
        for (Fantasma fantasma : listaFantasmas) {
            fantasma.mover();
        }
    }

    /**
     * Genera una fruta aleatoria a partir de la lista de frutas disponibles y configura un temporizador para cambiar la fruta mostrada en el laberinto.
     * Si actualizarFruta es verdadero y la lista de frutas no está vacía, se genera un índice aleatorio para seleccionar la fruta actual
     * de la lista de frutas. Luego, se configura un temporizador para cambiar la fruta actual en el laberinto después de un retraso aleatorio.
     */
    public void generarFrutaAleatoria() {
        if (actualizarFruta && listaFrutas.size() != 0) {
            Random rand = new Random();
            curIndiceFrutas = rand.nextInt(listaFrutas.size());
            frutaActual = listaFrutas.get(curIndiceFrutas);
            actualizarFruta = !actualizarFruta;
            TimerTask changeRefreshState = new TimerTask() {
                public void run() {
                    actualizarFruta = !actualizarFruta;
                }
            };
            int delay = rand.nextInt(5000, 10001);
            timer.schedule(changeRefreshState, delay);
        }
    }

    /**
     * Método que comprueba si el jugador ha comido puntos, frutas, poderes o fantasmas y aumenta el puntaje correspondiente.
     */
    public void comer() {
        // Comer puntos
        for (int i = 0; i < listaComidas.size(); i++) {
            if (jugador.come(listaComidas.get(i))) {
                listaComidas.remove(i);
                addPuntaje(10);
            }
        }
        // Comer frutas
        if (frutaActual != null) {
            if (jugador.come((frutaActual))) {
                listaFrutas.remove(curIndiceFrutas);
                frutaActual = null;
                addPuntaje(500);
            }
        }
        // Comer poderes
        for (int i = 0; i < listaPoder.size(); i++) {
            if (jugador.come(listaPoder.get(i))) {
                listaPoder.remove(i);
                jugador.setPoder();
                addPuntaje(50);
                contadorComerFantasma = 0;
            }
        }
        // Comer fantasmas
        if (jugador.jugadorTienePoder()) {
            for (Fantasma fantasma : listaFantasmas) {
                fantasma.setVelocidad(1); //Se reduce la velocidad cuando están asustados
                if (jugador.come(fantasma)) {
                    fantasma.reiniciar();
                    listaFantasmasEnBase.add(fantasma);
                    addPuntaje(200 * (int) Math.pow(2, contadorComerFantasma));
                    contadorComerFantasma++;
                }
            }
        }
    }

    /**
     * Mueve al jugador en la dirección indicada por el usuario,
     * siempre y cuando no choque con una pared.
     */
    public void moverJugador() {
        verificarSiJugadorChocaConLaPared();
        if (!jugador.estaGolpeandoPared()) {
            jugador.mover();
        }

        if ((jugador.getX() / 20.0) % 1 == 0 && ((jugador.getY() - 50) / 20.0) % 1 == 0) {
            if (listener.estaPresionandoIzquierda()) {
                if (puedeCambiarDeDireccion(Direcciones.IZQUIERDA)) {
                    jugador.cambiarDireccion(Direcciones.IZQUIERDA);
                }
            } else if (listener.estaPresionandoDerecha()) {
                if (puedeCambiarDeDireccion(Direcciones.DERECHA)) {
                    jugador.cambiarDireccion(Direcciones.DERECHA);
                }
            } else if (listener.estaPresionandoArriba()) {
                if (puedeCambiarDeDireccion(Direcciones.ARRIBA)) {
                    jugador.cambiarDireccion(Direcciones.ARRIBA);
                }
            } else if (listener.estaPresionandoAbajo()) {
                if (puedeCambiarDeDireccion(Direcciones.ABAJO)) {
                    jugador.cambiarDireccion(Direcciones.ABAJO);
                }
            }
        }
    }

    /**
     * Determina si el jugador puede cambiar de dirección sin chocar con una pared.
     *
     * @param direccion la dirección a la que se quiere cambiar.
     * @return true si el jugador puede cambiar de dirección, false en caso contrario.
     */
    public boolean puedeCambiarDeDireccion(Direcciones direccion) {
        int fila, columna, indice;
        switch (direccion) {
            case ARRIBA -> {
                fila = (jugador.getY() - 1 - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
            }
            case ABAJO -> {
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
            }
            case IZQUIERDA -> {
                fila = (jugador.getY() - 50) / 20;
                columna = (jugador.getX() - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
            }
            case DERECHA -> {
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica si el jugador choca con una pared en la dirección actual.
     * Se obtiene la fila y columna en la que se encuentra el jugador según su posición y dirección,
     * y se calcula el índice correspondiente en el laberinto actual. Luego se verifica si el valor de la celda
     * en el laberinto es una pared o un espacio vacío, y se actualiza el valor de "golpeaPared" del jugador en consecuencia.
     */
    public void verificarSiJugadorChocaConLaPared() {
        int fila, columna, indice;
        switch (jugador.getDireccion()) {
            case ARRIBA -> {
                fila = (jugador.getY() - 1 - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
            }
            case ABAJO -> {
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila + 1, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
            }
            case IZQUIERDA -> {
                fila = (jugador.getY() - 50) / 20;
                columna = (jugador.getX() - 1) / 20;
                indice = Coordenadas.getIndice(fila, columna, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
            }
            case DERECHA -> {
                fila = (jugador.getY() - 50) / 20;
                columna = jugador.getX() / 20;
                indice = Coordenadas.getIndice(fila, columna + 1, curLaberinto.getColumnas()).getIndice();
                if (curLaberinto.getLaberinto().get(indice).equals("W") ||
                        curLaberinto.getLaberinto().get(indice).equals("-")) {
                    jugador.setGolpeaPared(true);
                }
            }
        }
    }

    /**
     * Devuelve el jugador
     *
     * @return el jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Retorna el estado de pausa del juego.
     *
     * @return true si el juego está pausado, false si no lo está.
     */
    @Override
    public boolean isPaused() {
        return pausado;
    }

    /**
     * Comprueba si el juego necesita ser pausado verificando si el oyente ha detectado
     * que se ha pulsado el botón de pausa. Si el botón ha sido pulsado, el juego
     * juego se pausa y si se pulsa de nuevo, el juego se reanuda. También restablece
     * el estado del botón de pausa a false después de detectar la pulsación.
     */
    @Override
    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pausado = !pausado;

            listener.resetPause();
        }
    }

    /**
     * Inicia un nuevo juego, reiniciando las variables de vidas, puntaje, contador de comer fantasmas,
     * generando un nuevo laberinto y creando las listas de objetos necesarias para el juego, incluyendo
     * el jugador, los fantasmas, las comidas, las frutas, los poderes, los muros y los caminos. También
     * establece la fruta actual en la primera fruta de la lista de frutas, y guarda una lista de fantasmas
     * en su posición de inicio en la base.
     */
    @Override
    public void startNewGame() {
        vidasJugador = 3;
        puntajeJugador = 0;
        contadorComerFantasma = 0;
        pausado = true;
        curLaberinto = new Laberinto(nivel[nivelActual]);
        jugador = generatePlayer();
        listaFantasmas = generateGhostList();
        listaComidas = listaComida();
        listaFrutas = generarListaFrutas();
        listaPoder = generatePowerList();
        listaMuros = generarListaPared();
        listaMuroAires = generarListaMurosAire();
        frutaActual = listaFrutas.get(0);

        listaFantasmasEnBase = new ArrayList<>();
        listaFantasmasEnBase.addAll(listaFantasmas);
    }


    /**
     * Crea una lista de objetos de tipo Comida a partir de las posiciones de los puntos en el laberinto actual.
     *
     * @return una ArrayList de objetos de tipo Comida que representan los puntos que el jugador puede recolectar.
     */
    public ArrayList<Comida> listaComida() {
        ArrayList<String> listaLaberinto = curLaberinto.getLaberinto();
        ArrayList<Comida> listaComida = new ArrayList<>();
        for (int i = 0; i < listaLaberinto.size(); i++) {
            if (listaLaberinto.get(i).equals(".")) {
                listaComida.add(new Comida(i, curLaberinto));
            }
        }
        return listaComida;
    }

    /**
     * Devuelve la lista de todos los objetos Comida presentes actualmente en el juego.
     *
     * @return Un ArrayList de objetos Comida representando los ítems de comida actuales.
     */
    public ArrayList<Comida> getListaComidas() {
        return listaComidas;
    }

    /**
     * Genera una lista de objetos Fruta basada en la ubicación de "F" en el laberinto actual.
     *
     * @return la lista de objetos Fruta
     */
    public ArrayList<Fruta> generarListaFrutas() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Fruta> fruitArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("F")) {
                fruitArr.add(new Fruta(i, curLaberinto));
            }
        }
        return fruitArr;
    }


    /**
     * Genera una lista de objetos Muro a partir de los datos del laberinto actual.
     *
     * @return ArrayList de objetos Muro.
     */
    public ArrayList<Muro> generarListaPared() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Muro> wallArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("W")) {
                wallArr.add(new Muro(i, curLaberinto));
            }
        }
        return wallArr;
    }

    /**
     * Retorna la lista de objetos de tipo Muro que se encuentran en el laberinto actual.
     *
     * @return una lista de objetos de tipo Muro.
     */
    public ArrayList<Muro> getlistaPared() {
        return listaMuros;
    }

    /**
     * Genera una lista de todos los caminos disponibles en el laberinto actual.
     *
     * @return ArrayList de objetos Camino que representan cada camino disponible.
     */
    public ArrayList<MuroAire> generarListaMurosAire() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<MuroAire> wallArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("-")) {
                wallArr.add(new MuroAire(i, curLaberinto));
            }
        }
        return wallArr;
    }

    /**
     * Obtiene la lista de caminos del laberinto actual.
     *
     * @return la lista de caminos del laberinto actual
     */
    public ArrayList<MuroAire> getListaMuroAires() {
        return listaMuroAires;
    }

    /**
     * Genera una lista de power-ups basada en el laberinto actual
     *
     * @return Un ArrayList de objetos Poder que representan power-ups en el laberinto
     */
    public ArrayList<Poder> generatePowerList() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Poder> powerArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("*")) {
                powerArr.add(new Poder(i, curLaberinto));
            }
        }
        return powerArr;
    }

    /**
     * Devuelve la lista de objetos Poder en el laberinto actual.
     *
     * @return La lista de objetos Poder en el laberinto actual.
     */
    public ArrayList<Poder> getPowerList() {
        return listaPoder;
    }

    /**
     * Genera un nuevo objeto Jugador en la posición inicial del laberinto.
     *
     * @return objeto Jugador creado.
     * @throws Error si no se encuentra el objeto "P" en el laberinto.
     */
    public Jugador generatePlayer() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        for (int i = 0; i < mazeArr.size(); i++) {
            if (mazeArr.get(i).equals("P")) {
                int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                return new Jugador(posX, posY);
            }
        }
        throw new Error("Jugador no existe");
    }

    /**
     * Genera una lista de objetos de tipo Fantasma basados en la configuración del laberinto actual
     *
     * @return ArrayList de objetos de tipo Fantasma
     */
    public ArrayList<Fantasma> generateGhostList() {
        ArrayList<String> mazeArr = curLaberinto.getLaberinto();
        ArrayList<Fantasma> ghostArr = new ArrayList<>();
        for (int i = 0; i < mazeArr.size(); i++) {
            switch (mazeArr.get(i)) {
                case "B" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Blinky(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "K" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Pinky(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "C" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Clyde(posX, posY, curLaberinto, jugador));
                    break;
                }
                case "I" -> {
                    int posX = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getColumna() * 20;
                    int posY = Coordenadas.getCordenadas(i, curLaberinto.getColumnas()).getFila() * 20 + 50;
                    ghostArr.add(new Inky(posX, posY, curLaberinto, jugador));
                    break;
                }
            }
        }
        return ghostArr;
    }

    /**
     * Retorna la lista de fantasmas del laberinto.
     *
     * @return la lista de fantasmas del laberinto.
     */
    public ArrayList<Fantasma> getListaFantasmas() {
        return listaFantasmas;
    }


    /**
     * Devuelve un array de Strings con las rutas de los archivos de texto que contienen los laberintos de cada nivel.
     *
     * @return Un array de Strings con las rutas de los archivos de texto.
     */
    public String[] getNiveltxt() {
        String[] arr = new String[NO_NIVELES];
        for (int i = 0; i < NO_NIVELES; i++) {
            arr[i] = "./maze/" + i + ".txt";
        }
        return arr;
    }

    /**
     * Aumenta el puntaje del jugador en una cantidad dada y actualiza las vidas del jugador si el puntaje temporal
     * es mayor o igual a 10000.
     *
     * @param num La cantidad que se sumará al puntaje del jugador.
     */
    public void addPuntaje(int num) {
        puntajeJugador += num;
        puntajeTemp += num;
        if (puntajeTemp >= 2500) {
            vidasJugador += 1;
            puntajeTemp = 0;
        }
    }

    /**
     * Método que retorna la fruta actual que se encuentra en el tablero.
     *
     * @return Fruta objeto Fruta actual en el tablero.
     */
    public Fruta getFrutaActual() {
        return frutaActual;
    }

    /**
     * Devuelve el número del nivel actual en el juego.
     *
     * @return entero con el número del nivel actual.
     */
    public int getNivel() {
        return nivelActual;
    }

    /**
     * Devuelve el objeto Laberinto actual en el juego.
     *
     * @return objeto Laberinto actual en el juego.
     */
    public Laberinto getMaze() {
        return curLaberinto;
    }

    /**
     * Retorna verdadero si el nivel ha sido completado, es decir, si no hay comida ni poderes restantes.
     * Retorna falso en caso contrario.
     *
     * @return verdadero si el nivel ha sido completado, falso en caso contrario.
     */
    @Override
    public boolean isLevelFinished() {
        return listaComidas.size() == 0 && listaPoder.size() == 0;
    }

    /**
     * Retorna verdadero siempre, ya que el jugador siempre está vivo.
     *
     * @return verdadero siempre
     */
    @Override
    public boolean isPlayerAlive() {
        return true;
    }

    /**
     * No hace nada ya que el jugador no se destruye en este juego.
     */
    @Override
    public void resetDestroyedPlayer() {
    }

    /**
     * Este método permite avanzar al siguiente nivel del juego, actualizando las variables correspondientes y generando
     * los elementos del nuevo nivel, como el jugador, los fantasmas, la comida, las frutas y los poderes, así como la
     * pared y los caminos que conforman el laberinto del nivel.
     */
    @Override
    public void moveToNextLevel() {
        pausado = true;
        nivelActual++;
        contadorComerFantasma = 0;
        curLaberinto = new Laberinto(nivel[nivelActual]);

        jugador = generatePlayer();
        listaFantasmas = generateGhostList();
        listaComidas = listaComida();
        listaFrutas = generarListaFrutas();
        listaPoder = generatePowerList();
        listaMuros = generarListaPared();
        listaMuroAires = generarListaMurosAire();
        frutaActual = listaFrutas.get(0);

        listaFantasmasEnBase = new ArrayList<>();
        listaFantasmasEnBase.addAll(listaFantasmas);
    }

    /**
     * Indica si el juego ha terminado o no.
     *
     * @return true si el juego ha terminado, false en caso contrario.
     */
    @Override
    public boolean isGameOver() {
        if (vidasJugador == 0) {
            return true;
        }
        return nivelActual > NO_NIVELES;
    }

    /**
     * Devuelve el ancho de pantalla del juego.
     *
     * @return el ancho de pantalla.
     */
    @Override
    public int getScreenWidth() {
        return ANCHO_PANTALLA;
    }

    /**
     * Devuelve la altura de pantalla del juego.
     *
     * @return la altura de pantalla.
     */
    @Override
    public int getScreenHeight() {
        return ALTURA_PANTALLA;
    }
}
