/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalTime;

/**
 * Representa el horario de atención de un médico en el sistema. Cada horario
 * incluye el día de la semana, la hora de inicio y fin, y el médico asociado.
 *
 * @author Jp
 */
public class HorarioAtencion {

    /**
     * Identificador único del horario de atención.
     */
    private int idHorarioAtencion;

    /**
     * Día de la semana en el que aplica este horario.
     */
    private String diaSemana;

    /**
     * Hora de inicio de la atención médica.
     */
    private LocalTime horaInicio;

    /**
     * Hora de finalización de la atención médica.
     */
    private LocalTime horaFin;

    /**
     * Identificador del médico al que pertenece este horario.
     */
    private int idMedico;

    /**
     * Constructor por defecto.
     */
    public HorarioAtencion() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param idHorarioAtencion Identificador del horario de atención.
     * @param diaSemana Día de la semana en el que aplica este horario.
     * @param horaInicio Hora de inicio de la atención médica.
     * @param horaFin Hora de finalización de la atención médica.
     * @param idMedico Identificador del médico al que pertenece este horario.
     */
    public HorarioAtencion(int idHorarioAtencion, String diaSemana, LocalTime horaInicio, LocalTime horaFin, int idMedico) {
        this.idHorarioAtencion = idHorarioAtencion;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMedico = idMedico;
    }

    /**
     * Constructor sin ID del horario de atención.
     *
     * @param diaSemana Día de la semana en el que aplica este horario.
     * @param horaInicio Hora de inicio de la atención médica.
     * @param horaFin Hora de finalización de la atención médica.
     * @param idMedico Identificador del médico al que pertenece este horario.
     */
    public HorarioAtencion(String diaSemana, LocalTime horaInicio, LocalTime horaFin, int idMedico) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMedico = idMedico;
    }

    /**
     * Obtiene el identificador del horario de atención.
     *
     * @return ID del horario de atención.
     */
    public int getIdHorarioAtencion() {
        return idHorarioAtencion;
    }

    /**
     * Establece el identificador del horario de atención.
     *
     * @param idHorarioAtencion Nuevo ID del horario de atención.
     */
    public void setIdHorarioAtencion(int idHorarioAtencion) {
        this.idHorarioAtencion = idHorarioAtencion;
    }

    /**
     * Obtiene el día de la semana en que aplica el horario.
     *
     * @return Día de la semana.
     */
    public String getDiaSemana() {
        return diaSemana;
    }

    /**
     * Establece el día de la semana para el horario.
     *
     * @param diaSemana Nuevo día de la semana.
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * Obtiene la hora de inicio de la atención.
     *
     * @return Hora de inicio.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la atención.
     *
     * @param horaInicio Nueva hora de inicio.
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de finalización de la atención.
     *
     * @return Hora de finalización.
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de finalización de la atención.
     *
     * @param horaFin Nueva hora de finalización.
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el identificador del médico asociado al horario.
     *
     * @return ID del médico.
     */
    public int getIdMedico() {
        return idMedico;
    }

    /**
     * Establece el identificador del médico asociado al horario.
     *
     * @param idMedico Nuevo ID del médico.
     */
    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * Devuelve una representación en cadena del objeto HorarioAtencion.
     *
     * @return Cadena con los datos del horario de atención.
     */
    @Override
    public String toString() {
        return "HorarioAtencion{" + "idHorarioAtencion=" + idHorarioAtencion + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", idMedico=" + idMedico + '}';
    }

}
