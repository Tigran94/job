
import dbServer.DBHelperImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utilities.DBProperties;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{

    public static void main(String[] args) throws IOException {


        System.out.println("Server has started");
        int port = Integer.parseInt("8889");
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("unknown", new DBHelperImpl());
    }
}
