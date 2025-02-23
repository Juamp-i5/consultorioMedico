/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.Medico;
import excepciones.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Password;

/**
 *
 * @author Jp
 */
public class MedicoDAO implements IMedicoDAO {

    @Override
    public boolean agregarMedico(Medico medico) throws PersistenciaException {
        String queryUsuario = "INSERT INTO consultas_medicas.usuario (nombre, apellido_paterno, apellido_materno, contrasenia) VALUES (?, ?, ?, ?);";
        String queryMedico = "INSERT INTO consultas_medicas.medico (id_medico, especialidad, cedula_profesional, estado) VALUES (?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection()) {
            conexion.setAutoCommit(false);

            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, medico.getNombre());
                psUsuario.setString(2, medico.getApellidoPaterno());
                psUsuario.setString(3, medico.getApellidoMaterno());
                psUsuario.setString(4, medico.getContrasenia());

                int affectedRowsUsuario = psUsuario.executeUpdate();
                if (affectedRowsUsuario == 0) {
                    throw new SQLException("No se pudo insertar el usuario.");
                }

                try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idUsuario = generatedKeys.getInt(1);

                        try (PreparedStatement psMedico = conexion.prepareStatement(queryMedico)) {
                            psMedico.setInt(1, idUsuario);
                            psMedico.setString(2, medico.getEspecialidad());
                            psMedico.setString(3, medico.getCedulaProfesional());
                            psMedico.setString(4, medico.getEstado());

                            int affectedRowsMedico = psMedico.executeUpdate();
                            if (affectedRowsMedico == 0) {
                                throw new SQLException("No se pudo insertar el médico.");
                            }

                            conexion.commit(); 
                            return true;
                        }
                    } else {
                        throw new PersistenciaException("No se pudo obtener el ID del usuario.");
                    }
                }
            } catch (SQLException e) {
                conexion.rollback(); 
                throw new PersistenciaException("Error al agregar médico: " + e.getMessage(), e);
            } finally {
                conexion.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }


    @Override
    public Medico consultarMedico(int idMedico) throws PersistenciaException {
        String query = "SELECT m.id_medico, u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, "
                + "m.especialidad, m.cedula_profesional, m.estado, u.contrasenia "
                + "FROM consultas_medicas.Medico m "
                + "JOIN consultas_medicas.Usuario u ON m.id_medico = u.id_usuario "
                + "WHERE m.id_medico = ?;";

        Medico medico = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {

            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico(
                            rs.getString("especialidad"),
                            rs.getString("cedula_profesional"),
                            rs.getString("estado"),
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("contrasenia")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el medico: " + e.getMessage());
        }

        return medico;
    }

    @Override
    public List<Medico> consultarTodosLosMedicos() throws PersistenciaException {
        String query = "SELECT * FROM consultas_medicas.medico;";
        List<Medico> medicos = new ArrayList<>();

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("especialidad"),
                        rs.getString("cedula_profesional"),
                        rs.getString("estado")
                );
                medicos.add(medico);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarTodosLosMedicos: " + e.getMessage());
        }

        return medicos;
    }

    @Override
    public boolean actualizarMedico(Medico medico) throws PersistenciaException {
        String query = "UPDATE consultas_medicas.medico SET especialidad = ?, cedula_profesional = ?, estado = ? WHERE id_medico = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, medico.getEspecialidad());
            ps.setString(2, medico.getCedulaProfesional());
            ps.setString(3, medico.getEstado());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarMedico: " + e.getMessage());
        }
    }

    @Override
    public boolean existeMedico(int idMedico) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.medico WHERE id_medico = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idMedico);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existeMedico: " + e.getMessage());
        }

        return false;
    }

    @Override
    public int validarInicioSesion(Medico medico) throws PersistenciaException {
        if (!existeCedula(medico.getCedulaProfesional())) {
            throw new PersistenciaException("No existe la cedula");
        }
        
        String query = "SELECT id_usuario, contrasenia from vistainiciosesion WHERE cedula = ?";
        
        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)){
            ps.setString(1, medico.getCedulaProfesional());
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    if (Password.verifyPassword(medico.getContrasenia(), rs.getString("contrasenia"))) {
                        return rs.getInt("id_usuario");
                    } else {
                        throw new PersistenciaException("Contraseña incorrecta");
                    }
                }
                throw new PersistenciaException("Error al verificar cedula");
            }
            
        } catch (SQLException e) {
            throw new PersistenciaException("Error al validar inicio de sesion medico" + e.getMessage());
        }
    }

    @Override
    public boolean existeCedula(String cedula) throws PersistenciaException {
        String query = "SELECT COUNT(*) FROM VistaInicioSesion WHERE cedula = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar cedula: " + e.getMessage());
        }
    }

    public String obtenerEspecialidad(int idMedico) throws PersistenciaException {
        String consultaSQL = "SELECT especialidad FROM consultas_medicas.medico WHERE id_medico = ?";

        try (Connection conexion = Conexion.getConnection();
             PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
            ps.setInt(1, idMedico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("especialidad");
                } else {
                    throw new PersistenciaException("No se encontró el médico con ID: " + idMedico);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener la especialidad del médico", e);
        }
    }

    @Override
    public List<String> obtenerEspecialidadesMedicos() throws PersistenciaException {
        List<String> listaEspecialidades = new LinkedList<>();
        
        //CONSULTA SQL
        String ConsultaSQL = "SELECT * FROM consultas_medicas.vistaespecialidades";
        
        //CONECTAR CON LA BASE DE DATOS
        try (Connection conexion = Conexion.getConnection()){
            //EJECUTAR LA CONSULTA SQL
            try (PreparedStatement ps = conexion.prepareStatement(ConsultaSQL)){
                ResultSet rs = ps.executeQuery();
                //AGREGAR LOS DATOS OBTENIDOS A LA LISTA
                while(rs.next()){
                    listaEspecialidades.add(rs.getString("especialidad"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new PersistenciaException("Error al conseguir la tabla de especialidades",e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al conectar con la base de datos", e);
        }
        
        return listaEspecialidades;
    }

    @Override
    public List<Medico> obtenerMedicosPorEspecialidadActivos(String especialidad) throws PersistenciaException {
        List<Medico> listaMedicosDisponiblesPorEspecialidad = new LinkedList<>();
        
        String ConsultaSQL = "{CALL FiltrarMedicosPorEspecialidadYQueEsteActivo(?)}";
        
        try (Connection conexion = Conexion.getConnection()) {
            CallableStatement cs = conexion.prepareCall(ConsultaSQL);
            cs.setString(1,especialidad);
            ResultSet rs = cs.executeQuery();
            
            while(rs.next()){
                Medico medico = new Medico(rs.getString("nombre"),
                        rs.getString("cedula_profesional"), 
                        rs.getString("estado"), 
                        rs.getString("nombre"), 
                        rs.getString("apellido_paterno"), 
                        rs.getString("apellido_materno"), 
                        null);
                
                listaMedicosDisponiblesPorEspecialidad.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al obtener los medicos", e);
        }
        
        return listaMedicosDisponiblesPorEspecialidad;
    }


}
