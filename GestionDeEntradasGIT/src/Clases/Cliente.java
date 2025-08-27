package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tenerex
 */
public class Cliente {

    private String rut;
    private String nombre;
    private int asientosAComprar;
    private String acompanantes;
    private int edad;
    private String discapacidades;
    private GestorArchivos gestorArchivos;
    
    List<Compra> comprasCliente = new ArrayList<>();

    public Cliente(String nombre, String rut, int edad, int asientosAComprar, String acompanantes, String discapacidades){
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.asientosAComprar = asientosAComprar;
        this.acompanantes = acompanantes;
        this.discapacidades = discapacidades;
        
    }
   
    // Getters
    public String getRut(){return this.rut;}
    public String getNombre() {return this.nombre;}
    public String getAcompanantes() {return this.acompanantes;}
    public int getEdad(){return this.edad;}
    public int getAsientosAComprar() {return this.asientosAComprar;}
    public String getDiscapacidades(){return this.discapacidades;}
    
    //Setters
    public void setRut(String rut){this.rut = rut;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setAcompanantes(String acompanantes){this.acompanantes = acompanantes;}
    public void setEdad(int edad) {this.edad = edad;}
    public void setAsientosAComprar(int asientosAComprar){this.asientosAComprar = asientosAComprar;}
    public void setDiscapacidades(String discapacidades){this.discapacidades = discapacidades;}
    
}
