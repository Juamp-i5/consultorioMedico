/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.Medico;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Jp
 */
public interface IMedicoDAO {
    
    boolean agregarMedico(Medico medico) throws PersistenciaException;

    Medico consultarMedico(int idMedico) throws PersistenciaException;

    List<Medico> consultarTodosLosMedicos() throws PersistenciaException;

    boolean actualizarMedico(Medico medico) throws PersistenciaException;

    boolean existeMedico(int idMedico) throws PersistenciaException;
    
    int validarInicioSesion(Medico medico) throws PersistenciaException;
    
    boolean existeCedula(String cedula) throws PersistenciaException;
}
