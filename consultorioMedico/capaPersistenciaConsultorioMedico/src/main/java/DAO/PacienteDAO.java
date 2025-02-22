/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.DireccionPaciente;
import entidades.Paciente;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.Password;

/**
 *
 * @author Jp
 */
public class PacienteDAO implements IPacienteDAO {

    @Override
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException {
        String queryUsuario = "INSERT INTO consultas_medicas.usuario (nombre, apellido_paterno, apellido_materno, contrasenia) VALUES (?, ?, ?, ?);";
        String queryPaciente = "INSERT INTO consultas_medicas.paciente (id_paciente, fecha_nacimiento, telefono, correo_electronico) VALUES (?, ?, ?, ?);";
        String queryDireccion = "INSERT INTO consultas_medicas.direccion_paciente (id_direccion_paciente, calle, numero, colonia, codigo_postal) VALUES (?, ?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection()) {
            conexion.setAutoCommit(false); // Desactivar autoCommit para iniciar transacción

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
                        if (affectedRowsPaciente == 0) {
                            throw new SQLException("No se pudo insertar el paciente");
                        }

                        try (PreparedStatement psDireccion = conexion.prepareStatement(queryDireccion)) {
                            psDireccion.setInt(1, idUsuario);
                            psDireccion.setString(2, paciente.getDireccion().getCalle());
                            psDireccion.setString(3, paciente.getDireccion().getNumero());
                            psDireccion.setString(4, paciente.getDireccion().getColonia());
                            psDireccion.setString(5, paciente.getDireccion().getCodigoPostal());

                            int affectedRowsDireccion = psDireccion.executeUpdate();
                            if (affectedRowsDireccion == 0) {
                                throw new SQLException("No se pudo insertar la dirección del paciente");
                            }
                        }
                        conexion.commit(); // Confirmar transacción si todo está bien
                        return true;

                    }

                } else {
                    throw new PersistenciaException("No se pudo obtener el ID del usuario");
                }
            } catch (SQLException e) {
                conexion.rollback(); // Revertir transacción en caso de error osea que se deshace de todos los cambios realizados en los datos
                throw new PersistenciaException("Error al agregarPaciente: " + e.getMessage());
            } finally {
                conexion.setAutoCommit(true); // Restaurar el autoCommit por seguridad(dejar todo en orden para que no haya problemas despues, que todo se guarde automaticamente como debe de ser)
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar con la base de datos: " + e.getMessage());
        }

    }
    
    @Override
    public int validarInicioSesion(Paciente paciente) throws PersistenciaException{
        if (!existeCorreo(paciente.getCorreoElectronico())) {
            throw new PersistenciaException("El correo no existe");
        }
        
        String query = "Select id_usuario, contrasenia FROM VistaInicioSesion WHERE correo = ?";
        
        try (Connection conexion = Conexion.getConnection();
                PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setString(1, paciente.getCorreoElectronico());
            
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    if (Password.verifyPassword(paciente.getContrasenia(), rs.getString("contrasenia"))) {
                        return rs.getInt("id_usuario");
                    } else {
                        throw new PersistenciaException("Contraseña incorrecta");
                    }
                } else{
                    throw new PersistenciaException("El correo no existe");
                }
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error al validar inicio de sesion");
        }
    }
    
//
//    @Override
//    public Paciente consultarPaciente(int id) throws PersistenciaException {
//        if (!existePaciente(id)) {
//            throw new PersistenciaException("No existe el paciente con el id #" + id);
//        }
//
//        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico FROM consultas_medicas.usuario u JOIN consultas_medicas.paciente p ON u.id_usuario = p.id_paciente WHERE p.id_paciente = ?;";
//        Paciente paciente = null;
//
//        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    paciente = new Paciente(
//                            rs.getInt("id_paciente"),
//                            rs.getDate("fecha_nacimiento").toLocalDate(),
//                            rs.getString("telefono"),
//                            rs.getString("correo_electronico"),
//                            rs.getInt("id_usuario"),
//                            rs.getString("nombre"),
//                            rs.getString("apellido_paterno"),
//                            rs.getString("apellido_materno"),
//                            rs.getString("contrasenia")
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            throw new PersistenciaException("Error al consultarPaciente: " + e.getMessage());
//        }
//
//        return paciente;
//    }
//
//    @Override
//    public List<Paciente> consultarPacientes() throws PersistenciaException {
//        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico "
//                + "FROM consultas_medicas.usuario u "
//                + "JOIN consultas_medicas.paciente p ON u.id_usuario = p.id_paciente;";
//        List<Paciente> pacientes = new ArrayList<>();
//
//        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Paciente paciente = new Paciente(
//                        rs.getInt("id_paciente"),
//                        rs.getDate("fecha_nacimiento").toLocalDate(),
//                        rs.getString("telefono"),
//                        rs.getString("correo_electronico"),
//                        rs.getInt("id_usuario"),
//                        rs.getString("nombre"),
//                        rs.getString("apellido_paterno"),
//                        rs.getString("apellido_materno"),
//                        rs.getString("contrasenia")
//                );
//                pacientes.add(paciente);
//            }
//            return pacientes;
//        } catch (SQLException e) {
//            throw new PersistenciaException("Error al consultarPacientes: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException {
//        if (!existePaciente(paciente.getIdPaciente())) {
//            throw new PersistenciaException("No existe el paciente con el id #" + paciente.getIdPaciente());
//        }
//
//        String queryUsuario = "UPDATE consultas_medicas.usuario SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, contrasenia = ? WHERE id_usuario = ?;";
//        String queryPaciente = "UPDATE consultas_medicas.paciente SET fecha_nacimiento = ?, telefono = ?, correo_electronico = ? WHERE id_paciente = ?;";
//
//        try (Connection conexion = Conexion.getConnection()) {
//            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario)) {
//                psUsuario.setString(1, paciente.getNombre());
//                psUsuario.setString(2, paciente.getApellidoPaterno());
//                psUsuario.setString(3, paciente.getApellidoMaterno());
//                psUsuario.setString(4, paciente.getContrasenia());
//                psUsuario.setInt(5, paciente.getIdPaciente());
//
//                int affectedRowsUsuario = psUsuario.executeUpdate();
//                if (affectedRowsUsuario == 0) {
//                    throw new SQLException("No se pudo actualizar el usuario");
//                }
//
//                try (PreparedStatement psPaciente = conexion.prepareStatement(queryPaciente)) {
//                    psPaciente.setObject(1, paciente.getFechaNacimiento());
//                    psPaciente.setString(2, paciente.getTelefono());
//                    psPaciente.setString(3, paciente.getCorreoElectronico());
//                    psPaciente.setInt(4, paciente.getIdPaciente());
//
//                    int affectedRowsPaciente = psPaciente.executeUpdate();
//                    return affectedRowsPaciente > 0;
//                }
//            }
//        } catch (SQLException e) {
//            throw new PersistenciaException("Error al actualizarPaciente: " + e.getMessage());
//        }
//    }

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

    @Override
    public boolean existeCorreo(String correo) throws PersistenciaException {
        String query = "SELECT COUNT(*) FROM paciente WHERE correo_electronico = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar la existencia del correo", e);
        }

        return false;
    }

    @Override
    public boolean existeCelular(String celular) throws PersistenciaException {
        String query = "SELECT COUNT(*) FROM paciente WHERE telefono = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, celular);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar la existencia del celular", e);
        }

        return false;
    }
}
