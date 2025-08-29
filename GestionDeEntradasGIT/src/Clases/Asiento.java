package Clases;

/**
 *
 * @author Tenerex
 */
public class Asiento {
    
    private Cliente dueño = null;
    private Evento eventoAnfitrion = null;
    private boolean isOcupado = false;
    
    public Asiento(Cliente dueño, Evento eventoAnfitrion, boolean ocupado) {    
        this.dueño = dueño;
        this.eventoAnfitrion = eventoAnfitrion;
        this.isOcupado = ocupado;
    }
    
    public void setDueño(Cliente dueño){this.dueño = dueño;}
    public void setEventoAnfitrion(Evento eventoAnfitrion){this.eventoAnfitrion = eventoAnfitrion;}
    public void setIsOcupado(boolean estadoNuevo){this.isOcupado = estadoNuevo;}
    
    public Cliente getDueño(){return this.dueño;}
    public Evento getEventoAnfitrion(){return this.eventoAnfitrion;}
    public boolean getAsientoIsOcupado(){return this.isOcupado;}
}
