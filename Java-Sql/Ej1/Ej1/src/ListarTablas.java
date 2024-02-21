import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListarTablas {

    public static void Listar(Connection conexion, int listar) throws Exception {
        switch (listar) {
            case 1:
                ListarPlayer(conexion);
                break;
            case 2:
                ListarGames(conexion);
                break;
            case 3:
                ListarCompras(conexion);
                break;
        }
    }

    public static void ListarPlayer(Connection conexion) throws Exception {
    Statement tabla = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "SELECT * FROM Player;";
            ResultSet Resultados = tabla.executeQuery(sql);

            while (Resultados.next()) {
                System.out.println("Detalles del Jugador:");
                System.out.println("  - ID Jugador: " + Resultados.getInt("idPlayer"));
                System.out.println("  - Nick: " + Resultados.getString("Nick"));
                System.out.println("  - Contraseña: " + Resultados.getString("password"));
                System.out.println("  - Email: " + Resultados.getString("email"));
                System.out.println(); // Separador entre registros
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla != null) {
                tabla.close();
            }
        }
    }

    public static void ListarGames(Connection conexion) throws Exception {
    Statement tabla = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        tabla = conexion.createStatement();
        String sql2 = "SELECT * FROM Games;";
        ResultSet Resultados = tabla.executeQuery(sql2);

        while (Resultados.next()) {
            System.out.println("Detalles del Juego:");
            System.out.println("  - ID Juego: " + Resultados.getInt("idGames"));
            System.out.println("  - Nombre: " + Resultados.getString("Nombre"));
            System.out.println("  - Tiempo Jugado: " + Resultados.getString("tiempoJugado"));
            System.out.println(); // Separador entre registros
        }
     } catch (ClassNotFoundException e) {
        System.out.println("Error al cargar el driver");
    } catch (SQLException e) {
        System.out.println("Error de SQL");
    } finally {
        if (tabla != null) {
            tabla.close();
        }
    }
    }
    
    public static void ListarCompras(Connection conexion) throws Exception {
    Statement tabla = null;
    try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql3 = "SELECT * FROM Compras;";
            ResultSet Resultados = tabla.executeQuery(sql3);
            
            while (Resultados.next()) {
                System.out.println("Detalles de Compra:");
                System.out.println("  - ID Compra: " + Resultados.getInt("idCompra"));
                System.out.println("  - ID Jugador: " + Resultados.getInt("idPlayer"));
                System.out.println("  - ID Juego: " + Resultados.getInt("idGames"));
                System.out.println("  - Artículo: " + Resultados.getString("Cosa"));
                System.out.println("  - Precio: $" + Resultados.getDouble("precio"));
                System.out.println("  - Fecha de Compra: " + Resultados.getString("FechaCompra"));
                System.out.println(); // Separador entre registros
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla != null) {
                tabla.close();
            }
        }

    }

}
