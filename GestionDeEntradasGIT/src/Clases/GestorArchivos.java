package Clases;
import java.io.*;
import java.util.*;

/**
 *
 * @author Tenerex
 */
public class GestorArchivos {
    /* Esta clase deberia poder encargarse de 3 cosas po loco, 
    1. Guardar/cargar un Evento  
    2. Guardar/cargar un Comprador 
    3. Guardar/cargar Ordenes.
    */
    private static final String RUTA_EVENTOS = "Assets/eventosRegistrados.csv";
    private static final String RUTA_CLIENTES = "Assets/clientesRegistrados.csv";
    
    public GestorArchivos(){
    
        File directorio = new File("Assets");
        
        if(!directorio.exists()){
            directorio.mkdirs();
            System.out.println("Se ha creado el directorio Assets.");
        }
    }
    
    
    
    
    
    
}
