/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.DireccionPaciente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Jp
 */
public interface IDireccionPacienteDAO {

    boolean agregarDireccionPaciente(DireccionPaciente direccion) throws PersistenciaException;

    DireccionPaciente consultarDireccionPaciente(int idPaciente) throws PersistenciaException;

    List<DireccionPaciente> consultarDireccionesPaciente(int idPaciente) throws PersistenciaException;

    boolean actualizarDireccionPaciente(DireccionPaciente direccion) throws PersistenciaException;

    boolean existeDireccionPaciente(int idPaciente) throws PersistenciaException;
}
