/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ConsultaDAO;
import DAO.MedicoDAO;
import DTO.HistorialConsultaDTO;
import entidades.HistorialConsultaMedico;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.util.ArrayList;
import java.util.List;
import mappers.ConsultaMapper;

/**
 *
 * @author Jp
 */
public class ConsultaBO {

    public ConsultaDAO consultaDAO = new ConsultaDAO();
    public MedicoDAO medicoDAO = new MedicoDAO();

    public List<HistorialConsultaDTO> getHistorialConsultas(int idMedico) throws NegocioException {
        try {
            if (!medicoDAO.existeMedico(idMedico)) {
                throw new NegocioException("No existe el medico");
            }

            List<HistorialConsultaMedico> historialEntidad = consultaDAO.consultasMedico(idMedico);
            List<HistorialConsultaDTO> historialDTO = new ArrayList();
            for (HistorialConsultaMedico historial : historialEntidad) {
                historialDTO.add(ConsultaMapper.toDTO(historial));
            }
            return historialDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

}
