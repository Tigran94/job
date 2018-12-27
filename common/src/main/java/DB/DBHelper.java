package DB;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DBHelper extends Remote {

    int registerUser(String firstName, String lastName, String userName, String email, String password) throws RemoteException;
}
