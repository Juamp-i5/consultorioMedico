/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Paciente;
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
public class PacienteDAO implements IPacienteDAO {

    @Override
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException {
        String query = "INSERT INTO consultas_medicas.paciente (id_paciente, fecha_nacimiento, telefono, correo_electronico) VALUES (?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, paciente.getIdPaciente());
            ps.setObject(2, paciente.getFechaNacimiento());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getCorreoElectronico());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarPaciente: " + e.getMessage());
        }
    }

    @Override
    public Paciente consultarPaciente(int id) throws PersistenciaException {
        if (!existePaciente(id)) {
            throw new PersistenciaException("No existe el paciente con el id #" + id);
        }

        String query = "SELECT * FROM consultas_medicas.paciente WHERE id_paciente = ?;";
        Paciente paciente = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente(
                            rs.getInt("id_paciente"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("telefono"),
                            rs.getString("correo_electronico")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarPaciente: " + e.getMessage());
        }

        return paciente;
    }

    @Override
    public List<Paciente> consultarPacientes() throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.paciente;";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getString("correo_electronico")
                );
                pacientes.add(paciente);
            }
            return pacientes;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarPacientes: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException {
        if (!existePaciente(paciente.getIdPaciente())) {
            throw new PersistenciaException("No existe el paciente con el id #" + paciente.getIdPaciente());
        }

        String query = "UPDATE consultas_medicas.paciente SET fecha_nacimiento = ?, telefono = ?, correo_electronico = ? WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setObject(1, paciente.getFechaNacimiento());
            ps.setString(2, paciente.getTelefono());
            ps.setString(3, paciente.getCorreoElectronico());
            ps.setInt(4, paciente.getIdPaciente());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarPaciente: " + e.getMessage());
        }
    }

    @Override
    public boolean existePaciente(int id) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.paciente WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existePaciente: " + e.getMessage());
        }
        return false;
    }
}
