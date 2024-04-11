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
            
            /*String nick, ip, mensaje;   //  <-------------------
            PaqueteEnvio dataIn;    //  <-------------------*/
            
            ArrayList<Socket> listaClientesS = new ArrayList<>();   // Lista de sockets
            ArrayList<String> listaClientesN = new ArrayList<>();   // Lista de nombres
            ArrayList<String> listaClientesIP = new ArrayList<>();   // Lista de IPs
            int nC = 1; //Numero de cliente
            
            System.out.println("Servidor iniciado");
            while(true){
                
                sc = server.accept();
                listaClientesS.add(sc);
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                /*ObjectInputStream pData = new ObjectInputStream(sc.getInputStream());    //  <-------------------
                dataIn = (PaqueteEnvio) pData.readObject(); //  <-------------------
                
                nick = dataIn.getNick();    //  <-------------------
                ip = dataIn.getIp();    //  <-------------------
                mensaje = dataIn.getMensaje();  //  <-------------------
                System.out.println(nick + ": " + mensaje + " para " + ip);  //  <-------------------
                Socket ss = new Socket(ip, 5001);   //  <-------------------
                ObjectOutputStream pData2Send = new ObjectOutputStream(sc.getOutputStream());    //  <-------------------
                pData2Send.writeObject(dataIn);
                pData2Send.close();
                ss.close();
                */
                
                System.out.println("Nuevo cliente (" + nC + ")");
                out.writeUTF("Indica tu nombre");
                String nombreCliente = in.readUTF();
                out.writeUTF("Indica tu IP");
                String ipCliente = in.readUTF();
                
                listaClientesN.add(nombreCliente);
                listaClientesIP.add(ipCliente);
                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();
                
                /*System.out.println("Creada la conexion con el cliente " + nombreCliente +
                " con la direccion: " + listaClientesS.get(nC).getInetAddress() + 
                " con el puerto: " + listaClientesS.get(nC).getLocalPort());*/
                System.out.println("Creada la conexion con el cliente " + nombreCliente);// +
                //" con la direccion: " + ipCliente + 
                //" con el puerto: " + listaClientesS.get(nC-1).getLocalPort());
                
                nC += 1;
                
            }
        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } /*catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
}

/*
class Paquete1{
private String mensaje;
private String ip;
private String nick2;
private ArrayList<String> dirIps;
private ArrayList<Controles> control;
private int puerto;
private String destinatario;

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public ArrayList<Controles> getControl() {
        return control;
    }

    public void setControl(ArrayList<Controles> control) {
        this.control = control;
    }
   
   
    public ArrayList<String> getDirIps() {
        return dirIps;
    }

    public void setDirIps(ArrayList<String> dirIps) {
        this.dirIps = dirIps;
    }


 

 
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNick2() {
        return nick2;
    }

    public void setNick2(String nick2) {
        this.nick2 = nick2;
    }

 

}
class Controles{
private String ipcontrol;
private String nombrecontrol;
private int puerto;

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getIpcontrol() {
        return ipcontrol;
    }

    public void setIpcontrol(String ipcontrol) {
        this.ipcontrol = ipcontrol;
    }

    public String getNombrecontrol() {
        return nombrecontrol;
    }

    public void setNombrecontrol(String nombrecontrol) {
        this.nombrecontrol = nombrecontrol;
    }

}
*/