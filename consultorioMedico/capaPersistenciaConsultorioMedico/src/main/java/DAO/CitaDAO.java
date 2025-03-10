package DAO;

import conexion.Conexion;
import entidades.Cita;
import excepciones.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import static java.sql.Types.INTEGER;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janot
 */
public class CitaDAO implements ICita {

    private static final Logger logger = Logger.getLogger(CitaDAO.class.getName());

    /**
     * Agrega una cita nueva a la base de datos
     *
     * @param cita El objeto cita del cual sacaremos los valores de sus
     * atributos
     * @param idUsuarioPaciente El id del paciente que agenda la cita
     * @param idUsuarioMedico El id del medico el cual atendera la cita
     *
     * @return True si se logra agregar la cita y False en caso contrario
     * @throws PersistenciaException Si hay algun error al momento de agregar la
     * cita
     */
    @Override
    public boolean agendarCita(Cita cita) throws PersistenciaException {
        Timestamp timestamp = Timestamp.valueOf(cita.getFechaHora());
        String consultaInsertarCita = "{CALL InsertarCita(?,?,?,?,?)}";
        //CONECTAR CON LA BASE DE DATOS
        try (Connection conexion = Conexion.getConnection()) {
            //EJECUTAR EL PROCEDIMIENTO ALMACENADO
            try (CallableStatement cs = conexion.prepareCall(consultaInsertarCita)) {
                cs.setInt(1, cita.getIdPaciente());
                cs.setInt(2, cita.getIdMedico());
                cs.setTimestamp(3, timestamp);
                cs.setString(4, cita.getTipo());
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
                throw new PersistenciaException("Error al ejecutar la consultaSQL", e);
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
        try (Connection conexion = Conexion.getConnection()) {
            //3. EJECUTAR LA CONSULTA
            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idCita);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    logger.info("Se Cancelo la cita");
                    return true;
                } else {
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
     * @param idPaciente Id del paciente del que se quiere saber sus citas
     * disponibles
     * @return Una lista con todas las citas activas del paciente
     * @throws PersistenciaException Si no se logra obtener el listado
     */
    @Override
    public List<Cita> obtenerCitasActivasPaciente(int idPaciente) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>(); //Lista en la que se guardaran todas las citas disponibles

        //1. DEFINIR LA CONSULTA SQL PARA OBTENER TODA LA TABLA CON LAS CITAS
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_paciente = ? AND estado = 'Programado' ORDER BY fecha_hora";

        //2. HACER LA CONEXION CON LA BASE DE DATOS
        try (Connection conexion = Conexion.getConnection()) {

            //3. EJECUTAR LA CONSULTA SQL
            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idPaciente);//SE LLENA EL PARAMETRO DE LA CONSULTA CON EL ID DEL PACIENTE
                ResultSet rs = ps.executeQuery(); //EJECUTA LA CONSULTA Y OBTIENE EL RESULTADO DE LA CONSULTA

                while (rs.next()) {

                    //COMPROBACION SI LAS CITAS YA PASARON DE TIEMPO
                    int idCita = rs.getInt("id_cita");
                    LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                    String tipo = rs.getString("tipo");

                    if (tipo.equals("Emergencia")) {
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(10))) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 10 minutos
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    } else if (tipo.equals("Programada")) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 15 minutos
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(15))) {
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    }

