package serverSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void  main(String ...args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);

        while(true){
            System.out.println("Waiting for a connection ... ");
            Socket s = ss.accept();
            System.out.println("Connected " + ss);
            new Thread(new ClientProcessor(s)).start();

        }
    }
}
