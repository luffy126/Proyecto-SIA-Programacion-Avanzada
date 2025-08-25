package Clases;
import java.util.*;
import java.io.*;

/**
 *
 * @author Tenerex
 */

public class SistemaDeEntradas {
    // Declara Colecciones de Clases a usar, y un Objeto tipo GestionDeArchivos, para despues cargar bases de datos locales.
    private List<Evento> eventos;
    private List<Cliente> clientes;
    private List<Compra> compras;
    private GestionDeArchivos gestorArchivos;
    private boolean apagarSistema;
    private ValidarEntradas validador;
    
    // Objeto para leer, me parece mejor que el InputStreamBuffer, y basicamente es un SuperObjeto que le pone una skin mas bonita al BufferedReader
    private Scanner entrada = new Scanner(System.in); 
    
   
    // METODO CONSTRUCTOR
    public SistemaDeEntradas(){
        this.eventos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.apagarSistema = false;
    } 
    
    // METODO QUE EJECUTA TODO.
    public static void main(String[] args) {
        /* Esta es la clase principal tronco del proyecto, encargada de sostener todo */
        System.out.println("Se esta ejecutando el Sistema de Gestion de Entradas.");
        SistemaDeEntradas programa = new SistemaDeEntradas();
        programa.iniciarSistema();
    } 
    
    // METODOS PARA LA LOGICA DEL PROGRAMITA WOM
    public void iniciarSistema (){
        int opcion;
        
        // Solicita crear obligatoriamente un cliente, si es que no existe (proximamente tendra mas utilidad cuando haya persistencia a traves de archivos).
        if(this.clientes.isEmpty()){ 
            RegistrarCliente();
            limpiarConsola();
        }
        while(apagarSistema == false){  
            
            System.out.println("=== SISTEMA DE GESTIÓN DE ENTRADAS ===");
            System.out.println("1. Crear Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("3. Remover Evento");
            System.out.println("4. Modificar Evento");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Registrar Cliente");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.print(">> Seleccione una opción: ");
            
            limpiarConsola();
            
            // Me genero porblemas usar .nextInt, asi que se usara casteo cada vez que se quiera guardar un Int, ya que me lo recomendo varias IAs y tiene sentido
            opcion = Integer.parseInt(entrada.nextLine());
            
            switch(opcion){
                case 0:
                    System.out.println("Has salido exitosamente del Sistema de Entradas, gracias por preferirnos!");
                    apagarSistema = true;
                    break;
                case 1:
                    try {
                        CrearEvento();
                        break;
                    }
                    catch(Exception e) {
                        System.out.println("No se pudo crear evento.");
                        break;
                    }
                    
                case 2:
                    ListarEventos(getTodosLosEventos());
                    break;
                case 3:
                    RemoverEvento();
                    break;
                case 4:    
                    ModificarEvento();
                    break;
                case 5:
                    ListarClientes(clientes);
                    break;
                case 6:
                    RegistrarCliente();
                    break;
                   
            }
        }
    }
    
    public void CrearEvento() {
        System.out.println("Ingrese nombre del evento:");
        String nombre = entrada.nextLine();
        
        System.out.println("Capacidad del evento:");
        int capacidad = Integer.parseInt(entrada.nextLine());
        
        Random random = new Random();
        
        int ID = (random.nextInt(999)); 
        System.out.println("ID del evento creado: " + ID);
        Evento nuevoEvento = new Evento(nombre, capacidad, ID);
        eventos.add(nuevoEvento);
        System.out.println("Evento ID:" + ID + " - " + nombre + " se ha creado satisfactoriamente.");
    }
    
    public void ModificarEvento() {
        // Implementación pendiente [X]
        int idBuscado, opcion, posEvento = -1;
        boolean encontrado = false;
        
        if(eventos.isEmpty() || eventos == null){
            System.out.println("No hay eventos para mostrar.");
            return;
        }
        
        System.out.println("Ingrese ID del Evento: ");
        idBuscado = Integer.parseInt(entrada.nextLine());

        for (int i = 0; i < eventos.size(); i++) {
            
            if(idBuscado == eventos.get(i).ID){
                posEvento = i;
                encontrado = true;
            }
            
        }
        
        if (!encontrado) {
            System.out.println("No se encontro el evento " + idBuscado + ".");
            return;
        } else{
            
            System.out.println("Se ha encontrado el evento " + idBuscado + ".");
            System.out.println("----------------------------------------------");
            System.out.println("Que quiere modificar del evento?");
            System.out.println("1. Nombre");
            System.out.println("2. Capacidad de Personas");
            System.out.println("3. Ubicacion");
            System.out.println("4. Reservar Sillas");
            System.out.println("0. Cancelar");
            
            opcion = Integer.parseInt(entrada.nextLine());
            
            switch(opcion){
                case 0:
                    return;
                case 1:
                    String nombre = entrada.nextLine();
                    eventos.get(posEvento).nombre = nombre;
                    System.out.println("Se cambio el nombre a " + nombre + "!");
                    break;
                case 2:
                    System.out.println("Ingrese la nueva capacidad de personas en el evento: ");
                    int capacidad = Integer.parseInt(entrada.nextLine());
                    eventos.get(posEvento).capacidadPersonas = capacidad;
                    System.out.println("Capacidad actualizada!");
                    break;
                case 3:
                    System.out.println("Donde se realizara este evento: ");
                    eventos.get(posEvento).ubicacion = entrada.nextLine();
                    System.out.println("Ubicacion actualizada a " + eventos.get(posEvento).ubicacion);
                    break;
                case 4:
                    System.out.println("Cuantas sillas quiere reservar?");
                    int numeroSillasAReservar = Integer.parseInt(entrada.nextLine());

                    // Crear arreglo del tamaño que pidió el usuario
                    break;
 
            }
        }

    }
        @SuppressWarnings("UnnecessaryReturnStatement")
    public void RemoverEvento() {
        int idABorrar;
        int indice;
        Evento eventoEncontrado;
        System.out.println("Función de remover evento no implementada aún.");
        if (eventos.isEmpty() || eventos == null) {
            System.out.println("No hay eventos para borrar.");
            return;
        } else {
            System.out.println("Los eventos registrados son los siguientes: ");
            ListarEventos(eventos);
            idABorrar = Integer.parseInt(entrada.nextLine());
            eventoEncontrado = buscarEventosPorID(idABorrar);
            indice = eventos.indexOf(eventoEncontrado);
            eventos.remove(indice);
            System.out.println("Se ha eliminado el evento con ID: " + eventos.get(indice).ID);
        }
           
    }
    
    public void ListarEventos(List<Evento> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            System.out.println("No hay eventos para mostrar.");
            return;
        }
        
        for (Evento e : eventos) {
            String txt = "" + (eventos.size() + "1=. " + e.nombre + ", ID:" + e.ID);
            System.out.println(txt);
        }
  
    }
    public List<Evento> getTodosLosEventos() {
        return eventos;
    }
    
