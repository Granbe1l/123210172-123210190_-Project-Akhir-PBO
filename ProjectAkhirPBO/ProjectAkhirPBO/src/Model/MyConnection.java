package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {

    
    static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/ems", "root", "");
            System.out.println("connected");
        } catch (Exception ex) {

            System.out.println("Error" + ex.getMessage());

        }
        return con;
    }

}
