import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Borrar {

    public static void BorrarElegir(Connection conexion, int borrar) throws Exception {
        switch (borrar) {
            case 1:
                BorrarPersona(conexion);
                break;
            case 2:
                BorrarJuego(conexion);
                break;
            case 3:
                BorrarCompra(conexion);
                break;
        }
    } 

    public static void BorrarPersona(Connection conexion) throws SQLException {

        Statement tabla = null;
        int idPersona;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID de la persona que quieres borrar: ");
        idPersona = sca.nextInt();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "DELETE FROM Persona WHERE idPersona = " + idPersona;
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

    public static void BorrarJuego(Connection conexion) throws SQLException {

        Statement tabla = null;
        int idJuego;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID del juego que quieres borrar: ");
        idJuego = sca.nextInt();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "DELETE FROM Juego WHERE idJuego = " + idJuego;
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

    public static void BorrarCompra(Connection conexion) throws SQLException {

        Statement tabla = null;
        int idCompra;

        Scanner sca = new Scanner(System.in);
        System.out.println("Introduce el ID de la compra que quieres borrar: ");
        idCompra = sca.nextInt();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            tabla = conexion.createStatement();
            String sql = "DELETE FROM Compra WHERE idCompra = " + idCompra;
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
