/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;

            int nC = 1; //Numero de cliente
            
            ArrayList<String> mo = new ArrayList<>();   // maquinas online = <nombre>,<ip>,<puerto>
            
            System.out.println("Servidor iniciado");
            while(true){
                
                sc = server.accept();
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                
                //----------- DETECCION ONLINE --------- 
                InetAddress ipInet = sc.getInetAddress();   // Obtiene direccion del cliente en formato inet
                String ipCliente = ipInet.getHostAddress();  // Obtenemos la ip del cliente que se acaba de conectar
                
                InetAddress ipLocal = sc.getLocalAddress();
                String ipServidor = ipLocal.getHostAddress();
                //----------- ---------------- --------- 
                
                System.out.println("ip cliente: " + ipCliente + " ------- ip servidor: " + ipServidor);
                
                System.out.println("Nuevo cliente (" + nC + ")");
                out.writeUTF("Indica tu nombre");
                String[] mensaje = in.readUTF().split(",");
                String nombreCliente = mensaje[0];
                String puertoCliente = mensaje[1];      // Integer.valueOf(mensaje[1]);
                out.writeUTF(ipCliente);        // ---------->

                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();
                
                System.out.println("Creada la conexion con el cliente " + nombreCliente + " con ip: " + ipCliente);
                nC += 1;
                String moStr = "";
                
                //System.out.println(in.readUTF());
                
                if(!mo.contains(nombreCliente + "," + ipCliente + "," + puertoCliente)){
                    mo.add(nombreCliente + "," + ipCliente + "," + puertoCliente);
                }

                System.out.println("\n----------------- Maquinas conectadas -----------------");
                for(int i = 0; i < mo.size(); i++){
                    System.out.println(mo.get(i));
                    moStr = moStr + mo.get(i) + "/";
                }
                System.out.println("-------------------------------------------------------\n");

                for(int i = 0; i < mo.size(); i++){
                    System.out.println("----------> " + mo.get(i).split(",")[1]);
                    try (Socket so = new Socket(mo.get(i).split(",")[1], 5011); 
                            DataOutputStream outOnline = new DataOutputStream(so.getOutputStream())) {
                        outOnline.writeUTF(moStr);
                        outOnline.close();
                        so.close();
                    } catch (ConnectException ce){
                        System.out.println("Error al establecer una conexion:" + ce.getMessage());
                    }
                }
                
                
            }
        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}