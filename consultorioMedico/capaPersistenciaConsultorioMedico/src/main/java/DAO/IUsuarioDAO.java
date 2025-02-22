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
    public String obtenerNombre(int idUsuario) throws PersistenciaException;
}
