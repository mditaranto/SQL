import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertarDatos {
    public static void InsertarDatosTabla(Connection conexion) throws Exception {
        Statement tabla1 = null;
        Statement tabla2 = null;
        Statement tabla3 = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla1 = conexion.createStatement();
            String sql = "INSERT INTO Player (idPlayer, Nick, password, email)" +
            "VALUES" +
                "(1, 'Usuario1', 'contraseña1', 'usuario1@example.com')," +
                "(2, 'Usuario2', 'contraseña2', 'usuario2@example.com')," +
                "(3, 'Usuario3', 'contraseña3', 'usuario3@example.com');";
            tabla1.executeUpdate(sql);

            tabla2 = conexion.createStatement();
            String sql2 = "INSERT INTO Games (idGames, Nombre, tiempoJugado)" +
            "VALUES" +
                "(1, 'Juego1', '02:30:00')," +
                "(2, 'Juego2', '01:45:00')," + 
                "(3, 'Juego3', '03:15:00');";
            tabla2.executeUpdate(sql2);

            tabla3 = conexion.createStatement();
            String sql3 = "INSERT INTO Compras (idCompra, idPlayer, idGames, Cosa, precio, FechaCompra)" +
            "VALUES" +
                "(1, 1, 1, 'Objeto1', 19.99, '2023-11-15')," +
                "(2, 2, 3, 'Objeto2', 9.99, '2023-10-20')," +
                "(3, 3, 2, 'Objeto3', 14.50, '2023-12-05');";
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

