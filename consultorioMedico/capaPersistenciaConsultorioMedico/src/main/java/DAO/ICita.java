package DAO;

import entidades.Cita;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author janot
 */
public interface ICita {
    /**
     * Agenda una cita y la guarda en la base de datos
     * 
     * @param cita El cual tendra toda la info a guardar en la bd
     * @return true si se agenda la cita y se guarda en la bd, y false en caso
     * contrario
     * @throws PersistenciaException Si no se logra hacer la cita
     */
    public boolean agendarCita(Cita cita) throws PersistenciaException;
    
    /**
     * Cancela una cita de la base de datos, cambiando su estado a cancelado
     * 
     * @param idCita de la cita que se desea cancelar
     * @return True si logra hacer la cancelacion y false en caso contrario
     * @throws PersistenciaException 
     */
    public boolean cancelarCita(int idCita) throws PersistenciaException;
    

    /**
     * Obtiene todas las citas que estan activas del paciente que tiene ese id
     * 
     * @param idPaciente Id del paciente del que se quiere saber sus citas disponibles
     * @return Una lista con todas las citas activas del paciente
     * @throws PersistenciaException Si no se logra obtener el listado
     */
    public List<Cita> obtenerCitasActivasPaciente(int idPaciente) throws PersistenciaException;
    
    public int insertarCitaEmergencia(int idPaciente, String especialidad) throws PersistenciaException;
    
    public Cita obtenerCita(int idCita) throws PersistenciaException;
    
    public void actualizarEstadoCitaNoAsistido(int idCita) throws PersistenciaException;
}
