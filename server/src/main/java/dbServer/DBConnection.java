package dbServer;

import utilities.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    DBProperties.getDBUrl(),
                    DBProperties.getDBUsername(),
                    DBProperties.getDBPassword()
            );
            System.out.println("Connection successful");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
