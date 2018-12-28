import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String ip = "192.168.2.85";
    private static final int port  = 8080;
    private static Socket socket=null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) throws IOException {
        loop:
        while (true) {
            System.out.println("Welcome to job.am");
            System.out.println("Type R -> registration");
            System.out.println("Type L -> login");
            System.out.println("Type G -> guest user");
            System.out.println("Type E -> exit");
            String start = scanner.nextLine();
            switch (start) {
                case "R":
                   // sendServerSymbol(start);
                    registerUser(start);
                    break;
                case "L":
                    sendServerSymbol(start);

                    //  login();
                    break;
                case "G":
                    sendServerSymbol(start);

                    //guestLogin();
                    break;
                case "E":
                    sendServerSymbol(start);

                    System.out.println("We'll be waiting you");
                    return;
                default:
                    System.out.println("Please type correct value");
                    System.out.println();
                    continue loop;


            }
        }

       // socket = new Socket(ip,port);
      //  Scanner scanner = new Scanner(System.in);

      //  String v1 = scanner.nextLine();

       // String v2 = scanner.nextLine();
    }
    private static void sendServerSymbol(String symbol) throws IOException {
        socket=new Socket(ip,port);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(os,true);
        writer.println(symbol);

      //  String result = reader.readLine();

        System.out.println("Please wait, server is working...");

    }
    private static void registerUser(String start) throws IOException {
        System.out.println("Please Insert Your First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Please Insert Your Last Name: ");
        String lastName = scanner.nextLine();
        System.out.println("Please Insert Your User Name: ");
        String userName = scanner.nextLine();
        System.out.println("Please Insert Your Email: ");
        String email = scanner.nextLine();
        System.out.println("Please Insert Your Password: ");
        String password = scanner.nextLine();

        socket=new Socket(ip,port);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(os,true);

        StringBuffer stringBuffer = new StringBuffer(start+","+firstName+","+lastName+","+userName+","+email+","+password);
        writer.println(stringBuffer);

        String result = reader.readLine();

        System.out.println(result);
       // System.out.println(v1 + " + "+ v2 + " = "+ result);
    }
}
