package Clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author parki9999
 */
class GestionArchivos {
    private static final String RUTA_EVENTOS = "Assets/eventosRegistrados.csv";
    private static final String RUTA_CLIENTES = "Assets/clientesRegistrados.csv";
    
    public void GestorArchivos(){
    
        File directorio = new File("Assets");
        
        if(!directorio.exists()){
            directorio.mkdirs();
            System.out.println("Se ha creado el directorio Assets.");
        }
    }
    
    public List<Evento> cargarEventos() {
        System.out.println("aloha");
        List<Evento> listaEventos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // formato de LocalDate

        File archivo = new File(RUTA_EVENTOS);
        if (!archivo.exists()) {
            return listaEventos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_EVENTOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // separar por coma
                if (datos.length >= 3) { 
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    int capacidad = Integer.parseInt(datos[2]);
                    String ubicacion = datos[3];
                    LocalDate fecha = LocalDate.parse(datos[4], formatter);
                    String orador = datos[5];
                    String temaEvento = datos[6];
                    String descripcion = datos[7];
                    int asientosEspeciales = Integer.parseInt(datos[8]);
                    int precioEntrada = Integer.parseInt(datos[9]);

                    Evento e = new Evento(nombre, capacidad, id, ubicacion, fecha, orador, temaEvento, descripcion, asientosEspeciales, precioEntrada);
                    listaEventos.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEventos;
    }
    
    public void guardarEvento(Evento evento) {
        try (FileWriter fw = new FileWriter(RUTA_EVENTOS, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
        
            out.println(evento.getID() + "," + evento.getNombre() + "," + evento.getCapacidad() + "," + evento.getUbicacion() 
                    + "," + evento.getFechaEvento() + "," + evento.getOrador() + "," + evento.getTemaEvento() + "," 
                    + evento.getDescripcionEvento()+ "," + evento.getAsientosEspeciales() + "," + evento.getPrecioEntrada());
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
