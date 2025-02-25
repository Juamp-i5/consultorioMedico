/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.HistorialConsultaMedico;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IConsultaDAO {
    public List<HistorialConsultaMedico> consultasMedico(int idMedico) throws PersistenciaException;
}
