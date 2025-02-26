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
        PacienteNuevoDTO paciente1 = new PacienteNuevoDTO();
        paciente1.setNombre("Carlos");
        paciente1.setApellidoPaterno("Ramírez");
        paciente1.setApellidoMaterno("López");
        paciente1.setContrasenia("carlosPass123");
        paciente1.setFechaNacimiento(LocalDate.of(1998, 8, 22));
    }

}
