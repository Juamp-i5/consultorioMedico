/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pruebas;

import BO.PacienteBO;
import DTO.PacienteNuevoDTO;
import exception.NegocioException;
import java.time.LocalDate;

/**
 *
 * @author Jp
 */
public class CapaNegocioConsultorioMedico {

    public static void main(String[] args) {
        PacienteNuevoDTO pacienteNuevoDTO = new PacienteNuevoDTO();
        pacienteNuevoDTO.setNombre("Juan");
        pacienteNuevoDTO.setApellidoPaterno("Pérez");
        pacienteNuevoDTO.setApellidoMaterno("González");
        pacienteNuevoDTO.setContrasenia("password123");
        pacienteNuevoDTO.setFechaNacimiento(LocalDate.of(2005, 5, 15));
        pacienteNuevoDTO.setTelefono("1224527230");
        pacienteNuevoDTO.setCorreoElectronico("juanasssssssssssss.perez@example.com");

        PacienteBO pacienteBO = new PacienteBO();
        // Probar el método agregarPaciente
        try {
            boolean resultado = pacienteBO.agregarPaciente(pacienteNuevoDTO);
            if (resultado) {
                System.out.println("Paciente agregado correctamente.");
            } else {
                System.out.println("Error al agregar el paciente.");
            }
        } catch (NegocioException e) {
            System.out.println("Excepción de negocio: " + e.getMessage());
        }
    }
}
