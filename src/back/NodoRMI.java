/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author jlope
 */
public class NodoRMI extends UnicastRemoteObject implements InterfazNodoRMI{
    public InterfazUsuarioRMI usuario;
    public String ipNodo;
    
    //Matrices
    int [][] matriz1;
    int [][] matriz2;
    //int [][] matrizResultado;
    
    //rutas de las matrices
    String matriz1_100x100 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_100x100.txt";
    String matriz2_100x100 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_100x100.txt";
    String matriz1_1000x1000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_1000x1000.txt";
    String matriz2_1000x1000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_1000x1000.txt";
    String matriz1_10000x10000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_10000x10000.txt";
    String matriz2_10000x10000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_10000x10000.txt";
    
    public NodoRMI(String ipNodo) throws RemoteException{
        this.ipNodo = ipNodo;
    }

    //Primero se crea el objeto remoto
    public void crearObjetoRemoto(String ipNodo){
        try {
            LocateRegistry.createRegistry(1234);
            InterfazNodoRMI nodo = new NodoRMI(ipNodo);

            java.rmi.Naming.rebind("rmi://" + ipNodo + ":1234/Multiplicador", nodo);
            System.out.println("Nodo iniciado");
            
            this.ipNodo = ipNodo;
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    //Conectar cliente
    @Override
    public void conectarUsuario(InterfazUsuarioRMI usuario) throws RemoteException{
        this.usuario = usuario;
        usuario.recibirActualizacion("Nodo conectado.\n");
    }
//
    
    @Override
    public void encontrarMatriz(int tamanoMatrices) throws RemoteException {
        try{
            switch (tamanoMatrices) {
            case 0:
                matriz1 = leerMatrizDesdeArchivo(matriz1_100x100);
                matriz2 = leerMatrizDesdeArchivo(matriz2_100x100);
                //matrizResultado = new int [100][100];
                break;
            case 1:
                matriz1 = leerMatrizDesdeArchivo(matriz1_1000x1000);
                matriz2 = leerMatrizDesdeArchivo(matriz2_1000x1000);
                //matrizResultado = new int [100][100];
                break;
            case 2:
                matriz1 = leerMatrizDesdeArchivo(matriz1_10000x10000);
                matriz2 = leerMatrizDesdeArchivo(matriz2_10000x10000);
                //matrizResultado = new int [100][100];
                break;
            default:
                System.out.println("error al iniciar las matrices");
            }
        }catch(IOException ex){
            System.out.println("Error al leer los archivos .txt en NodoRMI");
        }
    }

    @Override
    public int [][] multiplicarMatrices(int filaInicial, int filaFinal, int[][] matrizResultado) throws RemoteException {
        //int matrizResultadoTemp[][] = new int[filaFinal][filaFinal];
        
        //mensaje
        usuario.recibirActualizacion("El nodo ha empezado a multiplicar.");
        
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = filaInicial; i < filaFinal; i++) {
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
        
        //mensaje
        usuario.recibirActualizacion("El nodo " + ipNodo + "ha terminado de multiplicar.");
        
        //return matrizResultadoTemp;
        return matrizResultado;
    }

    @Override
    public String obtenerIPNodo() throws RemoteException {
        return ipNodo;
    }
    
    public int[][] leerMatrizDesdeArchivo(String ruta) throws IOException {
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
    
}
