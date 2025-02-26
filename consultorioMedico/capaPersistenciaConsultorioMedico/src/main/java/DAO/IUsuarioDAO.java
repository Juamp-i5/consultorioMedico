/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import excepciones.PersistenciaException;

/**
 *
 * @author Admin
 */
public interface IUsuarioDAO {
    /**
     * Obtiene el nombre de un usuario a partir de su identificador único (ID).
     *
     * @param idUsuario El ID del usuario cuyo nombre se desea obtener.
     * @return El nombre del usuario si se encuentra en la base de datos.
     * @throws PersistenciaException Si no se encuentra el usuario o si ocurre un error en la base de datos.
     */
    public String obtenerNombre(int idUsuario) throws PersistenciaException;
}
