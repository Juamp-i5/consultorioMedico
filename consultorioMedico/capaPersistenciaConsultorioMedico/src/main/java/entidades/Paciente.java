package entidades;

import java.time.LocalDate;

/**
 * Representa un paciente en el sistema. Extiende la clase Usuario e incluye
 * información adicional como fecha de nacimiento, teléfono y correo
 * electrónico.
 *
 * @author Jp
 */
public class Paciente extends Usuario {

    private int idPaciente;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correoElectronico;

    /**
     * Constructor por defecto.
     */
    public Paciente() {
    }

    /**
     * Constructor para crear un objeto Paciente con un ID específico.
     *
     * @param idPaciente Identificador único del paciente.
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param telefono Número de teléfono del paciente.
     * @param correoElectronico Correo electrónico del paciente.
     * @param idUsuario Identificador del usuario asociado al paciente.
     * @param nombre Nombre del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param contrasenia Contraseña del usuario asociado al paciente.
     */
    public Paciente(int idPaciente, LocalDate fechaNacimiento, String telefono, String correoElectronico, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.idPaciente = idPaciente;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Constructor para crear un objeto Paciente sin un ID específico
     *
     * @param fechaNacimiento Fecha de nacimiento del paciente.
     * @param telefono Número de teléfono del paciente.
     * @param correoElectronico Correo electrónico del paciente.
     * @param nombre Nombre del paciente.
     * @param apellidoPaterno Apellido paterno del paciente.
     * @param apellidoMaterno Apellido materno del paciente.
     * @param contrasenia Contraseña del usuario asociado al paciente.
     */
    public Paciente(LocalDate fechaNacimiento, String telefono, String correoElectronico, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Obtiene el identificador del paciente.
     *
     * @return ID del paciente.
     */
    public int getIdPaciente() {
        return idPaciente;
    }

    /**
     * Establece el identificador del paciente.
     *
     * @param idPaciente Nuevo ID del paciente.
     */
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Obtiene la fecha de nacimiento del paciente.
     *
     * @return Fecha de nacimiento del paciente.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del paciente.
     *
     * @param fechaNacimiento Nueva fecha de nacimiento del paciente.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el número de teléfono del paciente.
     *
     * @return Número de teléfono del paciente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del paciente.
     *
     * @param telefono Nuevo número de teléfono del paciente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del paciente.
     *
     * @return Correo electrónico del paciente.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el correo electrónico del paciente.
     *
     * @param correoElectronico Nuevo correo electrónico del paciente.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Retorna una representación en cadena del objeto Paciente.
     *
     * @return Cadena con la información del paciente.
     */
    @Override
    public String toString() {
        return "Paciente{" + "idPaciente=" + idPaciente + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + '}';
    }
}
