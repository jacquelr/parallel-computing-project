/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package front;

import back.InterfazNodoRMI;
import back.ModeloConcurrente;
import back.ModeloSecuencial;
import back.UsuarioRMI;
import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author jlope
 */
public class VentanaUsuario extends javax.swing.JFrame {
    //archivos de texto
    String matriz1_100x100 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_100x100.txt";
    String matriz2_100x100 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_100x100.txt";
    String matriz1_1000x1000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_1000x1000.txt";
    String matriz2_1000x1000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_1000x1000.txt";
    String matriz1_10000x10000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz1_10000x10000.txt";
    String matriz2_10000x10000 = "C:\\Users\\jlope\\OneDrive\\Documents\\NetBeansProjects\\ProyectoComputaciónParalela\\src\\resources\\matriz2_10000x10000.txt";
    
    //variables
    int numeroHilos = 1;
    public int tamanoMatriz = -1;
    public int matriz1[][];
    public int matriz2[][];
    int matrizResultadoSecuencial[][];
    int matrizResultadoConcurrente[][];
    int matrizResultadoParalela[][];
    
    //paralelizando
    UsuarioRMI usuario;
    
    /**
     * Creates new form VentanaUsuario
     */
    public VentanaUsuario() {
        initComponents();
    }
    
    //funcion para validar los input
    public boolean validarDatos(){
        int inputNumeroHilos = Integer.parseInt(txtNumHilos.getText());
        int tamanosMatrices = inputTamanoMatrices.getSelectedIndex();
        
        switch (tamanosMatrices) {
            case 0://matriz de 100x100
                tamanoMatriz = 0;
                break;
            case 1://matriz de 1000x1000
                tamanoMatriz = 1;
                break;
            case 2: //matriz de 10000x10000
                tamanoMatriz = 2;
                break;
            default:
                System.out.println("tamaño de matriz inválido");
                return false;
        }
        
        if (inputNumeroHilos > 0 && inputNumeroHilos < 30){
            numeroHilos = inputNumeroHilos;
        }else{
            System.out.println("Ingrese un numero de hilos válido");
            return false;
        }
        
        return true;
    }
    
