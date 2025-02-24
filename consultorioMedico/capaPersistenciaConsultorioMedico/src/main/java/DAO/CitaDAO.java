package DAO;

import conexion.Conexion;
import entidades.Cita;
import excepciones.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author janot
 */
public class CitaDAO implements ICita{
    private static final Logger logger = Logger.getLogger(CitaDAO.class.getName());
    
    /**
     * Agrega una cita nueva a la base de datos
     * 
     * @param cita El objeto cita del cual sacaremos los valores de sus atributos
     * @param idUsuarioPaciente El id del paciente que agenda la cita
     * @param idUsuarioMedico El id del medico el cual atendera la cita
     * 
     * @return True si se logra agregar la cita y False en caso contrario
     * @throws PersistenciaException Si hay algun error al momento de agregar 
     * la cita
     */
    @Override
    public boolean agendarCita(Cita cita) throws PersistenciaException {
        Timestamp timestamp = Timestamp.valueOf(cita.getFechaHora());
        String consultaInsertarCita = "{CALL InsertarCita(?,?,?,?,?)}";
        //CONECTAR CON LA BASE DE DATOS
        try(Connection conexion = Conexion.getConnection()) {
            //EJECUTAR EL PROCEDIMIENTO ALMACENADO
            try(CallableStatement cs = conexion.prepareCall(consultaInsertarCita)) {
                cs.setInt(1, cita.getIdPaciente());
                cs.setInt(2,cita.getIdMedico());
                cs.setTimestamp(3,timestamp);
                cs.setString(4,cita.getTipo());
                cs.registerOutParameter(5, Types.BOOLEAN); 
                //EJECUTO EL PROCEDIMIENTO ALMACENADO
                cs.execute();
                //GUARDO LA SALIDA
                boolean seAgendo = cs.getBoolean(5);

                if (!seAgendo) {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new PersistenciaException("Error al ejecutar la consultaSQL",e);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenciaException("Error al conectarse con la bd", e);
        }
        
        return true;
    }



    /**
     * Cancela una cita, cambiando su estado a cancelado en la bd
     * 
     * @param idCita de la cita que se desea eliminar
     * @return True si se logra hacer la cancelacion y False en caso contrario
     * @throws PersistenciaException Si hay algun error al momento de cancelar 
     * la cita
     */
    @Override
    public boolean cancelarCita(int idCita) throws PersistenciaException {
        //1. CREAR LA CONSULTA SQL PARA MODIFICAR EL ESTADO DE LA CONSULTA A CANCELADA
        String consultaSQL = "UPDATE consultas_medicas.cita SET estado = 'Cancelada' WHERE id_cita = ?";
        
        //2. CONECTARE A LA BASE DE DATOS
        try (Connection conexion = Conexion.getConnection()){
            //3. EJECUTAR LA CONSULTA
            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)){
                ps.setInt(1, idCita);
                
                int filasAfectadas = ps.executeUpdate();
                if(filasAfectadas > 0){
                    logger.info("Se Cancelo la cita");
                    return true;
                }
                else{
                    logger.severe("No se pudo cancelar la cita");
                    return false;
                }
                
            } catch (SQLException e) {
                throw new PersistenciaException("Error al cancelar la cita", e);
            }
            
        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar a la BD", e);
        }
    }

    /**
     * Obtiene todas las citas que estan activas del paciente que tiene ese id
     * 
     * @param idPaciente Id del paciente del que se quiere saber sus citas disponibles
     * @return Una lista con todas las citas activas del paciente
     * @throws PersistenciaException Si no se logra obtener el listado
     */
    @Override
    public List<Cita> obtenerCitasActivasPaciente(int idPaciente) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>(); //Lista en la que se guardaran todas las citas disponibles
        
        //1. DEFINIR LA CONSULTA SQL PARA OBTENER TODA LA TABLA CON LAS CITAS
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_paciente= ?";
        
        //2. HACER LA CONEXION CON LA BASE DE DATOS
        try (Connection conexion = Conexion.getConnection()){
            
            //3. EJECUTAR LA CONSULTA SQL
            try(PreparedStatement ps = conexion.prepareStatement(consultaSQL)){
                ps.setInt(1, idPaciente);//SE LLENA EL PARAMETRO DE LA CONSULTA CON EL ID DEL PACIENTE
                ResultSet rs = ps.executeQuery(); //EJECUTA LA CONSULTA Y OBTIENE EL RESULTADO DE LA CONSULTA
                
                while(rs.next()){
                    Cita cita = new Cita(
                            rs.getInt("id_cita"),
                              rs.getString("tipo"),
                             rs.getString("folio"),
                          rs.getTimestamp("fecha_hora").toLocalDateTime(), 
                            rs.getString("estado"),
                         rs.getInt("id_paciente"),
                           rs.getInt("id_medico"));
                    
                    listaCitasActivas.add(cita);
                }
                
                return listaCitasActivas;
                
            }catch (SQLException e) {
                throw new PersistenciaException("Error al consultar las citas", e);
            }   
            
        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar a la BD", e);
        }
        
    }
    
    public List<Cita> obtenerCitasActivasMedico(int idMedico) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>();
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_medico = ? ";

        try (Connection conexion = Conexion.getConnection()) {

            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idMedico); 
                ResultSet rs = ps.executeQuery(); 

                while (rs.next()) {
                    Cita cita = new Cita(
                            rs.getInt("id_cita"),
                            rs.getString("tipo"),
                            rs.getString("folio"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_medico")
                    );

                    listaCitasActivas.add(cita);
                }

                return listaCitasActivas;

            } catch (SQLException e) {
                throw new PersistenciaException("Error al consultar las citas activas del médico", e);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar a la base de datos", e);
        }
    }
    
        public List<Cita> obtenerCitasPendientesMedico(int idMedico) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>();
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_medico = ? AND estado = 'No atendida'";

        try (Connection conexion = Conexion.getConnection()) {

            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idMedico); 
                ResultSet rs = ps.executeQuery(); 

                while (rs.next()) {
                    Cita cita = new Cita(
                            rs.getInt("id_cita"),
                            rs.getString("tipo"),
                            rs.getString("folio"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_medico")
                    );

                    listaCitasActivas.add(cita);
                }

                return listaCitasActivas;

            } catch (SQLException e) {
                throw new PersistenciaException("Error al consultar las citas activas del médico", e);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar a la base de datos", e);
        }
    }
    
}

