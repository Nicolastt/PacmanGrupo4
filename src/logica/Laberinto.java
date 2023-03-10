package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Laberinto {
    private ArrayList<String> laberintoArray;
    private int fila;
    private int columnas;

    /**
     * Constructor de la clase Laberinto
     * @param fileDir ruta del archivo de texto que contiene el laberinto
     */
    public Laberinto(String fileDir) {
        fila = 0;
        laberintoArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileDir)); // lee el archivo de texto
            String line = null;
            while ((line = reader.readLine()) != null) { // Lee cada línea del archivo
                for (String s : line.split("")) { // Separa cada caracter de la línea en una lista de strings
                    laberintoArray.add(s); // Agrega el caracter a la lista del laberinto
                    columnas = line.length(); // Establece la cantidad de columnas del laberinto como la longitud de la línea
                }
                fila += 1; // Aumenta el contador de filas por cada línea leída
            }
            reader.close(); // Cierra el archivo
        } catch (Exception e) {
            e.printStackTrace(); // Muestra la traza de error en caso de excepción
        }
    }

    /**
     * Método que devuelve el laberinto como una lista de strings
     * @return lista de strings que representa el laberinto
     */
    public ArrayList<String> getLaberinto() {
        return laberintoArray;
    }

    /**
     * Método que devuelve la cantidad de filas del laberinto
     * @return cantidad de filas del laberinto
     */
    public int getFilas() {
        return fila;
    }

    /**
     * Método que devuelve la cantidad de columnas del laberinto
     * @return cantidad de columnas del laberinto
     */
    public int getColumnas() {
        return columnas;
    }

}
