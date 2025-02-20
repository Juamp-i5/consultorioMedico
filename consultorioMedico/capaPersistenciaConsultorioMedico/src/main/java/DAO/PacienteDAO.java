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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jp
 */
public class PacienteDAO implements IPacienteDAO {

    @Override
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException {
        String queryUsuario = "INSERT INTO consultas_medicas.usuario (nombre, apellido_paterno, apellido_materno, contrasenia) VALUES (?, ?, ?, ?);";
        String queryPaciente = "INSERT INTO consultas_medicas.paciente (id_paciente, fecha_nacimiento, telefono, correo_electronico) VALUES (?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection()) {
            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, paciente.getNombre());
                psUsuario.setString(2, paciente.getApellidoPaterno());
                psUsuario.setString(3, paciente.getApellidoMaterno());
                psUsuario.setString(4, paciente.getContrasenia());

                int affectedRowsUsuario = psUsuario.executeUpdate();
                if (affectedRowsUsuario == 0) {
                    throw new SQLException("No se pudo insertar el usuario");
                }

                ResultSet generatedKeys = psUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    try (PreparedStatement psPaciente = conexion.prepareStatement(queryPaciente)) {
                        psPaciente.setInt(1, idUsuario);
                        psPaciente.setObject(2, paciente.getFechaNacimiento());
                        psPaciente.setString(3, paciente.getTelefono());
                        psPaciente.setString(4, paciente.getCorreoElectronico());

                        int affectedRowsPaciente = psPaciente.executeUpdate();
                        return affectedRowsPaciente > 0;
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarPaciente: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Paciente consultarPaciente(int id) throws PersistenciaException {
        if (!existePaciente(id)) {
            throw new PersistenciaException("No existe el paciente con el id #" + id);
        }

        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico FROM consultas_medicas.usuario u JOIN consultas_medicas.paciente p ON u.id_usuario = p.id_paciente WHERE p.id_paciente = ?;";
        Paciente paciente = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente(
                            rs.getInt("id_paciente"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("telefono"),
                            rs.getString("correo_electronico"),
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("contrasenia")
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
        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico "
                + "FROM consultas_medicas.usuario u "
                + "JOIN consultas_medicas.paciente p ON u.id_usuario = p.id_paciente;";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id_paciente"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("telefono"),
                        rs.getString("correo_electronico"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("contrasenia")
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

        String queryUsuario = "UPDATE consultas_medicas.usuario SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, contrasenia = ? WHERE id_usuario = ?;";
        String queryPaciente = "UPDATE consultas_medicas.paciente SET fecha_nacimiento = ?, telefono = ?, correo_electronico = ? WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection()) {
            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario)) {
                psUsuario.setString(1, paciente.getNombre());
                psUsuario.setString(2, paciente.getApellidoPaterno());
                psUsuario.setString(3, paciente.getApellidoMaterno());
                psUsuario.setString(4, paciente.getContrasenia());
                psUsuario.setInt(5, paciente.getIdPaciente());

                int affectedRowsUsuario = psUsuario.executeUpdate();
                if (affectedRowsUsuario == 0) {
                    throw new SQLException("No se pudo actualizar el usuario");
                }

                try (PreparedStatement psPaciente = conexion.prepareStatement(queryPaciente)) {
                    psPaciente.setObject(1, paciente.getFechaNacimiento());
                    psPaciente.setString(2, paciente.getTelefono());
                    psPaciente.setString(3, paciente.getCorreoElectronico());
                    psPaciente.setInt(4, paciente.getIdPaciente());

                    int affectedRowsPaciente = psPaciente.executeUpdate();
                    return affectedRowsPaciente > 0;
                }
            }
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
