package fposs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
/*
 *Mike Duffenais
 * Since 11/21/2013
 * Connects to DB and returns conn connnection 
 */
 
public class DbConnect {
// set user name password and host 
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String HOST = "jdbc:mysql://localhost:3306/fposs";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
        }
    }
// Connects to database. 
    public static Connection Connect() throws SQLException {
        
        Connection conn = null;

        try {
            // connection to DB 
            conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }
}