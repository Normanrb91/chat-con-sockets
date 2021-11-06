package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

    public static void main(String[] args){
        boolean activo = true;
        
        try {
            ServerSocket servidor = new ServerSocket(90);
            ArrayList<Socket> users = new ArrayList<Socket>();

            System.out.println("Servidor corriendo en el puerto " + servidor.getLocalPort());

            while(activo) {
                Socket cliente = new Socket();
                cliente = servidor.accept();
                users.add(cliente);
                HiloServer hl = new HiloServer(cliente, users);
                hl.start();
            }
        
        } catch (IOException ex) {
            System.out.println("en el server");
            activo = false;
        }

    }

}