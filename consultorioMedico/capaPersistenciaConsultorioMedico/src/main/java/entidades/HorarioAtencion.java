/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalTime;

/**
 *
 * @author Jp
 */
public class HorarioAtencion {

    private int idHorarioAtencion;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int idMedico;

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
