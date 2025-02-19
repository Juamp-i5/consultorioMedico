/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import entidades.Usuario;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Jp
 */
public interface IUsuarioDAO {

    public boolean agregarUsuario(Usuario usuario) throws PersistenciaException;

    public Usuario consultarUsuario(int id) throws PersistenciaException;

    public List<Usuario> consultarUsuarios() throws PersistenciaException;

    public boolean actualizarUsuario(Usuario usuario) throws PersistenciaException;
    
    public boolean existeUsuario(int id) throws PersistenciaException;
}
