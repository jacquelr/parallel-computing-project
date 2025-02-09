/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package front;

import back.NodoRMI;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author jlope
 */
public class VentanaNodo extends javax.swing.JFrame {
    public NodoRMI nodoRMI;
    public boolean nodoActivo = false;
    /**
     * Creates new form VentanaNodo
     */
    public VentanaNodo() {
        initComponents();
        iniciarNodo();
    }

    public void iniciarNodo(){
        String tempIP = null;
        try {
            //obtiene localhost para crear nodo
            tempIP = java.net.InetAddress.getLocalHost().getHostAddress();
            System.out.println(tempIP);
        } catch (UnknownHostException ex) {
            Logger.getLogger(VentanaNodo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            nodoRMI = new NodoRMI(tempIP);
        } catch (RemoteException ex) {
            Logger.getLogger(VentanaNodo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        nodoRMI.crearObjetoRemoto(tempIP);
        nodoActivo = true;
        System.out.println("Se creo el objeto remoto");
        actualizarUI();
    }
    
    public void actualizarUI(){
        SwingUtilities.invokeLater(() -> {
            if (nodoActivo == true) {
                lblServidor.setText("SERVIDOR ACTIVO");
                try {
                    lblIP.setText("IP: " + nodoRMI.obtenerIPNodo());
                } catch (RemoteException ex) {
                    Logger.getLogger(VentanaNodo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Error al actualizar UI nodo.");
            }
        });

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFondo = new javax.swing.JPanel();
        lblIP = new javax.swing.JLabel();
        lblIPUsuario = new javax.swing.JLabel();
        lblServidor = new javax.swing.JLabel();
        pnlBotton = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        lblTiempo = new javax.swing.JLabel();
        btnDesconectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlFondo.setBackground(new java.awt.Color(246, 255, 236));

        lblIP.setText("IP: ");

        lblIPUsuario.setText("IP del Usuario: ");

        lblServidor.setText("SERVIDOR APAGADO");

        pnlBotton.setBackground(new java.awt.Color(234, 254, 209));

        lblEstado.setText("Esperando...");

        lblTiempo.setText("Tiempo del último trabajo:");
        lblTiempo.setToolTipText("");

        javax.swing.GroupLayout pnlBottonLayout = new javax.swing.GroupLayout(pnlBotton);
        pnlBotton.setLayout(pnlBottonLayout);
        pnlBottonLayout.setHorizontalGroup(
            pnlBottonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBottonLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlBottonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTiempo)
                    .addComponent(lblEstado))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        pnlBottonLayout.setVerticalGroup(
            pnlBottonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBottonLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblEstado)
                .addGap(44, 44, 44)
                .addComponent(lblTiempo)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        btnDesconectar.setText("DESCONECTAR");

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIPUsuario)
                            .addComponent(pnlBotton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoLayout.createSequentialGroup()
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnDesconectar))
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addComponent(lblIP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblServidor)))
                        .addGap(68, 68, 68))))
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lblIP))
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblServidor)))
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblIPUsuario))
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnDesconectar)))
                .addGap(35, 35, 35)
                .addComponent(pnlBotton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaNodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaNodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaNodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaNodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaNodo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblIPUsuario;
    private javax.swing.JLabel lblServidor;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel pnlBotton;
    private javax.swing.JPanel pnlFondo;
    // End of variables declaration//GEN-END:variables
}
