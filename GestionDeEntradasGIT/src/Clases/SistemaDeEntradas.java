package Clases;
import java.util.*;
import java.io.*;

/**
 *
 * @author Tenerex
 */

public class SistemaDeEntradas {
    private List<Eventos> eventos;
    private List<Clientes> clientes;
    private List<Compras> compras;
    private GestionDeArchivos gestorArchivos;
    private boolean apagarSistema;
    
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
        
        if(this.clientes.size() == 0){
            
        }
        while(apagarSistema == false){
            
            System.out.println("=== SISTEMA DE GESTIÓN DE ENTRADAS ===");
            System.out.println("1. Crear Evento");
            System.out.println("2. Listar Eventos");
            System.out.println("3. Remover Evento");
            System.out.println("4. Modificar Evento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = entrada.nextInt();

            limpiarConsola();
            
            
            switch(opcion){
                case 0:
                    System.out.println("Has salido exitosamente del Sistema de Entradas, gracias por preferirnos!");
                    apagarSistema = true;
                    break;
                case 1:
                    CrearEvento();
                    break;
                case 2:
                    ListarEvento();
                    break;
                case 3:
                    RemoverEvento();
                    break;
                case 4:
                    ModificarEvento();
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
        int capacidad = 100;
        int ID = (100 + eventos.size()); // Otra forma de decir, evento 100, 101, 102, para que se vea mas profesional.
        System.out.println("ID del evento creado: " + ID);
        Eventos nuevoEvento = new Eventos(nombre, capacidad, ID);
        System.out.println("El Evento " + ID + " - " + nombre + "se ha creado satisfactoriamente.");
    }
    
    public void ListarEvento() {
        System.out.println("Eventos:" + eventos);
    }
    
    public void RemoverEvento() {
    
    }
    
    public void ModificarEvento() {}
  
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 15; i++) {
        System.out.println();
    }
}
}
    

