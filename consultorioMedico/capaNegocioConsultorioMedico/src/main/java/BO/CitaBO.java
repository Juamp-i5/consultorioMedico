package BO;

import DAO.CitaDAO;
import DTO.CitaDTO;
import DTO.CitaEmergenciaDTO;
import entidades.Cita;
import entidades.Medico;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import mappers.CitaMapper;

/**
 *
 * @author janot
 */
public class CitaBO {

    CitaDAO citaDAO = new CitaDAO();

    /**
     * Agenda una nueva cita médica en la base de datos.
     *
     * @param citaDTO Objeto {@code CitaDTO} que contiene la información de la cita a agendar.
     * @return {@code true} si la cita fue agendada exitosamente, {@code false} en caso contrario.
     * @throws NegocioException Si ocurre un error al intentar agendar la cita.
     */
    public boolean agendarCita(CitaDTO citaDTO) throws NegocioException {

        // Obtener datos de la cita
        int idMedico = citaDTO.getIdMedico();
        int idPaciente = citaDTO.getIdPaciente();
        LocalDate fecha = citaDTO.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir Date a LocalDate
        LocalTime horaCita = citaDTO.getHora();
        String tipo = citaDTO.getTipo();

        // Unir LocalDate y LocalTime en LocalDateTime
        LocalDateTime fechaHora = LocalDateTime.of(fecha, horaCita);
        System.out.println(fechaHora);
        // MAPPER PARA CONVERTIRLO A Cita
        Cita cita = CitaMapper.toEntity(citaDTO, fechaHora);

        try {
            return citaDAO.agendarCita(cita);
        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error al agregar cita", e);
        }
    }
    
    /**
     * Cancela una cita médica existente en la base de datos.
     *
     * @param idCita El ID de la cita que se desea cancelar.
     * @return {@code true} si la cita fue cancelada exitosamente, {@code false} en caso contrario.
     * @throws NegocioException Si ocurre un error al intentar cancelar la cita.
     */
    public boolean cancelarCita(int idCita) throws NegocioException{
        CitaDAO citaDAO = new CitaDAO(); 
        try {
            return citaDAO.cancelarCita(idCita);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cancelar cita",e);
        }
    }
    /**
     * Agenda una cita de emergencia para un paciente en una especialidad específica.
     *
     * @param idPaciente   El ID del paciente que necesita la cita de emergencia.
     * @param especialidad La especialidad médica requerida.
     * @return Un objeto {@code CitaEmergenciaDTO} con la información de la cita agendada.
     * @throws NegocioException Si ocurre un error al intentar agendar la cita de emergencia.
     */
    public CitaEmergenciaDTO agendarCitaEmergencia(int idPaciente, String especialidad) throws NegocioException {
        try {
            int idCita = citaDAO.insertarCitaEmergencia(idPaciente, especialidad);
            Cita cita = citaDAO.obtenerCita(idCita);
            Medico medico = new MedicoBO().consultarMedico(cita.getIdMedico());
            
            return new CitaEmergenciaDTO(
                    cita.getFechaHora(),
                    medico.getNombre() + " " + medico.getApellidoPaterno() + " " + medico.getApellidoMaterno(), 
                    cita.getFolio()
            );
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al agendarCitaEmergenecia: " + ex.getMessage());
        }

    }
    /**
     * Consulta una cita médica en la base de datos por su ID.
     *
     * @param idCita El ID de la cita a consultar.
     * @return Un objeto {@code Cita} con la información de la cita encontrada.
     * @throws NegocioException Si ocurre un error al intentar consultar la cita.
     */
    public Cita consultarCita(int idCita) throws NegocioException {
        try {
            return citaDAO.obtenerCita(idCita);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultarCita: " + e.getMessage());
        }
    }
}
