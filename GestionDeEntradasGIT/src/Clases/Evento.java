package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    
    private int ID;
    private String nombre;
    private int capacidadPersonas;
    private String ubicacion;
    private LocalDate fechaEvento;
    private String orador;
    private String temaEvento;
    private String descripcionEvento;
    private int asientosEspeciales;
    private int precioEntrada;
    
    private Map<Integer, Asiento> asientos;
    

    
    List<Cliente> clientes;
    // Un amigo que paso me recomendo, que a este evento, le meta HashMap, 
    
    /* el metodo toString() se usa para imprimir colecciones de variables, pero nuestro proyecto
     Usa colecciones de clases/objetos, por lo tanto nos termina señalando el package y el Hashcode de la clase, entonces con el @Override le decimos a Java
     que cuando se intente usar este metodo nativo de Java en esta clase Eventos o Clientes, reconfiguramos el comportamiento */

    public Evento (String nombre, int capacidadPersonas, int ID, String ubicacion, 
            LocalDate fechaEvento, String orador, String temaEvento, String descripcionEvento, int asientosEspeciales, int precioEntrada) {
        this.ID = ID;
        this.nombre = nombre;
        this.capacidadPersonas = capacidadPersonas;
        this.ubicacion = ubicacion;
        this.fechaEvento = fechaEvento;
        this.orador = orador;
        this.temaEvento = temaEvento;
        this.descripcionEvento = descripcionEvento;
        this.asientosEspeciales = asientosEspeciales;
        this.precioEntrada = precioEntrada;
        this.asientos = new HashMap<>();
        inicializarAsientos();
    }
    
       private void inicializarAsientos() {
        for (int i = 1; i <= getCapacidad(); i++) {
            // Crear asiento sin dueño inicialmente, el segundo parametro indica que pertenece a este evento, y el tercero es que esta desocupado
            Asiento nuevoAsiento = new Asiento(null, this, false);
            asientos.put(i, nuevoAsiento);
        }
    }
       
    public boolean reservarAsiento(int numeroAsiento, Cliente cliente) {
        Asiento asiento = asientos.get(numeroAsiento);
        
        if (asiento == null) {
            System.out.println("El asiento " + numeroAsiento + " no existe.");
            return false;
        }
        
        if (asiento.getEstadoSilla()) {
            System.out.println("El asiento " + numeroAsiento + " ya está ocupado por " + 
                             asiento.getDueño().getNombre());
            return false;
        }
        
        // Reservar el asiento y asginar el asiento al cliente
        asiento.setDueño(cliente);
        asiento.setOcupado(true);
        System.out.println("Asiento " + numeroAsiento + " reservado para " + cliente.getNombre());
        return true;
    }
    
    
    public HashMap<Integer, Asiento> buscarAsientosPorRUT(String rut) {
        HashMap<Integer, Asiento> asientosCliente = new HashMap<>();
        
       
        return asientosCliente;
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
    public LocalDate getFechaEvento(){return this.fechaEvento;}
    public String getOrador(){return this.orador;}
    public String getTemaEvento(){return this.temaEvento;}
    public String getDescripcionEvento(){return this.descripcionEvento;}
    public int getAsientosEspeciales(){return this.asientosEspeciales;}
    public int getPrecioEntrada(){return this.precioEntrada;}
    
    // Setters
    public void setID(int ID){this.ID = ID;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setCapacidadPersonas(int capacidadPersonas){this.capacidadPersonas = capacidadPersonas;}
    public void setUbicacion(String ubicacion){this.ubicacion = ubicacion;}
    public void setFechaEvento(LocalDate fechaEvento){this.fechaEvento = fechaEvento;}
    public void setOrador(String orador){this.orador = orador;}
    public void setTemaEvento(String temaEvento){this.temaEvento = temaEvento;}
    public void setDescripcionEvento(String descripcionEvento){this.descripcionEvento = descripcionEvento;}
    public void setAsientosEspeciales(int asientosEspeciales){this.asientosEspeciales = asientosEspeciales;}
    public void setPrecioEntrada(int precioEntrada){this.precioEntrada = precioEntrada;}
    
   
            
            
    // CORREGIR: public List<Clientes> getClientes(){return(new Clientes<>(this.clientes));}
  
}
