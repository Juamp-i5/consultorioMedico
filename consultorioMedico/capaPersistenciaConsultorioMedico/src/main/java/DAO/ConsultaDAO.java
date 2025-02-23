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
}

