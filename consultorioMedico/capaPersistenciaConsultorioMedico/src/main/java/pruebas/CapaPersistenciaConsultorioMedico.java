package pruebas;

import DAO.CitaDAO;
import entidades.Cita;
import entidades.DireccionPaciente;
import entidades.Medico;
import entidades.Paciente;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jp
 */
public class CapaPersistenciaConsultorioMedico {
// Cita c1 = new Cita("Me duele la cabeza",null, LocalDate.now(), "Programado", p1.getIdPaciente(), p1.getIdUsuario());
    public static void main(String[] args) {
        /*PRUEBA AGREGAR CITA */
        Medico m1 = new Medico("Urologo", "12345678", "Activo", "Alejandro", "Valdez", "Hermosillo", "Jano1234");
        Paciente p1 = new Paciente(LocalDate.of(2025, Month.MAY, 16), "6441273737", "marianavaldezhillo@gmail.com", "Mariana", "Valdez", "Hermosillo", "chuchu1234");
        DireccionPaciente d1 = new DireccionPaciente("Rio turia", "1309", "Puente Real", "47474");
        Cita c1 = new Cita("Chequeo por dolor de cabeza", null, LocalDate.now().toString(), "Programado", 15, 12);
        CitaDAO cDAO = new CitaDAO();
        
        try {
            System.out.println(cDAO.obtenerCitasActivasPaciente(15).size());
        } catch (PersistenciaException ex) {
            Logger.getLogger(CapaPersistenciaConsultorioMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
