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
    @Override
    public void run(){
        try {
            
            ServerSocket server = new ServerSocket(5001);
            System.out.println("El cliente está a la escucha");
            
            while(true){
                
                Socket sc = server.accept();
                DataInputStream in = new DataInputStream(sc.getInputStream());
                
                System.out.println(in.readUTF());
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
