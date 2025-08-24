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

    public Clientes(String rut, String nombre, int asientosAComprar, String[] acompanantes, int edad, String[] discapacidades){
        this.rut = rut;
        this.nombre = nombre;
        this.asientosAComprar = asientosAComprar;
        this.acompanantes = acompanantes;
        this.edad = edad;
        this.discapacidades = discapacidades;
        
    }

    public void  
}
