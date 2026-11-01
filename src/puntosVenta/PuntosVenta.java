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

//    while (libreTarjeta==0) && (libreEfectivo == 0) || (EfejctivoEsperando >0)
//    if( libreTarjeta >0)
//        tarjeta--
//            return t
//    else
//        efectivo--
//          return e
    public synchronized void entraEfectivo() throws InterruptedException {

        efectivosEsperando++;
        System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " entrando a cola y esperando...");

        while (libresEfectivo == 0) {
//            System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
//                    + " esperando...");
            wait();
        }
        efectivosEsperando--;

        System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " entrando a punto de venta...");
        libresEfectivo--;
    }

    /**
     *
     * @return 'T' si entra en terminal tarjeta <br> 'E' si entra en terminal
     * efectivo
     * @throws InterruptedException
     */
    public synchronized char entraTarjeta() throws InterruptedException {
        char tipoTerminal;
        System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " entrando a cola y esperando...");

        while ((libresTarjeta == 0) && (libresEfectivo == 0 || efectivosEsperando > 0)) {
//            System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
//                    + " esperando...");
            wait();
        }

        System.out.print("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " entrando a punto de venta ");
        if (libresTarjeta > 0) {
            System.out.println("de tarjeta...");
            tipoTerminal = 'T';
            libresTarjeta--;
        } else {
            System.out.println("de efectivo...");
            tipoTerminal = 'E';
            libresEfectivo--;
        }
        return tipoTerminal;
    }

    public synchronized void saleEfectivo() {
        System.out.println("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " saliendo de punto de venta...");
        libresEfectivo++;
        notifyAll();
    }

    public synchronized void saleTarjeta(char tipoTerminal) {

        System.out.print("\tRecurso: Hilo " + Thread.currentThread().getName()
                + " saliendo de punto de venta ");

        if (tipoTerminal == 'T') {
            System.out.println("de tarjeta...");
            libresTarjeta++;
        } else {
            System.out.println("de efectivo...");
            libresEfectivo++;
        }

        notifyAll();
    }

}
