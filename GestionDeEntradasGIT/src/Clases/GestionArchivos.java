package Clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author parki9999
 */
public class GestionArchivos {
    private static final String RUTA_EVENTOS = "Assets/eventosRegistrados.csv";
    private static final String RUTA_CLIENTES = "Assets/clientesRegistrados.csv";
    
    public void GestorArchivos(){
    
        File directorio = new File("Assets");
        
        if(!directorio.exists()){
            directorio.mkdirs();
            System.out.println("Se ha creado el directorio Assets.");
        }
    }
    
    public void guardarEvento(Evento evento) {
        try (FileWriter fw = new FileWriter(RUTA_EVENTOS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
        
            out.println(evento.getID() + "," + evento.getNombre() + "," + evento.getFechaEvento());
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
