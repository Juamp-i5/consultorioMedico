/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import excepciones.PersistenciaException;

public class UsuarioDAO {

    public String obtenerNombre(int idUsuario) throws PersistenciaException {
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
}

