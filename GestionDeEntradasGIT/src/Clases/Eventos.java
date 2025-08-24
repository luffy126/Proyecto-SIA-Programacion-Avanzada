package Clases;

import java.util.List;

/**
 *
 * @author Tenerex
 */

/*
 ID único de evento, nombre, capacidad de personas, ubicación, hora de realización, 
orador a cargo y tema, descripción breve del evento y disponibilidad de asientos 
especiales para personas con discapacidad.
*/

public class Eventos {
    
    int ID;
    String nombre;
    int capacidadPersonas;
    String ubicacion;
    boolean[] sillas;
 
    List<Clientes> clientes;
    
    public Eventos (String nombre, int capacidad, int ID) {
        this.ID = ID;
        this.nombre = nombre;
        this.capacidadPersonas = capacidadPersonas;
    }
    
    // Getters
    public int getID(){return this.ID;}
    public String getNombre(){return this.nombre;}
    public int getCapacidad(){return this.capacidadPersonas;}
    public String getUbicacion(){return this.ubicacion;}
    public boolean[] getSillas(){return this.sillas;}
    // CORREGIR: public List<Clientes> getClientes(){return(new Clientes<>(this.clientes));}
    
}
