/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Password;

/**
 *
 * @author Jp
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public Usuario consultarUsuario(int id) throws PersistenciaException {
        if (!existeUsuario(id)) {
            throw new PersistenciaException("No existe el usuario con el id #" + id);
        }

        String query = "SELECT * FROM consultas_medicas.usuario WHERE id_usuario = ?;";
        Usuario usuario = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("contrasenia")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarUsuario: " + e.getMessage());
        }

        return usuario;
    }

    @Override
    public List<Usuario> consultarUsuarios() throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.usuario;";

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("contrasenia")
                );
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarUsuarios: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) throws PersistenciaException {
        if (!existeUsuario(usuario.getIdUsuario())) {
            throw new PersistenciaException("No existe el usuario con el id #" + usuario.getIdUsuario());
        }

        String query = "UPDATE `consultas_medicas`.`usuario` SET `nombre` = ?, `apellido_paterno` = ?, `apellido_materno` = ?, `contrasenia` = ? WHERE (`id_usuario` = ?);";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoPaterno());
            ps.setString(3, usuario.getApellidoMaterno());
            ps.setString(4, Password.hashPassword(usuario.getContrasenia()));
            ps.setInt(5, usuario.getIdUsuario());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarUsuario: " + e.getMessage());
        }
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) throws PersistenciaException {
        String query = "INSERT INTO `consultas_medicas`.`usuario` (`nombre`, `apellido_paterno`, `apellido_materno`, `contrasenia`) VALUES (?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoPaterno());
            ps.setString(3, usuario.getApellidoMaterno());
            ps.setString(4, Password.hashPassword(usuario.getContrasenia()));

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarUsuario: " + e.getMessage());
        }
    }

    @Override
    public boolean existeUsuario(int id) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.usuario WHERE id_usuario = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existeUsuario: " + e.getMessage());
        }
        return false;
    }

}
