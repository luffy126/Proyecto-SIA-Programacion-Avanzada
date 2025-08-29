package Clases;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Tenerex
 */

// GITHUB: https://github.com/luffy126/Proyecto-SIA-Programacion-Avanzada

public class SistemaDeEntradas {
    // Declara Colecciones de Clases a usar, y un Objeto tipo GestionDeArchivos, para despues cargar bases de datos locales.
    private List<Evento> eventos;
    private List<Cliente> clientes;
    private List<Compra> compras;
    private GestorArchivos gestorArchivos;
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
            System.out.println("7. Eliminar Cliente");
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
                case 7:
                    removerCliente();
                    break;
                   
            }
        }
    }
    
    public void CrearEvento() {
        System.out.println("Ingrese nombre del evento:");
        String nombre = entrada.nextLine();
        
        System.out.println("Capacidad del evento:");
        int capacidad = Integer.parseInt(entrada.nextLine());
        
        System.out.println("Ingrese ubicacion del evento:");
        String ubicacion = entrada.nextLine();
        
        System.out.println("Ingrese fecha del evento (Formato: año-mes-dia):");
        LocalDate fecha = LocalDate.parse(entrada.nextLine());
        
        System.out.println("Ingrese nombre del orador del evento:");
        String orador = entrada.nextLine();
        
        System.out.println("Ingrese tema del evento:");
        String temaEvento = entrada.nextLine();
        
        System.out.println("Ingrese descripción del evento:");
        String descripcion = entrada.nextLine();
        
        System.out.println("Ingrese numero de asientos destinados a discapacitados del evento:");
        int asientosDiscapacidades = Integer.parseInt(entrada.nextLine());
        
        System.out.println("Ingrese precio de la entrada al evento (en pesos):");
        int precioEntrada = Integer.parseInt(entrada.nextLine());
        
        Random random = new Random();
        
        int ID = (random.nextInt(999)); 
        System.out.println("ID del evento creado: " + ID);
        Evento nuevoEvento = new Evento(nombre, capacidad, ID, ubicacion, fecha, orador, temaEvento, descripcion, asientosDiscapacidades, precioEntrada);
        eventos.add(nuevoEvento);
        System.out.println("Evento ID:" + ID + " - " + nombre + " se ha creado satisfactoriamente.");
    }
    
    public void ModificarEvento() {
        // Implementación pendiente [X]
        // Implementacion media pendiente, quedan como la mitad de atributos para modificar
        int idBuscado, opcion, posEvento = 0;

        boolean encontrado = false;
        
        if(eventos.isEmpty() || eventos == null){
            System.out.println("No hay eventos para mostrar.");
            return;
        }
        
        System.out.println("Ingrese ID del Evento: ");
        idBuscado = Integer.parseInt(entrada.nextLine());

        for (int i = 0; i < eventos.size(); i++) {
            
            if(idBuscado == eventos.get(i).getID()){
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
            System.out.println("4. Fecha de realización");
            System.out.println("5. Orador");
            System.out.println("6. Tema del evento");
            System.out.println("7. Descripción del evento");
            System.out.println("8. Asientos para discapacitados");
            System.out.println("9. Precio de la entrada");
            System.out.println("0. Cancelar");
            
            opcion = Integer.parseInt(entrada.nextLine());
            
            switch(opcion){
                
                case 0:
                    return;
                case 1: // nombre
                    System.out.println("Ingrese el nuevo nombre del evento:");
                    String nombre = entrada.nextLine();
                    eventos.get(posEvento).setNombre(nombre);
                    System.out.println("Se cambio el nombre a " + nombre + "!");
                    break;
                case 2: // capacidad
                    System.out.println("Ingrese la nueva capacidad de personas en el evento: ");
                    int capacidad = Integer.parseInt(entrada.nextLine());
                    eventos.get(posEvento).setCapacidadPersonas(capacidad);
                    System.out.println("Capacidad actualizada!");
                    break;
                case 3: // ubicacion
                    System.out.println("Donde se realizara este evento: ");
                    String ubicacion = entrada.nextLine();
                    eventos.get(posEvento).setUbicacion(ubicacion);
                    System.out.println("Ubicacion actualizada a " + ubicacion);
                    break;
                case 4: // fecha
                    System.out.println("Ingrese la fecha donde se realizará este evento (Formato: año-mes-dia): ");
                    LocalDate fechaEvento = LocalDate.parse(entrada.nextLine());
                    eventos.get(posEvento).setFechaEvento(fechaEvento);
                    System.out.println("Fecha del evento cambiada a: " + fechaEvento);
                    break;
                case 5: // orador
                    System.out.println("Ingrese el orador que presentará el evento: ");
                    String orador = entrada.nextLine();
                    eventos.get(posEvento).setOrador(orador);
                    System.out.println("Orador cambiado a: " + orador);
                    break;
                case 6: // tema del evento
                    System.out.println("Ingrese el tema del evento: ");
                    String tema = entrada.nextLine();
                    eventos.get(posEvento).setTemaEvento(tema);
                    System.out.println("Tema del evento cambiado a: " + tema);
                    break;
                case 7: // descripcion del evento
                    System.out.println("Ingrese la descripción del evento: ");
                    String descripcion = entrada.nextLine();
                    eventos.get(posEvento).setDescripcionEvento(descripcion);
                    System.out.println("Descripción del evento cambiado a: " + descripcion);
                    break;
                case 8: // asientos para discapacitados
                    System.out.println("Ingrese la capacidad de asientos para discapacitados del evento: ");
                    int asientosDiscapacitados = Integer.parseInt(entrada.nextLine());
                    eventos.get(posEvento).setAsientosEspeciales(asientosDiscapacitados);
                    System.out.println("Asientos para discapacitados cambiados a: " + asientosDiscapacitados);
                    break;
                case 9: // precio de la entrada
                    System.out.println("Ingrese el precio de la entrada al evento: ");
                    int precio = Integer.parseInt(entrada.nextLine());
                    eventos.get(posEvento).setPrecioEntrada(precio);
                    System.out.println("Precio del evento cambiado a: " + precio);
                    break;
                    
            }
        }

    }
        @SuppressWarnings("UnnecessaryReturnStatement")
    public void RemoverEvento() {
        int idABorrar;
        int indice;
        Evento eventoEncontrado;
        
        if (eventos.isEmpty() || eventos == null) {
            System.out.println("No hay eventos para borrar.");
            return;
        } else {
            System.out.println("Los eventos registrados son los siguientes: ");
            ListarEventos(eventos);
            System.out.println("Ingrese ID de evento a eliminar: ");
            idABorrar = Integer.parseInt(entrada.nextLine());
            eventoEncontrado = buscarEventosPorID(idABorrar);
            if (eventoEncontrado == null) {
                System.out.println("No existe el evento con la id especificada");
            }
            
            indice = eventos.indexOf(eventoEncontrado);
            System.out.println("Se ha eliminado el evento con ID: " + eventos.get(indice).getID());
            eventos.remove(indice);
        }
           
    }
    
    public void ListarEventos(List<Evento> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            System.out.println("No hay eventos para mostrar.");
            return;
        }
        
        for (Evento e : eventos) {
            String txt = "Nombre del evento: " + (e.getNombre() + ", ID: " + e.getID() + ", Tema: " + e.getTemaEvento());
            String txt2 = "Capacidad: " + (e.getCapacidad() + ", Precio: $" + e.getPrecioEntrada()+ ", Orador: " + e.getOrador());
            String txt3 = "Ubicación : " + (e.getUbicacion() + ", Fecha: " + e.getFechaEvento());
            System.out.println(" ");
            System.out.println(txt);
            System.out.println(txt2);
            System.out.println(txt3);
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
            System.out.println("Es tu primera vez ejecutando Sistema de Entradas. Creando datos iniciales...");
            System.out.println("Deberas modificar esta cuenta para poder administrar eventos!");
            System.out.println("-------------------------------------------------------");
            nuevoCliente = new Cliente("Claudio Cubillos", "12.345.678-9", 99, 0, "lorem ipsum", "lorem ipsum"); 
            clientes.add(nuevoCliente);
            return;
        }
        
        System.out.println("Ingrese su nombre: ");
        nombre = entrada.nextLine();
        
        System.out.println("Ingrese su RUT: ");
        
        //Validar RUT hasta que sea correcto
        while (true) {
            rut = entrada.nextLine();

            if (Cliente.validarRut(rut)) {
                System.out.println("RUT válido ✅.");
                break;
            } else {
                System.out.println("RUT inválido ❌. Por favor, ingrese un RUT válido.");
            }
        }

        // Validar edad
        while (true) {
            try {
                System.out.println("Ingrese su edad: ");
                edad = Integer.parseInt(entrada.nextLine());

                if (edad < 16 || edad > 120) {
                    System.out.println("Debes ingresar una edad válida! (16 - 120 años).");
                } else {
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número entero!");
            }
        }
        
        nuevoCliente = new Cliente(nombre, rut, edad, 0, "lorem ipsum", "lorem ipsum"); 
                
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
        
        int i = 1;
        for (Cliente c : clientes) {
            String txt = "" + (i + ". Nombre: " + c.getNombre() + ", RUT: " + c.getRut() + ", Edad: " + c.getEdad());
            System.out.println(txt);
            i++;
        }
    }
    
    public Cliente buscarClientePorRUT(String rut) {
        
        Cliente clienteEncontrado = null;
        
        if (clientes != null) {
            
        }
        
        return clienteEncontrado;
    }
    
    public void removerCliente() {

        String rutABorrar;
        int indice;
        Cliente cliente = null;
        
        if (clientes.isEmpty() || clientes == null) {
            System.out.println("No hay clientes para eliminar");
        }
        
        else {
            System.out.println("Los clientes son los siguientes: ");
            ListarClientes(clientes);
            System.out.println("Ingrese RUT del cliente a eliminar: ");
            
            try {
            cliente = buscarClientePorRUT(entrada.nextLine());
            clientes.remove(cliente);    // ESTO NO QUIERE BORRAR Y NO SE PORQUE.
            } 
            catch(Exception e) {
                System.out.println("No se pudo encontrar el Cliente por su RUT.");
            }
            
        }
    }
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 2; i++) {
        System.out.println();
        }
    }
}
    

