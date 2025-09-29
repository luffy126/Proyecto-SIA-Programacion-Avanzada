package Clases;
import InterfazSwing.Menu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

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
        compras = gestor.cargarCompras(clientes, eventos);
        
        System.out.println("Compras cargadas en memoria: " + compras.size());

        interfaz = new Menu(this);

        javax.swing.SwingUtilities.invokeLater(() -> {
        // o aqui?
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
        });
        
        if(this.clientes.isEmpty()){ 
            // RegistrarCliente();
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
            System.out.println("8. Crear Orden de Compra");
            System.out.println("9. Listar Compras");
            System.out.println("7. Modificar Cliente");
            System.out.println("8. Eliminar Cliente");
            System.out.println("9. Crear Orden de Compra");
            System.out.println("10. Listar Compras");
            System.out.println("11. Modificar Compra");
            System.out.println("12. Eliminar Compra");
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
                    // RemoverEvento();
                    break;
                case 4:    
                    // ModificarEvento();
                    break;
                case 5:
                    // ListarClientes(clientes);
                    System.out.println("");
                    System.out.println(">> Presione enter para continuar.");
                    entrada.nextLine();
                    break;
                case 6:
                    // RegistrarCliente();
                    break;
                case 7: 
                    // ModificarCliente();
                    break;
                case 8:
                    // removerCliente();
                    break;
                case 9:
                    // CrearCompra();
                    break;
                case 10:
                    // ListarCompra(compras);
                    System.out.println("");
                    System.out.println(">> Presione enter para continuar.");
                    entrada.nextLine();
                    break;
                case 11:
                    // ModificarCompra();
                    break;
                case 12:
                    // EliminarCompra();
                    break;
                   
            }
        }
    }
    
    public String modificarCliente(String rutIngresado, String nuevoNombre, String nuevaEdadStr, String nuevaDiscapacidad) {
        Cliente cliente = buscarClientePorRUT(rutIngresado);
        if (cliente == null) {
            return "No se encontró un cliente con ese RUT.";
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            cliente.setNombre(nuevoNombre.trim());
        }

        if (nuevaEdadStr != null && !nuevaEdadStr.trim().isEmpty()) {
            try {
                int nuevaEdad = Integer.parseInt(nuevaEdadStr.trim());
                if (nuevaEdad < 16 || nuevaEdad > 120) {
                    return "La edad debe estar entre 16 y 120 años.";
                }
                cliente.setEdad(nuevaEdad);
            } catch (NumberFormatException e) {
                return "Edad inválida. Debe ser un número.";
            }
        }

        if (nuevaDiscapacidad != null) {
            cliente.setDiscapacidades(nuevaDiscapacidad.trim());
        }

        gestor.guardarCliente(clientes);

        return null;
    }
    
    public void ModificarCliente() {
        if (clientes == null || clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
            return;
        }

        // Crear modelo de tabla
        String[] columnas = {"RUT", "Nombre", "Edad", "Discapacidades"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Cliente c : clientes) {
            Object[] fila = {c.getRut(), c.getNombre(), c.getEdad(), c.getDiscapacidades()};
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        int option = JOptionPane.showConfirmDialog(null, scrollPane, 
                "Seleccione el cliente a modificar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) return;

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No se seleccionó ningún cliente.");
            return;
        }

        String rutSeleccionado = (String) table.getValueAt(selectedRow, 0);
        Cliente cliente = buscarClientePorRUT(rutSeleccionado);

        JTextField nombreField = new JTextField(cliente.getNombre());
        JTextField edadField = new JTextField(String.valueOf(cliente.getEdad()));
        JTextField discapacidadField = new JTextField(cliente.getDiscapacidades());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Edad:")); panel.add(edadField);
        panel.add(new JLabel("Discapacidades:")); panel.add(discapacidadField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Modificar Cliente RUT: " + cliente.getRut(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nuevoNombre = nombreField.getText().trim();
            String nuevaEdadStr = edadField.getText().trim();
            String nuevaDiscapacidad = discapacidadField.getText().trim();

            String mensajeError = null;

            if (!nuevoNombre.isEmpty()) {
                cliente.setNombre(nuevoNombre);
            }

            if (!nuevaEdadStr.isEmpty()) {
                try {
                    int nuevaEdad = Integer.parseInt(nuevaEdadStr);
                    if (nuevaEdad < 16 || nuevaEdad > 120) {
                        mensajeError = "La edad debe estar entre 16 y 120 años.";
                    } else {
                        cliente.setEdad(nuevaEdad);
                    }
                } catch (NumberFormatException e) {
                    mensajeError = "Edad inválida. Debe ser un número.";
                }
            }

            if (!nuevaDiscapacidad.isEmpty()) {
                cliente.setDiscapacidades(nuevaDiscapacidad);
            }

            if (mensajeError != null) {
                JOptionPane.showMessageDialog(null, mensajeError);
            } else {
                gestor.guardarCliente(clientes); // Guardar cambios en CSV
                JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
            }
        }
    }

    public void modificarCompraSwing() {
        List<Compra> todasCompras = new ArrayList<>();
        for (Evento ev : eventos) {
            todasCompras.addAll(ev.getCompras());
        }

        if (todasCompras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay compras registradas en el sistema.");
            return;
        }

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JComboBox<Compra> comboCompras = new JComboBox<>();
        for (Compra c : todasCompras) {
            comboCompras.addItem(c);
        }

        comboCompras.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Compra) {
                    Compra compra = (Compra) value;
                    Evento evento = buscarEventoPorCompra(compra);
                    setText("ID: " + compra.getOrdenDeCompra() +
                            " | Cliente: " + compra.getRut() +
                            (evento != null ? " | Evento: " + evento.getNombre() : ""));
                }

                return this;
            }
        });

        JTextField txtFormaPago = new JTextField();
        JTextField txtEstado = new JTextField();

        panel.add(new JLabel("Seleccione Compra:"));
        panel.add(comboCompras);
        panel.add(new JLabel("Nueva forma de pago:"));
        panel.add(txtFormaPago);
        panel.add(new JLabel("Nuevo estado:"));
        panel.add(txtEstado);

        JButton btnModificar = new JButton("Modificar Compra");
        btnModificar.addActionListener(e -> {
            Compra compraSeleccionada = (Compra) comboCompras.getSelectedItem();
            if (compraSeleccionada == null) return;

            String nuevaFormaPago = txtFormaPago.getText().trim();
            String nuevoEstado = txtEstado.getText().trim();

            if (!nuevaFormaPago.isEmpty()) {
                compraSeleccionada.setFormaDePago(nuevaFormaPago);
            }
            if (!nuevoEstado.isEmpty()) {
                compraSeleccionada.setEstadoDeCompra(nuevoEstado);
            }

            Evento evento = buscarEventoPorCompra(compraSeleccionada);
            if (evento != null) {
                gestor.guardarCompra(evento.getCompras());
            }

            JOptionPane.showMessageDialog(null, "Compra modificada correctamente!");
        });

        panel.add(btnModificar);
        JOptionPane.showMessageDialog(null, panel, "Modificar Compra", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * método auxiliar para buscar evento dew una compra
     */
    private Evento buscarEventoPorCompra(Compra compra) {
        for (Evento ev : eventos) {
            if (ev.getCompras().contains(compra)) {
                return ev;
            }
        }
        return null;
    }

    public void eliminarCompra() {
        List<Compra> todasCompras = new ArrayList<>();
        for (Evento ev : eventos) {
            todasCompras.addAll(ev.getCompras());
        }

        if (todasCompras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay compras registradas en el sistema.");
            return;
        }

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JComboBox<Compra> comboCompras = new JComboBox<>();
        for (Compra c : todasCompras) {
            comboCompras.addItem(c);
        }

        comboCompras.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Compra) {
                    Compra compra = (Compra) value;
                    Evento evento = buscarEventoPorCompra(compra);
                    setText("ID: " + compra.getOrdenDeCompra() +
                            " | Cliente: " + compra.getRut() +
                            (evento != null ? " | Evento: " + evento.getNombre() : ""));
                }
                return this;
            }
        });

        panel.add(new JLabel("Seleccione la compra a eliminar:"));
        panel.add(comboCompras);

        JButton btnEliminar = new JButton("Eliminar Compra");

        btnEliminar.addActionListener(e -> {
            Compra compraSeleccionada = (Compra) comboCompras.getSelectedItem();
            if (compraSeleccionada == null) return;

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de eliminar la compra con ID " + compraSeleccionada.getOrdenDeCompra() + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            // Eliminar de evento
            Evento evento = buscarEventoPorCompra(compraSeleccionada);
            if (evento != null) {
                evento.getCompras().remove(compraSeleccionada);
            }

            // Eliminar de cliente
            Cliente cliente = buscarClientePorRUT(compraSeleccionada.getRut());
            if (cliente != null) {
                cliente.getComprasClientes().remove(compraSeleccionada);
            }

            // Eliminar de lista general en memoria
            todasCompras.remove(compraSeleccionada);

            // reescribir todo
            gestor.guardarCompra(todasCompras);
            gestor.guardarCliente(clientes);
            gestor.guardarEvento(eventos);

            JOptionPane.showMessageDialog(null, "Compra eliminada correctamente.");
        });

        panel.add(btnEliminar);
        JOptionPane.showMessageDialog(null, panel, "Eliminar Compra", JOptionPane.PLAIN_MESSAGE);
    }

    
    public Compra registrarCompra(Evento evento, Cliente cliente, int cantidadAsientos, String formaDePago) {
            
        Compra compra = evento.CrearOrdenDeCompra(cliente, cantidadAsientos, formaDePago, evento.getID());
        this.compras.add(compra);
        
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        }
        
        return compra;
    }
    
    public void CrearCompra() {
        // Panel principal para los inputs
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        // ComboBox de eventos
        JComboBox<Evento> comboEventos = new JComboBox<>();
        for (Evento ev : eventos) {
            comboEventos.addItem(ev);
        }

        // Campos de texto
        JTextField txtRUT = new JTextField();
        JTextField txtCantidadAsientos = new JTextField();
        JTextField txtFormaPago = new JTextField();

        // Agregamos labels y campos al panel
        panel.add(new JLabel("Seleccione Evento:"));
        panel.add(comboEventos);
        panel.add(new JLabel("RUT del cliente:"));
        panel.add(txtRUT);
        panel.add(new JLabel("Cantidad de asientos:"));
        panel.add(txtCantidadAsientos);
        panel.add(new JLabel("Forma de pago:"));
        panel.add(txtFormaPago);

        // Botón de compra
        JButton btnComprar = new JButton("Confirmar Compra");

        btnComprar.addActionListener(e -> {
            Evento eventoSeleccionado = (Evento) comboEventos.getSelectedItem();
            
            if (eventoSeleccionado == null) {
                JOptionPane.showMessageDialog(null, "No hay eventos disponibles.");
                return;
            }

            String rut = txtRUT.getText().trim();
            Cliente cliente = buscarClientePorRUT(rut);
            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ese RUT.");
                return;
            }

            int cantidadAsientos;
            try {
                cantidadAsientos = Integer.parseInt(txtCantidadAsientos.getText().trim());
                if (cantidadAsientos <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Cantidad de asientos inválida.");
                return;
            }

            String formaDePago = txtFormaPago.getText().trim();

            // Validar disponibilidad
            if (eventoSeleccionado.obtenerAsientosDisponibles().size() < cantidadAsientos) {
                JOptionPane.showMessageDialog(null, "No hay suficientes asientos disponibles.");
                return;
            }

            // Crear compra
            try {
                Compra compra = eventoSeleccionado.CrearOrdenDeCompra(cliente, cantidadAsientos, formaDePago, eventoSeleccionado.getID());
                gestor.guardarCompra(compra);
                gestor.guardarCliente(clientes);
                gestor.guardarEvento(eventos);

                JOptionPane.showMessageDialog(null,
                        "Compra realizada con éxito!\nID de Orden: " + compra.getOrdenDeCompra() +
                        "\nTotal: $" + compra.getMontoTotal());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al realizar la compra: " + ex.getMessage());
            }
        });

        // Agregamos el botón al panel
        panel.add(btnComprar);

        // Mostramos todo en un JOptionPane
        JOptionPane.showMessageDialog(null, panel, "Comprar Entradas", JOptionPane.PLAIN_MESSAGE);
    }

    public void guardarCompraTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de compras");
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            String ruta = archivoSeleccionado.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".txt")) {
                ruta += ".txt";
            }

            // Aquí llamas al sobrecargado de SistemaDeEntradas
            gestor.guardarCompra(compras, ruta);

            JOptionPane.showMessageDialog(null,
                "Reporte guardado en:\n" + ruta,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void guardarClienteTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de clientes");
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            String ruta = archivoSeleccionado.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".txt")) {
                ruta += ".txt";
            }

            gestor.guardarCliente(clientes, ruta);

            JOptionPane.showMessageDialog(null,
                "Reporte guardado en:\n" + ruta,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void guardarEventoTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de eventos");
        fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showSaveDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            String ruta = archivoSeleccionado.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".txt")) {
                ruta += ".txt";
            }

            gestor.guardarEvento(eventos, ruta);

            JOptionPane.showMessageDialog(null,
                "Reporte guardado en:\n" + ruta,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
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
    
    @SuppressWarnings("UnnecessaryReturnStatement")

    public void ModificarEvento() {
        if (eventos == null || eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para modificar.");
            return;
        }

        String[] columnas = {"ID", "Nombre", "Fecha", "Ubicación", "Orador"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Evento e : eventos) {
            Object[] fila = { e.getID(), e.getNombre(), e.getFechaEvento(), e.getUbicacion(), e.getOrador() };
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        int option = JOptionPane.showConfirmDialog(null, scrollPane, "Seleccione el evento a modificar",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No se seleccionó ningún evento.");
                return;
            }

            int idSeleccionado = (int) table.getValueAt(selectedRow, 0);
            Evento evento = buscarEventosPorID(idSeleccionado);

            JTextField nombreField = new JTextField(evento.getNombre());
            JTextField capacidadField = new JTextField(String.valueOf(evento.getCapacidad()));
            JTextField ubicacionField = new JTextField(evento.getUbicacion());
            JTextField fechaField = new JTextField(evento.getFechaEvento().toString());
            JTextField oradorField = new JTextField(evento.getOrador());
            JTextField temaField = new JTextField(evento.getTemaEvento());
            JTextField descripcionField = new JTextField(evento.getDescripcionEvento());
            JTextField asientosField = new JTextField(String.valueOf(evento.getAsientosEspeciales()));
            JTextField precioField = new JTextField(String.valueOf(evento.getPrecioEntrada()));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(10, 2, 5, 5));
            panel.add(new JLabel("Nombre:")); panel.add(nombreField);
            panel.add(new JLabel("Capacidad:")); panel.add(capacidadField);
            panel.add(new JLabel("Ubicación:")); panel.add(ubicacionField);
            panel.add(new JLabel("Fecha (yyyy-MM-dd):")); panel.add(fechaField);
            panel.add(new JLabel("Orador:")); panel.add(oradorField);
            panel.add(new JLabel("Tema:")); panel.add(temaField);
            panel.add(new JLabel("Descripción:")); panel.add(descripcionField);
            panel.add(new JLabel("Asientos especiales:")); panel.add(asientosField);
            panel.add(new JLabel("Precio:")); panel.add(precioField);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Modificar Evento ID: " + evento.getID(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    evento.setNombre(nombreField.getText());
                    evento.setCapacidadPersonas(Integer.parseInt(capacidadField.getText()));
                    evento.setUbicacion(ubicacionField.getText());
                    evento.setFechaEvento(LocalDate.parse(fechaField.getText()));
                    evento.setOrador(oradorField.getText());
                    evento.setTemaEvento(temaField.getText());
                    evento.setDescripcionEvento(descripcionField.getText());
                    evento.setAsientosEspeciales(Integer.parseInt(asientosField.getText()));
                    evento.setPrecioEntrada(Integer.parseInt(precioField.getText()));

                    gestor.guardarEvento(eventos); // guardar csv al final de todo
                    JOptionPane.showMessageDialog(null, "Evento modificado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al modificar el evento: " + ex.getMessage());
                }
            }
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
    
    public void RemoverEvento() {
        if (eventos == null || eventos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para borrar.");
            return;
        }

        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Fecha", "Ubicación", "Orador"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Evento e : eventos) {
            Object[] fila = { e.getID(), e.getNombre(), e.getFechaEvento(), e.getUbicacion(), e.getOrador() };
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo seleccionar una fila

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        int option = JOptionPane.showConfirmDialog(null, scrollPane, "Seleccione el evento a eliminar",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No se seleccionó ningún evento.");
                return;
            }

            // Obtener el evento seleccionado
            int idSeleccionado = (int) table.getValueAt(selectedRow, 0);
            Evento eventoEncontrado = buscarEventosPorID(idSeleccionado);

            // Confirmar eliminación
            int confirm = JOptionPane.showConfirmDialog(null, 
                "¿Seguro que desea eliminar el evento: " + eventoEncontrado.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                eventos.remove(eventoEncontrado);
                gestor.guardarEvento(eventos); // Guardar cambios en CSV
                JOptionPane.showMessageDialog(null, "Evento eliminado correctamente.");
            }
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
    
    public String RegistrarCliente(String nombre, String rutIngresado, String edadStr, String cantAsientosStr, String discapacidad) {
      
        if (nombre == null || nombre.trim().isEmpty() ||
            rutIngresado == null || rutIngresado.trim().isEmpty() ||
            edadStr == null || edadStr.trim().isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        // Normalizar RUT
        String rut = normalizarRUT(rutIngresado);

        // Validar RUT
        if (!Cliente.validarRut(rut)) {
            return "RUT inválido. Verifique el formato.";
        }

        // Verificar duplicados
        if (buscarClientePorRUT(rut) != null) {
            return "Ya existe un cliente con ese RUT.";
        }

        try {
            int edad = Integer.parseInt(edadStr);
            if (edad < 16 || edad > 120) {
                return "La edad debe estar entre 16 y 120 años.";
            }

            int cantAsientos = Integer.parseInt(cantAsientosStr);
            if (cantAsientos < 0) {
                return "La cantidad de asientos no puede ser negativa.";
            }

            // Crear cliente
            Cliente nuevoCliente = new Cliente(nombre.trim(), rut, edad, cantAsientos, discapacidad, null);
            clientes.add(nuevoCliente);
            gestor.guardarCliente(clientes); // si usas persistencia

            return null; // null = éxito
        } catch (NumberFormatException e) {
            return "Edad y cantidad de asientos deben ser números.";
        }
    }
    
    public String listarClientes() {
        if (clientes.isEmpty()) {
            return null; 
        }

        StringBuilder clientesInfo = new StringBuilder();
        clientesInfo.append("LISTA DE CLIENTES REGISTRADOS:\n");
        clientesInfo.append("==========================================\n\n");

        int i = 1;
        for (Cliente c : clientes) {
            String rutFormateado = normalizarRUT(c.getRut()); 

            clientesInfo.append(i).append(". Nombre: ").append(c.getNombre()).append("\n");
            clientesInfo.append("   RUT: ").append(rutFormateado).append("\n");
            clientesInfo.append("   Edad: ").append(c.getEdad()).append(" años\n");

            if (c.getDiscapacidades() != null && !c.getDiscapacidades().trim().isEmpty()) {
                clientesInfo.append("   Discapacidad: ").append(c.getDiscapacidades()).append("\n");
            }

            clientesInfo.append("\n");
            i++;
        }

        return clientesInfo.toString();
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
    
    public void ListarCompras() {
        // Comprobamos si hay compras
        List<Compra> todasLasCompras = new ArrayList<>();
        for (Evento ev : eventos) {
            todasLasCompras.addAll(ev.getCompras()); // suponiendo que cada evento tiene su lista de compras
        }

        if (todasLasCompras.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay compras registradas.", "Listado de Compras", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Construimos el texto para mostrar
        StringBuilder sb = new StringBuilder("=== LISTADO DE COMPRAS ===\n\n");
        for (Compra c : todasLasCompras) {
            sb.append("ID Orden: ").append(c.getOrdenDeCompra())
              .append(" | Cliente RUT: ").append(c.getRut())
              .append(" | Forma de Pago: ").append(c.getFormaDePago())
              .append(" | Fecha: ").append(c.getFechaDeCompra())
              .append(" | Estado: ").append(c.getEstadoDeCompra())
              .append(" | Monto Total: $").append(c.getMontoTotal())
              .append(" | ID del evento: ").append(c.getIdEvento())
              .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Listado de Compras", JOptionPane.INFORMATION_MESSAGE);
    }

    public void RemoverCliente() {
        if (clientes == null || clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes para eliminar.");
            return;
        }

        String[] columnas = {"RUT", "Nombre", "Edad", "Discapacidades"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Cliente c : clientes) {
            Object[] fila = {c.getRut(), c.getNombre(), c.getEdad(), c.getDiscapacidades()};
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        int option = JOptionPane.showConfirmDialog(null, scrollPane,
                "Seleccione el cliente a eliminar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) return;

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "No se seleccionó ningún cliente.");
            return;
        }

        // Obtener cliente seleccionado
        String rutSeleccionado = (String) table.getValueAt(selectedRow, 0);
        Cliente cliente = buscarClientePorRUT(rutSeleccionado);

        // Confirmar eliminación
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Seguro que desea eliminar el cliente: " + cliente.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            clientes.remove(cliente);
            gestor.guardarCliente(clientes); // Guardar cambios en CSV
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
        }
    }
    
    // METODOS MISCELANEOS
    public static void limpiarConsola() {
    for (int i = 0; i < 2; i++) {
        System.out.println();
        }
    }
    
    private String normalizarRUT(String rut) {
        if (rut == null) {
            return null;
        }

        String rutLimpio = rut.trim().replace(".", "").replace("-", "").toUpperCase();

        if (rutLimpio.length() >= 2) {
            String numero = rutLimpio.substring(0, rutLimpio.length() - 1);
            String dv = rutLimpio.substring(rutLimpio.length() - 1);
            return numero + "-" + dv;
        }
        
        return rutLimpio;
    }
}