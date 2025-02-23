package BO;

import DTO.CitaDTO;
import exception.NegocioException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * @author janot
 */
public class CitaBO {
    
    
    
    
    
    
    public boolean validarDatosCita(CitaDTO citaDTO) throws NegocioException{
        if(validarHora(citaDTO.getFechaHora().toString())){
            System.out.println("Hora Invalida");
        }
        if(validarFecha(citaDTO.getFechaHora())){
            
        }
        
        return true;
    }
    
    
    //VALIDA LA FECHA
    public boolean validarFecha(Date fecha) throws NegocioException{
        if(fecha.before(fecha)){
            throw new NegocioException("La fecha es invalida");
        }
        return true;
    }
    
    //VALIDA LA HORA
    public boolean validarHora(String hora) throws NegocioException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); 
        try {
            LocalTime horaCita = LocalTime.parse(hora,formatter); 
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new NegocioException("Hora no valida",e);
        }
        
        return true;
    }
}
