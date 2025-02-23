/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pruebas;

import BO.MedicoBO;
import BO.PacienteBO;
import DTO.MedicoNuevoDTO;
import DTO.PacienteNuevoDTO;
import entidades.Medico;
import exception.NegocioException;
import java.time.LocalDate;

/**
 *
 * @author Jp
 */
public class CapaNegocioConsultorioMedico {

    public static void main(String[] args) throws NegocioException {
//        PacienteNuevoDTO pacienteNuevoDTO = new PacienteNuevoDTO();
//        pacienteNuevoDTO.setNombre("Juan");
//        pacienteNuevoDTO.setApellidoPaterno("Pérez");
//        pacienteNuevoDTO.setApellidoMaterno("González");
//        pacienteNuevoDTO.setContrasenia("password123");
//        pacienteNuevoDTO.setFechaNacimiento(LocalDate.of(2005, 5, 15));
//        pacienteNuevoDTO.setTelefono("9223527230");
//        pacienteNuevoDTO.setCorreoElectronico("juanasssvczxcsssssssss.perez@example.com");
//        pacienteNuevoDTO.setCalle("Rio garona");
//        pacienteNuevoDTO.setNumero("2344");
//        pacienteNuevoDTO.setColonia("Villa Bonita");
//        pacienteNuevoDTO.setCodigoPostal("1234");
//
//        PacienteBO pacienteBO = new PacienteBO();
//        // Probar el método agregarPaciente
//        try {
//            boolean resultado = pacienteBO.agregarPaciente(pacienteNuevoDTO);
//            if (resultado) {
//                System.out.println("Paciente agregado correctamente.");
//            } else {
//                System.out.println("Error al agregar el paciente.");
//            }
//        } catch (NegocioException e) {
//            System.out.println("Excepción de negocio: " + e.getMessage());
//        }

//        MedicoNuevoDTO medicoNuevoDTO = new MedicoNuevoDTO();
//        medicoNuevoDTO.setNombre("Jorge");
//        medicoNuevoDTO.setApellidoPaterno("Marquez");
//        medicoNuevoDTO.setApellidoMaterno("Valenzuela");
//        medicoNuevoDTO.setEspecialidad("Genetica");
//        medicoNuevoDTO.setCedulaProfesional("12903478");
//        medicoNuevoDTO.setEstado("Activo");
//        medicoNuevoDTO.setContrasenia("control123");
        MedicoBO medicoBO = new MedicoBO();

//        try {
//            boolean resultado = medicoBO.agregarMedico(medicoNuevoDTO);
//            if (resultado) {
//                System.out.println("Medico Agregado correctamente.");
//            } else {
//                System.out.println("Error al agregar paciente.");
//            }
//        } catch (NegocioException e) {
//            System.out.println("Excepcion de negocio: " + e.getMessage());
//        }

        MedicoNuevoDTO medico1 = new MedicoNuevoDTO(
                "Juan", "Pérez", "Gómez", "Cardiología",
                "1234567", "Activo", "password123"
        );

        MedicoNuevoDTO medico2 = new MedicoNuevoDTO(
                "María", "López", "Martínez", "Neurología",
                "7654321", "Activo", "securePass"
        );

        MedicoNuevoDTO medico3 = new MedicoNuevoDTO(
                "Carlos", "Fernández", "Díaz", "Pediatría",
                "9876543", "Inactivo", "pass1234"
        );

        try {
            boolean agregado1 = medicoBO.agregarMedico(medico1);
            boolean agregado2 = medicoBO.agregarMedico(medico2);
            boolean agregado3 = medicoBO.agregarMedico(medico3);

            System.out.println("Médico 1 agregado: " + agregado1);
            System.out.println("Médico 2 agregado: " + agregado2);
            System.out.println("Médico 3 agregado: " + agregado3);
        } catch (NegocioException e) {
            e.printStackTrace();
        }

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
