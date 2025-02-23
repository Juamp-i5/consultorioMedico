package entidades;

/**
 * Representa un médico en el sistema. Extiende la clase Usuario e incluye
 * información adicional como especialidad, cédula profesional y estado.
 *
 * @author Jp
 */
public class Medico extends Usuario {

    /**
     * Especialidad médica del doctor.
     */
    private String especialidad;

    /**
     * Cédula profesional que certifica al médico.
     */
    private String cedulaProfesional;

    /**
     * Si el doctor esta dado de alta o de baja
     */
    private String estado;

    /**
     * Constructor por defecto.
     */
    public Medico() {
        super();
    }

    /**
     * Constructor que inicializa solo al médico con sus datos básicos.
     *
     * @param idMedico Identificador único del médico.
     * @param especialidad Especialidad médica del doctor.
     * @param cedulaProfesional Cédula profesional del médico.
     * @param estado Estado en el que ejerce el médico.
     */
    public Medico(int idMedico, String especialidad, String cedulaProfesional, String estado) {
        super();
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estado = estado;
    }

    /**
     * Constructor que inicializa un médico con datos personales y de usuario.
     *
     * @param especialidad Especialidad médica del doctor.
     * @param cedulaProfesional Cédula profesional del médico.
     * @param estado Estado en el que ejerce el médico.
     * @param idUsuario Identificador del usuario.
     * @param nombre Nombre del médico.
     * @param apellidoPaterno Apellido paterno del médico.
     * @param apellidoMaterno Apellido materno del médico.
     * @param contrasenia Contraseña del usuario médico.
     */
    public Medico(String especialidad, String cedulaProfesional, String estado, int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(idUsuario, nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estado = estado;
    }

    /**
     * Constructor que inicializa un médico sin ID asignado.
     *
     * @param especialidad Especialidad médica del doctor.
     * @param cedulaProfesional Cédula profesional del médico.
     * @param estado Estado en el que ejerce el médico.
     * @param nombre Nombre del médico.
     * @param apellidoPaterno Apellido paterno del médico.
     * @param apellidoMaterno Apellido materno del médico.
     * @param contrasenia Contraseña del usuario médico.
     */
    public Medico(String especialidad, String cedulaProfesional, String estado, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        super(nombre, apellidoPaterno, apellidoMaterno, contrasenia);
        this.especialidad = especialidad;
        this.cedulaProfesional = cedulaProfesional;
        this.estado = estado;
    }

    /**
     * Obtiene la especialidad del médico.
     *
     * @return Especialidad médica del doctor.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del médico.
     *
     * @param especialidad Nueva especialidad del médico.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene la cédula profesional del médico.
     *
     * @return Cédula profesional del médico.
     */
    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    /**
     * Establece la cédula profesional del médico.
     *
     * @param cedulaProfesional Nueva cédula profesional del médico.
     */
    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    /**
     * Obtiene el estado donde ejerce el médico.
     *
     * @return Estado donde ejerce el médico.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado donde ejerce el médico.
     *
     * @param estado Nuevo estado donde ejerce el médico.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna una representación en cadena del objeto Medico.
     *
     * @return Cadena con la información del médico.
     */
    @Override
    public String toString() {
        return "Dr. " + this.getNombre() + " " + this.getApellidoPaterno() + " " + this.getApellidoMaterno();
    }
}
