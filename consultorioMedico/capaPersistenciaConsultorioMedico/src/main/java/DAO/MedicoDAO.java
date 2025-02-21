/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Medico;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jp
 */
public class MedicoDAO implements IMedicoDAO {

    @Override
    public boolean agregarMedico(Medico medico) throws PersistenciaException {
        String query = "INSERT INTO consultas_medicas.medico (especialidad, cedula_profesional, estado) VALUES (?, ?, ?);";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, medico.getEspecialidad());
            ps.setString(2, medico.getCedulaProfesional());
            ps.setString(3, medico.getEstado());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarMedico: " + e.getMessage());
        }
    }

    @Override
    public Medico consultarMedico(int idMedico) throws PersistenciaException {
        String query = "SELECT m.id_medico, u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, " +
                       "m.especialidad, m.cedula_profesional, m.estado, u.contrasenia " +
                       "FROM consultas_medicas.Medico m " +
                       "JOIN consultas_medicas.Usuario u ON m.id_medico = u.id_usuario " +
                       "WHERE m.id_medico = ?;";

        Medico medico = null;

        try (Connection conexion = Conexion.getConnection(); 
             PreparedStatement ps = conexion.prepareStatement(query)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico(
                            rs.getInt("id_medico"),              
                            rs.getString("especialidad"),       
                            rs.getString("cedula_profesional"), 
                            rs.getString("estado"),             
                            rs.getInt("id_usuario"),            
                            rs.getString("nombre"),             
                            rs.getString("apellido_paterno"),   
                            rs.getString("apellido_materno"),   
                            rs.getString("contrasenia")          
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el medico: " + e.getMessage());
        }

        return medico;
    }

    @Override
    public List<Medico> consultarTodosLosMedicos() throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.medico;";
        List<Medico> medicos = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("especialidad"),
                        rs.getString("cedula_profesional"),
                        rs.getString("estado")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarTodosLosMedicos: " + e.getMessage());
        }

        return medicos;
    }

    @Override
    public boolean actualizarMedico(Medico medico) throws PersistenciaException {
        String query = "UPDATE consultas_medicas.medico SET especialidad = ?, cedula_profesional = ?, estado = ? WHERE id_medico = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, medico.getEspecialidad());
            ps.setString(2, medico.getCedulaProfesional());
            ps.setString(3, medico.getEstado());
            ps.setInt(4, medico.getIdMedico());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarMedico: " + e.getMessage());
        }
    }

    @Override
    public boolean existeMedico(int idMedico) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.medico WHERE id_medico = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existeMedico: " + e.getMessage());
        }

        return false;
    }
}
