import db.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection c = DBConnection.getConnection();
        System.out.println("Connexion OK : " + (c != null));
        c.close();
    }
}
