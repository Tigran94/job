package clientSide.pages;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage {
    private static Scanner scanner = new Scanner(System.in);
    private static Socket socket=null;
    private static final String ip = "192.168.2.85";
    private static final int port  = 8080;
    private static InputStream is=null;
    private static OutputStream os=null;
    private static BufferedReader reader=null;
    private static StringBuffer stringBuffer=null;
    private static PrintWriter writer = null;

    public LoginPage(){
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void start() throws IOException {
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
                    registerUser(start);
                    break;
                case "L":
                    login(start);
                    break;
                case "G":

                    guestLogin(start);
                    break;
                case "E":

                    System.out.println("We'll be waiting you");
                    return;
                default:
                    System.out.println("Please type correct value");
                    System.out.println();
                    continue loop;
            }
        }
    }

    private static void login(String start) throws IOException {
        System.out.println("Please Insert Your User Name: ");
        String userName = scanner.nextLine();
        System.out.println("Please Insert Your Password: ");
        String password = scanner.nextLine();

        socket=new Socket(ip,port);
        is = socket.getInputStream();
        os= socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(os,true);

        stringBuffer = new StringBuffer(start+","+userName+","+password);
        writer.println(stringBuffer);

        String result = reader.readLine();
        if(result.equals("false")){
            System.out.println("Please input correct values");
            login(start);
        }
    }

    private static void guestLogin(String start) throws IOException {

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

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        while (!matcher.matches()){
            System.out.println("Please Insert Correct Email: ");
            email = scanner.nextLine();
            matcher = pattern.matcher(email);
        }

        System.out.println("Please Insert Your Password: ");
        String password = scanner.nextLine();

        socket=new Socket(ip,port);
        is = socket.getInputStream();
        os = socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(os,true);

        stringBuffer = new StringBuffer(start+","+firstName+","+lastName+","+userName+","+email+","+password);
        writer.println(stringBuffer);

        String result = reader.readLine();

        System.out.println(result);
        System.out.println("Please login");
        login("L");
    }

}
