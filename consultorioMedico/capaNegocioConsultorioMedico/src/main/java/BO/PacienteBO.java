/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import entidades.DireccionPaciente;
import entidades.Paciente;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.time.LocalDate;
import mappers.PacienteMapper;
import utils.Password;

/**
 *
 * @author Jp
 */
public class PacienteBO {

    IPacienteDAO pacienteDAO = new PacienteDAO();

    public boolean agregarPaciente(PacienteNuevoDTO pacienteNuevoDTO) throws NegocioException {
        if (pacienteNuevoDTO == null || !validarDatosPacienteNuevoDTO(pacienteNuevoDTO)) {
            return false;
        }
        pacienteNuevoDTO.setContrasenia(Password.hashPassword(pacienteNuevoDTO.getContrasenia()));
        Paciente paciente = PacienteMapper.toEntity(pacienteNuevoDTO);
        DireccionPaciente direccionPaciente = new DireccionPaciente(
                pacienteNuevoDTO.getCalle(),
                pacienteNuevoDTO.getNumero(),
                pacienteNuevoDTO.getColonia(),
                pacienteNuevoDTO.getCodigoPostal(),
                0
        ); 

        try {
            return pacienteDAO.agregarPaciente(paciente, direccionPaciente);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregarPaciente: " + e.getMessage());
        }
    }

    public boolean validarDatosPacienteNuevoDTO(PacienteNuevoDTO pacienteNuevoDTO) {
        if (!validarNombre(pacienteNuevoDTO.getNombre())) {
            System.out.println("Error: El nombre no es válido.");
            return false;
        }
        if (!validarApellidoPaterno(pacienteNuevoDTO.getApellidoPaterno())) {
            System.out.println("Error: El apellido paterno no es válido.");
            return false;
        }
        if (!validarApellidoMaterno(pacienteNuevoDTO.getApellidoMaterno())) {
            System.out.println("Error: El apellido materno no es válido.");
            return false;
        }
        if (!validarContrasenia(pacienteNuevoDTO.getContrasenia())) {
            System.out.println("Error: La contraseña no es válida.");
            return false;
        }
        if (!validarFechaNacimiento(pacienteNuevoDTO.getFechaNacimiento())) {
            System.out.println("Error: La fecha de nacimiento no es válida.");
            return false;
        }
        if (!validarTelefono(pacienteNuevoDTO.getTelefono())) {
            System.out.println("Error: El teléfono no es válido.");
            return false;
        }
        if (!validarCorreoElectronico(pacienteNuevoDTO.getCorreoElectronico())) {
            System.out.println("Error: El correo electrónico no es válido.");
            return false;
        }
        return true;
    }

    public boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100;
    }

    public boolean validarApellidoPaterno(String apellidoPaterno) {
        return apellidoPaterno != null && !apellidoPaterno.trim().isEmpty() && apellidoPaterno.length() <= 50;
    }

    public boolean validarApellidoMaterno(String apellidoMaterno) {
        return apellidoMaterno == null || apellidoMaterno.length() <= 50;
    }

    public boolean validarContrasenia(String contrasenia) {
        return contrasenia != null && !contrasenia.trim().isEmpty() && contrasenia.length() <= 50;
    }

    public boolean validarFechaNacimiento(LocalDate fechaNacimiento) {
        return fechaNacimiento != null;
    }

    public boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{10,15}");
    }

    public boolean validarCorreoElectronico(String correoElectronico) {
        return correoElectronico != null && correoElectronico.length() <= 100 && correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }
}
