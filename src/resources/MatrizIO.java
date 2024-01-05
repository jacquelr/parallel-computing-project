/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileNotFoundException;

/**
 *
 * @author jlope
 */

public class MatrizIO {
    public static void escribirMatriz(Matriz matrizA, Matriz matrizB, String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 0; i < matrizA.getFilas(); i++) {
                for (int j = 0; j < matrizA.getColumnas(); j++) {
                    writer.write(matrizA.getValor(i, j) + " ");
                }
                writer.newLine();
            }
            writer.newLine();
            for (int i = 0; i < matrizB.getFilas(); i++) {
                for (int j = 0; j < matrizB.getColumnas(); j++) {
                    writer.write(matrizB.getValor(i, j) + " ");
                }
                writer.newLine();
            }
        }
    }

    public static Matriz[] leerMatriz(String archivo) throws IOException {
        Matriz[] matrices = new Matriz[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int filas = 0;
            int columnas = 0;
            boolean primerMatr = true;
            boolean segundoMatr = false;

            while ((linea = reader.readLine()) != null) {
                if (linea.isEmpty()) {
                    primerMatr = false;
                    segundoMatr = true;
                    continue;
                }
                String[] valores = linea.split(" ");
                if (primerMatr) {
                    if (filas == 0) {
                        filas = valores.length;
                    }
                    columnas++;
                } else if (segundoMatr) {
                    if (matrices[1] == null) {
                        matrices[1] = new Matriz(valores.length, columnas);
                    }
                    for (int i = 0; i < valores.length; i++) {
                        matrices[1].setValor(i, columnas - 1, Integer.parseInt(valores[i]));
                    }
                    columnas++;
                }
            }
            matrices[0] = new Matriz(filas, columnas - 1);
        }
        return matrices;
    }
    
    public static void main(String[] args) throws IOException {
        /*Matriz matriz1 = new Matriz(3, 3);
        Matriz matriz2 = new Matriz(3, 3);
        MatrizIO io = new MatrizIO();
        Matriz[] matrizLeida = null;

        int contador = 1;
        for (int i = 0; i < matriz1.getFilas(); i++) {
            for (int j = 0; j < matriz1.getColumnas(); j++) {
                matriz1.setValor(i, j, contador);
                contador++;
            }
        }

        for (int k = 0; k < matriz2.getFilas(); k++) {
            for (int l = 0; l < matriz2.getColumnas(); l++) {
                matriz1.setValor(k, l, contador);
                contador++;
            }
        }
        
        System.out.println("Matriz 1:");
        io.escribirMatriz(matriz1, matriz2, "prueba1.txt");

        Matriz matrizLeida = null;
        MatrizIO io = new MatrizIO();
        matrizLeida = io.leerMatriz("prueba1.txt");

           System.out.println("Matriz 1");

            for (int i = 0; i < matrizLeida[0].getFilas(); i++) {
                for (int j = 0; j < matrizLeida[0].getColumnas(); j++) {
                    System.out.print(matrizLeida[0].getValor(i, j));
                }
                System.out.println();
            }

            System.out.println("Matriz 2");
            for (int i = 0; i < matrizLeida[1].getFilas(); i++) {
                for (int j = 0; j < matrizLeida[1].getColumnas(); j++) {
                    System.out.print(matrizLeida[1].getValor(i, j));
                }
                System.out.println();
            }*/
        
        
    }
    
    
}