import DB.DBHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.Scanner;

public class Client {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        loop: while (true){
            System.out.println("Welcome to job.am");
            System.out.println("Type R -> registration");
            System.out.println("Type L -> login");
            System.out.println("Type G -> guest user");
            System.out.println("Type E -> exit");
            String start = scanner.nextLine();
            switch (start){
                case "R":
                    registerUser();
                    break;
                case "L":
                    login();
                    break;
                case "G":
                    guestLogin();
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

    private static void registerUser() {
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

        DBHelper dbHelper = null;
        int userId = 0;

        try {
            dbHelper = (DBHelper) Naming.lookup("rmi://192.168.2.85/unknown");
            userId = dbHelper.registerUser(firstName,lastName,userName,email,password);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (userId != -1) {
            System.out.println(
                    MessageFormat.format(
                            "Phone Number: {0} created successfully",
                            userId
                    )
            );
        } else {
            System.out.println(
                    "Error while creating the user"
            );
        }
    }

    private static void guestLogin() {

    }

    private static void login() {

    }
}
