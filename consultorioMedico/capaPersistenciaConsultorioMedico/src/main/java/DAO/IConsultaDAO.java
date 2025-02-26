/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.HistorialConsultaMedico;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IConsultaDAO {
    /**
     * Obtiene el historial de consultas atendidas por un médico en la base de datos.
     *
     * @param idMedico El ID del médico cuyas consultas se desean recuperar.
     * @return Una lista de objetos {@code HistorialConsultaMedico} con los detalles de las consultas.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de datos.
     */
    public List<HistorialConsultaMedico> consultasMedico(int idMedico) throws PersistenciaException;
}
