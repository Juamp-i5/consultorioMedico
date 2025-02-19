/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Jp
 */
public class DireccionPaciente {

    private int idDireccionPaciente;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private int idPaciente;

    public DireccionPaciente() {
    }

    public DireccionPaciente(int idDireccionPaciente, String calle, String numero, String colonia, String codigoPostal, int idPaciente) {
        this.idDireccionPaciente = idDireccionPaciente;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.idPaciente = idPaciente;
    }

    public DireccionPaciente(String calle, String numero, String colonia, String codigoPostal, int idPaciente) {
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.idPaciente = idPaciente;
    }

    public int getIdDireccionPaciente() {
        return idDireccionPaciente;
    }

    public void setIdDireccionPaciente(int idDireccionPaciente) {
        this.idDireccionPaciente = idDireccionPaciente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return "DireccionPaciente{" + "idDireccionPaciente=" + idDireccionPaciente + ", calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + ", idPaciente=" + idPaciente + '}';
    }

}
