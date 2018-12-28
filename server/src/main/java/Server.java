
import dbServer.DBHelperImpl;
import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);


        System.out.println("Server has started");
        int port = Integer.parseInt("8889");
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("unknown", new DBHelperImpl());
    }
}
