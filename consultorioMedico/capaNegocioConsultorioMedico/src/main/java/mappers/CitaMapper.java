package mappers;

import DTO.CitaDTO;
import entidades.Cita;
import java.time.LocalDateTime;

/**
 *
 * @author janot
 */
public class CitaMapper {
    public static Cita toEntity(CitaDTO citaDTO,LocalDateTime fechaYhora){
        return new Cita(citaDTO.getTipo(), null, fechaYhora, null, citaDTO.getIdPaciente(), citaDTO.getIdMedico());
    }
}
