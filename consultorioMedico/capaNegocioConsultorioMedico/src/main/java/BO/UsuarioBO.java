/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.UsuarioDAO;
import entidades.Usuario;
import exception.NegocioException;
import excepciones.PersistenciaException;
import java.sql.SQLException;

public class UsuarioBO {

    private UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAO();
    }


    public int agregarUsuario(Usuario usuario) throws NegocioException {
        try {
            return usuarioDAO.agregarUsuario(usuario);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregar usuario: " + e.getMessage(), e);
        }
    }


    public String obtenerNombre(int idUsuario) throws NegocioException {
        try {
            return usuarioDAO.obtenerNombre(idUsuario);
        } catch (PersistenciaException | SQLException e) {
            throw new NegocioException("Error al obtener nombre del usuario: " + e.getMessage(), e);
        }
    }


    public int obtenerIdUsuario(String nombreUsuario) throws NegocioException {
        try {
            return usuarioDAO.obtenerIdUsuario(nombreUsuario);
        } catch (PersistenciaException | SQLException e) {
            throw new NegocioException("Error al obtener ID del usuario: " + e.getMessage(), e);
        }
    }


    public Usuario consultarUsuarioPorId(int idUsuario) throws NegocioException {
        try {
            return usuarioDAO.consultarUsuarioPorId(idUsuario);
        } catch (PersistenciaException | SQLException e) {
            throw new NegocioException("Error al consultar usuario por ID: " + e.getMessage(), e);
        }
    }
}

