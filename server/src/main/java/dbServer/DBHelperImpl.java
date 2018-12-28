
package dbServer;

import db.DBHelper;
import entities.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DBHelperImpl extends UnicastRemoteObject implements DBHelper {

    private static Session session = null;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public DBHelperImpl() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {

        DBHelperImpl dbHelper = new DBHelperImpl();
        dbHelper.registerUser("hel1a","d2wja","wdda3","ww4qada","aa5wd");
    }
    static Session getConnection() {
        return sessionFactory.openSession();
    }

    public void registerUser(String firstName, String lastName, String userName, String email, String password) throws RemoteException {
        session = getConnection();
        Users users = new Users();

        users.setFirstName(firstName);
        users.setLastName(lastName);
        users.setUserName(lastName);
        users.setEmail(lastName);
        users.setPassword(lastName);

        Transaction transaction = session.beginTransaction();
        session.save(users);
        transaction.commit();
        if(transaction.isActive()){
           session.flush();}
        session.close();
        System.out.println("User is registered");
    }
}
