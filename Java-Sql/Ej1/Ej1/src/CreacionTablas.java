
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionTablas {

    
    public static void CrearTablas(Connection conexion) throws SQLException {
        Statement tabla1 = null;
        Statement tabla2 = null;
        Statement tabla3 = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla1 = conexion.createStatement();
            String sql = "CREATE TABLE Player(" +
                    "idPlayer INT primary key," +
                    "Nick varchar(45)," +
                    "password varchar(128)," +
                    "email varchar(100)" +
                    ");";
            tabla1.executeUpdate(sql);

            tabla2 = conexion.createStatement();
            String sql2 = "CREATE TABLE Games(" +
                "idGames INT primary key," +
                "Nombre varchar(45)," +
                "tiempoJugado time" +
                ");";
            tabla2.executeUpdate(sql2);

            tabla3 = conexion.createStatement();
            String sql3 = "CREATE TABLE Compras(" +
                "idCompra INT primary key," +
                "idPlayer INT," +
                "idGames INT," +
                "Cosa varchar(45)," +
                "precio decimal(6,2)," +
                "FechaCompra date," +
                "FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)," +
                "FOREIGN KEY (idGames) REFERENCES Games(idGames)" +
                ");";
            tabla3.executeUpdate(sql3);

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla1 != null) {
                tabla1.close();
            }
            if (tabla2 != null) {
                tabla2.close();
            }
            if (tabla3 != null) {
                tabla3.close();
            }
        }

    }
}