    public Evento buscarEventosPorID(int id) {

        for(Evento e : eventos) {
            if(e.getID() == id){
            return e;
            }
        }
        return null;
    }
    
    public void RegistrarCliente() {
        int edad;
        String rut;
        String nombre;
        
        Cliente nuevoCliente;
        
        if(clientes == null || clientes.isEmpty()){
            System.out.println("");
            System.out.println("-------------------------------------------------------");
            System.out.println("Es tu primera vez ejecutando Sistema de Entradas.");
            System.out.println("Deberas crear una cuenta para poder administrar eventos!");
            System.out.println("-------------------------------------------------------");
        }
        
        System.out.println("Ingrese su nombre: ");
        nombre = entrada.nextLine();
        
        System.out.println("Ingrese su RUT: ");
        rut = entrada.nextLine();
        
        System.out.println("Ingrese su edad: ");
        
        edad = Integer.parseInt(entrada.nextLine());
        
        while(true){
                if(edad < 16 || edad > 120){
                System.out.println("Debes ingresar una edad valida! (16 - 120 años).");
                edad = Integer.parseInt(entrada.nextLine());
                } else{break;}
                
            }
        
        nuevoCliente = new Cliente(nombre, rut, edad); 
                
        nuevoCliente.setEdad(edad);
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setRut(rut);
        
        System.out.println("Gracias por registrarte!");
        clientes.add(nuevoCliente);
        
    }
    
    public void ListarClientes(List<Cliente> clientes) {
         if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        for (Cliente c : clientes) {
            String txt = "" + (clientes.size() + c.nombre + ", RUT:" + c.rut);
            System.out.println(txt);
        }
    }
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 2; i++) {
        System.out.println();
        }
    }
}
    

