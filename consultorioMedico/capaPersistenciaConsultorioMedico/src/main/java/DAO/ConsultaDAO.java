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
}

