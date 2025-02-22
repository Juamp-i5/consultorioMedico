/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTO.PacienteInicioSesionDTO;
import DTO.PacienteNuevoDTO;
import entidades.DireccionPaciente;
import entidades.Paciente;

/**
 *
 * @author Jp
 */
public class PacienteMapper {

    public static Paciente toEntity(PacienteNuevoDTO pacienteNuevoDTO) {

        return new Paciente(
                pacienteNuevoDTO.getFechaNacimiento(),
                pacienteNuevoDTO.getTelefono(),
                pacienteNuevoDTO.getCorreoElectronico(),
                pacienteNuevoDTO.getDireccion(),
                pacienteNuevoDTO.getNombre(),
                pacienteNuevoDTO.getApellidoPaterno(),
                pacienteNuevoDTO.getApellidoMaterno(),
                pacienteNuevoDTO.getContrasenia()
        );
    }

    public static Paciente toEntity(PacienteInicioSesionDTO pacienteInicioSesion) {
        Paciente paciente = new Paciente();
        paciente.setCorreoElectronico(pacienteInicioSesion.getCorreo());
        paciente.setContrasenia(pacienteInicioSesion.getContrasenia());
        return paciente;
    }

//    public static PacienteNuevoDTO toDTO(Paciente paciente) {
//        return new PacienteNuevoDTO(
//                paciente.getNombre(),
//                paciente.getApellidoPaterno(),
//                paciente.getApellidoMaterno(),
//                paciente.getContrasenia(),
//                paciente.getFechaNacimiento(),
//                paciente.getTelefono(),
//                paciente.getCorreoElectronico()
//        );
//    }
}
