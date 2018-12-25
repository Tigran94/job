import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class DBHelperImpl extends UnicastRemoteObject implements DBHelper {

    private Connection connection = null;

    protected DBHelperImpl() throws RemoteException {
    }


    private Connection setDBConnection(){
        if(connection==null){
            connection =  DBConnection.getConnection();
            return connection;
        }else return connection;
    }

    public int registerUser(String firstName, String lastName, String userName, String email, String password) throws RemoteException{
        connection = setDBConnection();

        String sql = "insert into users (firstName,lastName,userName,email,password) values (?,?,?,?,?);";

        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            stat.setString(1,firstName);
            stat.setString(2,lastName);
            stat.setString(3,userName);
            stat.setString(4,email);
            stat.setString(5,password);

            stat.execute();

            String idSql = "select max(id) from users";
            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery(idSql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
