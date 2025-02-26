/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import DAO.CitaDAO;
import DAO.ConsultaDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import DAO.UsuarioDAO;
import entidades.Cita;
import entidades.Consulta;
import excepciones.PersistenciaException;
import java.awt.Color;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Admin
 */
public class historialConsultasPacienteTableForm extends javax.swing.JPanel {

    public final int idPaciente;

    /**
     * Creates new form crearCuentaForm
     */
    public historialConsultasPacienteTableForm(int idPaciente) {
        this.idPaciente = idPaciente;
        initComponents();
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
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jTextField2.setText("jTextField2");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Historial Consultas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jTable1.setBackground(new java.awt.Color(153, 153, 153));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Fecha_Hora", "Tipo", "Especialidad", "Medico", "Diagnostico", "Tratamiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 830, 410));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox1.setText("Filtrar Consultas");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jLabel2.setText("Especialidad");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        jTextArea3.setMinimumSize(new java.awt.Dimension(13, 10));
        jScrollPane3.setViewportView(jTextArea3);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 110, 30));

        jLabel3.setText("Fecha Inicio");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jTextArea1.setForeground(new java.awt.Color(153, 153, 153));
        jTextArea1.setText("YYYY-MM-DD");
        jTextArea1.setMinimumSize(new java.awt.Dimension(13, 10));
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea1FocusLost(evt);
            }
        });
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 110, 30));

        jTextArea2.setForeground(new java.awt.Color(153, 153, 153));
        jTextArea2.setText("YYYY-MM-DD");
        jTextArea2.setMinimumSize(new java.awt.Dimension(13, 10));
        jTextArea2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea2FocusLost(evt);
            }
        });
        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 110, 30));

        jButton7.setBackground(new java.awt.Color(153, 153, 153));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton7.setText("Atras");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 54));

        jLabel4.setText("Fecha Fin");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        PacienteDAO pacienteDAO = new PacienteDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
            if (jCheckBox1.isSelected()) { 
                // Obtener valores necesarios para el filtro
                //String pene = jTextArea1.getText();
                String fechaInicio = jTextArea1.getText() + " 00:00:00";
                String fechaFin = jTextArea2.getText() + " 00:00:00";
                String especialidad = jTextArea3.getText();
                //int idPaciente = utils.InicioSesion.getIdUsuario(); 
                try {
                    // Obtener consultas filtradas
                    List<Consulta> consultasFiltradas = consultaDAO.obtenerConsultasFiltradas(idPaciente, especialidad, fechaInicio, fechaFin);

                    if (!consultasFiltradas.isEmpty()) {
                        tableModel.setRowCount(0); // Limpiar la tabla

                        for (Consulta consulta : consultasFiltradas) { // Recorrer cada consulta

                        // Obtener los datos para la fila
                        String fechaHora = consultaDAO.getFechaHora(consulta.getIdConsulta());
                        String tipo = consultaDAO.getTipoCita(consulta.getIdConsulta());
                        especialidad = consultaDAO.obtenerEspecialidad(consulta.getIdConsulta());
                        String nombreMedico = consultaDAO.obtenerNombreMedico(consulta.getIdConsulta());
                        String diagnostico = consulta.getDiagnostico();
                        String tratamiento = consulta.getTratamiento();

                        // Agregar una fila con los datos de la consulta
                        tableModel.addRow(new Object[]{fechaHora, tipo, especialidad, nombreMedico, diagnostico, tratamiento});
                    }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron consultas en el rango y especialidad seleccionada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                        tableModel.setRowCount(0); // Vaciar la tabla si no hay resultados
                    }
                } catch (PersistenciaException ex) {
                    Logger.getLogger(historialConsultasPacienteTableForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                boolean hayConsultas = pacienteDAO.filtroConsultasPaciente(idPaciente, especialidad, fechaInicio, fechaFin);
            if (hayConsultas) {
                System.out.println("Se encontraron consultas en el rango y especialidad seleccionada.");
                
            } else {
                 JOptionPane.showMessageDialog(null, "No se encontraron consultas en el rango y especialidad seleccionada.\n Nota: Recuerda llenar todos los filtros", "Información", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0);
            }
        } else { 
            System.out.println("Filtro desactivado");
            cargarDatosEnTabla();
        }        
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        javax.swing.JFrame frameActual = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JFrame frame = new javax.swing.JFrame("Menu Paciente");
        menuPacienteForm datosPaciente = new menuPacienteForm();

        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(datosPaciente);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (frameActual != null) {
            frameActual.dispose();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextArea1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusGained
        // TODO add your handling code here:
        if (jTextArea1.getText().equals("YYYY-MM-DD")) {
        jTextArea1.setText("");
        jTextArea1.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextArea1FocusGained

    private void jTextArea1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusLost
        // TODO add your handling code here:
        if (jTextArea1.getText().equals("")) {
        jTextArea1.setText("YYYY-MM-DD");
        jTextArea1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextArea1FocusLost

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
        // TODO add your handling code here:
        jTextArea1.setText("");
    }//GEN-LAST:event_jTextArea1MouseClicked

    private void jTextArea2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea2FocusGained
        // TODO add your handling code here:
        if (jTextArea2.getText().equals("YYYY-MM-DD")) {
        jTextArea2.setText("");
        jTextArea2.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextArea2FocusGained

    private void jTextArea2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea2FocusLost
        // TODO add your handling code here:
        if (jTextArea2.getText().equals("")) {
        jTextArea2.setText("YYYY-MM-DD");
        jTextArea2.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextArea2FocusLost

    private void jTextArea2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea2MouseClicked
        // TODO add your handling code here:
        jTextArea2.setText("");
    }//GEN-LAST:event_jTextArea2MouseClicked

    private void cargarDatosEnTabla() {
        ConsultaDAO consultaDAO = new ConsultaDAO();
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0); // Limpiar la tabla antes de cargar datos nuevos

        try {
            // Obtener las consultas del paciente
            List<Consulta> consultas = consultaDAO.obtenerConsultasPaciente(idPaciente);
            for (Consulta consulta : consultas) { // Recorrer cada consulta

                // Obtener los datos para la fila
                String fechaHora = consultaDAO.getFechaHora(consulta.getIdConsulta());
                String tipo = consultaDAO.getTipoCita(consulta.getIdConsulta()); // Puedes cambiar esto si hay diferentes tipos de consulta
                String especialidad = consultaDAO.obtenerEspecialidad(consulta.getIdConsulta());
                String nombreMedico = consultaDAO.obtenerNombreMedico(consulta.getIdConsulta());
                

                String diagnostico = consulta.getDiagnostico();
                String tratamiento = consulta.getTratamiento();

                // Agregar una fila con los datos de la consulta
                tableModel.addRow(new Object[]{fechaHora, tipo, especialidad, nombreMedico, diagnostico, tratamiento});
            }
        } catch (PersistenciaException ex) {
            Logger.getLogger(historialConsultasPacienteTableForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
