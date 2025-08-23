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
        
        while(apagarSistema == false){
            System.out.println("=== SISTEMA DE GESTIÓN DE ENTRADAS ===");
            System.out.println("1. Crear Evento");
            System.out.println("2. Listar Eventos");
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
                    /*
                    Aqui deberia ir un CrearEvento();
                    break;
                    */
                case 2:
                    /*
                    Aqui deberia ir un ListarEvento();
                    break;
                    */  
            }
        }
    }
    
    // METODOS SETTERS Y GETTERS (FALTAN HACERLOS UWU)
    
    
    
    
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 15; i++) {
        System.out.println();
    }
}
}
    

