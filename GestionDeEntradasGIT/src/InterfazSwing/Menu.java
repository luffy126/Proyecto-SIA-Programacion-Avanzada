/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InterfazSwing;

import Clases.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tomas
 */
public class Menu extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Menu.class.getName());
    private SistemaDeEntradas sistema;
    private JPanel panelFiltros;
    private JTextField txtBuscarRUT, txtBuscarNombre; // Para clientes
    private JTextField txtBuscarIDEvento, txtBuscarNombreEvento; // Para eventos
    private JTextField txtBuscarIDCompra, txtBuscarRUTCompra; // Para compras
    private JButton btnBuscarClientes, btnBuscarEventos, btnBuscarCompras;
    private JTable tablaResultados;
    private DefaultTableModel model;
    private JRadioButton rbClientes, rbEventos, rbCompras;
    private ButtonGroup grupoFiltros;
    
    /**
     * Creates new form Menu
     */
    public Menu(SistemaDeEntradas sistema) {
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"RUT", "Nombre", "Edad", "Acompañantes", "Asientos", "Discapacidades"});
        tablaResultados = new JTable(model);
        this.sistema = sistema;
        initComponents();
        inicializarPanelBusqueda();
    }
    
    private void inicializarPanelBusqueda() {
        // Panel principal de filtros (vertical)
        panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));

        // --- 1. Panel de botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        rbClientes = new JRadioButton("Clientes");
        rbEventos = new JRadioButton("Eventos");
        rbCompras = new JRadioButton("Compras");

        grupoFiltros = new ButtonGroup();
        grupoFiltros.add(rbClientes);
        grupoFiltros.add(rbEventos);
        grupoFiltros.add(rbCompras);

        btnBuscarClientes = new JButton("Buscar Clientes");
        btnBuscarEventos = new JButton("Buscar Eventos");
        btnBuscarCompras = new JButton("Buscar Compras");
        JButton btnVolverMenu = new JButton("Salir");
            btnVolverMenu.addActionListener(e -> {
                panFondoBusqueda.setVisible(false);
                panFondoPrincipal.setVisible(true);
            });

        panelBotones.add(rbClientes);
        panelBotones.add(rbEventos);
        panelBotones.add(rbCompras);
        panelBotones.add(btnBuscarClientes);
        panelBotones.add(btnBuscarEventos);
        panelBotones.add(btnBuscarCompras);
        panelBotones.add(btnVolverMenu);

        panelFiltros.add(panelBotones);

        // --- 2. Panel de campos de búsqueda ---
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new CardLayout()); // un panel por tipo de búsqueda

        // Campos Clientes
        JPanel panelClientes = new JPanel(new GridLayout(2, 2, 5, 5));
        txtBuscarRUT = new JTextField(10);
        txtBuscarNombre = new JTextField(10);
        panelClientes.add(new JLabel("RUT:"));
        panelClientes.add(txtBuscarRUT);
        panelClientes.add(new JLabel("Nombre:"));
        panelClientes.add(txtBuscarNombre);

        // Campos Eventos
        JPanel panelEventos = new JPanel(new GridLayout(2, 2, 5, 5));
        txtBuscarIDEvento = new JTextField(10);
        txtBuscarNombreEvento = new JTextField(10);
        panelEventos.add(new JLabel("ID Evento:"));
        panelEventos.add(txtBuscarIDEvento);
        panelEventos.add(new JLabel("Nombre:"));
        panelEventos.add(txtBuscarNombreEvento);

        // Campos Compras
        JPanel panelCompras = new JPanel(new GridLayout(2, 2, 5, 5));
        txtBuscarIDCompra = new JTextField(10);
        txtBuscarRUTCompra = new JTextField(10);
        panelCompras.add(new JLabel("ID Compra:"));
        panelCompras.add(txtBuscarIDCompra);
        panelCompras.add(new JLabel("RUT Cliente:"));
        panelCompras.add(txtBuscarRUTCompra);

        panelCampos.add(panelClientes, "Clientes");
        panelCampos.add(panelEventos, "Eventos");
        panelCampos.add(panelCompras, "Compras");

        panelFiltros.add(panelCampos);

        // --- 3. Tabla de resultados ---
        model = new DefaultTableModel();
        tablaResultados = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        panFondoBusqueda.removeAll();
        panFondoBusqueda.setLayout(new BorderLayout());
        panFondoBusqueda.add(panelFiltros, BorderLayout.NORTH);
        panFondoBusqueda.add(scrollPane, BorderLayout.CENTER);

        // --- 4. Listeners ---
        btnBuscarClientes.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelCampos.getLayout();
            cl.show(panelCampos, "Clientes");
            buscarClientes();
        });

        btnBuscarEventos.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelCampos.getLayout();
            cl.show(panelCampos, "Eventos");
            buscarEventos();
        });

        btnBuscarCompras.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelCampos.getLayout();
            cl.show(panelCampos, "Compras");
            buscarCompras();
        });

        panFondoBusqueda.revalidate();
        panFondoBusqueda.repaint();
    }

    
    // 3. Métodos de búsqueda
    private void buscarClientes() {
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"RUT", "Nombre", "Edad", "Acompañantes", "Asientos", "Discapacidades"});

        String rut = txtBuscarRUT.getText().trim().toLowerCase();
        String nombre = txtBuscarNombre.getText().trim().toLowerCase();

        List<Cliente> resultados = new ArrayList<>();
        for (Cliente c : sistema.getClientes()) {
            boolean coincideRUT = rut.isEmpty() || c.getRut().toLowerCase().contains(rut);
            boolean coincideNombre = nombre.isEmpty() || c.getNombre().toLowerCase().contains(nombre);
            if (coincideRUT && coincideNombre) resultados.add(c);
        }

        for (Cliente c : resultados) {
            model.addRow(new Object[]{c.getRut(), c.getNombre(), c.getEdad(),
                c.getAcompanantes(), c.getAsientosAComprar(), c.getDiscapacidades()});
        }
    }

    // 4. Métodos similares para eventos y compras
    private void buscarEventos() {
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Ubicación", "Fecha", "Orador", "Tema"});

        String id = txtBuscarIDEvento.getText().trim().toLowerCase();
        String nombre = txtBuscarNombreEvento.getText().trim().toLowerCase();

        List<Evento> resultados = new ArrayList<>();
        for (Evento ev : sistema.getEventos()) {
            boolean coincideID = id.isEmpty() || String.valueOf(ev.getID()).contains(id);
            boolean coincideNombre = nombre.isEmpty() || ev.getNombre().toLowerCase().contains(nombre);
            if (coincideID && coincideNombre) resultados.add(ev);
        }

        for (Evento ev : resultados) {
            model.addRow(new Object[]{ev.getID(), ev.getNombre(), ev.getUbicacion(),
                ev.getFechaEvento(), ev.getOrador(), ev.getTemaEvento()});
        }
    }

    private void buscarCompras() {
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"ID Compra", "RUT Cliente", "Evento ID", "Forma Pago", "Estado", "Monto"});

        String idCompra = txtBuscarIDCompra.getText().trim().toLowerCase();
        String rut = txtBuscarRUTCompra.getText().trim().toLowerCase();

        List<Compra> resultados = new ArrayList<>();
        for (Evento ev : sistema.getEventos()) {
            for (Compra c : ev.getCompras()) {
                boolean coincideID = idCompra.isEmpty() || String.valueOf(c.getOrdenDeCompra()).contains(idCompra);
                boolean coincideRUT = rut.isEmpty() || c.getRut().toLowerCase().contains(rut);
                if (coincideID && coincideRUT) resultados.add(c);
            }
        }

        for (Compra c : resultados) {
            model.addRow(new Object[]{c.getOrdenDeCompra(), c.getRut(), c.getIdEvento(),
                c.getFormaDePago(), c.getEstadoDeCompra(), c.getMontoTotal()});
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotonesBusqueda = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        panPadre = new javax.swing.JPanel();
        panFondoPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnEventos = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblPanelEventos = new javax.swing.JLabel();
        lblPanelClientes = new javax.swing.JLabel();
        lblPanelCompras = new javax.swing.JLabel();
        lblVer = new javax.swing.JLabel();
        btnBuscador = new javax.swing.JButton();
        lblPanelBuscador = new javax.swing.JLabel();
        btnReporte = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panFondoEventos = new javax.swing.JPanel();
        lblTitulo1 = new javax.swing.JLabel();
        btnAgregarEvento = new javax.swing.JButton();
        btnListarEvento = new javax.swing.JButton();
        btnModificarEvento = new javax.swing.JButton();
        btnVolverEventos = new javax.swing.JButton();
        lblVer1 = new javax.swing.JLabel();
        btnCompras2 = new javax.swing.JButton();
        jtxtListarEvento = new javax.swing.JTextArea();
        jtxtAgregarEvento1 = new javax.swing.JTextArea();
        jtxtListarEvento1 = new javax.swing.JTextArea();
        jtxtListarEvento2 = new javax.swing.JTextArea();
        panFondoClientes = new javax.swing.JPanel();
        lblTitulo2 = new javax.swing.JLabel();
        btnAgregarClientes = new javax.swing.JButton();
        btnListarClientes = new javax.swing.JButton();
        btnModificarClientes = new javax.swing.JButton();
        btnEliminarClientes = new javax.swing.JButton();
        btnVolverClientes = new javax.swing.JButton();
        jtxtAgregarCliente = new javax.swing.JTextArea();
        jtxtListarCliente = new javax.swing.JTextArea();
        jtxtListarCliente1 = new javax.swing.JTextArea();
        jtxtListarCliente2 = new javax.swing.JTextArea();
        panFondoCompras = new javax.swing.JPanel();
        btnAgregarCompras = new javax.swing.JButton();
        btnListarCompras = new javax.swing.JButton();
        btnModificarCompras = new javax.swing.JButton();
        btnEliminarCompras = new javax.swing.JButton();
        btnVolverCompras = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        panFondoBusqueda = new javax.swing.JPanel();
        jlblTitulo = new javax.swing.JLabel();
        btnVolverBusqueda = new javax.swing.JButton();
        jPanInterno = new javax.swing.JLayeredPane();
        SubEvento = new javax.swing.JRadioButton();
        SubCliente = new javax.swing.JRadioButton();
        SubCompra = new javax.swing.JRadioButton();
        jPanTablas = new javax.swing.JPanel();
        ScrollPaneEventos = new javax.swing.JScrollPane();
        jTablaEventos = new javax.swing.JTable();
        ScrollPaneClientes = new javax.swing.JScrollPane();
        jTablaClientes = new javax.swing.JTable();
        ScrollPaneCompras = new javax.swing.JScrollPane();
        jTablaCompras = new javax.swing.JTable();
        panFondoReporte = new javax.swing.JPanel();
        lblMsjExito = new javax.swing.JLabel();
        lblAdvertencia = new javax.swing.JLabel();
        jlblTituloReporte = new javax.swing.JLabel();
        checkRutaEspecifica = new javax.swing.JCheckBox();
        txtFieldRutaDeArchivo = new javax.swing.JTextField();
        btnGuardarPDF = new javax.swing.JButton();
        btnGuardarWord = new javax.swing.JButton();
        btnVolverReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Entradas - by: Simon, Tomas, Pancha");
        setResizable(false);
        setSize(new java.awt.Dimension(666, 499));

        panPadre.setLayout(new java.awt.CardLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setText("Sistema de Gestion de Entradas");

        btnEventos.setText("Eventos");
        btnEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventosActionPerformed(evt);
            }
        });

        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnCompras.setText("Compras");
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lblPanelEventos.setText("Panel de Eventos");

        lblPanelClientes.setText("Panel de Clientes");

        lblPanelCompras.setText("Panel de Compras");

        lblVer.setText("ver1.2");

        btnBuscador.setText("Buscador");
        btnBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscadorActionPerformed(evt);
            }
        });

        lblPanelBuscador.setText("Visualiza las entradas de cada categoria");

        btnReporte.setText("Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel1.setText("Genera .txt de los datos totales");

        javax.swing.GroupLayout panFondoPrincipalLayout = new javax.swing.GroupLayout(panFondoPrincipal);
        panFondoPrincipal.setLayout(panFondoPrincipalLayout);
        panFondoPrincipalLayout.setHorizontalGroup(
            panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPanelCompras)
                            .addComponent(lblPanelClientes)
                            .addComponent(lblPanelEventos))
                        .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(btnBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPanelBuscador)
                            .addComponent(jLabel1)))
                    .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblVer))
                    .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(lblTitulo)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        panFondoPrincipalLayout.setVerticalGroup(
            panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoPrincipalLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitulo)
                .addGap(135, 135, 135)
                .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPanelEventos)
                    .addComponent(btnBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPanelBuscador))
                .addGap(50, 50, 50)
                .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPanelClientes)
                    .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(50, 50, 50)
                .addGroup(panFondoPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPanelCompras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblVer))
        );

        panPadre.add(panFondoPrincipal, "card2");

        lblTitulo1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo1.setText("Eventos");

        btnAgregarEvento.setText("Agregar");
        btnAgregarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEventoActionPerformed(evt);
            }
        });

        btnListarEvento.setText("Listar");
        btnListarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarEventoActionPerformed(evt);
            }
        });

        btnModificarEvento.setText("Modificar");
        btnModificarEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEventoActionPerformed(evt);
            }
        });

        btnVolverEventos.setText("Volver");
        btnVolverEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverEventosActionPerformed(evt);
            }
        });

        lblVer1.setText("ver1.1");

        btnCompras2.setText("Eliminar");
        btnCompras2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompras2ActionPerformed(evt);
            }
        });

        jtxtListarEvento.setEditable(false);
        jtxtListarEvento.setColumns(1);
        jtxtListarEvento.setLineWrap(true);
        jtxtListarEvento.setRows(5);
        jtxtListarEvento.setText("Elimina un evento del listado de eventos.");
        jtxtListarEvento.setWrapStyleWord(true);
        jtxtListarEvento.setAutoscrolls(false);
        jtxtListarEvento.setRequestFocusEnabled(false);

        jtxtAgregarEvento1.setEditable(false);
        jtxtAgregarEvento1.setColumns(1);
        jtxtAgregarEvento1.setLineWrap(true);
        jtxtAgregarEvento1.setRows(5);
        jtxtAgregarEvento1.setText("Permite agregar un nuevo evento que este planificado, junto con sus caracteristicas y parametros.");
        jtxtAgregarEvento1.setWrapStyleWord(true);
        jtxtAgregarEvento1.setAutoscrolls(false);
        jtxtAgregarEvento1.setRequestFocusEnabled(false);

        jtxtListarEvento1.setEditable(false);
        jtxtListarEvento1.setColumns(1);
        jtxtListarEvento1.setLineWrap(true);
        jtxtListarEvento1.setRows(5);
        jtxtListarEvento1.setText("Muestra en pantalla todos los eventos.");
        jtxtListarEvento1.setWrapStyleWord(true);
        jtxtListarEvento1.setAutoscrolls(false);
        jtxtListarEvento1.setRequestFocusEnabled(false);

        jtxtListarEvento2.setEditable(false);
        jtxtListarEvento2.setColumns(1);
        jtxtListarEvento2.setLineWrap(true);
        jtxtListarEvento2.setRows(5);
        jtxtListarEvento2.setText("Te permite modificar un evento a traves de su identificador.");
        jtxtListarEvento2.setWrapStyleWord(true);
        jtxtListarEvento2.setAutoscrolls(false);
        jtxtListarEvento2.setRequestFocusEnabled(false);

        javax.swing.GroupLayout panFondoEventosLayout = new javax.swing.GroupLayout(panFondoEventos);
        panFondoEventos.setLayout(panFondoEventosLayout);
        panFondoEventosLayout.setHorizontalGroup(
            panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoEventosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolverEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(panFondoEventosLayout.createSequentialGroup()
                .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFondoEventosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblVer1))
                    .addGroup(panFondoEventosLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFondoEventosLayout.createSequentialGroup()
                                .addComponent(btnListarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtListarEvento1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFondoEventosLayout.createSequentialGroup()
                                .addComponent(btnAgregarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtAgregarEvento1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTitulo1)
                            .addGroup(panFondoEventosLayout.createSequentialGroup()
                                .addComponent(btnCompras2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtListarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFondoEventosLayout.createSequentialGroup()
                                .addComponent(btnModificarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtListarEvento2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        panFondoEventosLayout.setVerticalGroup(
            panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoEventosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo1)
                .addGap(100, 100, 100)
                .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtAgregarEvento1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarEvento1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModificarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarEvento2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panFondoEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCompras2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(btnVolverEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblVer1))
        );

        panPadre.add(panFondoEventos, "card3");

        lblTitulo2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo2.setText("Clientes");

        btnAgregarClientes.setText("Agregar Cliente");
        btnAgregarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClientesActionPerformed(evt);
            }
        });

        btnListarClientes.setText("Listar Clientes");
        btnListarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarClientesActionPerformed(evt);
            }
        });

        btnModificarClientes.setText("Modificar Clientes");
        btnModificarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarClientesActionPerformed(evt);
            }
        });

        btnEliminarClientes.setText("Eliminar Clientes");
        btnEliminarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClientesActionPerformed(evt);
            }
        });

        btnVolverClientes.setText("Volver");
        btnVolverClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverClientesActionPerformed(evt);
            }
        });

        jtxtAgregarCliente.setEditable(false);
        jtxtAgregarCliente.setColumns(1);
        jtxtAgregarCliente.setLineWrap(true);
        jtxtAgregarCliente.setRows(5);
        jtxtAgregarCliente.setText("Permite agregar un cliente, con su informacion al listado.");
        jtxtAgregarCliente.setWrapStyleWord(true);
        jtxtAgregarCliente.setAutoscrolls(false);
        jtxtAgregarCliente.setRequestFocusEnabled(false);

        jtxtListarCliente.setEditable(false);
        jtxtListarCliente.setColumns(1);
        jtxtListarCliente.setLineWrap(true);
        jtxtListarCliente.setRows(5);
        jtxtListarCliente.setText("Muestra en pantalla, la lista completa de clientes registrados.");
        jtxtListarCliente.setWrapStyleWord(true);
        jtxtListarCliente.setAutoscrolls(false);
        jtxtListarCliente.setRequestFocusEnabled(false);

        jtxtListarCliente1.setEditable(false);
        jtxtListarCliente1.setColumns(1);
        jtxtListarCliente1.setLineWrap(true);
        jtxtListarCliente1.setRows(5);
        jtxtListarCliente1.setText("Permite modificar un cliente por su RUT asociado.");
        jtxtListarCliente1.setWrapStyleWord(true);
        jtxtListarCliente1.setAutoscrolls(false);
        jtxtListarCliente1.setRequestFocusEnabled(false);

        jtxtListarCliente2.setEditable(false);
        jtxtListarCliente2.setColumns(1);
        jtxtListarCliente2.setLineWrap(true);
        jtxtListarCliente2.setRows(5);
        jtxtListarCliente2.setText("Elimina un cliente seleccionado del listado.");
        jtxtListarCliente2.setWrapStyleWord(true);
        jtxtListarCliente2.setAutoscrolls(false);
        jtxtListarCliente2.setRequestFocusEnabled(false);

        javax.swing.GroupLayout panFondoClientesLayout = new javax.swing.GroupLayout(panFondoClientes);
        panFondoClientes.setLayout(panFondoClientesLayout);
        panFondoClientesLayout.setHorizontalGroup(
            panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoClientesLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFondoClientesLayout.createSequentialGroup()
                        .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnListarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtListarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtListarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtListarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTitulo2))
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoClientesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolverClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        panFondoClientesLayout.setVerticalGroup(
            panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoClientesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo2)
                .addGap(92, 92, 92)
                .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtAgregarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModificarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panFondoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtListarCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(btnVolverClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        panPadre.add(panFondoClientes, "card4");

        panFondoCompras.setEnabled(false);

        btnAgregarCompras.setText("Realizar Compra");
        btnAgregarCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarComprasActionPerformed(evt);
            }
        });

        btnListarCompras.setText("Listar Compras por ID");
        btnListarCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarComprasActionPerformed(evt);
            }
        });

        btnModificarCompras.setText("Modificar Compras");
        btnModificarCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarComprasActionPerformed(evt);
            }
        });

        btnEliminarCompras.setText("Eliminar Compras");
        btnEliminarCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarComprasActionPerformed(evt);
            }
        });

        btnVolverCompras.setText("Volver");
        btnVolverCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverComprasActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Compras");

        javax.swing.GroupLayout panFondoComprasLayout = new javax.swing.GroupLayout(panFondoCompras);
        panFondoCompras.setLayout(panFondoComprasLayout);
        panFondoComprasLayout.setHorizontalGroup(
            panFondoComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoComprasLayout.createSequentialGroup()
                .addGroup(panFondoComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFondoComprasLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(panFondoComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnListarCompras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarCompras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarCompras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarCompras, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panFondoComprasLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel2)))
                .addContainerGap(481, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoComprasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnVolverCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        panFondoComprasLayout.setVerticalGroup(
            panFondoComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoComprasLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(118, 118, 118)
                .addComponent(btnAgregarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnListarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(btnVolverCompras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        panPadre.add(panFondoCompras, "card4");

        jlblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblTitulo.setText("Busqueda en subconjuntos");

        btnVolverBusqueda.setText("Volver");
        btnVolverBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverBusquedaActionPerformed(evt);
            }
        });

        grupoBotonesBusqueda.add(SubEvento);
        SubEvento.setText("Eventos");
        SubEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEventoActionPerformed(evt);
            }
        });

        grupoBotonesBusqueda.add(SubCliente);
        SubCliente.setText("Clientes");
        SubCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubClienteActionPerformed(evt);
            }
        });

        grupoBotonesBusqueda.add(SubCompra);
        SubCompra.setText("Compras");
        SubCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubCompraActionPerformed(evt);
            }
        });

        ScrollPaneEventos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                VisibilidadEventos(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jTablaEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ScrollPaneEventos.setViewportView(jTablaEventos);

        ScrollPaneClientes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Visibilidad(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jTablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ScrollPaneClientes.setViewportView(jTablaClientes);

        ScrollPaneCompras.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                VisibilidadCompras(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jTablaCompras.setAutoCreateColumnsFromModel(false);
        jTablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ScrollPaneCompras.setViewportView(jTablaCompras);

        javax.swing.GroupLayout jPanTablasLayout = new javax.swing.GroupLayout(jPanTablas);
        jPanTablas.setLayout(jPanTablasLayout);
        jPanTablasLayout.setHorizontalGroup(
            jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanTablasLayout.createSequentialGroup()
                .addComponent(ScrollPaneEventos)
                .addContainerGap())
            .addGroup(jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanTablasLayout.createSequentialGroup()
                    .addComponent(ScrollPaneClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanTablasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ScrollPaneCompras)
                    .addContainerGap()))
        );
        jPanTablasLayout.setVerticalGroup(
            jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanTablasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPaneEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanTablasLayout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(ScrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(jPanTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanTablasLayout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(ScrollPaneCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanInterno.setLayer(SubEvento, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPanInterno.setLayer(SubCliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPanInterno.setLayer(SubCompra, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jPanInterno.setLayer(jPanTablas, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanInternoLayout = new javax.swing.GroupLayout(jPanInterno);
        jPanInterno.setLayout(jPanInternoLayout);
        jPanInternoLayout.setHorizontalGroup(
            jPanInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInternoLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(SubEvento)
                .addGap(90, 90, 90)
                .addComponent(SubCliente)
                .addGap(90, 90, 90)
                .addComponent(SubCompra)
                .addContainerGap(145, Short.MAX_VALUE))
            .addGroup(jPanInternoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanTablas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanInternoLayout.setVerticalGroup(
            jPanInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanInternoLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanInternoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SubEvento)
                    .addComponent(SubCliente)
                    .addComponent(SubCompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanTablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panFondoBusquedaLayout = new javax.swing.GroupLayout(panFondoBusqueda);
        panFondoBusqueda.setLayout(panFondoBusquedaLayout);
        panFondoBusquedaLayout.setHorizontalGroup(
            panFondoBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoBusquedaLayout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addGroup(panFondoBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoBusquedaLayout.createSequentialGroup()
                        .addComponent(jlblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoBusquedaLayout.createSequentialGroup()
                        .addComponent(btnVolverBusqueda)
                        .addContainerGap())))
            .addGroup(panFondoBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panFondoBusquedaLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jPanInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(8, Short.MAX_VALUE)))
        );
        panFondoBusquedaLayout.setVerticalGroup(
            panFondoBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoBusquedaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jlblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 506, Short.MAX_VALUE)
                .addComponent(btnVolverBusqueda)
                .addContainerGap())
            .addGroup(panFondoBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panFondoBusquedaLayout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(jPanInterno)
                    .addGap(65, 65, 65)))
        );

        panPadre.add(panFondoBusqueda, "card6");

        lblMsjExito.setVisible(false);
        lblMsjExito.setFont(new java.awt.Font("Calibri", 2, 18)); // NOI18N
        lblMsjExito.setForeground(new java.awt.Color(0, 255, 0));
        lblMsjExito.setText("Se ha creado exitosamente el archivo!");

        lblAdvertencia.setText("(La casilla desactivada generara el archivo dentro del directorio del proyecto)");

        jlblTituloReporte.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlblTituloReporte.setText("Reporte");

        checkRutaEspecifica.setText("Utilizar esta ruta especifica");
        checkRutaEspecifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRutaEspecificaActionPerformed(evt);
            }
        });

        txtFieldRutaDeArchivo.setText("C://Users/NombreDeEjemplo/Documents/NetbeansProjects/");
        txtFieldRutaDeArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldRutaDeArchivoActionPerformed(evt);
            }
        });

        btnGuardarPDF.setText("Guardar en .PDF");
        btnGuardarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPDFActionPerformed(evt);
            }
        });

        btnGuardarWord.setText("Guardar en .word");
        btnGuardarWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarWordActionPerformed(evt);
            }
        });

        btnVolverReporte.setText("Volver");
        btnVolverReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panFondoReporteLayout = new javax.swing.GroupLayout(panFondoReporte);
        panFondoReporte.setLayout(panFondoReporteLayout);
        panFondoReporteLayout.setHorizontalGroup(
            panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFondoReporteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolverReporte)
                .addGap(17, 17, 17))
            .addGroup(panFondoReporteLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMsjExito)
                    .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFondoReporteLayout.createSequentialGroup()
                                .addComponent(txtFieldRutaDeArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkRutaEspecifica))
                            .addComponent(lblAdvertencia))
                        .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panFondoReporteLayout.createSequentialGroup()
                                .addComponent(btnGuardarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardarWord, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panFondoReporteLayout.createSequentialGroup()
                                .addComponent(jlblTituloReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        panFondoReporteLayout.setVerticalGroup(
            panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFondoReporteLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jlblTituloReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addComponent(lblMsjExito)
                .addGap(52, 52, 52)
                .addComponent(lblAdvertencia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldRutaDeArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkRutaEspecifica))
                .addGap(33, 33, 33)
                .addGroup(panFondoReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarWord, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addComponent(btnVolverReporte)
                .addGap(9, 9, 9))
        );

        panPadre.add(panFondoReporte, "card6");

        getContentPane().add(panPadre, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventosActionPerformed
        panFondoPrincipal.setVisible(false);
        panFondoEventos.setVisible(true);
    }//GEN-LAST:event_btnEventosActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        panFondoPrincipal.setVisible(false);
        panFondoClientes.setVisible(true);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        panFondoPrincipal.setVisible(false);
        panFondoCompras.setVisible(true);
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.out.println("Se ha cerrado la aplicacion.");
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAgregarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEventoActionPerformed
        JTextField txtNombre = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtUbicacion = new JTextField();
        JTextField txtFecha = new JTextField(); // formato yyyy-MM-dd
        JTextField txtOrador = new JTextField();
        JTextField txtTema = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtAsientosDiscap = new JTextField();
        JTextField txtPrecio = new JTextField();

        Object[] message = {
            "Nombre:", txtNombre,
            "Capacidad:", txtCapacidad,
            "Ubicación:", txtUbicacion,
            "Fecha (yyyy-MM-dd):", txtFecha,
            "Orador:", txtOrador,
            "Tema:", txtTema,
            "Descripción:", txtDescripcion,
            "Asientos discapacitados:", txtAsientosDiscap,
            "Precio:", txtPrecio
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Evento", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            sistema.CrearEvento(
                    txtNombre.getText(),
                    txtCapacidad.getText(),
                    txtUbicacion.getText(),
                    txtFecha.getText(),
                    txtOrador.getText(),
                    txtTema.getText(),
                    txtDescripcion.getText(),
                    txtAsientosDiscap.getText(),
                    txtPrecio.getText()
            );
        }
    }//GEN-LAST:event_btnAgregarEventoActionPerformed

    private void btnListarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarEventoActionPerformed
        List<Evento> eventos = sistema.getEventos();
        sistema.ListarEventos(eventos);
    }//GEN-LAST:event_btnListarEventoActionPerformed

    private void btnModificarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEventoActionPerformed
        sistema.ModificarEvento();
    }//GEN-LAST:event_btnModificarEventoActionPerformed

    private void btnCompras2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompras2ActionPerformed
        sistema.RemoverEvento();
    }//GEN-LAST:event_btnCompras2ActionPerformed

    private void btnListarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarClientesActionPerformed
        String info = sistema.listarClientes();

        if (info == null) {
            JOptionPane.showMessageDialog(this, "No hay clientes registrados.", "Lista de clientes", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, info, "Lista de clientes", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnListarClientesActionPerformed

    private void btnAgregarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClientesActionPerformed
        JTextField txtNombre = new JTextField();
        JTextField txtRut = new JTextField();
        JTextField txtEdad = new JTextField();
        JTextField txtCantAsientos = new JTextField("1");

        Object[] mensaje = {
            "Nombre:", txtNombre,
            "RUT (puede escribirlo con o sin puntos/guión):", txtRut,
            "Edad:", txtEdad,
            "Cantidad de asientos a comprar:", txtCantAsientos
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            String discapacidad = "";
            int opcionDiscapacidad = JOptionPane.showConfirmDialog(this,
                    "¿Desea registrar alguna discapacidad para este cliente?",
                    "Discapacidad", JOptionPane.YES_NO_OPTION);

            if (opcionDiscapacidad == JOptionPane.YES_OPTION) {
                discapacidad = JOptionPane.showInputDialog(this, "Ingrese la discapacidad:");
                if (discapacidad == null) {
                    discapacidad = "";
                }
            }

            String error = sistema.RegistrarCliente(
                    txtNombre.getText(),
                    txtRut.getText(),
                    txtEdad.getText(),
                    txtCantAsientos.getText(),
                    discapacidad
            );

            if (error == null) {
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarClientesActionPerformed

    private void btnModificarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClientesActionPerformed
        sistema.ModificarCliente();
    }//GEN-LAST:event_btnModificarClientesActionPerformed

    private void btnEliminarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClientesActionPerformed
        sistema.RemoverCliente();
    }//GEN-LAST:event_btnEliminarClientesActionPerformed

    private void btnVolverClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverClientesActionPerformed
        panFondoClientes.setVisible(false);
        panFondoPrincipal.setVisible(true);
    }//GEN-LAST:event_btnVolverClientesActionPerformed

    private void btnListarComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarComprasActionPerformed
        sistema.ListarCompras();
    }//GEN-LAST:event_btnListarComprasActionPerformed

    private void btnAgregarComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarComprasActionPerformed
        sistema.CrearCompra();
    }//GEN-LAST:event_btnAgregarComprasActionPerformed

    private void btnModificarComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarComprasActionPerformed
        sistema.modificarCompraSwing();
    }//GEN-LAST:event_btnModificarComprasActionPerformed

    private void btnEliminarComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarComprasActionPerformed
        sistema.eliminarCompra();
    }//GEN-LAST:event_btnEliminarComprasActionPerformed

    private void btnVolverComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverComprasActionPerformed
        panFondoPrincipal.setVisible(true);
        panFondoCompras.setVisible(false);
    }//GEN-LAST:event_btnVolverComprasActionPerformed

    private void btnVolverEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverEventosActionPerformed
        panFondoEventos.setVisible(false);
        panFondoPrincipal.setVisible(true);
    }//GEN-LAST:event_btnVolverEventosActionPerformed

    private void btnBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscadorActionPerformed
        panFondoPrincipal.setVisible(false);
        panFondoBusqueda.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscadorActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        panFondoPrincipal.setVisible(false);
        panFondoReporte.setVisible(true);
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnVolverBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverBusquedaActionPerformed
        panFondoBusqueda.setVisible(false);
        panFondoPrincipal.setVisible(true);
    }//GEN-LAST:event_btnVolverBusquedaActionPerformed

    private void Visibilidad(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Visibilidad
        jTablaClientes.setVisible(false); // VisibilidadClientes se me fue ponerlo de nombre y ya esta generado. 
    }//GEN-LAST:event_Visibilidad

    private void VisibilidadEventos(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_VisibilidadEventos
        jTablaEventos.setVisible(false);
    }//GEN-LAST:event_VisibilidadEventos

    private void VisibilidadCompras(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_VisibilidadCompras
        jTablaCompras.setVisible(false);
    }//GEN-LAST:event_VisibilidadCompras

    private void btnGuardarWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarWordActionPerformed

        lblMsjExito.setVisible(true);

    }//GEN-LAST:event_btnGuardarWordActionPerformed

    private void txtFieldRutaDeArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldRutaDeArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldRutaDeArchivoActionPerformed

    private void checkRutaEspecificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRutaEspecificaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkRutaEspecificaActionPerformed

    private void btnVolverReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverReporteActionPerformed
        panFondoReporte.setVisible(false);
        panFondoPrincipal.setVisible(true);
        lblMsjExito.setVisible(false);

    }//GEN-LAST:event_btnVolverReporteActionPerformed

    private void btnGuardarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPDFActionPerformed

        lblMsjExito.setVisible(true);

    }//GEN-LAST:event_btnGuardarPDFActionPerformed

    private void SubEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEventoActionPerformed

    }//GEN-LAST:event_SubEventoActionPerformed

    private void SubClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SubClienteActionPerformed

    private void SubCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SubCompraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SistemaDeEntradas sistema = new SistemaDeEntradas();
                new Menu(sistema).setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneClientes;
    private javax.swing.JScrollPane ScrollPaneCompras;
    private javax.swing.JScrollPane ScrollPaneEventos;
    private javax.swing.JRadioButton SubCliente;
    private javax.swing.JRadioButton SubCompra;
    private javax.swing.JRadioButton SubEvento;
    private javax.swing.JButton btnAgregarClientes;
    private javax.swing.JButton btnAgregarCompras;
    private javax.swing.JButton btnAgregarEvento;
    private javax.swing.JButton btnBuscador;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnCompras2;
    private javax.swing.JButton btnEliminarClientes;
    private javax.swing.JButton btnEliminarCompras;
    private javax.swing.JButton btnEventos;
    private javax.swing.JButton btnGuardarPDF;
    private javax.swing.JButton btnGuardarWord;
    private javax.swing.JButton btnListarClientes;
    private javax.swing.JButton btnListarCompras;
    private javax.swing.JButton btnListarEvento;
    private javax.swing.JButton btnModificarClientes;
    private javax.swing.JButton btnModificarCompras;
    private javax.swing.JButton btnModificarEvento;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVolverBusqueda;
    private javax.swing.JButton btnVolverClientes;
    private javax.swing.JButton btnVolverCompras;
    private javax.swing.JButton btnVolverEventos;
    private javax.swing.JButton btnVolverReporte;
    private javax.swing.JCheckBox checkRutaEspecifica;
    private javax.swing.ButtonGroup grupoBotonesBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jPanInterno;
    private javax.swing.JPanel jPanTablas;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTable jTablaClientes;
    private javax.swing.JTable jTablaCompras;
    private javax.swing.JTable jTablaEventos;
    private javax.swing.JLabel jlblTitulo;
    private javax.swing.JLabel jlblTituloReporte;
    private javax.swing.JTextArea jtxtAgregarCliente;
    private javax.swing.JTextArea jtxtAgregarEvento1;
    private javax.swing.JTextArea jtxtListarCliente;
    private javax.swing.JTextArea jtxtListarCliente1;
    private javax.swing.JTextArea jtxtListarCliente2;
    private javax.swing.JTextArea jtxtListarEvento;
    private javax.swing.JTextArea jtxtListarEvento1;
    private javax.swing.JTextArea jtxtListarEvento2;
    private javax.swing.JLabel lblAdvertencia;
    private javax.swing.JLabel lblMsjExito;
    private javax.swing.JLabel lblPanelBuscador;
    private javax.swing.JLabel lblPanelClientes;
    private javax.swing.JLabel lblPanelCompras;
    private javax.swing.JLabel lblPanelEventos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JLabel lblTitulo2;
    private javax.swing.JLabel lblVer;
    private javax.swing.JLabel lblVer1;
    private javax.swing.JPanel panFondoBusqueda;
    private javax.swing.JPanel panFondoClientes;
    private javax.swing.JPanel panFondoCompras;
    private javax.swing.JPanel panFondoEventos;
    private javax.swing.JPanel panFondoPrincipal;
    private javax.swing.JPanel panFondoReporte;
    private javax.swing.JPanel panPadre;
    private javax.swing.JTextField txtFieldRutaDeArchivo;
    // End of variables declaration//GEN-END:variables
}
