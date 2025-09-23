package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tenerex
 */

public class Cliente {
    private String rut;
    private String nombre;
    private int asientosAComprar;
    private String acompanantes;
    private int edad;
    private String discapacidades;
    
    List<Compra> comprasCliente = new ArrayList<>();
    
    public Cliente(String nombre, String rut, int edad, int asientosAComprar, String acompanantes, String discapacidades){
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.asientosAComprar = asientosAComprar;
        this.acompanantes = acompanantes;
        this.discapacidades = null;
    }
   
    // Getters originales
    public String getRut(){return this.rut;}
    public String getNombre() {return this.nombre;}
    public String getAcompanantes() {return this.acompanantes;}
    public int getEdad(){return this.edad;}
    public int getAsientosAComprar() {return this.asientosAComprar;}
    public String getDiscapacidades(){return this.discapacidades;}
    public List<Compra> getComprasClientes(){return this.comprasCliente;}
    
    // Setters originales
    public void setRut(String rut){this.rut = rut;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setAcompanantes(String acompanantes){this.acompanantes = acompanantes;}
    public void setEdad(int edad) {this.edad = edad;}
    public void setAsientosAComprar(int asientosAComprar){this.asientosAComprar = asientosAComprar;}
    public void setDiscapacidades(String discapacidades){this.discapacidades = discapacidades;}
    
    // ========================================================================
    // MÉTODOS DE VALIDACIÓN DE RUT 
    // ========================================================================
    
    /**
     * Valida si un RUT chileno es válido o no
     * @param RUT a validar (puede tener puntos y guión o no)
     * @return true si el RUT es válido, false si no existe/es inválido
     */
    public static boolean validarRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
        
        // Limpiar RUT (quitar puntos, guiones, espacios)
        rut = rut.replaceAll("[.\\-\\s]", "").trim().toUpperCase();
        
        // Verificar que tenga el formato correcto: 7-8 dígitos + dígito verificador
        if (!rut.matches("\\d{7,8}[0-9K]")) {
            return false;
        }
        
        // Separar número del dígito verificador
        String numero = rut.substring(0, rut.length() - 1);
        char digitoVerificador = rut.charAt(rut.length() - 1);
        
        // Calcular el dígito verificador correcto
        char digitoCalculado = calcularDigitoVerificador(numero);
        
        // Comparar si coinciden
        return digitoVerificador == digitoCalculado;
    }
    
    /**
     * Calcula el dígito verificador de un RUT según el algoritmo chileno
     * @param  numero Número del RUT sin dígito verificador
     * @return Dígito verificador calculado
     */
    private static char calcularDigitoVerificador(String numero) {
        int suma = 0;
        int multiplicador = 2;
        
        // Recorrer de derecha a izquierda multiplicando por 2,3,4,5,6,7,2,3...
        for (int i = numero.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(numero.charAt(i)) * multiplicador;
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }
        
        // Aplicar módulo 11
        int resto = suma % 11;
        int dv = 11 - resto;
        
        if (dv == 11) {
            return '0';
        } else if (dv == 10) {
            return 'K';
        } else {
            return (char) ('0' + dv);
        }
    }
}
