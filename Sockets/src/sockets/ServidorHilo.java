/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class ServidorHilo extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
        
    }
    
    @Override
    public void run(){
        
        //String mensaje;
        int opcion;
        File f = new File("NumeroAleatorios.txt");
        boolean cerrar = false;
        
        while(!cerrar) {
            
            try {
                
                opcion = in.readInt();
                
                switch(opcion){
                    case 1:
                        escribirNumeroAleatorio(f, in.readInt());
                        System.out.println("Se escribio el numero en el cliente " +  nombreCliente);
                        out.writeUTF("Numero guardado correctamente");
                        break;
                    case 2:
                        break;
                    case 3:
                        System.out.println(in.readUTF());
                        System.out.println("El cliente " + nombreCliente + " se desconecto");
                        cerrar = true;
                        break;
                    default:
                        out.writeUTF("Solo los numeros que estan en pantalla");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    public void escribirNumeroAleatorio(File f, int numeroAleatorio) throws IOException{
        
        FileWriter fw = new FileWriter(f, true);
        fw.write(nombreCliente + ": " + numeroAleatorio + "\r\n");
        fw.close();
        
    }
    
}
