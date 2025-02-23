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
    
    public boolean agregarMedico(Medico medico) throws PersistenciaException;

    public Medico consultarMedico(int idMedico) throws PersistenciaException;

    public List<Medico> consultarTodosLosMedicos() throws PersistenciaException;

    public boolean actualizarMedico(Medico medico) throws PersistenciaException;

    public boolean existeMedico(int idMedico) throws PersistenciaException;
    
    public int validarInicioSesion(Medico medico) throws PersistenciaException;
    
    public boolean existeCedula(String cedula) throws PersistenciaException;
    
    public String obtenerEspecialidad(int idMedico) throws PersistenciaException;
    
    public List<String> obtenerEspecialidadesMedicos() throws PersistenciaException;
    
    public List<Medico> obtenerMedicosPorEspecialidadActivos(String especialidad) throws PersistenciaException;
    
    public boolean citasPendientes(int idMedico) throws PersistenciaException;
    
    public boolean darBaja(int idMedico) throws PersistenciaException;
    
    public boolean darAlta(int idMedico) throws PersistenciaException;
    
}

