package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {


    private static InputStream inputStream;
    private static Properties properties;

    static {
        try {
            inputStream = new FileInputStream("server/src/main/resources/db.properties");
            properties = new Properties();
            properties.load(inputStream);
        }catch (IOException e){
            System.out.println("No db properties file in resources");
            e.printStackTrace();
        }
    }


    public static String getDBUsername(){
        return properties.getProperty("db.username");
    }

    public static String getDBPassword(){
        return properties.getProperty("db.password");
    }

   public static String getDBUrl(){
        return properties.getProperty("db.url");
   }

    public static String getRMiPort(){
        return properties.getProperty("rmi.port");
    }

}
