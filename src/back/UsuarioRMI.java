/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back;

import java.rmi.Naming;
import java.util.List;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author jlope
 */
public class UsuarioRMI extends UnicastRemoteObject implements InterfazUsuarioRMI{
    //tiene una lista de nodos
    public List<InterfazNodoRMI> nodos;
    public InterfazNodoRMI nodoPrueba;
    public String id;
    public JTextArea output;
    
    public UsuarioRMI (JTextArea output) throws RemoteException{
        nodos = new ArrayList<>();
        //nodoPrueba = new NodoRMI();
        this.output = output;
    }
    
    public void añadirNodo(String ipNodo, JTextArea output){
        try {
            String nodoURL = "rmi://" + ipNodo + ":1234/Multiplicador"; 
            
            nodoPrueba = new NodoRMI(ipNodo);
            nodoPrueba = (InterfazNodoRMI) Naming.lookup(nodoURL);
            
            //
            InterfazUsuarioRMI usuario = new UsuarioRMI(output);
            nodoPrueba.conectarUsuario(usuario);
            //
            
            nodos.add(nodoPrueba);
            
            //System.out.println(nodos.get(0));
            //InterfazUsuarioRMI usuario = new UsuarioRMI(output);
            //conectar el nodo actual con nosotros
            /*for (InterfazNodoRMI nodo : nodos) {
                if (nodo.obtenerIPNodo().equals(ipNodo)) {
                    // Enviar mensaje directo al destinatario
                    nodo.conectarUsuario(usuario);
                    return;
                }
            }*/
            //nodoPrueba.conectarUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recibirActualizacion(String actualizacion) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            if (output != null) {
                output.append(actualizacion + "\n\n");
            } else {
                System.out.println("Buzón de usuario no encontrado.");
            }
        });
    }

    @Override
    public void recibirResultado(int[][] matrizResultado) throws RemoteException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
