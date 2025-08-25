package Clases;

import java.util.ArrayList;
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

public class Evento {
    
    int ID;
    String nombre;
    int capacidadPersonas;
    String ubicacion;

    
    List<Cliente> clientes;
    // Un amigo que paso me recomendo, que a este evento, le meta HashMap, 
    
    /* el metodo toString() se usa para imprimir colecciones de variables, pero nuestro proyecto
     Usa colecciones de clases/objetos, por lo tanto nos termina señalando el package y el Hashcode de la clase, entonces con el @Override le decimos a Java
     que cuando se intente usar este metodo nativo de Java en esta clase Eventos o Clientes, vamos a reconfigurar el comportamiento */

    public Evento (String nombre, int capacidadPersonas, int ID) {
        this.ID = ID;
        this.nombre = nombre;
        this.capacidadPersonas = capacidadPersonas;
    }
    
    public String listarEventos(ArrayList<Evento> eventos) {
        
        String txt = "" + (eventos.size()+1) + nombre + ", ID:" + ID;
        return (txt);
                
    }
    
    // Getters
    public int getID(){return this.ID;}
    public String getNombre(){return this.nombre;}
    public int getCapacidad(){return this.capacidadPersonas;}
    public String getUbicacion(){return this.ubicacion;}
    
    // Setters
    public void setID(int ID){this.ID = ID;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setCapacidadPersonas(int capacidadPersonas){this.capacidadPersonas = capacidadPersonas;}
    public void setUbicacion(String ubicacion){this.ubicacion = ubicacion;}
   
            
            
    // CORREGIR: public List<Clientes> getClientes(){return(new Clientes<>(this.clientes));}
  
}
