import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {


    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/ingredientes";
    private String user = "root";
    private String password = "senha";

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(this.driver);
        return DriverManager
                .getConnection(this.url, this.user, this.password);
    }
}

