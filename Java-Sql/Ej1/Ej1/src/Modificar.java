import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;

public class Modificar {    

    public static void ModificarElegir(Connection conexion, int modificar) throws Exception {
        switch (modificar) {
            case 1:
                ModificarPlayer(conexion);
                break;
            case 2:
                ModificarGames(conexion);
                break;
            case 3:
                ModificarCompras(conexion);
                break;
        }
    }
    
    public static void ModificarPlayer(Connection conexion) throws SQLException {
        
        Statement tabla = null;
        int idPlayer;
        String Nick;
        String password;
        String email;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID del jugador que quieres modificar: ");
        idPlayer = sca.nextInt();
        System.out.println("Introduce el nuevo Nick: ");
        Nick = sca.next();
        System.out.println("Introduce la nueva contraseña: ");
        password = sca.next();
        System.out.println("Introduce el nuevo email: ");
        email = sca.next();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "UPDATE Player SET Nick = "+ Nick +
                        ", password = "+ password +
                        ", email = "+ email +
                        " WHERE idPlayer = " + idPlayer;
            tabla.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla != null) {
                tabla.close();
            }
        }

        sca.close();
    }

    public static void ModificarGames(Connection conexion) throws SQLException {
        
        Statement tabla = null;
        int idGames;
        String Nombre;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID del juego que quieres modificar: ");
        idGames = sca.nextInt();
        System.out.println("Introduce el nuevo nombre: ");
        Nombre = sca.next();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "UPDATE Games SET Nombre = '"+ Nombre + "'" +
                        " WHERE idGames = " + idGames;
            tabla.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla != null) {
                tabla.close();
            }
        }

        sca.close();
    }

    public static void ModificarCompras(Connection conexion) throws SQLException {
        
        Statement tabla = null;
        int idCompra;
        int idPlayer;
        int idGames;
        String Cosa;
        double precio;
        String FechaCompra;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID de la compra que quieres modificar: ");
        idCompra = sca.nextInt();
        System.out.println("Introduce el nuevo ID del jugador: ");
        idPlayer = sca.nextInt();
        System.out.println("Introduce el nuevo ID del juego: ");
        idGames = sca.nextInt();
        System.out.println("Introduce la nueva cosa comprada: ");
        Cosa = sca.next();
        System.out.println("Introduce el nuevo precio: ");
        precio = sca.nextDouble();
        System.out.println("Introduce la nueva fecha de compra: ");
        FechaCompra = sca.next();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "UPDATE Compras SET idPlayer = "+ idPlayer +
                        ", idGames = "+ idGames +
                        ", Cosa = "+ Cosa +
                        ", precio = "+ precio +
                        ", FechaCompra = "+ FechaCompra +
                        " WHERE idCompra = " + idCompra;
            tabla.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        } finally {
            if (tabla != null) {
                tabla.close();
            }
        }

        sca.close();
    }
}
