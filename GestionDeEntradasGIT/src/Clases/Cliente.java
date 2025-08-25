package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tenerex
 */
public class Cliente {

    String rut;
    String nombre;
    int asientosAComprar;
    String[] acompanantes;
    int edad;
    String[] discapacidades;
    GestorArchivos gestorArchivos;
    
    List<Compra> comprasCliente = new ArrayList<>();

    public Cliente(String nombre, String rut, int edad){
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
    public String[] getAcompanantes() {return this.acompanantes;}
    public int getEdad(){return this.edad;}
    public int getAsientosAComprar() {return this.asientosAComprar;}
    public String[] getDiscapacidades(){return this.discapacidades;}
    
    //Setters
    public void setEdad(int edad) {this.edad = edad;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setRut(String rut) {this.rut = rut;}
    public void setAcompanantes(){}
    
    
}
