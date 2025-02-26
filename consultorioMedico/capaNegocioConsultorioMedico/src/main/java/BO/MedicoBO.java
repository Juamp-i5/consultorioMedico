/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.IMedicoDAO;
import DAO.MedicoDAO;
import DTO.MedicoInicioSesionDTO;
import DTO.MedicoNuevoDTO;
import entidades.Medico;
import excepciones.PersistenciaException;
import exception.NegocioException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.MedicoMapper;
import utils.Password;

/**
 *
 * @author Admin
 */
public class MedicoBO {

    IMedicoDAO medicoDAO = new MedicoDAO();
    /**
     * Agrega un nuevo paciente al sistema.
     * @param pacienteNuevoDTO Datos del paciente a registrar.
     * @return true si se agregó correctamente, false en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso.
     */
    public boolean agregarMedico(MedicoNuevoDTO medicoNuevoDTO) throws NegocioException {
        if (medicoNuevoDTO == null) {
            return false;
        }

        medicoNuevoDTO.setContrasenia(Password.hashPassword(medicoNuevoDTO.getContrasenia()));
        Medico medico = MedicoMapper.toEntity(medicoNuevoDTO);

        try {
            return medicoDAO.agregarMedico(medico);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al agregar médico: " + e.getMessage(), e);
        }
    }


    public Medico consultarMedico(int idMedico) throws NegocioException {
        //Validar que el id sea valido
        if (idMedico <= 0) {
            throw new NegocioException("El idMedico no es valido ");
        }

        try {
            Medico medico = medicoDAO.consultarMedico(idMedico);
            if (medico == null) {
                throw new NegocioException("No se encontró un médico con el ID: " + idMedico);
            }
            return medico;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar el medico con ID " + idMedico + ": " + e.getMessage());
        }
    }

    public List<String> obtenerEspecialidadesMedicos() throws NegocioException{
        try {
            return medicoDAO.obtenerEspecialidadesMedicos(); 
        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error obtener las especialidades de los medicos", e);
        }
    }
    
    public List<Medico> obtenerMedicosDisponiblesActivos(String especialidad) throws NegocioException{
        try {
            return medicoDAO.obtenerMedicosPorEspecialidadActivos(especialidad);
        } catch (PersistenciaException e) {
            e.printStackTrace();
            throw new NegocioException("Error al cargar los datos de Medicos", e);
        }
    }
    
        /**
     * Valida las credenciales de inicio de sesión de un paciente.
     * @param pacienteInicioSesion Datos del paciente.
     * @return ID del usuario si las credenciales son correctas.
     * @throws NegocioException Si las credenciales son incorrectas o hay un error.
     */
    public int iniciarSesion(MedicoInicioSesionDTO medico) throws NegocioException {
        if (medico == null) {
            throw new NegocioException("medico es null");
        }

        try {
            int id = medicoDAO.validarInicioSesion(MedicoMapper.toEntity(medico));
            return id;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al iniciarSesion: " + ex.getMessage());
        }
    }
    
    public boolean citasPendientes(int idMedico) throws NegocioException {
        try {
            return medicoDAO.citasPendientes(idMedico);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al verificar citas pendientes: " + ex.getMessage());
        }
    }
    
    public boolean darAlta(int idMedico) throws NegocioException {
        try {
            return medicoDAO.darAlta(idMedico);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al dar de alta: " + ex.getMessage());
        }
    }
    
    public boolean darBaja(int idMedico) throws NegocioException {
        try {
            return medicoDAO.darBaja(idMedico);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al dar de baja: " + ex.getMessage()); 
        }
    }

    public boolean validarDatosMedicoNuevoDTO(MedicoNuevoDTO medicoNuevoDTO) {
        if (!validarNombre(medicoNuevoDTO.getNombre())) {
            System.out.println("Error: el nombre no es valido");
        }

        if (!validarApellidoPaterno(medicoNuevoDTO.getApellidoPaterno())) {
            System.out.println("Error: el apellido paterno no es valido");
        }

        if (!validarApellidoMaterno(medicoNuevoDTO.getApellidoMaterno())) {
            System.out.println("Error: el apellido materno no es valido");
        }

        if (!validarEspecialidad(medicoNuevoDTO.getEspecialidad())) {
            System.out.println("Error: la especialidad no es valida");
        }

        if (!validarCedulaProfesional(medicoNuevoDTO.getCedulaProfesional())) {
            System.out.println("Error: la cedula profesional no es valida");
        }

        if (!validarEstado(medicoNuevoDTO.getEstado())) {
            System.out.println("Error: el estado no es valido");
        }

        if (!validarContrasenia(medicoNuevoDTO.getContrasenia())) {
            System.out.println("Error: la contraseña no es valida");
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

    public boolean validarEspecialidad(String especialidad) {
        return especialidad != null && !especialidad.trim().isEmpty() && especialidad.length() <= 100;
    }

    public boolean validarCedulaProfesional(String cedulaProfesional) {
        return cedulaProfesional != null && !cedulaProfesional.trim().isEmpty() && cedulaProfesional.length() <= 20;
    }

    public boolean validarEstado(String estado) {
        return estado != null && (estado.equalsIgnoreCase("ACTIVO") || estado.equalsIgnoreCase("INACTIVO"));
    }

    public boolean validarContrasenia(String contrasenia) {
        return contrasenia != null && !contrasenia.trim().isEmpty() && contrasenia.length() <= 50;
    }

}

