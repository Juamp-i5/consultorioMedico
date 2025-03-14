/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import conexion.Conexion;
import entidades.DireccionPaciente;
import entidades.Paciente;
import excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.Password;

/**
 *
 * @author Jp
 */
public class PacienteDAO implements IPacienteDAO {
    /**
     * Agrega un nuevo paciente al sistema.
     *
     * @param paciente El objeto {@link Paciente} que contiene los datos del
     * paciente a agregar.
     * @return {@code true} si el paciente se agregó exitosamente, {@code false}
     * si hubo un error.
     * @throws PersistenciaException Si ocurre un error al agregar el paciente.
     */
    @Override
    public boolean agregarPaciente(Paciente paciente) throws PersistenciaException {
        String queryUsuario = "INSERT INTO consultas_medicas.usuario (nombre, apellido_paterno, apellido_materno, contrasenia) VALUES (?, ?, ?, ?);";
        String queryPaciente = "INSERT INTO consultas_medicas.paciente (id_paciente, fecha_nacimiento, telefono, correo_electronico) VALUES (?, ?, ?, ?);";
        String queryDireccion = "INSERT INTO consultas_medicas.direccion_paciente (id_direccion_paciente, calle, numero, colonia, codigo_postal) VALUES (?, ?, ?, ?, ?);";

        try (Connection conexion = Conexion.getConnection()) {
            // Desactivar autoCommit para iniciar transacción
            conexion.setAutoCommit(false);

            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, paciente.getNombre());
                psUsuario.setString(2, paciente.getApellidoPaterno());
                psUsuario.setString(3, paciente.getApellidoMaterno());
                psUsuario.setString(4, paciente.getContrasenia());

                //Comprobamos si se inserto el usuario
                int affectedRowsUsuario = psUsuario.executeUpdate();
                if (affectedRowsUsuario == 0) {
                    throw new SQLException("No se pudo insertar el usuario");
                }

                //Guardamos el idUsuario generado para luego insertar el paciente y la direccion
                ResultSet generatedKeys = psUsuario.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    try (PreparedStatement psPaciente = conexion.prepareStatement(queryPaciente)) {
                        psPaciente.setInt(1, idUsuario);
                        psPaciente.setObject(2, paciente.getFechaNacimiento());
                        psPaciente.setString(3, paciente.getTelefono());
                        psPaciente.setString(4, paciente.getCorreoElectronico());

                        //Comprobamos si se inserto el paciente
                        int affectedRowsPaciente = psPaciente.executeUpdate();
                        if (affectedRowsPaciente == 0) {
                            throw new SQLException("No se pudo insertar el paciente");
                        }

                        try (PreparedStatement psDireccion = conexion.prepareStatement(queryDireccion)) {
                            psDireccion.setInt(1, idUsuario);
                            psDireccion.setString(2, paciente.getDireccion().getCalle());
                            psDireccion.setString(3, paciente.getDireccion().getNumero());
                            psDireccion.setString(4, paciente.getDireccion().getColonia());
                            psDireccion.setString(5, paciente.getDireccion().getCodigoPostal());

                            //Comprobamos si se inserto la direccion del paciente
                            int affectedRowsDireccion = psDireccion.executeUpdate();
                            if (affectedRowsDireccion == 0) {
                                throw new SQLException("No se pudo insertar la dirección del paciente");
                            }
                        }

                        // Confirmar transacción si todo está bien
                        conexion.commit();
                        return true;

                    }

                } else {
                    throw new PersistenciaException("No se pudo obtener el ID del usuario");
                }
            } catch (SQLException e) {
                // Revertir transacción en caso de error osea que se deshace de todos los cambios realizados en los datos
                conexion.rollback();
                throw new PersistenciaException("Error al agregarPaciente: " + e.getMessage());
            } finally {
                // Restaurar el autoCommit por seguridad(dejar todo en orden para que no haya problemas despues, que todo se guarde automaticamente como debe de ser)
                conexion.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al conectar con la base de datos: " + e.getMessage());
        }

    }

    /**
     * Valida el inicio de sesión de un paciente en el sistema.
     *
     * @param paciente El objeto {@link Paciente} con las credenciales a
     * validar.
     * @return El ID del paciente si las credenciales son correctas, o un valor
     * negativo si no son válidas.
     * @throws PersistenciaException Si ocurre un error al validar las
     * credenciales.
     */
    @Override
    public int validarInicioSesion(Paciente paciente) throws PersistenciaException {
        if (!existeCorreo(paciente.getCorreoElectronico())) {
            throw new PersistenciaException("El correo no existe");
        }

        String query = "Select id_usuario, contrasenia FROM VistaInicioSesion WHERE correo = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, paciente.getCorreoElectronico());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (Password.verifyPassword(paciente.getContrasenia(), rs.getString("contrasenia"))) {
                        return rs.getInt("id_usuario");
                    } else {
                        throw new PersistenciaException("Contraseña incorrecta");
                    }
                } else {
                    throw new PersistenciaException("El correo no existe");
                }
            }
        } catch (Exception e) {
            throw new PersistenciaException("Error al validar inicio de sesion");
        }
    }
    /**
     * Consulta los datos de un paciente dado su ID.
     *
     * @param id El ID del paciente que se desea consultar.
     * @return Un objeto {@link Paciente} con los datos del paciente.
     * @throws PersistenciaException Si no se encuentra el paciente con el ID
     * proporcionado o si ocurre un error en la consulta.
     */
    @Override
    public Paciente consultarPaciente(int id) throws PersistenciaException {
        if (!existePaciente(id)) {
            throw new PersistenciaException("No existe el paciente con el id #" + id);
        }

        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico, d.calle, d.numero, d.colonia, d.codigo_postal FROM consultas_medicas.usuario AS u JOIN consultas_medicas.paciente AS p ON u.id_usuario = p.id_paciente JOIN consultas_medicas.direccion_paciente AS d ON p.id_paciente = d.id_direccion_paciente WHERE p.id_paciente = ?";
        Paciente paciente = null;

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente(
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("telefono"),
                            rs.getString("correo_electronico"),
                            new DireccionPaciente(rs.getString("calle"), rs.getString("numero"), rs.getString("colonia"), rs.getString("codigo_postal")),
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("contrasenia")
                    );
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultarPaciente: " + e.getMessage());
        }

        return paciente;
    }
