/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import entidades.Paciente;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import mappers.PacienteMapper;
import utils.Password;

/**
 *
 * @author Jp
 */
public class PacienteBO {

    IPacienteDAO pacienteDAO = new PacienteDAO();

    public boolean agregarPaciente(PacienteNuevoDTO pacienteNuevoDTO) throws NegocioException {
        if (pacienteNuevoDTO == null) {
            return false;
        }
        pacienteNuevoDTO.setContrasenia(Password.hashPassword(pacienteNuevoDTO.getContrasenia()));
        Paciente paciente = PacienteMapper.toEntity(pacienteNuevoDTO);

        try {
            return pacienteDAO.agregarPaciente(paciente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregarPaciente: " + e.getMessage());
        }
    }

    public boolean validarDatosRegistrarCuenta(String correo, String contraseña, String contraseñaRepetida) throws NegocioException {
        try {
            if (!validarCorreoElectronico(correo)) {
                throw new NegocioException("El correo no es valido");
            }

            if (pacienteDAO.existeCorreo(correo)) {
                throw new NegocioException("El correo ya está registrado.");
            }

            if (!contraseña.equals(contraseñaRepetida)) {
                throw new NegocioException("Las contraseñas no coinciden.");
            }

            if (!validarContrasenia(contraseña)) {
                throw new NegocioException("Contraseñas no validas");
            }

            return true;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al validar los datos del primer registro: " + e.getMessage());
        }
    }

    public boolean validarDatosPaciente(String nombre, String apellidoPaterno, String apellidoMaterno, String numeroTelefono, String fechaNacimiento, String calle, String numero, String colonia, String codigoPostal) throws NegocioException {
        try {
            if (pacienteDAO.existeCelular(numeroTelefono)) {
                throw new NegocioException("El celular ya está registrado");
            }

            if (!validarNombre(nombre)) {
                throw new NegocioException("El nombre no es válido");
            }

            if (!validarApellidoPaterno(apellidoPaterno)) {
                throw new NegocioException("El apellido paterno no es válido");
            }

            if (!validarApellidoMaterno(apellidoMaterno)) {
                throw new NegocioException("El apellido materno no es válido");
            }

            if (!validarTelefono(numeroTelefono)) {
                throw new NegocioException("El número de teléfono no es válido");
            }

            if (!validarFechaNacimiento(fechaNacimiento)) {
                throw new NegocioException("La fecha de nacimiento no es válida");
            }

            if (!validarCalle(calle)) {
                throw new NegocioException("La calle no es valida");
            }

            if (!validarNumero(numero)) {
                throw new NegocioException("El número no es válido");
            }

            if (!validarColonia(colonia)) {
                throw new NegocioException("La colonia no es válida");
            }

            if (!validarCodigoPostal(codigoPostal)) {
                throw new NegocioException("El código postal no es válido");
            }

            return true;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al validar los datos del usuario", ex);
        }
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
        return contrasenia != null && !contrasenia.trim().isEmpty() && contrasenia.length() <= 50 && contrasenia.length() >= 8;
    }

    public boolean validarFechaNacimiento(String fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fecha = LocalDate.parse(fechaNacimiento, formatter);
            return !fecha.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{10,15}");
    }

    public boolean validarCorreoElectronico(String correoElectronico) {
        return correoElectronico != null && correoElectronico.length() <= 100 && correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean validarCalle(String calle) {
        return calle != null && !calle.trim().isEmpty() && calle.length() <= 30;
    }

    public boolean validarNumero(String numero) {
        return numero != null && !numero.trim().isEmpty() && numero.length() <= 10;
    }

    public boolean validarColonia(String colonia) {
        return colonia != null && !colonia.trim().isEmpty() && colonia.length() <= 60;
    }

    public boolean validarCodigoPostal(String codigoPostal) {
        return codigoPostal != null && !codigoPostal.trim().isEmpty() && codigoPostal.matches("\\d{5}");
    }

}
