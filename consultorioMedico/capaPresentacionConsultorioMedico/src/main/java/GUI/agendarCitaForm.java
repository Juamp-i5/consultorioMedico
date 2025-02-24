/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BO.CitaBO;
import BO.MedicoBO;
import DTO.CitaDTO;
import com.toedter.calendar.JDateChooser;
import entidades.Medico;
import exception.NegocioException;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import utils.InicioSesion;


/**
 *
 * @author Admin
 */
public class agendarCitaForm extends javax.swing.JPanel {

    /**
     * Creates new form crearCuentaForm
     *
     *
     */
    private int idUsuario;

    public agendarCitaForm() {
        initComponents();
    }

    agendarCitaForm(int idUsuario) {
        this.idUsuario = idUsuario;
        initComponents();
        cargarEspecialidades();
        configuracionTxtHoraConsulta();

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
        jPanel2 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        btnAgendar = new javax.swing.JButton();
        jComboEspecialidades = new javax.swing.JComboBox<>();
        jComboMedicos = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        txtHoraConsulta = new javax.swing.JTextField();

        jTextField2.setText("jTextField2");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Agendar Cita");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 158, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tipo");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Fecha");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Especialidad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Medico");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, -1, -1));

        btnRegresar.setBackground(new java.awt.Color(0, 0, 0));
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel2.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 80, 30));

        btnAgendar.setBackground(new java.awt.Color(0, 0, 0));
        btnAgendar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgendar.setText("Agendar");
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 80, 30));

        jComboEspecialidades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboEspecialidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEspecialidadesActionPerformed(evt);
            }
        });
        jPanel2.add(jComboEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 160, 30));

        jComboMedicos.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboMedicosActionPerformed(evt);
            }
        });
        jPanel2.add(jComboMedicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 160, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Hora consulta");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, -1, -1));
        jPanel2.add(txtTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 160, 30));
        jPanel2.add(txtHoraConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 160, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 380, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        javax.swing.JFrame frameActual = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        javax.swing.JFrame frame = new javax.swing.JFrame("Menu Paciente");
        menuPacienteForm agendaCitas = new menuPacienteForm(idUsuario);

        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(agendaCitas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (frameActual != null) {
            frameActual.dispose();
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
        if (jDateChooser1.getDate() == null || 
            jComboEspecialidades.getSelectedItem() == null || 
            jComboMedicos.getSelectedItem() == null || 
            txtHoraConsulta.getText().equals("HH:MM")  || 
            txtTipo.getText().trim().isEmpty()) { 
            JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
        }
        
        else if(!validarFormatoHora(txtHoraConsulta.getText())){
            JOptionPane.showMessageDialog(null, "Hora Invalida");
        }
        
        else if(!validarFecha(jDateChooser1)){
            JOptionPane.showMessageDialog(null, "La fecha no puede ser anterior a la actual.");
        }
        else{
            agendarCita();
            
        }
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void jComboEspecialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEspecialidadesActionPerformed
        //EVITA QUE SE ACUMULEN LOS ITEMS DEL JCOMBO
        for (int i = jComboMedicos.getItemCount() - 1; i >= 0; i--) {
            jComboMedicos.removeItemAt(i);
        }
        String seleccionado = (String) jComboEspecialidades.getSelectedItem();
        cargarMedicos(seleccionado);
        
    }//GEN-LAST:event_jComboEspecialidadesActionPerformed

    private void jComboMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboMedicosActionPerformed
        
    }//GEN-LAST:event_jComboMedicosActionPerformed

    /*FUNCIONES*/
    public void agendarCita(){
        CitaBO citaBO = new CitaBO();
        //OBTENGO TODOS LOS DATOS
        Date fechaSeleccionada = jDateChooser1.getDate();//OBTENEMOS LA FECHA
        String especialidad = jComboEspecialidades.getSelectedItem().toString(); //OBTENEMOS LA ESPECIALIDAD
        Medico medico = (Medico) jComboMedicos.getSelectedItem(); //OBTENEMOS EL MEDICO SELECCIONADO
        String horaCita = txtHoraConsulta.getText(); //OBTENEMOS LA HORA DE LA CITA
        // Formateador para HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // Convertir String a LocalTime
        LocalTime hora = LocalTime.parse(horaCita, formatter);
        String tipo = txtTipo.getText(); //OBTENEMOS EL TIPO
        
        CitaDTO citaDTO = new CitaDTO(tipo, null, fechaSeleccionada, hora, tipo, InicioSesion.getIdUsuario(), medico.getIdUsuario());
        
        try {
            if(citaBO.agendarCita(citaDTO)){
                JOptionPane.showMessageDialog(null, "Se agendo la cita");
                javax.swing.JFrame frameActual = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

                javax.swing.JFrame frame = new javax.swing.JFrame("Menu Paciente");
                menuPacienteForm agendaCitas = new menuPacienteForm(idUsuario);

                frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(agendaCitas);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                if (frameActual != null) {
                    frameActual.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null, "El medico se encuentra ocupado en esa hora");
            }
            
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        
        
    }
    
    
    
    //CARGA LAS ESPECIALIDADES REGISTRADAS EN LA BD
    public void cargarEspecialidades(){
        //LOS ELIMINE POR QUE SE AGREGAN POR DEFECTO
        jComboEspecialidades.removeItem("Item 1");
        jComboEspecialidades.removeItem("Item 2");
        jComboEspecialidades.removeItem("Item 3");
        jComboEspecialidades.removeItem("Item 4");
        MedicoBO medicoBO = new MedicoBO();
        List<String> listaEspecialidades = new LinkedList<>();
        try {
            listaEspecialidades = medicoBO.obtenerEspecialidadesMedicos();
        } catch (NegocioException e) {
            e.printStackTrace();
        }
        
        for (String especialidad : listaEspecialidades) {
            jComboEspecialidades.addItem(especialidad);
        }
    }
    
    //CARGA LOS MEDICOS SEGUN LA ESPECIALIDAD EN EL COMBO BOX
    public void cargarMedicos(String especialidad){
        MedicoBO medicoBO = new MedicoBO();
        List<Medico> listaMedicos = new LinkedList<>();
        try {
            listaMedicos = medicoBO.obtenerMedicosDisponiblesActivos(especialidad);
        } catch (NegocioException e) {
            e.printStackTrace();
        }
        for (Medico medico : listaMedicos) {
            jComboMedicos.addItem(medico);
        }
    }
    
    //LE DA EL ESTILO AL CAMPO DE TEXTO DE LA HORA
    public void configuracionTxtHoraConsulta() {
        txtHoraConsulta.setColumns(5); // Espacio suficiente para HH:mm
        String placeholder = "HH:MM";
        txtHoraConsulta.setForeground(Color.GRAY);
        txtHoraConsulta.setText(placeholder);
        
        txtHoraConsulta.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (txtHoraConsulta.getText().equals(placeholder)) {
                            txtHoraConsulta.setText("");
                            txtHoraConsulta.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (txtHoraConsulta.getText().isEmpty()) {
                            txtHoraConsulta.setForeground(Color.GRAY);
                            txtHoraConsulta.setText(placeholder);
                        }
                    }
                });

        txtHoraConsulta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String texto = txtHoraConsulta.getText().replace(":", ""); // Eliminamos los ':'

                // Permitir solo números y limitar a 4 caracteres sin ':'
                if (!Character.isDigit(c) || texto.length() >= 4) {
                    e.consume(); // Bloquea entrada si no es número o si ya hay 4 dígitos
                    return;
                }

                SwingUtilities.invokeLater(() -> {
                    String nuevoTexto = txtHoraConsulta.getText().replace(":", ""); // Quitamos ':'
                    StringBuilder formateado = new StringBuilder(nuevoTexto);

                    // Insertar ':' después de 2 dígitos
                    if (formateado.length() > 2) {
                        formateado.insert(2, ":");
                    }

                    txtHoraConsulta.setText(formateado.toString());
                });
            }
        });
    }

    public boolean validarFormatoHora(String hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime.parse(hora, formatter);
            return true; // La hora es válida
        } catch (DateTimeParseException e) {
            return false; // La hora es inválida
        }
    }
    
    public boolean validarFecha(JDateChooser dateChooser) {
        // Obtener la fecha seleccionada
        Date fechaSeleccionada = dateChooser.getDate();

        // Obtener la fecha actual sin hora
        Date fechaActual = new Date();
        // Comparar fechas (sin importar la hora)
        if (fechaSeleccionada.before(fechaActual)) {
            return false;
        }

        return true; // Fecha válida
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> jComboEspecialidades;
    private javax.swing.JComboBox<Medico> jComboMedicos;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField txtHoraConsulta;
    private javax.swing.JTextField txtTipo;
    // End of variables declaration//GEN-END:variables
}
