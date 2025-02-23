/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.Paciente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para los pacientes en
 * el sistema de consultas médicas. Contiene métodos para agregar, consultar,
 * actualizar y verificar la existencia de pacientes.
 *
 * @author Jp
 */
public interface IPacienteDAO {

    /**
     * Agrega un nuevo paciente al sistema.
     *
     * @param paciente El objeto {@link Paciente} que contiene los datos del
     * paciente a agregar.
     * @return {@code true} si el paciente se agregó exitosamente, {@code false}
     * si hubo un error.
     * @throws PersistenciaException Si ocurre un error al agregar el paciente.
     */
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException;

    /**
     * Consulta los datos de un paciente dado su ID.
     *
     * @param id El ID del paciente que se desea consultar.
     * @return Un objeto {@link Paciente} con los datos del paciente.
     * @throws PersistenciaException Si no se encuentra el paciente con el ID
     * proporcionado o si ocurre un error en la consulta.
     */
    public Paciente consultarPaciente(int id) throws PersistenciaException;

//
//    public List<Paciente> consultarPacientes() throws PersistenciaException;
//
    /**
     * Actualiza los datos de un paciente en el sistema.
     *
     * @param paciente El objeto {@link Paciente} con los nuevos datos que se
     * desean actualizar.
     * @return {@code true} si la actualización fue exitosa, {@code false} si
     * hubo un error.
     * @throws PersistenciaException Si ocurre un error al actualizar los datos
     * del paciente.
     */
    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException;

    /**
     * Valida el inicio de sesión de un paciente en el sistema.
     *
     * @param paciente El objeto {@link Paciente} con las credenciales a
     * validar.
     * @return El ID del paciente si las credenciales son correctas, o un valor
     * negativo si no son válidas.
     * @throws PersistenciaException Si ocurre un error al validar las
     * credenciales.
     */
    public int validarInicioSesion(Paciente paciente) throws PersistenciaException;

    /**
     * Verifica si existe un paciente dado su ID.
     *
     * @param id El ID del paciente que se desea verificar.
     * @return {@code true} si el paciente existe, {@code false} si no existe.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del paciente.
     */
    public boolean existePaciente(int id) throws PersistenciaException;

    /**
     * Verifica si existe un paciente con el correo electrónico dado.
     *
     * @param correo El correo electrónico del paciente que se desea verificar.
     * @return {@code true} si existe un paciente con ese correo, {@code false}
     * si no.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del correo.
     */
    public boolean existeCorreo(String correo) throws PersistenciaException;

    /**
     * Verifica si existe un paciente con el número de celular dado.
     *
     * @param celular El número de celular del paciente que se desea verificar.
     * @return {@code true} si existe un paciente con ese número de celular,
     * {@code false} si no.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del celular.
     */
    public boolean existeCelular(String celular) throws PersistenciaException;
}
