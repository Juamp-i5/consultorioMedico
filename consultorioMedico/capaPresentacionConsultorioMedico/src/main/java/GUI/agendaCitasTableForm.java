/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DAO.CitaDAO;
import DAO.MedicoDAO;
import DAO.UsuarioDAO;
import entidades.Cita;
import excepciones.PersistenciaException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class agendaCitasTableForm extends javax.swing.JPanel {
    int idUsuario;
    /**
     * Creates new form crearCuentaForm
     */
    public agendaCitasTableForm() {
        initComponents();
        this.idUsuario = idUsuario;
        cargarDatosEnTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Agenda de Citas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jTable1.setBackground(new java.awt.Color(153, 153, 153));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha_Hora", "Especialidad", "Medico", "      "
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 740, 400));

        jButton7.setBackground(new java.awt.Color(153, 153, 153));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton7.setText("Atras");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 54));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        javax.swing.JFrame frameActual = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JFrame frame = new javax.swing.JFrame("Menu Paciente");
        menuPacienteForm datosPaciente = new menuPacienteForm(utils.InicioSesion.getIdUsuario());

        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(datosPaciente);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (frameActual != null) {
            frameActual.dispose();
        }
    }//GEN-LAST:event_jButton7ActionPerformed
        private void cargarDatosEnTabla() {
        //Instaciamos los DAOS para interactuar con las tablas en la base de datos
        CitaDAO citaDAO = new CitaDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0); // Limpia la tabla antes de cargar datos nuevos (proximos)

        try {
            // Utilizar el idPaciente pasado al constructor (falta agregar esa funcion)
            List<Cita> citas = citaDAO.obtenerCitasActivasPaciente(utils.InicioSesion.getIdUsuario());
            for (Cita cita : citas) { //buscar en cada cita 
                // Obtener los datos necesarios para agregarlos a una fila en la tabla
                String fechaHora = cita.getFechaHora().toString();
                String especialidad = medicoDAO.obtenerEspecialidad(cita.getIdMedico());
                String nombreMedico = usuarioDAO.obtenerNombre(cita.getIdMedico());

                // Agregamos una fila con los datos obtenidos aqui arribita y SE AGREGA PASADO DE LANZA
                tableModel.addRow(new Object[]{fechaHora, especialidad, nombreMedico, "Cancelar Cita"});
                
            }
           
                
        } catch (PersistenciaException e) {
        } catch (SQLException ex) {
            Logger.getLogger(agendaCitasTableForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
