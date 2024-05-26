/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            String dir = "192.168.152.130";   // AQUI SE TIENE QUE SUSTITUIR LA IP POR LA DE LA MAQUINA DEL SERVIDOR
            
            
            Socket sc = new Socket(dir, 5000);
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            
            String mensaje = in.readUTF();  // Nombre
            System.out.println(mensaje);
            
            int puertoRandom = 5011;//generaPuertoAleatorio(5001, 5100);
            
            String nombre = sn.next();
            out.writeUTF(nombre + "," + puertoRandom);

            
            String ip = in.readUTF();   // ---------->
            
            

            ClienteServidorHilo hiloEscucha = new ClienteServidorHilo(puertoRandom, ip, nombre);
            
            ClienteHilo hiloMenu = new ClienteHilo(in, out, sc, puertoRandom, ip, nombre, hiloEscucha);
            
            
            
            //hiloEscucha.setMo(ip);
            hiloEscucha.start();
            hiloMenu.start();
            hiloMenu.join();
            hiloEscucha.join();
            
            /*
            out.writeUTF("Verificando si es cliente nuevo");
            String esNuevo = in.readUTF();
            
            if(!esNuevo.equals("Nuevo")){
                hiloEscucha.setMo(esNuevo);
            }*/
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int generaPuertoAleatorio(int minimo, int maximo){
        return (int)Math.floor(Math.random() * (maximo-minimo+1) + (minimo));
    }
    
}