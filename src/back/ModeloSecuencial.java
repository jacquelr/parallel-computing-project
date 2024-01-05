/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back;

/**
 *
 * @author jlope
 */
public class ModeloSecuencial {
    
    int filasMatriz1;
    int columnasMatriz1;
    int columnasMatriz2;
    
    int matriz1[][];
    int matriz2[][];
    int matrizResultado[][];

    public ModeloSecuencial(int matriz1[][], int matriz2[][], int matrizResultado[][]) {
        this.matriz1 = matriz1;
        this.matriz2 = matriz2;
        this.matrizResultado = matrizResultado;
        
        this.filasMatriz1 = matriz1.length;
        this.columnasMatriz1 = matriz1[0].length;
        this.columnasMatriz2 = matriz2[0].length;
    }

    public void multiplicacionMatrices(){
        for (int i = 0; i < filasMatriz1; i++) {
            for (int j = 0; j < columnasMatriz2; j++) {
                for (int k = 0; k < columnasMatriz1; k++) {
                    matrizResultado[i][j] += matriz1[i][k] * matriz2[k][j];
                }
            }
        }
    } 
}

