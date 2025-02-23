/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Representa la dirección de un paciente en el sistema. Contiene información
 * sobre la calle, número, colonia y código postal.
 *
 * @author Jp
 */
public class DireccionPaciente {

    /**
     * Nombre de la calle de la dirección del paciente.
     */
    private String calle;

    /**
     * Número de la casa o edificio en la dirección del paciente.
     */
    private String numero;

    /**
     * Nombre de la colonia donde se encuentra la dirección.
     */
    private String colonia;

    /**
     * Código postal correspondiente a la dirección.
     */
    private String codigoPostal;

    /**
     * Constructor por defecto.
     */
    public DireccionPaciente() {
    }

    /**
     * Constructor con todos los atributos de la dirección.
     *
     * @param calle Nombre de la calle.
     * @param numero Número de la casa o edificio.
     * @param colonia Nombre de la colonia.
     * @param codigoPostal Código postal.
     */
    public DireccionPaciente(String calle, String numero, String colonia, String codigoPostal) {
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene el nombre de la calle.
     *
     * @return Nombre de la calle.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece el nombre de la calle.
     *
     * @param calle Nuevo nombre de la calle.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el número de la casa o edificio.
     *
     * @return Número de la dirección.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la casa o edificio.
     *
     * @param numero Nuevo número de la dirección.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el nombre de la colonia.
     *
     * @return Nombre de la colonia.
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * Establece el nombre de la colonia.
     *
     * @param colonia Nuevo nombre de la colonia.
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * Obtiene el código postal.
     *
     * @return Código postal.
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal.
     *
     * @param codigoPostal Nuevo código postal.
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Devuelve una representación en cadena del objeto DireccionPaciente.
     *
     * @return Cadena con los datos de la dirección del paciente.
     */
    @Override
    public String toString() {
        return "DireccionPaciente{" + "calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + '}';
    }

}
