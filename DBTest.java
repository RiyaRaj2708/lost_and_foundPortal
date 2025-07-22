import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    public static void main(String[] args) {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/lost_and_found", "root", "riyaraj@1234"
            );

            System.out.println("✅ Connected Successfully to MySQL!");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
