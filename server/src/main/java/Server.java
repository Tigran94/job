import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{
    public static void main(String[] args) throws RemoteException {
        System.out.println("Server has started");
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("unknown", new DBHelperImpl());
    }
}
