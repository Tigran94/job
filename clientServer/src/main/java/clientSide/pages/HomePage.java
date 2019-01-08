package clientSide.pages;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage {
    private static Scanner scanner = new Scanner(System.in);

    private static void start() throws IOException {
        loop:
        while (true) {
            System.out.println("Hello dear client");
            System.out.println("Type S -> search");
            System.out.println("Type P -> post");
            System.out.println("Type L -> logout");
            System.out.println("Type E -> exit");

            String start = scanner.nextLine();
            switch (start) {
                case "S":
                    search(start);
                    break;
                case "P":
                  //  post(start);
                    break;
                case "L":
                   // logout(start);
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

    private static void search(String start) {
        System.out.println("All fields can be empty");

        System.out.println("Please Insert Title: ");
        String title = scanner.nextLine();
        System.out.println("Please Insert Description: ");
        String description = scanner.nextLine();
        System.out.println("Please Insert Type: ");
        String type = scanner.nextLine();
        System.out.println("Please Insert Salary: ");
        String salary = scanner.nextLine();
        System.out.println("Please Insert Working Time: if Half Time-> (H) , if Full Time -> (F)");
        String workTime = scanner.nextLine();
        while(!workTime.equals("H") || !workTime.equals("F")){
            workTime = scanner.nextLine();
        }
        System.out.println("Please Insert Email: ");
        String email = scanner.nextLine();

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        while (!matcher.matches()){
            System.out.println("Please Insert Correct Email: ");
            email = scanner.nextLine();
            matcher = pattern.matcher(email);
        }


    }
}
