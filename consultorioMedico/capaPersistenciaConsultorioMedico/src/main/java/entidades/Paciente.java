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

    private LocalDate fechaNacimiento;
    private String telefono;
    private String correoElectronico;
    private DireccionPaciente direccion;

    /**
     * Constructor por defecto.
     */
    public Paciente() {
    }

    public Paciente(LocalDate fechaNacimiento, String telefono, String correoElectronico, DireccionPaciente direccion, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
    }

    public Paciente(LocalDate fechaNacimiento, String telefono, String correoElectronico, DireccionPaciente direccion, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
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

    public DireccionPaciente getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionPaciente direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Paciente{" + "fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + ", direccion=" + direccion + '}';
    }

}
