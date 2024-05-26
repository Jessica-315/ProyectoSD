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
    private String mo;

    public ClienteServidorHilo(int puerto, String ip, String nombre) {
        this.puerto = puerto;
        this.ip = ip;
        this.nombre = nombre;
    }
    
    public int getPuerto() {
        return puerto;
    }
    
    public String getMo() {
        return mo;
    }
    
    public String[] getMoInfUsr(){
        return mo.split("/");
    }
    
    public int getMoTotalUsr(){
        return mo.split("/").length;
    }

    public void setMo(String mo) {
        this.mo = mo;
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
                
                String mensaje = in.readUTF();
                
                if(mensaje.contains("Finaliza")){
                    escucha = false;
                    System.out.println("Adios");
                }
                else if(mensaje.contains(",") && mensaje.contains("/")){    // Se conecta un nuevo usuario
                    setMo(mensaje);
                    System.out.println(getMo());
                }
                else{
                    System.out.println("Te envia " + mensaje);
                    out.writeUTF(nombre + ": MENSAJE RECIBIDO (" + "Hora: " + hours  + ":"+ minutes +":"+ seconds + ")");
                }
                
                sc.close();
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
