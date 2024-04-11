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
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class ClienteHilo extends Thread{
    
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc;
    private int pr;
    private String nombre;

    public ClienteHilo(DataInputStream in, DataOutputStream out, Socket sc, int pr, String nombre) {
        this.in = in;
        this.out = out;
        this.sc = sc;
        this.pr = pr;
        this.nombre = nombre;
    }
    
    @Override
    public void run(){
        
        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion = 0;
        boolean salir = false;
        
        File f = new File("Conversaciones-" + nombre + ".txt");


        while(!salir){
            try {
                System.out.println("1. Almacenar numero en el archivo");
                System.out.println("2. Mandar mensaje");
                System.out.println("3. Salir");

                opcion = sn.nextInt();
                out.writeInt(opcion);

                switch (opcion) {
                    case 1:
                        int numeroAleatorio = generaNumeroAleatorio(1, 100);
                        System.out.println("Numero generado: " + numeroAleatorio);
                        out.writeInt(numeroAleatorio);
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                    case 2:
                        System.out.println(in.readUTF());
                        mensaje = sn.next();
                        out.writeUTF(mensaje);
                        System.out.println(in.readUTF());
                        mensaje = sn.next();
                        out.writeUTF(mensaje);
                        System.out.println(in.readUTF());
                        out.writeInt(sn.nextInt());
                        System.out.println(in.readUTF());   // Confirmacion de recibido
                        
                        out.writeUTF("Mensaje almacenado");
                        almacenarMensaje(f, in.readUTF());
                        
                        break;
                    case 3:
                        mensaje = "Desconectando";
                        out.writeUTF(mensaje);
                        sc.close();
                        salir = true;
                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                }

            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            
            
            
            
            /*try {   //  <-------------------
            ServerSocket server = new ServerSocket(5001);   //  <-------------------
            Socket sc;  //  <-------------------
            
            PaqueteEnvio dataIn;    //  <-------------------
            
            while(true) {   //  <-------------------
            
            sc = server.accept();   //  <-------------------
            
            ObjectInputStream pData = new ObjectInputStream(sc.getInputStream());    //  <-------------------
            dataIn = (PaqueteEnvio) pData.readObject(); //  <-------------------
            System.out.println(dataIn.getNick() + ": " + dataIn.getMensaje());  //  <-------------------
                
            }   //  <-------------------
            
            } catch (IOException ex) {  //  <-------------------
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);  //  <-------------------
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);  //  <-------------------
            }   //  <-------------------*/

        
    }
    
    public int generaNumeroAleatorio(int minimo, int maximo){
        return (int)Math.floor(Math.random() * (maximo-minimo+1) + (minimo));
    }
    
    public void almacenarMensaje(File f, String mensaje) throws IOException{
        
        FileWriter fw = new FileWriter(f, true);
        fw.write(mensaje + "\r\n");
        fw.close();
        
    }
    
}