//
//    @Override
//    public List<Paciente> consultarPacientes() throws PersistenciaException {
//        String query = "SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.contrasenia, p.id_paciente, p.fecha_nacimiento, p.telefono, p.correo_electronico "
//                + "FROM consultas_medicas.usuario u "
//                + "JOIN consultas_medicas.paciente p ON u.id_usuario = p.id_paciente;";
//        List<Paciente> pacientes = new ArrayList<>();
//
//        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                Paciente paciente = new Paciente(
//                        rs.getInt("id_paciente"),
//                        rs.getDate("fecha_nacimiento").toLocalDate(),
//                        rs.getString("telefono"),
//                        rs.getString("correo_electronico"),
//                        rs.getInt("id_usuario"),
//                        rs.getString("nombre"),
//                        rs.getString("apellido_paterno"),
//                        rs.getString("apellido_materno"),
//                        rs.getString("contrasenia")
//                );
//                pacientes.add(paciente);
//            }
//            return pacientes;
//        } catch (SQLException e) {
//            throw new PersistenciaException("Error al consultarPacientes: " + e.getMessage());
//        }
//    }
//

    /**
     * Actualiza los datos de un paciente en el sistema.
     *
     * @param paciente El objeto {@link Paciente} con los nuevos datos que se
     * desean actualizar.
     * @return {@code true} si la actualización fue exitosa, {@code false} si
     * hubo un error.
     * @throws PersistenciaException Si ocurre un error al actualizar los datos
     * del paciente.
     */
    public boolean actualizarPaciente(Paciente paciente) throws PersistenciaException {

        if (!existePaciente(paciente.getIdUsuario())) {
            throw new PersistenciaException("No existe el paciente con el id #" + paciente.getIdUsuario());
        }

        String queryUsuario = "UPDATE consultas_medicas.usuario SET nombre = ?, apellido_paterno = ?, apellido_materno = ? WHERE id_usuario = ?;";
        String queryPaciente = "UPDATE consultas_medicas.paciente SET fecha_nacimiento = ?, telefono = ?, correo_electronico = ? WHERE id_paciente = ?;";
        String queryDireccion = "UPDATE consultas_medicas.direccion_Paciente SET calle = ?, numero = ?, colonia = ?, codigo_postal = ? WHERE id_direccion_paciente = ?";

        try (Connection conexion = Conexion.getConnection()) {
            conexion.setAutoCommit(false); // Desactivar autoCommit para iniciar transacción
            //ACTUALIZAR USURIO
            try (PreparedStatement psUsuario = conexion.prepareStatement(queryUsuario)) {
                psUsuario.setString(1, paciente.getNombre());
                psUsuario.setString(2, paciente.getApellidoPaterno());
                psUsuario.setString(3, paciente.getApellidoMaterno());
                psUsuario.setInt(4, paciente.getIdUsuario());

                int affectedRowsUsuario = psUsuario.executeUpdate();
                if (affectedRowsUsuario == 0) {
                    return false;
                }
            } catch (SQLException e) {
                conexion.rollback(); // Revertir transacción en caso de error
                throw new PersistenciaException("Error al actualizar Usuario", e);
            }

            //ACTUALIZAR PACIENTE
            try (PreparedStatement psPaciente = conexion.prepareStatement(queryPaciente)) {
                psPaciente.setObject(1, paciente.getFechaNacimiento());
                psPaciente.setString(2, paciente.getTelefono());
                psPaciente.setString(3, paciente.getCorreoElectronico());
                psPaciente.setInt(4, paciente.getIdUsuario());
                int affectedRowsPaciente = psPaciente.executeUpdate();
                if (affectedRowsPaciente == 0) {
                    return false;
                }
            } catch (SQLException e) {
                conexion.rollback(); // Revertir transacción en caso de error
                throw new PersistenciaException("Error al actualizar Paciente", e);
            }

            //ACTUALIZAR DIRECCION
            try (PreparedStatement ps = conexion.prepareStatement(queryDireccion)) {
                ps.setString(1, paciente.getDireccion().getCalle());
                ps.setString(2, paciente.getDireccion().getNumero());
                ps.setString(3, paciente.getDireccion().getColonia());
                ps.setString(4, paciente.getDireccion().getCodigoPostal());
                ps.setInt(5, paciente.getIdUsuario());

                int affectedRowsDireccion = ps.executeUpdate();
                if (affectedRowsDireccion == 0) {
                    return false;
                } else {
                    conexion.commit(); // guardar los cambios
                }
            } catch (SQLException e) {
                conexion.rollback(); // Revertir transacción en caso de error
                throw new PersistenciaException("Error al actualizar direccion", e);
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizarPaciente: " + e.getMessage());
        }

        return true;
    }

    /**
     * Verifica si existe un paciente dado su ID.
     *
     * @param id El ID del paciente que se desea verificar.
     * @return {@code true} si el paciente existe, {@code false} si no existe.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del paciente.
     */
    @Override
    public boolean existePaciente(int id) throws PersistenciaException {
        String query = "SELECT COUNT(*) AS total FROM consultas_medicas.paciente WHERE id_paciente = ?;";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error en existePaciente: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si existe un paciente con el correo electrónico dado.
     *
     * @param correo El correo electrónico del paciente que se desea verificar.
     * @return {@code true} si existe un paciente con ese correo, {@code false}
     * si no.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del correo.
     */
    @Override
    public boolean existeCorreo(String correo) throws PersistenciaException {
        String query = "SELECT COUNT(*) FROM paciente WHERE correo_electronico = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar la existencia del correo", e);
        }

        return false;
    }

    /**
     * Verifica si existe un paciente con el número de celular dado.
     *
     * @param celular El número de celular del paciente que se desea verificar.
     * @return {@code true} si existe un paciente con ese número de celular,
     * {@code false} si no.
     * @throws PersistenciaException Si ocurre un error al verificar la
     * existencia del celular.
     */
    @Override
    public boolean existeCelular(String celular) throws PersistenciaException {
        String query = "SELECT COUNT(*) FROM paciente WHERE telefono = ?";

        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, celular);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al verificar la existencia del celular", e);
        }

        return false;
    }
    
    
    public boolean filtroConsultasPaciente(int idPaciente, String especialidad, String fechaInicio, String fechaFin) {
        String query = "SELECT * FROM consulta as con " +
                       "JOIN cita as cit ON con.id_cita = cit.id_cita " +
                       "JOIN medico as m ON m.id_medico = cit.id_medico " +
                       "WHERE cit.id_paciente = ? AND m.especialidad = ? " +
                       "AND cit.fecha_hora BETWEEN ? AND ?;";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPaciente);
            pstmt.setString(2, especialidad);
            pstmt.setString(3, fechaInicio);
            pstmt.setString(4, fechaFin);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; 
            }
        } catch (SQLException e) {
        }
        return false; // No se encontraron consultas
    }
}
