/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class ClienteServidorHilo extends Thread{
    
    private int puerto;
    private String ip;
    private String nombre;

    public ClienteServidorHilo(int puerto, String ip, String nombre) {
        this.puerto = puerto;
        this.ip = ip;
        this.nombre = nombre;
    }
    
    public int getPuerto() {
        return puerto;
    }
    
    @Override
    public void run(){
        try {
            
            ServerSocket server = new ServerSocket(puerto);
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");
            System.out.println("El cliente " + nombre + " con IP " + ip + " está a la escucha,\n puerto: " + puerto);
            System.out.println("-----------------------------------------------------------");
            System.out.println("-----------------------------------------------------------");
            
            boolean escucha = true;
            
            while(escucha){
                
                Socket sc = server.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                LocalDateTime locaDate = LocalDateTime.now();
                int hours  = locaDate.getHour();
                int minutes = locaDate.getMinute();
                int seconds = locaDate.getSecond();
                
                System.out.println("Te envia " + in.readUTF());
                out.writeUTF(nombre + ": MENSAJE RECIBIDO (" + "Hora: " + hours  + ":"+ minutes +":"+ seconds + ")");
                
                //sc.close();
                //escucha = false;
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