                    Cita cita = new Cita(
                            idCita,
                            tipo,
                            rs.getString("folio"),
                            fechaHora,
                            rs.getString("estado"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_medico"));

                    listaCitasActivas.add(cita);
                }

                return listaCitasActivas;

            } catch (SQLException e) {
                throw new PersistenciaException("Error al consultar las citas", e);
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
    public List<Cita> obtenerCitasActivasMedico(int idMedico) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>();
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_medico = ? AND estado = 'Programado' ORDER BY fecha_hora";

        try (Connection conexion = Conexion.getConnection()) {

            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idMedico);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idCita = rs.getInt("id_cita");
                    LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                    String tipo = rs.getString("tipo");
                    
                    if (tipo.equals("Emergencia")) {
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(10))) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 10 minutos
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    } else if (tipo.equals("Programada")) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 15 minutos
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(15))) {
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    }
                    
                    
                    
                    Cita cita = new Cita(
                            idCita,
                            tipo,
                            rs.getString("folio"),
                            fechaHora,
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

    /**
     * Obtiene la citas pendientes del medico
     * @param idMedico id del medico que se desea obtener las citas pendientes
     * @return Una lista con todas las citas pendientes de los medicos
     * @throws PersistenciaException Cuando hay error al consultar las citas pendientes
     */
    public List<Cita> obtenerCitasPendientesMedico(int idMedico) throws PersistenciaException {
        List<Cita> listaCitasActivas = new LinkedList<>();
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_medico = ? AND estado = 'Programado' ORDER BY fecha_hora";

        try (Connection conexion = Conexion.getConnection()) {

            try (PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
                ps.setInt(1, idMedico);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    
                    //COMPROBACION SI LAS CITAS YA PASARON DE TIEMPO
                    int idCita = rs.getInt("id_cita");
                    LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                    String tipo = rs.getString("tipo");

                    if (tipo.equals("Emergencia")) {
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(10))) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 10 minutos
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    } else if (tipo.equals("Programada")) { // Si la fecha hora actual es mayor a la fechaHora de la cita mas 15 minutos
                        if (LocalDateTime.now().isAfter(fechaHora.plusMinutes(15))) {
                            actualizarEstadoCitaNoAsistido(idCita);
                            continue;
                        }
                    }
                    
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

    /**
     * Obtiene las citas filtrandolas por fecha
     * @param idMedico El id del medico del que se desea obtener las citas
     * @param fechaStr La fecha especifica de las citas
     * @return Lista con las citas filtradas por fecha y id del medico
     * @throws PersistenciaException 
     */
    public List<Cita> obtenerCitasFiltradas(int idMedico, String fechaStr) throws PersistenciaException {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM consultas_medicas.cita WHERE id_medico = ? AND fecha_hora BETWEEN ? AND ?";

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new PersistenciaException("Error al convertir la fecha", e);
        }

        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(LocalTime.MAX);

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            ps.setTimestamp(2, Timestamp.valueOf(inicioDia));
            ps.setTimestamp(3, Timestamp.valueOf(finDia));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita cita = new Cita(
                            rs.getInt("id_cita"),
                            rs.getString("tipo"),
                            rs.getString("folio"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_medico"));
                    citas.add(cita);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener citas filtradas", e);
        }
        return citas;
    }

    /**
     * Inserta una cita de emergencia en la bd
     * @param idPaciente El id del paciente que quiere hacer una cita de mergencia
     * @param especialidad La especialidad del medico, de la cita que se quire hacer
     * @return Folio de la cita
     * @throws PersistenciaException Si no se logra insertar en la bd
     */
    @Override
    public int insertarCitaEmergencia(int idPaciente, String especialidad) throws PersistenciaException {
        String procedure = "{CALL InsertarCitaEmergencia(?, ?, ?)}";

        try (Connection conexion = Conexion.getConnection(); CallableStatement cs = conexion.prepareCall(procedure)) {
            cs.setInt(1, idPaciente);
            cs.setString(2, especialidad);
            cs.registerOutParameter(3, INTEGER);

            cs.execute();

            return cs.getInt(3);

        } catch (SQLException e) {
            throw new PersistenciaException("Error al insertarCitaEmergencia: " + e.getMessage());
        }
    }

    /**
     * Obtiene la cita por el id
     * @param idCita El id de la cita que se desea obtener
     * @return La cita
     * @throws PersistenciaException Si no se logra obtener la cita
     */
    @Override
    public Cita obtenerCita(int idCita) throws PersistenciaException {
        String query = "SELECT id_cita, id_paciente, id_medico, tipo, folio, fecha_hora, estado FROM Cita WHERE id_cita = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cita(
                            idCita,
                            rs.getString("tipo"),
                            rs.getString("folio"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getInt("id_paciente"),
                            rs.getInt("id_medico")
                    );
                } else {
                    throw new PersistenciaException("No existe la cita");
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarCita: " + e.getMessage());
        }

    }

    /**
     * Obtiene todas las citas programadas por un paciente
     * @param idPaciente id del paciente del cual se desea obtenr las citas programadas
     * @return Lista con las citas programadas del paciente
     * @throws PersistenciaException Si no se logra obtnener las citas programdas
     */
    public List<Cita> obtenerCitasProgramadasPaciente(int idPaciente) throws PersistenciaException {
        List<Cita> listaCitasProgramadas = new LinkedList<>();
        String consultaSQL = "SELECT * FROM consultas_medicas.cita WHERE id_paciente = ? AND estado = 'Programado'";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita(
                        rs.getInt("id_cita"),
                        rs.getString("tipo"),
                        rs.getString("folio"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getString("estado"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"));
                listaCitasProgramadas.add(cita);
            }
            return listaCitasProgramadas;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar las citas programadas del paciente", e);
        }
    }

    /**
     * Actualiza el estado de la cita a no asistido
     * @param idCita Id de la cita de la que sdesea actualziar el estado
     * @throws PersistenciaException Si no se logra actualizar el estado
     */
    @Override
    public void actualizarEstadoCitaNoAsistido(int idCita) throws PersistenciaException {
        String consultaSQL = "UPDATE consultas_medicas.cita SET estado = 'No Asistió' WHERE id_cita = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(consultaSQL)) {

            ps.setInt(1, idCita);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new PersistenciaException("No se encontró la cita con ID: " + idCita);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el estado de la cita", e);
        }
    }
}
