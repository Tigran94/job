import entities.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
class ClientProcessor implements Runnable{
    private Socket socket;
    private static Session session = null;
    private String[] data;
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


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
                 //   login();
                    break;
                case "G":
                 //   guestLogin();
                    break;
                    default:return;
            }
        }catch(IOException e){

        }
    }
    private String readSymbol(String clientData){
        data = clientData.split(",");
        return data[0];
    }

    private void registerUser(PrintWriter writer){
        session = getConnection();
        Users users = new Users();

        users.setFirstName(data[0]);
        users.setLastName(data[1]);
        users.setUserName(data[2]);
        users.setEmail(data[3]);
        users.setPassword(data[4]);

        Transaction transaction = session.beginTransaction();
        session.save(users);
        transaction.commit();
        if(transaction.isActive()){
            session.flush();}
        session.close();
        writer.println("successful");
    }
}