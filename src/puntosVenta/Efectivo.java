/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puntosVenta;

import java.util.Random;

/**
 *
 * @author javi
 */
public class Efectivo extends Thread {

    private final String idHilo;
    private final PuntosVenta recurso;
    private final Random rnd;
    private final int[] rangoEspera;

    public Efectivo(String idHilo, PuntosVenta recurso, Random rnd,
             int[] rangoEspera) {
        this.idHilo = idHilo;
        this.recurso = recurso;
        this.rnd = rnd;
        this.rangoEspera = rangoEspera;
    }

    @Override
    public void run() {
        // 
        this.setName(idHilo);
        System.out.println("Hilo " + idHilo + " empezando...");

        try {
            // entrar
            System.out.println("Hilo " + idHilo + " entrando...");
            recurso.entraEfectivo();

            // Emular pago esperando
            sleep(rnd.nextInt(rangoEspera[0], rangoEspera[1] + 1) * 1000);

            // salir
            System.out.println("Hilo " + idHilo + " saliendo...");
            recurso.saleEfectivo();

        } catch (InterruptedException ex) {
            System.getLogger(Efectivo.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

}
