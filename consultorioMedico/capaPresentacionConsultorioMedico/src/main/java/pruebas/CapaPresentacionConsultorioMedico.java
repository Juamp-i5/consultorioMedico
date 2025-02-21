/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pruebas;

import GUI.inicioSesionForm;
import javax.swing.JFrame;

/**
 *
 * @author Jp
 */
public class CapaPresentacionConsultorioMedico {

    public static void main(String[] args) {
         JFrame frame = new JFrame("Inicio de Sesión");

        // Agregar el JPanel de inicioSesionForm
        inicioSesionForm panel = new inicioSesionForm();
        frame.add(panel);

        // Configurar propiedades del JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600); // Ajusta el tamaño según lo necesites
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }
}
