import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfiguration {

    static final String url="jdbc:mysql://localhost:3306/Jukebox";
    static final String user="root";
    static final String password="krishna@1232";

    public static Connection getConnection(){
        Connection conn=null;
        try
        {
            conn= DriverManager.getConnection(url,user,password);
           // System.out.println("Connected With DataBase Successfully");
            Statement stmt=conn.createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return conn;
    }

}
