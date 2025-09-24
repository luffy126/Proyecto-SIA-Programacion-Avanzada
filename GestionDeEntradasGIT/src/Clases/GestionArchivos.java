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
    
    public GestionArchivos() {
        File directorio = new File("Assets");
        if (!directorio.exists()) {
            directorio.mkdirs();
            System.out.println("Se ha creado el directorio Assets.");
        }

        // Crear archivos si no existen
        try {
            File archivoEventos = new File(directorio, "eventosRegistrados.csv");
            if (!archivoEventos.exists()) {
                archivoEventos.createNewFile();
                System.out.println("Se ha creado el archivo eventosRegistrados.csv");
            }

            File archivoClientes = new File(directorio, "clientesRegistrados.csv");
            if (!archivoClientes.exists()) {
                archivoClientes.createNewFile();
                System.out.println("Se ha creado el archivo clientesRegistrados.csv");
            }
        } catch (IOException e) {
            // Manejar el error
            System.out.println("Error al crear los archivos: " + e.getMessage());
            e.printStackTrace();
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
                if (datos.length == 10) { 
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
    
    public List<Cliente> cargarClientes() {
        System.out.println("aloha de nuevo");
        List<Cliente> listaClientes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // formato de LocalDate

        File archivo = new File(RUTA_CLIENTES);
        if (!archivo.exists()) {
            return listaClientes;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CLIENTES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); // separar por coma
                if (datos.length == 6) { 
                    String rut = datos[0];
                    String nombre = datos[1];
                    String acompanantes = datos[2];
                    int edad = Integer.parseInt(datos[3]);
                    int asientos = Integer.parseInt(datos[4]);
                    String discapacidades = datos[5];
                    
                    Cliente c = new Cliente(nombre, rut, edad, asientos, acompanantes, discapacidades);
                    listaClientes.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes;
    }
    
    public void guardarEvento(Evento evento) {
        System.out.println("Guardando evento: " + evento.getNombre());
        try (FileWriter fw = new FileWriter(RUTA_EVENTOS, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(evento.getID() + "," + evento.getNombre() + "," + evento.getCapacidad() + "," + evento.getUbicacion()
                    + "," + evento.getFechaEvento() + "," + evento.getOrador() + "," + evento.getTemaEvento() + ","
                    + evento.getDescripcionEvento() + "," + evento.getAsientosEspeciales() + "," + evento.getPrecioEntrada());

            System.out.println("Evento guardado en CSV.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarEvento(List<Evento> eventos) { // guardarEvento sobrecargado, si le llega un evento lo inserta y si le llega la lista lo modifica
        System.out.println("Guardando lista completa de eventos...");
        try (FileWriter fw = new FileWriter(RUTA_EVENTOS, false); 
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (Evento evento : eventos) {
                out.println(evento.getID() + "," + evento.getNombre() + "," + evento.getCapacidad() + "," + evento.getUbicacion()
                        + "," + evento.getFechaEvento() + "," + evento.getOrador() + "," + evento.getTemaEvento() + ","
                        + evento.getDescripcionEvento() + "," + evento.getAsientosEspeciales() + "," + evento.getPrecioEntrada());
            }

            System.out.println("Lista de eventos sobrescrita en CSV.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarCliente(Cliente cliente) {
        System.out.println("Guardando cliente: " + cliente.getNombre());
        try (FileWriter fw = new FileWriter(RUTA_CLIENTES, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(cliente.getRut()+ "," + cliente.getNombre() + "," + cliente.getAcompanantes()
                    + "," + cliente.getEdad() + "," + cliente.getAsientosAComprar()+ "," + cliente.getDiscapacidades());
            System.out.println("Cliente guardado en CSV.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarCliente(List<Cliente> clientes) { // guardarCliente sobrecargado, si le llega un cliente lo inserta y si le llega la lista lo modifica
        System.out.println("Guardando lista completa de clientes...");
        try (FileWriter fw = new FileWriter(RUTA_CLIENTES, false); 
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            for (Cliente cliente : clientes) {
                out.println(cliente.getRut()+ "," + cliente.getNombre() + "," + cliente.getAcompanantes()
                + "," + cliente.getEdad() + "," + cliente.getAsientosAComprar()+ "," + cliente.getDiscapacidades());
            }
            System.out.println("Lista de clientes sobrescrita en CSV.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
