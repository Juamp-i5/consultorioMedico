/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTO.HistorialConsultaDTO;
import entidades.HistorialConsultaMedico;
import java.time.LocalDateTime;

/**
 *
 * @author Jp
 */
public class ConsultaMapper {

    public static HistorialConsultaDTO toDTO(HistorialConsultaMedico historial) {
        return new HistorialConsultaDTO(
                historial.getFechaHora(),
                historial.getTipoConsulta(),
                historial.getEstadoConsulta(),
                historial.getNombrePaciente(),
                historial.getApellidoPaternoPaciente(),
                historial.getApellidoMaternoPaciente(),
                historial.getDiagnostico(),
                historial.getTratamiento(),
                historial.getIdPaciente()
        );
    }
}
