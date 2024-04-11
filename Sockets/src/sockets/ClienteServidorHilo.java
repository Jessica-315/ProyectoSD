/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class ClienteServidorHilo extends Thread{
    
    private int puerto;

    public ClienteServidorHilo(int puerto) {
        this.puerto = puerto;
    }
    
    public int getPuerto() {
        return puerto;
    }
    
    @Override
    public void run(){
        try {
            
            ServerSocket server = new ServerSocket(puerto);
            System.out.println("El cliente está a la escucha, puerto: " + puerto);
            
            boolean escucha = true;
            
            while(escucha){
                
                Socket sc = server.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                
                System.out.println("Te envian: " + in.readUTF());
                
                //sc.close();
                //escucha = false;
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
