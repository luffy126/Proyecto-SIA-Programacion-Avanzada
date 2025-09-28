    package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.io.*;

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
    
    private HashMap<Integer, Asiento> asientos;
    List<Cliente> clientes;
    List<Compra> compras;
    
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
        this.clientes = new ArrayList<>();
        this.compras = new ArrayList<>();
        
        
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
    public List<Compra> getCompras() {return this.compras;}
    public List<Cliente> getClientes() {return this.clientes;}
    
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
    public void setCompras(List<Compra> compras) {this.compras = compras;}
    
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
        
        if (asiento.getAsientoIsOcupado()) {
            System.out.println("El asiento " + numeroAsiento + " ya está ocupado por " + 
                             asiento.getDueño().getNombre());
            return false;
        }
        
        // Reservar el asiento y asginar el asiento al cliente
        asiento.setDueño(cliente);
        asiento.setIsOcupado(true);
        System.out.println("Asiento " + numeroAsiento + " reservado para " + cliente.getNombre());
        return true;
    }
    
    
     public boolean liberarAsiento(int numeroAsiento) { // Devuelve si se pudo o no liberar el asiento, despues de hacer condiciones logicas, 
        Asiento asiento = asientos.get(numeroAsiento);
        
        if (asiento == null) {
            System.out.println("El asiento " + numeroAsiento + " no existe.");
            return false;
        }
        
        if (!asiento.getAsientoIsOcupado()) {
            System.out.println("El asiento " + numeroAsiento + " ya está disponible.");
            return false;
        }
        
        String nombreAnterior = asiento.getDueño().getNombre(); // Var. local para el printeo mas formal indicando a quien se lo quitamos, para mayor trazabilidad y entendimiento.
        asiento.setDueño(null);
        asiento.setIsOcupado(false);
        System.out.println("Asiento " + numeroAsiento + " liberado (antes ocupado por " + 
                         nombreAnterior + ")");
        return true;
    }
    
     public HashMap<Integer, Asiento> obtenerAsientosDisponibles() { // Recorrido for simple, para ir guardando en un nuevo hashmap los disponibles y devolverlo
        HashMap<Integer, Asiento> disponibles = new HashMap<>();
        
        for (Map.Entry<Integer, Asiento> entry : asientos.entrySet()) {
            if (entry.getValue().getAsientoIsOcupado() == false) {
                disponibles.put(entry.getKey(), entry.getValue());
            }
        }
        
        return disponibles;
    }
    
    public HashMap<Integer, Asiento> obtenerAsientosOcupados() { // Lo mismo que el anterior pero al reve'
        HashMap<Integer, Asiento> ocupados = new HashMap<>();
        
        for (Map.Entry<Integer, Asiento> entry : asientos.entrySet()) {
            if (entry.getValue().getAsientoIsOcupado() == true) {
                ocupados.put(entry.getKey(), entry.getValue());
            }
        }
        return ocupados;
    } 
     
    public HashMap<Integer, Asiento> buscarAsientosPorRUT(String rut) {
        HashMap<Integer, Asiento> asientosCliente = new HashMap<>(); // nuevo mapa para guardar los asientos del rut
         
        for (Map.Entry<Integer, Asiento> entry : asientos.entrySet()) {  // entrySet devuelve las llaves y valores por entradas, cada entrada corresponde a un asiento y porta consigo 2 tipos de dato, integer y obj Asiento
            Asiento asiento = entry.getValue(); // Guarda la entrada actual para hacerle logica y confirmar que tiene dueño y el rut sea igual.
            if (asiento.getDueño() != null && asiento.getDueño().getRut().equals(rut)) {
                asientosCliente.put(entry.getKey(), asiento); // Agrega al HashMap esta entrada
            }
        }
        return asientosCliente;
    }
    
    public Asiento buscarAsiento(int numeroAsiento) { // Forma para buscar asientos por id, creo que seria util para comprobaciones internas, o si se llega a necesitar en un futuro.
        return asientos.get(numeroAsiento);
    }
    
    public void mostrarEstadisticasAsientos() {
        int totalAsientos = asientos.size();
        int ocupados = obtenerAsientosOcupados().size();
        int disponibles = totalAsientos - ocupados;
        double porcentajeOcupacion = totalAsientos > 0 ? (ocupados * 100.0 / totalAsientos) : 0;
        
        System.out.println("=== ESTADÍSTICAS DEL EVENTO: " + getNombre() + " ===");
        System.out.println("Total de asientos: " + totalAsientos);
        System.out.println("Asientos ocupados: " + ocupados);
        System.out.println("Asientos disponibles: " + disponibles);
        System.out.printf("Porcentaje de ocupación: %.2f%%\n", porcentajeOcupacion);
    }

    
    public String listarEventos(ArrayList<Evento> eventos) {
        
        String txt = "" + (eventos.size()+1) + nombre + ", ID:" + ID;
        return (txt);
                
    }
    
    public Compra CrearOrdenDeCompra(Cliente cliente, int cantidadAsientos, String formaDePago, int ide) {
        
        Random random = new Random();
        int ID = (random.nextInt(9999)); 
        String metodoDePago = formaDePago;
        String estadoDeCompra = "Confirmada";
        int montoTotal;
        int idEvento = ide;
        
        List<Asiento> asientosReservados = new ArrayList<>();
        
        for (Asiento asiento : asientos.values()) {
            if (!asiento.getAsientoIsOcupado()) {
                asiento.setDueño(cliente);
                asiento.setEventoAnfitrion(this);
                asiento.setIsOcupado(true);
                asientosReservados.add(asiento);

        if (asientosReservados.size() == cantidadAsientos) break; // ✅ se detiene justo al llegar a la cantidad solicitada
    }
}
        
        if (asientosReservados.size() < cantidadAsientos) {
            throw new IllegalArgumentException("No hay asientos suficientes para esta orden de compra");
        }
        
        montoTotal = cantidadAsientos * this.precioEntrada;
        
        Compra nuevaCompra = new Compra (
                ID, cliente.getRut(), metodoDePago, LocalDate.now(), estadoDeCompra, montoTotal, idEvento
        );
        
        cliente.getComprasClientes().add(nuevaCompra);
        this.compras.add(nuevaCompra);
        
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
        
        return nuevaCompra;
    }
    
    @Override
    public String toString() {
        return nombre + " (ID: " + ID + ")";
    }

    
}
