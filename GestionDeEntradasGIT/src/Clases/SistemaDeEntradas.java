package Clases;
import InterfazSwing.Menu;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Tenerex
 */

// GITHUB: https://github.com/luffy126/Proyecto-SIA-Programacion-Avanzada

public class SistemaDeEntradas {
    // Declaracion de  Colecciones de Clases a usar, y un Objeto tipo GestionDeArchivos, para despues cargar bases de datos locales.
    private List<Evento> eventos;
    private List<Cliente> clientes;
    private List<Compra> compras;
    private GestionArchivos gestor = new GestionArchivos();
    private boolean apagarSistema;
    private ValidarEntradas validador;
    private Menu interfaz;
    
    // Objeto para leer, me parece mejor que el InputStreamBuffer, y burdamente es un SuperObjeto que le pone una skin mas bonita al BufferedReader
    private Scanner entrada = new Scanner(System.in); 
    
    // METODO CONSTRUCTOR
    public SistemaDeEntradas(){
        this.eventos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.apagarSistema = false;
        
    } 
    
    // getters (faltaban pq si)
    public List<Evento> getEventos() { return eventos; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Compra> getCompras() { return compras; }
    
    // METODO QUE EJECUTA TODO.
    public static void main(String[] args) {
        
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("No se pudo estilizar el Menu a 'FlatDarkLaf'");
        }
        
        /* Esta es la clase principal tronco del proyecto, encargada de sostener todo */
        System.out.println("Se esta ejecutando el Sistema de Gestion de Entradas.");
        SistemaDeEntradas programa = new SistemaDeEntradas();
        
        
        
        programa.iniciarSistema();
    } 
    
