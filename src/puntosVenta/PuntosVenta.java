/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puntosVenta;

/**
 * Recurso de la práctica
 *
 * @author javi
 */
public class PuntosVenta {

    private int libresEfectivo = 2;
    private int libresTarjeta = 2;
    private int efectivosEsperando = 0;

    private CanvasPuntosVenta canvas;

    public PuntosVenta() {
    }

    public PuntosVenta(CanvasPuntosVenta canvas) {
        this.canvas = canvas;
    }

    public synchronized void entraEfectivo() throws InterruptedException {
        String id = Thread.currentThread().getName();
        efectivosEsperando++;
        System.out.println("\tRecurso: Hilo " + id + " entrando a cola y esperando...");

       
        canvas.entraCola(id);
        

        while (libresEfectivo == 0) {
            wait();
        }
        efectivosEsperando--;

        System.out.println("\tRecurso: Hilo " + id + " entrando a punto de venta...");
        libresEfectivo--;

     
        canvas.entraTerminal(id, 'E');
        
    }

    /**
     *
     * @return 'T' si entra en terminal tarjeta <br> 'E' si entra en terminal
     * efectivo
     * @throws InterruptedException
     */
    public synchronized char entraTarjeta() throws InterruptedException {
        String id = Thread.currentThread().getName();
        char tipoTerminal;
        System.out.println("\tRecurso: Hilo " + id + " entrando a cola y esperando...");

       
        canvas.entraCola(id);
        

        while ((libresTarjeta == 0) && (libresEfectivo == 0 || efectivosEsperando > 0)) {
            wait();
        }

        System.out.print("\tRecurso: Hilo " + id + " entrando a punto de venta ");
        if (libresTarjeta > 0) {
            System.out.println("de tarjeta...");
            tipoTerminal = 'T';
            libresTarjeta--;
        } else {
            System.out.println("de efectivo...");
            tipoTerminal = 'E';
            libresEfectivo--;
        }

        
        canvas.entraTerminal(id, tipoTerminal);
        

        return tipoTerminal;
    }

    public synchronized void saleEfectivo() {
        String id = Thread.currentThread().getName();
        System.out.println("\tRecurso: Hilo " + id + " saliendo de punto de venta...");
        libresEfectivo++;

        
        canvas.saleTerminal(id);
        

        notifyAll();
    }

    public synchronized void saleTarjeta(char tipoTerminal) {
        String id = Thread.currentThread().getName();
        System.out.print("\tRecurso: Hilo " + id + " saliendo de punto de venta ");

        if (tipoTerminal == 'T') {
            System.out.println("de tarjeta...");
            libresTarjeta++;
        } else {
            System.out.println("de efectivo...");
            libresEfectivo++;
        }

        
        canvas.saleTerminal(id);
        

        notifyAll();
    }

}
