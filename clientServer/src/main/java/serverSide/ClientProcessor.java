package serverSide;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import clientSide.Users;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.*;
import java.net.Socket;

class ClientProcessor implements Runnable{
    private Socket socket;
    private static Session session = null;
    private String[] data;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static Users loggedInUser = null;

    public ClientProcessor(Socket socket){
        this.socket = socket;
    }

    static Session getConnection() {
        return sessionFactory.openSession();
    }

    @Override
    public void run(){
        InputStream is=null;
        OutputStream os=null;
        {
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(os,true);
        try {
            String symbol =  readSymbol(reader.readLine());
            switch (symbol){
                case "R":
                    registerUser(writer);
                    break;
                case "L":
                    login(writer);
                    break;
                case "G":
                    guestLogin();
                    break;
                    default:return;
            }
        }catch(IOException e){

        }
    }

    private void guestLogin() {
        session = getConnection();

    }

    private void login(PrintWriter writer) {
        session = getConnection();
        Users users = new Users();
        users.setUserName(data[1]);
        users.setPassword(data[2]);
        Transaction transaction = session.beginTransaction();
        //session.createQuery("SELECT userName,password FROM Users WHERE userName=? AND password=?");
        TypedQuery<Users> query = session.createQuery("from Users h where h.userName = :userName and h.password = :password", Users.class);
        query.setParameter("userName", users.getUserName());
        query.setParameter("password", users.getPassword());

        transaction.commit();
        try {
            loggedInUser = query.getSingleResult();
            writer.println("You have successfully login");

        }catch (NoResultException e){
            writer.println(false);
        }
        if(transaction.isActive()){
            session.flush();
        }
        session.close();


    }

    private String readSymbol(String clientData){
        data = clientData.split(",");
        return data[0];
    }

    private void registerUser(PrintWriter writer){
        session = getConnection();
        Users users = new Users();

        users.setFirstName(data[1]);
        users.setLastName(data[2]);
        users.setUserName(data[3]);
        users.setEmail(data[4]);
        users.setPassword(data[5]);

        Transaction transaction = session.beginTransaction();
        session.save(users);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();
        }
        session.close();

        writer.println("Successfully registered");
    }
}
