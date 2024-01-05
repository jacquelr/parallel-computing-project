/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package back;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author jlope
 */
public interface InterfazNodoRMI extends Remote {
    void conectarUsuario(InterfazUsuarioRMI usuario) throws RemoteException;
    void encontrarMatriz (int tamanoMatrices) throws RemoteException;
    int [][] multiplicarMatrices(int filaInicial, int filaFinal, int [][] matrizResultado) throws RemoteException;
    String obtenerIPNodo() throws RemoteException;
}
