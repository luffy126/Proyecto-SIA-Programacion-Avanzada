package Clases;
import java.util.*;
import java.io.*;

/**
 *
 * @author Tenerex
 */

 /**
 * MÉTODOS PRINCIPALES PARA LISTAS EN JAVA
 * 
 * // MÉTODOS DE AGREGACIÓN
 * add(E element) -                         Añade un elemento al final de la lista
 * add(int index, E element) -              Inserta un elemento en la posición especificada
 * addAll(Collection<? extends E> c) -      Añade todos los elementos de una colección
 * 
 * // MÉTODOS DE ELIMINACIÓN
 * remove(int index) -          Elimina el elemento en la posición especificada
 * remove(Object o) -           Elimina la primera ocurrencia del elemento especificado
 * removeAll(Collection<?> c) - Elimina todos los elementos contenidos en la colección
 * clear() -                    Elimina todos los elementos de la lista
 * 
 * // MÉTODOS DE CONSULTA
 * get(int index) -         Devuelve el elemento en la posición especificada
 * size() -                 Devuelve el número de elementos en la lista
 * isEmpty() -              Verifica si la lista está vacía
 * contains(Object o) -     Verifica si la lista contiene el elemento especificado
 * indexOf(Object o) -      Devuelve el índice de la primera ocurrencia del elemento
 * lastIndexOf(Object o) -  Devuelve el índice de la última ocurrencia del elemento
 * 
 * // MÉTODOS DE ITERACIÓN
 * iterator() -                             Devuelve un iterador sobre los elementos de la lista
 * listIterator() -                         Devuelve un list iterator sobre los elementos de la lista
 * forEach(Consumer<? super E> action) -    Ejecuta una acción para cada elemento
 * 
 * // MÉTODOS DE CONVERSIÓN
 * toArray() -                              Convierte la lista en un array de objetos
 * toArray(T[] a) -                         Convierte la lista en un array del tipo especificado
 * 
 * // MÉTODOS DE MANIPULACIÓN
 * set(int index, E element) -              Reemplaza el elemento en la posición especificada
 * subList(int fromIndex, int toIndex) -    Devuelve una vista de una porción de la lista
 * sort(Comparator<? super E> c) -          Ordena la lista según el comparador especificado
 * 
 * // MÉTODOS DE COMPARACIÓN
 * equals(Object o) -                       Compara la lista con otro objeto para igualdad
 * hashCode() -                             Devuelve el código hash de la lista
 * 
 * // MÉTODOS DE BÚSQUEDA Y FILTRADO
 * containsAll(Collection<?> c) -           Verifica si la lista contiene todos los elementos de la colección
 * retainAll(Collection<?> c) -             Retiene solo los elementos contenidos en la colección especificada
 * 
 * // MÉTODOS DE REPLACE (Java 8+)
 * replaceAll(UnaryOperator<E> operator) -      Reemplaza cada elemento con el resultado del operador
 * removeIf(Predicate<? super E> filter) -      Elimina todos los elementos que cumplen el predicado
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
                   
            }
        }
    }
    
    
    public List<Eventos> getTodosLosEventos() {
        return eventos;
    }
    
    public Eventos buscarEventosPorID(String id) {
        Eventos eventoGuardado = null;
        int i;
        
        for(i = 0; i < eventos.size(); i++) {
            
            if
        
        
        }
        
        
        
        return eventoGuardado;
    }
    
    public void CrearEvento() {
        
    }
    
    public void ListarEvento() {
    
    }
    
    public void RemoverEvento() {
    
    }
  
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 15; i++) {
        System.out.println();
    }
}
}
    

