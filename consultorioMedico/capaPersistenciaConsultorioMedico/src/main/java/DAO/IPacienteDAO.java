/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.DireccionPaciente;
import entidades.Paciente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Jp
 */
public interface IPacienteDAO {

    public boolean agregarPaciente(Paciente paciente, DireccionPaciente direccionPaciente) throws PersistenciaException;

    public Paciente consultarPaciente(int id) throws PersistenciaException;

    public List<Paciente> consultarPacientes() throws PersistenciaException;

    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException;

    public boolean existePaciente(int id) throws PersistenciaException;
}
