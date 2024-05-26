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
import java.net.ConnectException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                        out.writeUTF("Escribe el mensaje:");
                        String mensaje = in.readUTF();
                        String ip = mensaje.split("/")[1];
                        int puerto = Integer.valueOf(mensaje.split("/")[2]);
                        System.out.println(nombreCliente + " manda mensaje a " + ip);
                        
                        try(
                            Socket ss = new Socket(ip, puerto);
                            DataOutputStream outOut = new DataOutputStream(ss.getOutputStream());   // Reenvio de informacion al cliente destino
                            DataInputStream inIn = new DataInputStream(ss.getInputStream());
                        ){
                        
                            LocalDateTime locaDate = LocalDateTime.now();
                            int hours  = locaDate.getHour();
                            int minutes = locaDate.getMinute();
                            int seconds = locaDate.getSecond();

                            String conversacion = nombreCliente + ": " + mensaje.split("/")[0] + 
                                    " (Hora: " + hours  + ":"+ minutes +":"+ seconds + ")";

                            outOut.writeUTF(conversacion);
                            String confirmacion = inIn.readUTF();

                            out.writeUTF(confirmacion);

                            System.out.println(in.readUTF());
                            out.writeUTF(conversacion + '\n' + confirmacion); // Se almacena la conversacion

                            inIn.close();
                            outOut.close();
                            ss.close();
                        } catch (ConnectException ce){
                            System.out.println("Error al establecer una conexion: " + ce.getMessage());
                            out.writeUTF("El nodo al que desea enviar mensaje se encuentra desconectado");
                            System.out.println(in.readUTF());
                            out.writeUTF("Intento fallido al enviar un mensaje a un nodo desconectado (" + 
                                    ip + ")");
                        }
                        break;
                    case 3:
                        String mensajeArr[];
                        
                        mensajeArr = in.readUTF().split(",");   // Recibe un mensaje, ip y puerto

                        Socket sf = new Socket(mensajeArr[1], Integer.valueOf(mensajeArr[2]));
                        DataOutputStream outF = new DataOutputStream(sf.getOutputStream());
                        
                        outF.writeUTF("Finaliza");
                        
                        System.out.println("El cliente " + nombreCliente + " se desconecto");
                        
                        outF.close();
                        sf.close();
                        
                        in.close();
                        out.close();
                        cerrar = true;
                        break;
                    case 4:
                        System.out.println(in.readUTF());
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
