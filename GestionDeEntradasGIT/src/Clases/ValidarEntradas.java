package Clases;
import java.util.*;

/**
 *
 * @author Tenerex
 */
public class ValidarEntradas {
    Scanner entrada = new Scanner(System.in);

 
    
    public void validarRUT(String rut){
    }
    // public static boolean validarCapacidad(int capacidad){}
    public int validarEdad(int edad){
        int edadLocal = edad;
        while(true){
                if(edad < 16 || edad > 120){
                System.out.println("Debes ingresar una edad valida! (16 - 120 años).");
                edadLocal = Integer.parseInt(entrada.nextLine());
                } else{break;}
                
            }
            
        return edadLocal;
    }
    // public static boolean validarAsientosDisponibles(){}
 
    
}
