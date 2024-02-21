import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    public static Connection GetConexion() {
        Connection conexion = null;
        String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2324_mditaranto";
        String usuario = "mditaranto";
        String clave = "12345";

        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión establecida: " + conexion.toString());

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }

        return conexion;
    }  
    
}
