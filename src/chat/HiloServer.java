package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloServer extends Thread{
    Socket cliente = null;
    ArrayList<Socket> users = null;
    DataInputStream entrada = null;
    DataOutputStream salida = null;
    String nombre = "";
    String mensaje= "";
    boolean escuchando = true;
	
    public HiloServer(Socket cliente, ArrayList<Socket> users) {
        this.cliente = cliente;
        this.users = users;
    }
    
    @Override
    public void run() {    
        try {
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
        }catch (IOException ex) {
            System.out.println(ex.toString());
        }
            
        while(escuchando){  
            try {
                mensaje = entrada.readUTF();
                int i = mensaje.indexOf(' ');
                if (i != -1) nombre = mensaje.substring(0, i);
                
                if (mensaje.equals("123456")){
                    enviarOtrosClientes(nombre + " abandona la sala");
                    escuchando = false;
                    users.remove(users.size()-1);
                    System.out.println(nombre + " se ha desconectado");
                }else enviarOtrosClientes(mensaje);
                
                }catch (IOException ex) {
                    System.out.println(ex.toString());
                    escuchando = false;
                }
            }
        
            try {
                entrada.close();
                salida.close();
                cliente.close();
                
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
	}

    private void enviarOtrosClientes(String mensaje) { 
        for (int i = 0; i < users.size(); i++) {
            Socket s = users.get(i);
            try {
                DataOutputStream salidaUsers = new DataOutputStream(s.getOutputStream());
                salidaUsers.writeUTF(mensaje);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
    
    
}

