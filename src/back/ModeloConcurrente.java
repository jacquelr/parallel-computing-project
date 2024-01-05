/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author jlopez
 */

public class ModeloConcurrente {
    int matriz1[][];
    int matriz2[][];
    int matrizResultado[][];
    int numHilos;
    
    public ModeloConcurrente(int matriz1[][], int matriz2[][], int matrizResultado[][], int numHilos) {
        this.matriz1 = matriz1;
        this.matriz2 = matriz2;
        this.matrizResultado = matrizResultado;
        this.numHilos = numHilos;
    }
    
    
    public void multiplicacion(){
        ExecutorService executor = Executors.newFixedThreadPool(numHilos);
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz2[0].length; j++) {
                final int row = i;
                final int col = j;
                Future<?> future = executor.submit(() -> {
                    int sum = 0;
                    for (int k = 0; k < matriz1[0].length; k++) {
                        sum += matriz1[row][k] * matriz2[k][col];
                    }
                    matrizResultado[row][col] = sum;
                });
            }
        }

        // Espera a que todos los hilos terminen
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Espera hasta que todos los hilos hayan terminado
        }
    } 

}

