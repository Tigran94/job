<<<<<<< HEAD:server/src/main/java/DB/DBConnection.java
package db;
=======
package dbServer;

import utilities.DBProperties;
>>>>>>> 2561ba7e2f8d52459f82e11ecf4c640cb775caf8:server/src/main/java/dbServer/DBConnection.java

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
