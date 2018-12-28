<<<<<<< HEAD
import db.DBHelperImpl;
=======
import dbServer.DBHelperImpl;
import utilities.DBProperties;
>>>>>>> 2561ba7e2f8d52459f82e11ecf4c640cb775caf8

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{



    public static void main(String[] args) throws IOException {

        System.out.println("Server has started");
        int port = Integer.parseInt(DBProperties.getRMiPort());
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("unknown", new DBHelperImpl());
    }
}
