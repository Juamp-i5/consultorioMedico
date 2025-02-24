package BO;

import DAO.CitaDAO;
import DTO.CitaDTO;
import entidades.Cita;
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
    public boolean agendarCita(CitaDTO citaDTO) throws NegocioException {
        CitaDAO citaDAO = new CitaDAO();

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

}
