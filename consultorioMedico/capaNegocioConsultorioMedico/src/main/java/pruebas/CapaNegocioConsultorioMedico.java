/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pruebas;

import BO.MedicoBO;
import BO.PacienteBO;
import DTO.PacienteNuevoDTO;
import entidades.Medico;
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
        pacienteNuevoDTO.setTelefono("9223527230");
        pacienteNuevoDTO.setCorreoElectronico("juanasssvczxcsssssssss.perez@example.com");
        pacienteNuevoDTO.setCalle("Rio garona");
        pacienteNuevoDTO.setNumero("2344");
        pacienteNuevoDTO.setColonia("Villa Bonita");
        pacienteNuevoDTO.setCodigoPostal("1234");

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
//        MedicoBO medicoBO = new MedicoBO();

            
//        try {
//            Medico medicoConsultado = medicoBO.consultarMedico(3);
//            if (medicoConsultado != null) {
//                System.out.println("Medico encontrado:");
//                System.out.println("Nombre: " + medicoConsultado.getNombre());
//                System.out.println("Apellido Paterno: " + medicoConsultado.getApellidoPaterno());
//                System.out.println("Especialidad: " + medicoConsultado.getEspecialidad());
//            } else {
//                System.out.println("No se encontró el médico con ese ID ");
//            }
//        } catch (NegocioException e) {
//            System.out.println("Excepcion de negocio al consultar medico: " + e.getMessage());
//        }
//        
    }
    
}
