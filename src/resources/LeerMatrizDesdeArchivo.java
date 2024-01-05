/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author jlope
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerMatrizDesdeArchivo {
    public static void main(String[] args) {
        // Especifica la ruta del archivo
        String rutaArchivo = "matriz1_100x100.txt";

        // Intenta leer la matriz desde el archivo
        try {
            int[][] matriz = leerMatrizDesdeArchivo(rutaArchivo);

            // Imprime la matriz
            imprimirMatriz(matriz);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Método para leer la matriz desde un archivo
    public static int[][] leerMatrizDesdeArchivo(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        int filas = 0;
        int columnas = 0;

        // Determina el número de filas y columnas en el archivo
        while ((linea = br.readLine()) != null) {
            filas++;
            String[] elementos = linea.split("\\s+"); // Considera cualquier espacio en blanco como delimitador
            columnas = elementos.length;
        }

        // Vuelve a abrir el archivo para leer los datos
        br.close();
        br = new BufferedReader(new FileReader(ruta));

        // Crea la matriz con las dimensiones determinadas
        int[][] matriz = new int[filas][columnas];

        // Lee los datos del archivo y llena la matriz
        for (int i = 0; i < filas; i++) {
            linea = br.readLine();
            String[] elementos = linea.split("\\s+");
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = Integer.parseInt(elementos[j]);
            }
        }

        // Cierra el BufferedReader
        br.close();

        return matriz;
    }

    // Método para imprimir la matriz
    public static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
