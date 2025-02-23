/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción personalizada que se utiliza para manejar errores relacionados con
 * la persistencia de datos en el sistema.
 * <p>
 * Esta clase extiende {@link Exception} y proporciona constructores para
 * generar excepciones con diferentes detalles de mensaje y causas.
 * </p>
 *
 * @author Jp
 */
public class PersistenciaException extends Exception {

    /**
     * Crea una nueva instancia de PersistenciaException sin mensaje ni causa.
     */
    public PersistenciaException() {
    }

    /**
     * Crea una nueva instancia de PersistenciaException con un mensaje de
     * error.
     *
     * @param message El mensaje de error que describe la excepción.
     */
    public PersistenciaException(String message) {
        super(message);
    }

    /**
     * Crea una nueva instancia de PersistenciaException con un mensaje de error
     * y una causa que originó la excepción.
     *
     * @param message El mensaje de error que describe la excepción.
     * @param cause La causa de la excepción, que puede ser otra excepción.
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }

}
