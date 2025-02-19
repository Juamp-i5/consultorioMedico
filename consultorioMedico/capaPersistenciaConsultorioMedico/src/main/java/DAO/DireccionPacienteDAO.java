/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.DireccionPaciente;
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
public class DireccionPacienteDAO implements IDireccionPacienteDAO {

    @Override
    public boolean agregarDireccionPaciente(DireccionPaciente direccion) throws PersistenciaException {
        String query = "INSERT INTO consultas_medicas.direccion_paciente (id_paciente, calle, numero, colonia, codigo_postal) VALUES (?, ?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, direccion.getIdPaciente());
            ps.setString(2, direccion.getCalle());
            ps.setString(3, direccion.getNumero());
            ps.setString(4, direccion.getColonia());
            ps.setString(5, direccion.getCodigoPostal());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarDireccionPaciente: " + e.getMessage());
        }
    }

    @Override
    public DireccionPaciente consultarDireccionPaciente(int idPaciente) throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.direccion_paciente WHERE id_paciente = ?;";
        DireccionPaciente direccion = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    direccion = new DireccionPaciente(
                            rs.getInt("id_direccion_paciente"),
                            rs.getString("calle"),
                            rs.getString("numero"),
                            rs.getString("colonia"),
                            rs.getString("codigo_postal"),
                            rs.getInt("id_paciente")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarDireccionPaciente: " + e.getMessage());
        }

        return direccion;
    }

    @Override
    public List<DireccionPaciente> consultarDireccionesPaciente(int idPaciente) throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.direccion_paciente WHERE id_paciente = ?;";
        List<DireccionPaciente> direcciones = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DireccionPaciente direccion = new DireccionPaciente(
                            rs.getInt("id_direccion_paciente"),
                            rs.getString("calle"),
                            rs.getString("numero"),
                            rs.getString("colonia"),
                            rs.getString("codigo_postal"),
                            rs.getInt("id_paciente")
                    );
                    direcciones.add(direccion);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarDireccionesPaciente: " + e.getMessage());
        }

        return direcciones;
    }

    @Override
    public boolean actualizarDireccionPaciente(DireccionPaciente direccion) throws PersistenciaException {
        String query = "UPDATE consultas_medicas.direccion_paciente SET calle = ?, numero = ?, colonia = ?, codigo_postal = ? WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getNumero());
            ps.setString(3, direccion.getColonia());
            ps.setString(4, direccion.getCodigoPostal());
            ps.setInt(5, direccion.getIdPaciente());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarDireccionPaciente: " + e.getMessage());
        }
    }

    @Override
    public boolean existeDireccionPaciente(int idPaciente) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.direccion_paciente WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idPaciente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existeDireccionPaciente: " + e.getMessage());
        }

        return false;
    }
}