    //funcion para leer matrices
    public void iniciarMatrices() throws IOException{
        switch (tamanoMatriz) {
            case 0:
                matriz1 = leerMatrizDesdeArchivo(matriz1_100x100);
                matriz2 = leerMatrizDesdeArchivo(matriz2_100x100);
                matrizResultadoSecuencial = new int [100][100];
                matrizResultadoConcurrente = new int [100][100];
                matrizResultadoParalela = new int [100][100];
                break;
            case 1:
                matriz1 = leerMatrizDesdeArchivo(matriz1_1000x1000);
                matriz2 = leerMatrizDesdeArchivo(matriz2_1000x1000);
                matrizResultadoSecuencial = new int [1000][1000];
                matrizResultadoConcurrente = new int [1000][1000];
                matrizResultadoParalela = new int [1000][1000];
                break;
            case 2:
                matriz1 = leerMatrizDesdeArchivo(matriz1_10000x10000);
                matriz2 = leerMatrizDesdeArchivo(matriz2_10000x10000);
                matrizResultadoSecuencial = new int [10000][10000];
                matrizResultadoConcurrente = new int [10000][10000];
                matrizResultadoParalela = new int [10000][10000];
                break;
            default:
                System.out.println("error al iniciar las matrices");
        }
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
    
    //funcion para multiplicar (secuencial y concurrente)
    public void multiplicarMatrices(){
        ModeloSecuencial secuencial = new ModeloSecuencial(matriz1,matriz2,matrizResultadoSecuencial);
        ModeloConcurrente concurrente = new ModeloConcurrente(matriz1,matriz2,matrizResultadoConcurrente,numeroHilos);
        

        long inicioSecuencial = System.nanoTime();
        secuencial.multiplicacionMatrices();
        long finSecuencial = System.nanoTime()- inicioSecuencial;
        tiempoSecuencial.setText("Secuencial: " + finSecuencial + " ns");
        
        long inicioConcurrente = System.nanoTime();
        concurrente.multiplicacion();
        long finConcurrente = System.nanoTime()- inicioConcurrente;
        tiempoConcurrente.setText("Concurrente: " + finConcurrente + " ns");
    }
    
    public void multiplicarMatricesParalelo() throws RemoteException{
        //int numeroNodos = usuario.nodos.size();
        int numeroNodos = 1;
        int filasPorNodo = matriz1.length / numeroNodos;
        
        //le digo que matrices va a utilizar
        ExecutorService executorGuardarMatrices = Executors.newFixedThreadPool(5);
        for (int nodo = 0; nodo < numeroNodos; nodo++) {
            InterfazNodoRMI nodoActual = usuario.nodos.get(nodo);
                executorGuardarMatrices.submit(() -> {
                try {
                    nodoActual.encontrarMatriz(tamanoMatriz);
                } catch (RemoteException ex) {
                    System.out.println("error al iniciar matrices en los nodos");
                }
            });
        }
        
        
        //tiempo inicia
        long inicioParalelo = System.nanoTime();
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //int[][] matrizResultadoTemp = new int [matriz1.length][matriz2[0].length];
        for (int nodo = 0; nodo < numeroNodos; nodo++) {
            int filaInicio = nodo * filasPorNodo;
            int filaFinal = (nodo == numeroNodos - 1) ? matriz1.length : filaInicio + filasPorNodo;
            
            InterfazNodoRMI nodoActual = usuario.nodos.get(nodo);
            // Invoca el método remoto en el cliente i
            //int[][] resultadoNodo = usuario.nodos.get(nodo).multiplicarMatrices(filaInicio, filaFinal, matrizResultadoParalela);
            executor.submit(() -> {
                try {
                    matrizResultadoParalela = nodoActual.multiplicarMatrices(filaInicio, filaFinal, matrizResultadoParalela);
                } catch (RemoteException ex) {
                    System.out.println("error al enviar hilo al cliente.");
                }
            });
           
            long finParalelo = System.nanoTime()- inicioParalelo;
            tiempoParalelo.setText("Paralelo: " + finParalelo + " ns");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        pnlFondo = new javax.swing.JPanel();
        pnlIzq = new javax.swing.JPanel();
        lblTamaño = new javax.swing.JLabel();
        inputTamanoMatrices = new javax.swing.JComboBox<>();
        lblHilos = new javax.swing.JLabel();
        txtNumHilos = new javax.swing.JTextField();
        btnIniciar = new javax.swing.JButton();
        lblLog = new javax.swing.JLabel();
        txtOutput = new javax.swing.JScrollPane();
        txtAreaOutput = new javax.swing.JTextArea();
        lblTiempos = new javax.swing.JLabel();
        tiempoSecuencial = new javax.swing.JLabel();
        tiempoConcurrente = new javax.swing.JLabel();
        tiempoParalelo = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnRMI = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlFondo.setBackground(new java.awt.Color(255, 243, 236));

        pnlIzq.setBackground(new java.awt.Color(255, 225, 229));

        lblTamaño.setText("Tamaño de las matrices:");

        inputTamanoMatrices.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100 x 100", "1000 x 1000" }));

        lblHilos.setText("Número de hilos para concurrencia:");

        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlIzqLayout = new javax.swing.GroupLayout(pnlIzq);
        pnlIzq.setLayout(pnlIzqLayout);
        pnlIzqLayout.setHorizontalGroup(
            pnlIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIzqLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputTamanoMatrices, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumHilos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
            .addGroup(pnlIzqLayout.createSequentialGroup()
                .addGroup(pnlIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIzqLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHilos)
                            .addComponent(lblTamaño)))
                    .addGroup(pnlIzqLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnIniciar)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        pnlIzqLayout.setVerticalGroup(
            pnlIzqLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIzqLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTamaño)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputTamanoMatrices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblHilos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumHilos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIniciar)
                .addGap(50, 50, 50))
        );

