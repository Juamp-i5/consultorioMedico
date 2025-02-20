package pruebas;

import DAO.MedicoDAO;
import DAO.UsuarioDAO;
import conexion.Conexion;
import entidades.Usuario;
import excepciones.PersistenciaException;

/**
 *
 * @author Jp
 */
public class CapaPersistenciaConsultorioMedico {

    public static void main(String[] args){
        Conexion con = new Conexion();
        Usuario u1 = new Usuario("Alejandro", "Valdez", "Hermosillo","jano1234");
        UsuarioDAO u1DAO = new UsuarioDAO();
        
        try {
            u1DAO.agregarUsuario(u1);
        } catch (PersistenciaException e) {
            e.printStackTrace(); 
        }
        
    }
}
