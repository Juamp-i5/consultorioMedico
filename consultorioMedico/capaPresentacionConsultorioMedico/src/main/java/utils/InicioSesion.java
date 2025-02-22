/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Jp
 */
public class InicioSesion {
    
    private static Integer idUsuario;

    public static boolean esCorreo(String identificador) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return identificador.matches(emailRegex);
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static void setIdUsuario(int idUsuario) {
        InicioSesion.idUsuario = idUsuario;
    }
    
    public static void resetIdUsuario(){
        idUsuario = null;
    }
    
    
}
