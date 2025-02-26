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
    
    public boolean cancelarCita(int idCita) throws NegocioException{
        CitaDAO citaDAO = new CitaDAO(); 
        try {
            return citaDAO.cancelarCita(idCita);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cancelar cita",e);
        }
    }

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
    
    public Cita consultarCita(int idCita) throws NegocioException {
        try {
            return citaDAO.obtenerCita(idCita);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultarCita: " + e.getMessage());
        }
    }
}
