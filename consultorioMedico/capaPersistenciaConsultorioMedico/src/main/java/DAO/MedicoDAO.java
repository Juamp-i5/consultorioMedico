package DAO;

import conexion.Conexion;
import entidades.Medico;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Jp
 */
public class MedicoDAO implements IMedicoDAO {
    private static final Logger logger = Logger.getLogger(CitaDAO.class.getName());
    @Override
    public boolean agregarMedico(Medico medico) throws PersistenciaException {
        int idGeneradoDeUsuario = 0; //CUANDO SE AGREGA USUARIO, SE GUARDARA EN ESTE CAMPO SU ID PARA AGREGARLO EN LA BD DE PACIENTE
        
        //1. CONSULTAS SQL
        String consultaUsuario = "INSERT INTO consultas_medicas.usuario (nombre, apellido_paterno, apellido_materno, contrasenia) VALUES (?, ?, ?, ?)";
        String consultaMedico = "INSERT INTO consultas_medicas.medico (id_medico, especialidad, cedula_profesional, estado) VALUES (?, ?, ?, ?);";

        //2. CREANDO LA CONEXION CON LA BD
        try (Connection conexion = Conexion.getConnection();) {
            conexion.setAutoCommit(false); // Desactivar autoCommit para iniciar transacción
            
            //INSERCION DE USUARIO
            try (PreparedStatement ps = conexion.prepareStatement(consultaUsuario,Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, medico.getNombre());
                ps.setString(2, medico.getApellidoPaterno());
                ps.setString(3, medico.getApellidoMaterno());
                ps.setString(4, medico.getContrasenia());
                
                int filasAfectadas = ps.executeUpdate();
                if(filasAfectadas > 0){
                    logger.info("Se ha agregado usuario a la bd");
                    //OBTENEMOS EL ID DEL USUARIO GUARDADO
                    try (ResultSet obtenerIdDelNuevoUsuario = ps.getGeneratedKeys()){
                        if(obtenerIdDelNuevoUsuario.next()){
                            idGeneradoDeUsuario = obtenerIdDelNuevoUsuario.getInt(1);
                        }
                        else{
                            logger.info("No se obtuvo el id del usuario"); 
                        }
                    } catch (SQLException e) {
                        conexion.rollback(); //DESHACE TODAS LAS INSERCIONES
                        throw new PersistenciaException("Error al obtener la id de usuario",e);
                    }

                }
                else{
                    logger.severe("No se ha podido usuario a la bd");
                }
            } catch (SQLException e) {
                 conexion.rollback(); //DESHACE TODAS LAS INSERCIONES
                throw new PersistenciaException("Error al agregar al usuario a la BD",e);
            }
            
            //INSERCION DE MEDICO{
            try (PreparedStatement ps = conexion.prepareStatement(consultaMedico,Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1, idGeneradoDeUsuario);
                ps.setString(2, medico.getEspecialidad());
                ps.setString(3, medico.getCedulaProfesional());
                ps.setString(4,medico.getEstado());
                
                int filasAfectadas = ps.executeUpdate();
                if(filasAfectadas > 0){
                    logger.info("Se ha agregado medico a la bd");
                    conexion.commit();
                    return true;
                }
                else{
                    logger.severe("No se ha podido usuario a la bd");
                    return false;
                }
                
            } catch (SQLException e) {
                 conexion.rollback(); //DESHACE TODAS LAS INSERCIONES
                throw new PersistenciaException("Error al agregar al medico a la BD",e);
            }
            finally{
                conexion.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al agregarMedico: " + e.getMessage(),e);
        }
    }

    @Override
    public Medico consultarMedico(int idMedico) throws PersistenciaException {
        String query = "SELECT m.id_medico, u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, " +
                       "m.especialidad, m.cedula_profesional, m.estado, u.contrasenia " +
                       "FROM consultas_medicas.Medico m " +
                       "JOIN consultas_medicas.Usuario u ON m.id_medico = u.id_usuario " +
                       "WHERE m.id_medico = ?;";

        Medico medico = null;

        try (Connection conexion = Conexion.getConnection(); 
             PreparedStatement ps = conexion.prepareStatement(query)) {

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
}