    // METODOS PARA LA LOGICA DEL PROGRAMITA WOM
    public void iniciarSistema (){
        int opcion;
        eventos = gestor.cargarEventos();
        clientes = gestor.cargarClientes();
        interfaz = new Menu(this);

        javax.swing.SwingUtilities.invokeLater(() -> {
        // o aqui?
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
        });
        
        if(this.clientes.isEmpty()){ 
            RegistrarCliente();
            limpiarConsola();
        }
        
        while(apagarSistema == false){  
            
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
                        // CrearEvento();
                        break;
                    }
                    catch(Exception e) {
                        System.out.println("No se pudo crear evento.");
                        break;
                    }
                    
                case 2:
                    // ListarEventos(getTodosLosEventos());
                    System.out.println("");
                    System.out.println(">> Presione enter para continuar.");
                    entrada.nextLine();
                    break;
                case 3:
                    RemoverEvento();
                    break;
                case 4:    
                    // ModificarEvento();
                    break;
                case 5:
                    ListarClientes(clientes);
                    System.out.println("");
                    System.out.println(">> Presione enter para continuar.");
                    entrada.nextLine();
                    break;
                case 6:
                    RegistrarCliente();
                    break;
                case 7: 
                    ModificarCliente();
                    break;
                case 8:
                    removerCliente();
                    break;
                case 9:
                    CrearCompra();
                    break;
                case 10:
                    ListarCompra(compras);
                    System.out.println("");
                    System.out.println(">> Presione enter para continuar.");
                    entrada.nextLine();
                    break;
                case 11:
                    ModificarCompra();
                    break;
                case 12:
                    EliminarCompra();
                    break;
                   
            }
        }
    }
    
    public void ModificarCliente() {
        System.out.println("falta implementarlo !!! venga mas tarde");
    }
    
    public void ModificarCompra() {
        System.out.println("falta implementarlo !!! venga mas tarde");
    }
    
    public void EliminarCompra() {
        System.out.println("falta implementarlo !!! venga mas tarde");
    }
    
    public Compra registrarCompra(Evento evento, Cliente cliente, int cantidadAsientos, String formaDePago) {
            
        Compra compra = evento.CrearOrdenDeCompra(cliente, cantidadAsientos, formaDePago);
        this.compras.add(compra);
        
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
        
        return compra;
    }
    
    public void CrearCompra() {
        if (eventos.isEmpty()) { // si no hay eventos
            System.out.println("No hay eventos para comprar entradas");
            return;
        }
        
        System.out.println("Eventos disponibles: ");
        for (int i = 0; i < eventos.size(); i++) {
            Evento ev = eventos.get(i);
            System.out.println((i + 1) + ". " + ev.getNombre() + " (ID: " + ev.getID() + ")");
        }
        
        System.out.println("Ingrese ID del evento deseado: ");
        int opcionEvento = Integer.parseInt(entrada.nextLine());
        Evento eventoSeleccionado = buscarEventosPorID(opcionEvento);
        if (eventoSeleccionado == null) {
            System.out.println("No se ha encontrado el evento solicitado");
        }
        
        System.out.println("Ingrese RUT del cliente: ");
        String rut = entrada.nextLine();
        Cliente cliente = buscarClientePorRUT(rut);
        
        if (cliente == null) {
            System.out.println("No se encontró el rut del cliente en el sistema");
            return;
        }
        
        System.out.println("Ingrese la cantidad de asientos a comprar: ");
        int asientosAComprar = Integer.parseInt(entrada.nextLine());
        
        System.out.println("Ingrese la forma de pago: ");
        String formaDePago = entrada.nextLine();
        
        System.out.println("✅");
        System.out.println(
            "Evento: " + eventoSeleccionado.getNombre() +
            " | Cliente: " + cliente.getNombre() +
            " | Asientos a comprar: " + asientosAComprar +
            " | Forma de pago: " + formaDePago
        );

        try {
            
            Compra compra = registrarCompra(eventoSeleccionado, cliente, asientosAComprar, formaDePago);
            System.out.println("✅");
            System.out.println("✅Compra realizada con éxito.");
            System.out.println("ID de Orden de Compra: " + compra.getOrdenDeCompra());
            System.out.println("Monto total: $" + compra.getMontoTotal());
            System.out.println("✅");
        }    
        
        catch (IllegalArgumentException e) {
            System.out.println("No se pudo realizar la compra: " + e.getMessage());
        }
    }
    
    public void CrearEvento(String nombre, String capacidadStr, String ubicacion, 
                                       String fechaStr, String orador, String temaEvento, 
                                       String descripcion, String asientosStr, String precioStr) {
        try {
            int capacidad = Integer.parseInt(capacidadStr);
            int asientosDiscapacidades = Integer.parseInt(asientosStr);
            int precioEntrada = Integer.parseInt(precioStr);

            LocalDate fecha = LocalDate.parse(fechaStr); // formato: yyyy-MM-dd

            Random random = new Random();
            int ID = random.nextInt(9999);

            Evento nuevoEvento = new Evento(
                    nombre, capacidad, ID, ubicacion, fecha,
                    orador, temaEvento, descripcion, asientosDiscapacidades, precioEntrada
            );

            eventos.add(nuevoEvento);
            gestor.guardarEvento(nuevoEvento);

            JOptionPane.showMessageDialog(null,
                    "Evento creado satisfactoriamente!\nID: " + ID + " - " + nombre,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: Algunos valores numéricos no son válidos.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: La fecha debe tener el formato yyyy-MM-dd",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ModificarEventoSwingPanel() {
        if (eventos == null || eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para mostrar.");
            return;
        }

        // Pedir ID del evento
        String idStr = JOptionPane.showInputDialog("Ingrese ID del Evento:");
        if (idStr == null) return; // Cancelar
        int idBuscado = Integer.parseInt(idStr);

        Evento eventoSeleccionado = null;
        for (Evento e : eventos) {
            if (e.getID() == idBuscado) {
                eventoSeleccionado = e;
                break;
            }
        }

        if (eventoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el evento " + idBuscado + ".");
            return;
        }

        // Crear panel con campos para modificar
        JTextField nombreField = new JTextField(eventoSeleccionado.getNombre());
        JTextField capacidadField = new JTextField(String.valueOf(eventoSeleccionado.getCapacidad()));
        JTextField ubicacionField = new JTextField(eventoSeleccionado.getUbicacion());
        JTextField fechaField = new JTextField(eventoSeleccionado.getFechaEvento().toString());
        JTextField oradorField = new JTextField(eventoSeleccionado.getOrador());
        JTextField temaField = new JTextField(eventoSeleccionado.getTemaEvento());
        JTextField descripcionField = new JTextField(eventoSeleccionado.getDescripcionEvento());
        JTextField asientosField = new JTextField(String.valueOf(eventoSeleccionado.getAsientosEspeciales()));
        JTextField precioField = new JTextField(String.valueOf(eventoSeleccionado.getPrecioEntrada()));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Capacidad:")); panel.add(capacidadField);
        panel.add(new JLabel("Ubicación:")); panel.add(ubicacionField);
        panel.add(new JLabel("Fecha (yyyy-MM-dd):")); panel.add(fechaField);
        panel.add(new JLabel("Orador:")); panel.add(oradorField);
        panel.add(new JLabel("Tema del evento:")); panel.add(temaField);
        panel.add(new JLabel("Descripción:")); panel.add(descripcionField);
        panel.add(new JLabel("Asientos especiales:")); panel.add(asientosField);
        panel.add(new JLabel("Precio de entrada:")); panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(null, panel, 
            "Modificar Evento ID: " + idBuscado, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                eventoSeleccionado.setNombre(nombreField.getText());
                eventoSeleccionado.setCapacidadPersonas(Integer.parseInt(capacidadField.getText()));
                eventoSeleccionado.setUbicacion(ubicacionField.getText());
                eventoSeleccionado.setFechaEvento(LocalDate.parse(fechaField.getText()));
                eventoSeleccionado.setOrador(oradorField.getText());
                eventoSeleccionado.setTemaEvento(temaField.getText());
                eventoSeleccionado.setDescripcionEvento(descripcionField.getText());
                eventoSeleccionado.setAsientosEspeciales(Integer.parseInt(asientosField.getText()));
                eventoSeleccionado.setPrecioEntrada(Integer.parseInt(precioField.getText()));

                gestor.guardarEvento(eventos);
                JOptionPane.showMessageDialog(null, "Evento modificado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar el evento: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(null, "No hay eventos para mostrar.", "Listado de Eventos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear un JTextArea para mostrar los eventos
        JTextArea textArea = new JTextArea(20, 50); // 20 filas, 50 columnas
        textArea.setEditable(false); // que no se pueda editar

        // Construir el texto con los eventos
        StringBuilder sb = new StringBuilder();
        for (Evento e : eventos) {
            sb.append("Nombre del evento: ").append(e.getNombre())
              .append(", ID: ").append(e.getID())
              .append(", Tema: ").append(e.getTemaEvento()).append("\n");
            sb.append("Capacidad: ").append(e.getCapacidad())
              .append(", Precio: $").append(e.getPrecioEntrada())
              .append(", Orador: ").append(e.getOrador()).append("\n");
            sb.append("Ubicación: ").append(e.getUbicacion())
              .append(", Fecha: ").append(e.getFechaEvento()).append("\n");
            sb.append("\n"); // línea en blanco entre eventos
        }

        textArea.setText(sb.toString());

        // Mostrar en un JScrollPane dentro de un JOptionPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Listado de Eventos", JOptionPane.INFORMATION_MESSAGE);
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
            gestor.guardarCliente(nuevoCliente);
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
        
        nuevoCliente = new Cliente(nombre, rut, edad, 0, "", null); 
        
        // ✅ PREGUNTAR SI DESEA INGRESAR DISCAPACIDAD
        System.out.println("¿Desea registrar alguna discapacidad para este cliente? (s/n): ");
        String opcionDiscapacidad = entrada.nextLine();
        
        if (opcionDiscapacidad.toLowerCase().equals("s")) {
            System.out.println("Ingrese la discapacidad: ");
            String discapacidad = entrada.nextLine();
            
            nuevoCliente.setEdad(edad);
            nuevoCliente.setNombre(nombre);
            nuevoCliente.setRut(rut);
            nuevoCliente.setDiscapacidades(discapacidad);
            
        } else {
        
            nuevoCliente.setEdad(edad);
            nuevoCliente.setNombre(nombre);
            nuevoCliente.setRut(rut);
            
            
        }

        gestor.guardarCliente(nuevoCliente);
        clientes.add(nuevoCliente);
        System.out.println("Gracias por registrarte!");
        
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
        for (Cliente cliente : clientes) { // Itera sobre los objetos cliente en la lista
            if (cliente.getRut().equals(rut)) { // Compara con el getter de la instancia actual al rut recibido.
                clienteEncontrado = cliente;
                break; 
            }
        }
    } else {
        System.out.println("No se encontro cliente con ese rut.");
        return null;
    }
    
    return clienteEncontrado;
}
    
    public void ListarCompra(List<Compra> compras) {
    
        if (compras.isEmpty() || compras == null){
            System.out.println("No existen compradas realizadas.");
        } else {
            
            System.out.println("Las compras son las siguentes: ");
            for (Compra c : compras) {
                System.out.println((compras.indexOf(c)+1) + ". Orden de Compra: " + compras.get(compras.indexOf(c)).getOrdenDeCompra() + " - Rut asociado: " + compras.get(compras.indexOf(c)).getRut() + " - Monto total (CLP): $" + compras.get(compras.indexOf(c)).getMontoTotal());
            }
           
        }
        
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
            clientes.remove(cliente);  
            } 
            catch(Exception e) {
                System.out.println("Fallo en la funcion buscar Cliente por RUT.");
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
    

