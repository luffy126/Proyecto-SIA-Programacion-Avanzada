package Clases;
import java.time.LocalDate;

/**
 *
 * @author Panchiky
 */
//Clase y sus atributos
public class Compra {
    private int ordenDeCompra;
    private String rut;
    private String formaDePago;
    private LocalDate fechaDeCompra;
    private String estadoDeCompra;
    private int montoTotal;
    
    //constructor con parámetros 
    public Compra (int ordenDeCompra, String rut, String formaDePago, LocalDate fechaDeCompra, String estadoDeCompra, int montoTotal) {
        this.ordenDeCompra =  ordenDeCompra;
        this.rut = rut;
        this.formaDePago = formaDePago;
        this.fechaDeCompra = fechaDeCompra;
        this.estadoDeCompra = estadoDeCompra;
        this.montoTotal = montoTotal;
    }
    
    //GETTERS
    public int getOrdenDeCompra(){return ordenDeCompra;}
    public String getRut(){return rut;}
    public String getFormaDePago(){return formaDePago;}
    public LocalDate getFechaDeCompra(){return fechaDeCompra;}
    public String getEstadoDeCompra(){return estadoDeCompra;}
    public int getMontoTotal(){return montoTotal;}
    
    // SETTERS
    public void setOrdenDeCompra(int ordenDeCompra){this.ordenDeCompra = ordenDeCompra;}
    public void setRut(String rut){this.rut = rut;}
    public void setFormaDePago(String formaDePago){this.formaDePago = formaDePago;}
    public void setFechaDeCompra(LocalDate fechaDeCompra){this.fechaDeCompra = fechaDeCompra;}
    public void setEstadoDeCompra(String estadoDeCompra){this.estadoDeCompra = estadoDeCompra;}
    public void setMontoTotal(int montoTotal){this.montoTotal = montoTotal;}
}
