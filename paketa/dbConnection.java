package paketa;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection() throws NamingException, SQLException, ClassNotFoundException {
        String userName = "root";
        String password = "riseandshine";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/siguri?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UCT";
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
