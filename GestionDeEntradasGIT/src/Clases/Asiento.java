package Clases;

/**
 *
 * @author Tenerex
 */
public class Asiento {
    
    private Cliente dueño = null;
    private Evento eventoAnfitrion = null;
    private boolean ocupado = false;
    
    public Asiento(Cliente dueño, Evento eventoAnfitrion, boolean ocupado) {    
        this.dueño = dueño;
        this.eventoAnfitrion = eventoAnfitrion;
        this.ocupado = ocupado;
    }
    
    public void setDueño(Cliente dueño){this.dueño = dueño;}
    public void setEventoAnfitrion(Evento eventoAnfitrion){this.eventoAnfitrion = eventoAnfitrion;}
    public void setOcupado(boolean estadoNuevo){this.ocupado = estadoNuevo;}
    
    public Cliente getDueño(){return this.dueño;}
    public Evento getEventoAnfitrion(){return this.eventoAnfitrion;}
    public boolean getEstadoSilla(){return this.ocupado;}
}
