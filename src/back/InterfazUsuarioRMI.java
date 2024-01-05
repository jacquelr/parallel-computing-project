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
public interface InterfazUsuarioRMI extends Remote {
    void recibirActualizacion(String actualizacion) throws RemoteException;
    void recibirResultado(int [][] matrizResultado) throws RemoteException;
    //void desconectarNodo() throws RemoteException;
}