        lblLog.setText("Log de conexiones RMI:");

        txtAreaOutput.setColumns(20);
        txtAreaOutput.setRows(5);
        txtOutput.setViewportView(txtAreaOutput);

        lblTiempos.setText("Tiempos de ejecucion:");

        tiempoSecuencial.setText("Secuencial:");

        tiempoConcurrente.setText("Concurrente:");

        tiempoParalelo.setText("Paralelo:");

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnRMI.setText("CONECTAR");
        btnRMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRMIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(pnlIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(txtOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addComponent(lblTiempos)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addComponent(lblLog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRMI)
                                .addGap(18, 18, 18))))
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tiempoConcurrente)
                                    .addComponent(tiempoSecuencial)
                                    .addComponent(tiempoParalelo)))
                            .addGroup(pnlFondoLayout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(btnNuevo)))
                        .addContainerGap())))
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlIzq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLog)
                    .addComponent(btnRMI))
                .addGap(18, 18, 18)
                .addComponent(txtOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(lblTiempos)
                .addGap(27, 27, 27)
                .addComponent(tiempoSecuencial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tiempoConcurrente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tiempoParalelo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(82, 82, 82))
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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        //HABILITAR ELEMENTOS DE LADO IZQUERDO
        //Limpiar todos los textos
        
        SwingUtilities.invokeLater(() -> {
                inputTamanoMatrices.setEnabled(true);
                txtNumHilos.setEnabled(true);
                //txtAreaInformacion.setEnabled(true);
                btnIniciar.setEnabled(true);
                txtNumHilos.setText("");
            });
    
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        //Validar datos (cual matriz eligio) y numero de hilos
        boolean datosValidos = validarDatos();
        
        if(datosValidos){//si los datos estan bien
            // Deshabilitar todo el lado izquierdo
            SwingUtilities.invokeLater(() -> {
                inputTamanoMatrices.setEnabled(false);
                txtNumHilos.setEnabled(false);
                //txtAreaInformacion.setEnabled(false);
                btnIniciar.setEnabled(false);
            });
        
            
            try {
                //iniciarMatrices
                iniciarMatrices();
            } catch (IOException ex) {
                Logger.getLogger(VentanaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //multiplicar matrices
            multiplicarMatrices();
            try {
                multiplicarMatricesParalelo();
            } catch (RemoteException ex) {
                System.out.println("Error al multiplicar en paralelo");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Datos Inválidos");
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnRMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRMIActionPerformed
        try {
            this.usuario = new UsuarioRMI(txtAreaOutput);
        } catch (RemoteException ex) {
            System.out.println("Error al inicializar Usuario RMI");;
        }
        
        int numeroNodos = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "¿Cuántos nodos va a ingresar?", "Usuario", HEIGHT));
        
        for (int i = 0 ; i < numeroNodos ; i++){
            String ipNodoActual = null;
            ipNodoActual = JOptionPane.showInputDialog(rootPane, "Escribe la ip del nodo " + (i + 1) + " :", "Nodo", HEIGHT);
            usuario.añadirNodo(ipNodoActual, txtAreaOutput);
        }
    }//GEN-LAST:event_btnRMIActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRMI;
    private javax.swing.JComboBox<String> inputTamanoMatrices;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblHilos;
    private javax.swing.JLabel lblLog;
    private javax.swing.JLabel lblTamaño;
    private javax.swing.JLabel lblTiempos;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlIzq;
    private javax.swing.JLabel tiempoConcurrente;
    private javax.swing.JLabel tiempoParalelo;
    private javax.swing.JLabel tiempoSecuencial;
    private javax.swing.JTextArea txtAreaOutput;
    private javax.swing.JTextField txtNumHilos;
    private javax.swing.JScrollPane txtOutput;
    // End of variables declaration//GEN-END:variables
}
