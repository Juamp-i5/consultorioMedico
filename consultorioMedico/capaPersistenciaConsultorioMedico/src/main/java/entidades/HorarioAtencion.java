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

    public HorarioAtencion(int idHorarioAtencion, String diaSemana, LocalTime horaInicio, LocalTime horaFin, int idMedico) {
        this.idHorarioAtencion = idHorarioAtencion;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMedico = idMedico;
    }

    public HorarioAtencion(String diaSemana, LocalTime horaInicio, LocalTime horaFin, int idMedico) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idMedico = idMedico;
    }

    public int getIdHorarioAtencion() {
        return idHorarioAtencion;
    }

    public void setIdHorarioAtencion(int idHorarioAtencion) {
        this.idHorarioAtencion = idHorarioAtencion;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    @Override
    public String toString() {
        return "HorarioAtencion{" + "idHorarioAtencion=" + idHorarioAtencion + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", idMedico=" + idMedico + '}';
    }

}
