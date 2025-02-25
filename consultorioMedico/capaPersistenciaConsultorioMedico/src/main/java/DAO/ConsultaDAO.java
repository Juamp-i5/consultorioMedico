/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Consulta;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsultaDAO {
    
    private static final Logger logger = Logger.getLogger(ConsultaDAO.class.getName());
    
    public String getEstado(int idConsulta) throws PersistenciaException {
        String estado = null;
        String sql = "SELECT estado FROM consulta WHERE id = ?";
        
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                estado = rs.getString("estado");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener estado de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener estado de la consulta", e);
        }
        return estado;
    }
    
    public String getDiagnostico(int idConsulta) throws PersistenciaException {
        String diagnostico = null;
        String sql = "SELECT diagnostico FROM consulta WHERE id = ?";
        
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                diagnostico = rs.getString("diagnostico");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener diagnóstico de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener diagnóstico de la consulta", e);
        }
        return diagnostico;
    }
    
    public String getTratamiento(int idConsulta) throws PersistenciaException {
        String tratamiento = null;
        String sql = "SELECT tratamiento FROM consulta WHERE id = ?";
        
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                tratamiento = rs.getString("tratamiento");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener tratamiento de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener tratamiento de la consulta", e);
        }
        return tratamiento;
    }
    
    public List<Consulta> obtenerConsultasPaciente(int idPaciente) throws PersistenciaException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta as con join cita as cit on con.id_cita = cit.id_cita where id_paciente = ?;";  

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setDiagnostico(rs.getString("diagnostico"));
                consulta.setTratamiento(rs.getString("tratamiento"));
                consulta.setEstado(rs.getString("estado"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener consultas del paciente: " + e.getMessage());
            throw new PersistenciaException("Error al obtener consultas del paciente", e);
        }
        return consultas;
    }
    

    public List<Consulta> obtenerConsultasFiltradas(int idPaciente, String especialidad, String fechaInicioStr, String fechaFinStr) throws PersistenciaException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta as con  join cita as cit on con.id_cita = cit.id_cita  join medico as m on m.id_medico = cit.id_medico where id_paciente = ?  and especialidad = ? and fecha_hora between ? and ? ";

        // Convertir Strings a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;

        try {
            fechaInicio = LocalDateTime.parse(fechaInicioStr.trim(), formatter);
            fechaFin = LocalDateTime.parse(fechaFinStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new PersistenciaException("Error al convertir las fechas", e);
        }

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.setString(2, especialidad);
            ps.setObject(3, fechaInicio);
            ps.setObject(4, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setTratamiento(rs.getString("tratamiento"));
                    consulta.setEstado(rs.getString("estado"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener consultas del paciente: " + e.getMessage());
            throw new PersistenciaException("Error al obtener consultas del paciente", e);
        }
        return consultas;
    }
    
    public String obtenerEspecialidad(int idConsulta) throws PersistenciaException {
        String sql = "SELECT m.especialidad " +
                     "FROM consulta con " +
                     "JOIN cita cit ON con.id_cita = cit.id_cita " +
                     "JOIN medico m ON cit.id_medico = m.id_medico " +
                     "WHERE con.id_consulta = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("especialidad");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener la especialidad: " + e.getMessage());
            throw new PersistenciaException("Error al obtener la especialidad de la consulta", e);
        }
        return ""; // Retorna cadena vacía si no encuentra información
    }
    
    public String obtenerNombreMedico(int idConsulta) throws PersistenciaException {
        String sql = "SELECT u.nombre  FROM usuario as u JOIN medico as m ON m.id_medico = u.id_usuario JOIN cita cit ON cit.id_medico = m.id_medico  JOIN consulta con ON con.id_cita = cit.id_cita WHERE con.id_consulta = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el nombre del médico: " + e.getMessage());
            throw new PersistenciaException("Error al obtener el nombre del médico de la consulta", e);
        }
        return ""; // Retorna cadena vacía si no encuentra información
    }

    public String getTipoCita(int idConsulta) {
        String sql = "SELECT c.tipo FROM Cita c " +
                     "JOIN Consulta co ON c.id_cita = co.id_cita " +
                     "WHERE co.id_consulta = ?";
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tipo");
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Obtiene la fecha y hora de la cita asociada a una consulta.
     * @param idConsulta Identificador de la consulta.
     * @return Fecha y hora de la cita como String, o null si no se encuentra.
     */
    public String getFechaHora(int idConsulta) {
        String sql = "SELECT c.fecha_hora FROM Cita c " +
                     "JOIN Consulta co ON c.id_cita = co.id_cita " +
                     "WHERE co.id_consulta = ?";
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("fecha_hora");
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }    
}

