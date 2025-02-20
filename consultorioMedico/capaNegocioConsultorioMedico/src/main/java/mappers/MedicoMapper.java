/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTO.MedicoNuevoDTO;
import entidades.Medico;

/**
 *
 * @author Admin
 */
public class MedicoMapper {
    
    
    public static Medico toEntity(MedicoNuevoDTO medicoNuevo){

        
// public Medico(String especialidad, String cedulaProfesional, String estado, String nombre, String apellidoPaterno, String apellidoMaterno, String contrasenia) {
        return new Medico(
                medicoNuevo.getEspecialidad(),
                medicoNuevo.getCedulaProfesional(),
                medicoNuevo.getEstado(),
                medicoNuevo.getNombre(),
                medicoNuevo.getApellidoPaterno(),
                medicoNuevo.getApellidoMaterno(),
                medicoNuevo.getContrasenia()
        );

    }
    public static MedicoNuevoDTO toDTO(Medico medico){
        return new MedicoNuevoDTO(
            medico.getNombre(),
            medico.getApellidoPaterno(),
            medico.getApellidoMaterno(),
            medico.getEspecialidad(),
            medico.getCedulaProfesional(),
            medico.getEstado(),
            medico.getContrasenia()
        );
    
    }
}