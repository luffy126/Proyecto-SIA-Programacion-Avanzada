package Clases;
import java.util.*;
import java.io.*;

/**
 *
 * @author Tenerex
 */

public class SistemaDeEntradas {
    // Declara Colecciones de Clases a usar, y un Objeto tipo GestionDeArchivos, para despues cargar bases de datos locales.
    private List<Eventos> eventos;
    private List<Clientes> clientes;
    private List<Compras> compras;
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
        if(this.clientes.size() == 0){ 
            RegistrarCliente();
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
            System.out.print("Seleccione una opción: ");
            
            // Me genero porblemas usar .nextInt, asi que se usara casteo cada vez que se quiera guardar un Int, ya que me lo recomendo varias IAs y tiene sentido
            String lineaLeida = entrada.nextLine();
            opcion = Integer.parseInt(lineaLeida);
            
            limpiarConsola();
            
            
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
                    ListarEvento();
                    break;
                case 3:
                    RemoverEvento();
                    break;
                case 4:
                    ModificarEvento();
                    break;
                case 5:
                    ListarClientes();
                    break;
                case 6:
                    RegistrarCliente();
                    break;
                   
            }
        }
    }
    
    public List<Eventos> getTodosLosEventos() {
        return eventos;
    }
    
    public Eventos buscarEventosPorID(int id) {

        for(Eventos e : eventos) {
            if(e.getID() == id){
            return e;
            }
        }
        return null;
    }
    
    public void CrearEvento() {
        System.out.println("Ingrese nombre del evento:");
        String nombre = entrada.nextLine();
        
        System.out.println("Capacidad del evento:");
        int capacidad = Integer.parseInt(entrada.nextLine());
        
        int ID = (100 + eventos.size()); // Otra forma de decir, evento 100, 101, 102, para que se vea mas profesional.
        System.out.println("ID del evento creado: " + ID);
        Eventos nuevoEvento = new Eventos(nombre, capacidad, ID);
        eventos.add(nuevoEvento);
        System.out.println("Evento ID:" + ID + " - " + nombre + " se ha creado satisfactoriamente.");
    }
    
    public void ListarEvento() {
        System.out.println("Eventos: " + eventos);
    }
    public void ListarClientes() {
        System.out.println("Clientes: " + clientes.toString());
    }
    
    public void RemoverEvento() {
        // Implementación pendiente
        System.out.println("Función de remover evento no implementada aún.");
    }
    
    public void ModificarEvento() {
        // Implementación pendiente
        System.out.println("Función de modificar evento no implementada aún.");
    }
    public void RegistrarCliente() {
        int edad;
        Clientes nuevoCliente = new Clientes();
        
        if(clientes.size() == 0){
            System.out.println("Es tu primera vez ejecutando Sistema de Entradas.");
            System.out.println("Deberas crear una cuenta para poder administrar eventos!");
        }
        
        System.out.println("Ingrese su nombre: ");
        nuevoCliente.nombre = entrada.nextLine();
        System.out.println("Ingrese su RUT: ");
        nuevoCliente.rut = entrada.nextLine();
        System.out.println("Ingrese su edad: ");
        edad = Integer.parseInt(entrada.nextLine());
        while(true){
                if(edad < 16 || edad > 120){
                System.out.println("Debes ingresar una edad valida! (16 - 120 años).");
                edad = Integer.parseInt(entrada.nextLine());
                } else{break;}
                
            }
        nuevoCliente.edad = edad;
        System.out.println("Gracias por registrarte!");
        clientes.add(nuevoCliente);
        
    }
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 5; i++) {
        System.out.println();
        }
    }
}
    

