/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import BO.MedicoBO;
import exception.NegocioException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jp
 */
public class Info {
    static MedicoBO medico = new MedicoBO();
    
    public static List<String> getEspecialidades(){
        try {
            return medico.obtenerEspecialidadesMedicos();
        } catch (NegocioException ex) {
            JOptionPane panel = new JOptionPane(ex.getMessage());
        }
        return null;
    }
}
