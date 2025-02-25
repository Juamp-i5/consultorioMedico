/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BO.CitaBO;
import DAO.CitaDAO;
import DAO.MedicoDAO;
import DAO.UsuarioDAO;
import GUI.citasPendientesTableForm.ButtonRenderer;
import entidades.Cita;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id_cita", "Fecha_Hora", "Especialidad", "Medico", "      "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

                boolean esCancelable = cita.getFechaHora().isAfter(LocalDateTime.now().plusHours(24)) && !"Cancelada".equals(cita.getEstado());
                
                String texto = "";
                if (esCancelable) {
                    texto = "Cancelar cita";
                }

                tableModel.addRow(new Object[]{cita.getIdCita(), fechaHora, especialidad, nombreMedico, texto});
            }

            // Detectar clic en las celdas de la tabla
            jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = jTable1.rowAtPoint(e.getPoint());
                    int column = jTable1.columnAtPoint(e.getPoint());

                    if (column == 4) {
                        boolean esCancelable = jTable1.getValueAt(row, column).equals("Cancelar cita");

                        if (esCancelable) {
                            int idCita = (int) jTable1.getValueAt(row, 0);
                            cancelarCita(idCita);
                        }
                    }
                }
            });

        } catch (PersistenciaException e) {
        } catch (SQLException ex) {
            Logger.getLogger(agendaCitasTableForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cancelarCita(int idCita) {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea Cancelar la cita?", "CancelarCita", 1);
        if(respuesta == JOptionPane.YES_OPTION){
            CitaBO citaBO = new CitaBO();
            try {
                if(citaBO.cancelarCita(idCita)){
                    JOptionPane.showMessageDialog(this, "Se cancelo la cita Exitosamente");
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
                }
                
            } catch (NegocioException e) {
                JOptionPane.showMessageDialog(this, e);
            }
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
