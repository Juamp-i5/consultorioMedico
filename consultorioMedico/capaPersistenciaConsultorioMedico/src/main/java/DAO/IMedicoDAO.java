/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.Medico;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para los médicos en el
 * sistema de consultas médicas. Contiene métodos para agregar, consultar,
 * actualizar y gestionar médicos en el sistema.
 *
 * @author Jp
 */
public interface IMedicoDAO {

    /**
     * Agrega un nuevo médico al sistema.
     *
     * @param medico El objeto {@link Medico} con los datos del médico a
     * agregar.
     * @return {@code true} si el médico se agregó exitosamente, {@code false}
     * si hubo un error.
     * @throws PersistenciaException Si ocurre un error al agregar el médico.
     */
    public boolean agregarMedico(Medico medico) throws PersistenciaException;

    /**
     * Consulta los datos de un médico dado su ID.
     *
     * @param idMedico El ID del médico que se desea consultar.
     * @return Un objeto {@link Medico} con los datos del médico.
     * @throws PersistenciaException Si no se encuentra el médico con el ID
     * proporcionado o si ocurre un error en la consulta.
     */
    public Medico consultarMedico(int idMedico) throws PersistenciaException;

    /**
     * Consulta todos los médicos registrados en el sistema.
     *
     * @return Una lista de objetos {@link Medico} con los datos de todos los
     * médicos.
     * @throws PersistenciaException Si ocurre un error al consultar los
     * médicos.
     */
    public List<Medico> consultarTodosLosMedicos() throws PersistenciaException;

    /**
     * Actualiza los datos de un médico en el sistema.
     *
     * @param medico El objeto {@link Medico} con los nuevos datos que se desean
     * actualizar.
     * @return {@code true} si la actualización fue exitosa, {@code false} si
     * hubo un error.
     * @throws PersistenciaException Si ocurre un error al actualizar los datos
     * del médico.
     */
    public boolean actualizarMedico(Medico medico) throws PersistenciaException;

    /**
     * Verifica si existe un médico dado su ID.
     *
     * @param idMedico El ID del médico que se desea verificar.
     * @return {@code true} si el médico existe, {@code false} si no existe.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del médico.
     */
    public boolean existeMedico(int idMedico) throws PersistenciaException;

    /**
     * Valida el inicio de sesión de un médico en el sistema.
     *
     * @param medico El objeto {@link Medico} con las credenciales a validar.
     * @return El ID del médico si las credenciales son correctas, o un valor
     * negativo si no son válidas.
     * @throws PersistenciaException Si ocurre un error al validar las
     * credenciales.
     */
    public int validarInicioSesion(Medico medico) throws PersistenciaException;

    /**
     * Verifica si existe un médico con la cédula proporcionada.
     *
     * @param cedula La cédula del médico que se desea verificar.
     * @return {@code true} si existe un médico con esa cédula, {@code false} si
     * no.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia de la cédula.
     */
    public boolean existeCedula(String cedula) throws PersistenciaException;

    /**
     * Obtiene la especialidad de un médico dado su ID.
     *
     * @param idMedico El ID del médico cuya especialidad se desea obtener.
     * @return Un {@code String} con la especialidad del médico.
     * @throws PersistenciaException Si ocurre un error al obtener la
     * especialidad del médico.
     */
    public String obtenerEspecialidad(int idMedico) throws PersistenciaException;

    /**
     * Obtiene una lista de todas las especialidades de los médicos registrados
     * en el sistema.
     *
     * @return Una lista de {@code String} con las especialidades de los
     * médicos.
     * @throws PersistenciaException Si ocurre un error al obtener las
     * especialidades.
     */
    public List<String> obtenerEspecialidadesMedicos() throws PersistenciaException;

    /**
     * Obtiene una lista de los médicos activos por especialidad.
     *
     * @param especialidad La especialidad de los médicos que se desean
     * consultar.
     * @return Una lista de objetos {@link Medico} con los médicos que tienen la
     * especialidad indicada.
     * @throws PersistenciaException Si ocurre un error al obtener los médicos
     * por especialidad.
     */
    public List<Medico> obtenerMedicosPorEspecialidadActivos(String especialidad) throws PersistenciaException;

    /**
     * Verifica si un médico tiene citas pendientes.
     *
     * @param idMedico El ID del médico cuya disponibilidad se desea verificar.
     * @return {@code true} si el médico tiene citas pendientes, {@code false}
     * si no.
     * @throws PersistenciaException Si ocurre un error al verificar las citas
     * pendientes.
     */
    public boolean citasPendientes(int idMedico) throws PersistenciaException;

    /**
     * Da de baja a un médico en el sistema.
     *
     * @param idMedico El ID del médico que se desea dar de baja.
     * @return {@code true} si la baja fue exitosa, {@code false} si hubo un
     * error.
     * @throws PersistenciaException Si ocurre un error al dar de baja al
     * médico.
     */
    public boolean darBaja(int idMedico) throws PersistenciaException;

    /**
     * Da de alta a un médico en el sistema.
     *
     * @param idMedico El ID del médico que se desea dar de alta.
     * @return {@code true} si la alta fue exitosa, {@code false} si hubo un
     * error.
     * @throws PersistenciaException Si ocurre un error al dar de alta al
     * médico.
     */
    public boolean darAlta(int idMedico) throws PersistenciaException;
}
