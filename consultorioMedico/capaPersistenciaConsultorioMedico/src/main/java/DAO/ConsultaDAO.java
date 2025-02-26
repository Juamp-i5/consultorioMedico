package DAO;

import DAO.IConsultaDAO;
import conexion.Conexion;
import entidades.Cita;
import entidades.Consulta;
import entidades.HistorialConsultaMedico;
import entidades.Paciente;
import excepciones.PersistenciaException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsultaDAO implements IConsultaDAO {

    private static final Logger logger = Logger.getLogger(ConsultaDAO.class.getName());

    /**
     * Obtiene el estado de una consulta específica a partir de su ID.
     *
     * @param idConsulta El identificador único de la consulta.
     * @return El estado de la consulta como una cadena de texto. Retorna {@code null} si no se encuentra la consulta.
     * @throws PersistenciaException Si ocurre un error al conectarse a la base de datos o ejecutar la consulta.
     */
    public String getEstado(int idConsulta) throws PersistenciaException {
        String estado = null;
        String sql = "SELECT estado FROM consulta WHERE id = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                estado = rs.getString("estado");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener estado de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener estado de la consulta", e);
        }
        return estado;
    }

     /**
     * Obtiene el diagnóstico de una consulta específica a partir de su ID.
     *
     * @param idConsulta El identificador único de la consulta.
     * @return El diagnóstico de la consulta como una cadena de texto. Retorna {@code null} si no se encuentra la consulta.
     * @throws PersistenciaException Si ocurre un error al conectarse a la base de datos o ejecutar la consulta.
     */
    public String getDiagnostico(int idConsulta) throws PersistenciaException {
        String diagnostico = null;
        String sql = "SELECT diagnostico FROM consulta WHERE id = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                diagnostico = rs.getString("diagnostico");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener diagnóstico de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener diagnóstico de la consulta", e);
        }
        return diagnostico;
    }

    /**
     * Obtiene el tratamiento de una consulta específica a partir de su ID.
     *
     * @param idConsulta El identificador único de la consulta.
     * @return El tratamiento de la consulta como una cadena de texto. Retorna {@code null} si no se encuentra la consulta.
     * @throws PersistenciaException Si ocurre un error al conectarse a la base de datos o ejecutar la consulta.
     */
    public String getTratamiento(int idConsulta) throws PersistenciaException {
        String tratamiento = null;
        String sql = "SELECT tratamiento FROM consulta WHERE id = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tratamiento = rs.getString("tratamiento");
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener tratamiento de la consulta: " + e.getMessage());
            throw new PersistenciaException("Error al obtener tratamiento de la consulta", e);
        }
        return tratamiento;
    }

    /**
     * Obtiene la lista de consultas atendidas de un paciente específico a partir de su ID.
     *
     * @param idPaciente El identificador único del paciente.
     * @return Una lista de objetos {@link Consulta} que representan las consultas atendidas del paciente.
     *         Si el paciente no tiene consultas atendidas, se retorna una lista vacía.
     * @throws PersistenciaException Si ocurre un error al conectarse a la base de datos o ejecutar la consulta.
     */
    public List<Consulta> obtenerConsultasPaciente(int idPaciente) throws PersistenciaException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta as con join cita as cit on con.id_cita = cit.id_cita where id_paciente = ? and con.estado = \"Atendida\";";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setDiagnostico(rs.getString("diagnostico"));
                consulta.setTratamiento(rs.getString("tratamiento"));
                consulta.setEstado(rs.getString("estado"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener consultas del paciente: " + e.getMessage());
            throw new PersistenciaException("Error al obtener consultas del paciente", e);
        }
        return consultas;
    }

    /**
     * Obtiene una lista de consultas atendidas de un paciente filtradas por especialidad y un rango de fechas.
     *
     * @param idPaciente     El identificador único del paciente.
     * @param especialidad   La especialidad médica de la consulta.
     * @param fechaInicioStr La fecha de inicio del rango en formato "yyyy-MM-dd HH:mm:ss".
     * @param fechaFinStr    La fecha de fin del rango en formato "yyyy-MM-dd HH:mm:ss".
     * @return Una lista de objetos {@link Consulta} que cumplen con los filtros especificados.
     *         Si no hay consultas que coincidan, se retorna una lista vacía.
     * @throws PersistenciaException Si ocurre un error al convertir las fechas, conectarse a la base de datos o ejecutar la consulta.
     */
    public List<Consulta> obtenerConsultasFiltradas(int idPaciente, String especialidad, String fechaInicioStr, String fechaFinStr) throws PersistenciaException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta as con  join cita as cit on con.id_cita = cit.id_cita  join medico as m on m.id_medico = cit.id_medico where id_paciente = ? and con.estado = \"Atendida\"  and especialidad = ? and fecha_hora between ? and ? ";

        // Convertir Strings a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;

        try {
            fechaInicio = LocalDateTime.parse(fechaInicioStr.trim(), formatter);
            fechaFin = LocalDateTime.parse(fechaFinStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new PersistenciaException("Error al convertir las fechas", e);
        }

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.setString(2, especialidad);
            ps.setObject(3, fechaInicio);
            ps.setObject(4, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setDiagnostico(rs.getString("diagnostico"));
                    consulta.setTratamiento(rs.getString("tratamiento"));
                    consulta.setEstado(rs.getString("estado"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener consultas del paciente: " + e.getMessage());
            throw new PersistenciaException("Error al obtener consultas del paciente", e);
        }
        return consultas;
    }

    public String obtenerEspecialidad(int idConsulta) throws PersistenciaException {
        String sql = "SELECT m.especialidad "
                + "FROM consulta con "
                + "JOIN cita cit ON con.id_cita = cit.id_cita "
                + "JOIN medico m ON cit.id_medico = m.id_medico "
                + "WHERE con.id_consulta = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("especialidad");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener la especialidad: " + e.getMessage());
            throw new PersistenciaException("Error al obtener la especialidad de la consulta", e);
        }
        return ""; // Retorna cadena vacía si no encuentra información
    }
    
    /**
     * Obtiene la especialidad médica asociada a una consulta específica.
     *
     * @param idConsulta El identificador único de la consulta.
     * @return La especialidad del médico que atendió la consulta.
     *         Si no se encuentra información, retorna una cadena vacía.
     * @throws PersistenciaException Si ocurre un error al conectarse a la base de datos o ejecutar la consulta.
     */
    public String obtenerNombreMedico(int idConsulta) throws PersistenciaException {
        String sql = "SELECT u.nombre  FROM usuario as u JOIN medico as m ON m.id_medico = u.id_usuario JOIN cita cit ON cit.id_medico = m.id_medico  JOIN consulta con ON con.id_cita = cit.id_cita WHERE con.id_consulta = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConsulta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            logger.severe("Error al obtener el nombre del médico: " + e.getMessage());
            throw new PersistenciaException("Error al obtener el nombre del médico de la consulta", e);
        }
        return ""; // Retorna cadena vacía si no encuentra información
    }

    /**
     * Obtiene el tipo de cita asociada a una consulta específica.
     *
     * @param idConsulta El identificador único de la consulta.
     * @return El tipo de cita correspondiente a la consulta. Si no se encuentra información, retorna {@code null}.
     */
    public String getTipoCita(int idConsulta) {
        String sql = "SELECT c.tipo FROM Cita c "
                + "JOIN Consulta co ON c.id_cita = co.id_cita "
                + "WHERE co.id_consulta = ?";
        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tipo");
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Obtiene la fecha y hora de la cita asociada a una consulta.
     *
     * @param idConsulta Identificador de la consulta.
     * @return Fecha y hora de la cita como String, o null si no se encuentra.
     */
    public String getFechaHora(int idConsulta) {
        String sql = "SELECT c.fecha_hora FROM Cita c "
                + "JOIN Consulta co ON c.id_cita = co.id_cita "
                + "WHERE co.id_consulta = ?";
        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("fecha_hora");
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * Obtiene una lista del historial de consultas realizadas por un médico específico.
     *
     * @param idMedico El identificador único del médico.
     * @return Una lista de objetos {@code HistorialConsultaMedico} con la información de las consultas realizadas por el médico.
     * @throws PersistenciaException Si ocurre un error al obtener las consultas del médico.
     */
    public List<HistorialConsultaMedico> consultasMedico(int idMedico) throws PersistenciaException {
        List<HistorialConsultaMedico> consultas = new ArrayList<>();

        String query = "{CALL ObtenerConsultasMEdico(?)}";

        try (Connection conexion = Conexion.getConnection(); CallableStatement stmt = conexion.prepareCall(query)) {

            stmt.setInt(1, idMedico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistorialConsultaMedico consulta = new HistorialConsultaMedico(
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getString("tipo"),
                        rs.getString("estado"),
                        rs.getString("nombre_paciente"),
                        rs.getString("apellido_paterno"),
                        rs.getString("apellido_materno"),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento"),
                        rs.getInt("id_paciente")
                );
                consultas.add(consulta);
            }

            return consultas;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al obtener las consultas del medico: " + e.getMessage());
        }
    }

    /**
     * Atiende una cita médica y crea una nueva consulta en la base de datos.
     * Este método llama a un procedimiento almacenado que registra el diagnóstico, tratamiento
     * y notas médicas de la consulta, y devuelve el identificador de la nueva consulta creada.
     *
     * @param idCita       El identificador único de la cita a atender.
     * @param diagnostico  El diagnóstico médico de la consulta.
     * @param tratamiento  El tratamiento prescrito al paciente.
     * @param notasMedicas Notas adicionales del médico sobre la consulta.
     * @return El identificador único de la consulta creada. Retorna -1 en caso de error.
     * @throws PersistenciaException Si ocurre un error al procesar la consulta en la base de datos.
     */
    public int atenderCitaYCrearConsulta(int idCita, String diagnostico, String tratamiento, String notasMedicas)
            throws PersistenciaException {
        int idConsulta = -1; // Valor por defecto en caso de error

        // Consulta SQL para llamar al procedimiento almacenado
        String sql = "{call AtenderCitaYCrearConsulta(?, ?, ?, ?, ?)}";

        try (Connection conexion = Conexion.getConnection(); // Obtener la conexión a la BD
                 CallableStatement cs = conexion.prepareCall(sql)) {

            // Configurar los parámetros de entrada
            cs.setInt(1, idCita);               // p_id_cita
            cs.setString(2, diagnostico);        // p_diagnostico
            cs.setString(3, tratamiento);       // p_tratamiento
            cs.setString(4, notasMedicas);      // p_notas_medicas

            // Configurar el parámetro de salida
            cs.registerOutParameter(5, Types.INTEGER); // p_id_consulta

            // Ejecutar el procedimiento almacenado
            cs.execute();

            // Obtener el valor del parámetro de salida
            idConsulta = cs.getInt(5);

        } catch (SQLException ex) {
            // Lanzar una excepción personalizada con el mensaje de error
            throw new PersistenciaException("Error al atender la cita y crear la consulta: " + ex.getMessage());
        }

        return idConsulta;
    }
}
