/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utilidad para el manejo de contraseñas utilizando el algoritmo de
 * encriptación BCrypt. Esta clase proporciona métodos para generar un hash de
 * una contraseña y verificar si una contraseña coincide con un hash previamente
 * generado.
 *
 * @author Jp
 */
public class Password {

    // Instancia del codificador BCrypt para encriptar y verificar contraseñas.
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Genera un hash seguro para la contraseña proporcionada.
     *
     * @param password La contraseña en texto plano que se desea encriptar.
     * @return El hash de la contraseña utilizando el algoritmo BCrypt.
     */
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Verifica si la contraseña en texto plano coincide con el hash
     * proporcionado.
     *
     * @param rawPassword La contraseña en texto plano a verificar.
     * @param hashedPassword El hash de la contraseña con el que se va a
     * verificar.
     * @return {@code true} si la contraseña en texto plano coincide con el
     * hash, {@code false} en caso contrario.
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

}
