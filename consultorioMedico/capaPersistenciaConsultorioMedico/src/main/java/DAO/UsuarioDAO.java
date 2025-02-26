/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import excepciones.PersistenciaException;
import java.sql.Statement;

public class UsuarioDAO {

    /**
     * Agrega un nuevo usuario a la base de datos.
     * Inserta un registro en la tabla "usuario" con los datos proporcionados y 
     * devuelve el identificador generado para el nuevo usuario.
     *
     * @param usuario El objeto {@link Usuario} que contiene la información del usuario a registrar.
     * @return El identificador único del usuario recién agregado.
     * @throws PersistenciaException Si ocurre un error al realizar la operación en la base de datos.
     */
    public int agregarUsuario(Usuario usuario) throws PersistenciaException {
        String consultaSQL = "INSERT INTO consultas_medicas.usuario (nombre, apellidoPaterno, apellidoMaterno, contrasenia) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoPaterno());
            ps.setString(3, usuario.getApellidoMaterno());
            ps.setString(4, usuario.getContrasenia());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retornar el id generado
                    } else {
                        throw new PersistenciaException("Error al obtener el id del usuario recién registrado");
                    }
                }
            } else {
                throw new PersistenciaException("Error al registrar el usuario");
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregar el usuario", e);
        }
    }

    /**
     * Obtiene el nombre de un usuario a partir de su identificador único.
     *
     * @param idUsuario El ID del usuario cuyo nombre se desea obtener.
     * @return El nombre del usuario si se encuentra en la base de datos.
     * @throws PersistenciaException Si no se encuentra el usuario o ocurre un error en la base de datos.
     * @throws SQLException Si ocurre un error en la ejecución de la consulta SQL.
     */
    public String obtenerNombre(int idUsuario) throws PersistenciaException, SQLException {
        String consultaSQL = "SELECT nombre FROM consultas_medicas.usuario WHERE id_usuario = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                } else {
                    throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener el nombre del usuario", e);
        }
    }

    /**
     * Obtiene el identificador único (ID) de un usuario a partir de su nombre de usuario.
     *
     * @param nombreUsuario El nombre del usuario cuyo ID se desea obtener.
     * @return El ID del usuario si se encuentra en la base de datos.
     * @throws PersistenciaException Si no se encuentra el usuario o ocurre un error en la base de datos.
     * @throws SQLException Si ocurre un error en la ejecución de la consulta SQL.
     */
    public int obtenerIdUsuario(String nombreUsuario) throws PersistenciaException, SQLException {
        String consultaSQL = "SELECT id_usuario FROM consultas_medicas.usuario WHERE nombre = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                } else {
                    throw new PersistenciaException("No se encontró el usuario con nombre: " + nombreUsuario);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener el id del usuario", e);
        }
    }
    
    /**
     * Consulta un usuario en la base de datos a partir de su identificador único (ID).
     *
     * @param idUsuario El ID del usuario que se desea consultar.
     * @return Un objeto {@code Usuario} con la información del usuario si se encuentra en la base de datos.
     * @throws PersistenciaException Si no se encuentra el usuario o si ocurre un error en la base de datos.
     */
    public Usuario consultarUsuarioPorId(int idUsuario) throws PersistenciaException, SQLException {
        String consultaSQL = "SELECT * FROM consultas_medicas.usuario WHERE id_usuario = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellidoPaterno(rs.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(rs.getString("apellidoMaterno"));
                    usuario.setContrasenia(rs.getString("contrasenia"));

                    return usuario;
                } else {
                    throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el usuario por ID", e);
        }
    }
}


