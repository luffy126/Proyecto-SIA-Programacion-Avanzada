package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tenerex
 */
public class Clientes {

    String rut;
    String nombre;
    int asientosAComprar;
    String[] acompanantes;
    int edad;
    String[] discapacidades;

    List<Compras> comprasCliente = new ArrayList<>();

    public Clientes(String rut, String nombre){
        this.rut = rut;
        this.nombre = nombre;
        this.asientosAComprar = asientosAComprar;
        this.acompanantes = acompanantes;
        this.edad = edad;
        this.discapacidades = discapacidades;
        
    }
    
    
    // Getters
    public String getRut(){return this.rut;}
    public String getNombre() {return this.nombre;}
    public String[] getAcompanantes() {return this.acompanantes;}
    public int getEdad(){return this.edad;}
    public int getAsientosAComprar() {return this.asientosAComprar;}
    public String[] getDiscapacidades(){return this.discapacidades;}
    
    //Setters
    public void setEdad(int edad) {this.edad = edad;}
    
    
}
