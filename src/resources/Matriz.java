/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

/**
 *
 * @author jlope
 */
public class Matriz {
    private int filas;
    private int columnas;
    private int[][] valores;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.valores = new int[filas][columnas];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getValor(int fila, int columna) {
        return valores[fila][columna];
    }

    public void setValor(int fila, int columna, int valor) {
        valores[fila][columna] = valor;
    }
}
