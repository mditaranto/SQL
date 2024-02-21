import java.sql.Connection;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        Connection conexion = null;

        conexion = Conexion.GetConexion();
        
        Scanner sca = new Scanner(System.in);
        int opcion = 9;

        while (opcion != 0) {
            System.out.println("");
            System.out.println("1. Crear tablas");
            System.out.println("2. Insertar datos");
            System.out.println("3. Listar tablas");
            System.out.println("4. Modificar datos");
            System.out.println("5. Borrar datos");
            System.out.println("0. Salir");
    
            System.out.println("Introduce una opción: ");
            opcion = sca.nextInt();
            switch (opcion) {
                case 1:
                    CreacionTablas.CrearTablas(conexion);
                    break;
                case 2:
                    InsertarDatos.InsertarDatosTabla(conexion);
                    break;
                case 3:
                    System.out.println("1. Listar Player");
                    System.out.println("2. Listar Games");
                    System.out.println("3. Listar Compras");
                    int listar = sca.nextInt();
                    ListarTablas.Listar(conexion, listar);
                    break;
                case 4:
                    System.out.println("1. Modificar Player");
                    System.out.println("2. Modificar Games");
                    System.out.println("3. Modificar Compras");
                    int modificar = sca.nextInt();
                    Modificar.ModificarElegir(conexion, modificar);
                    break;
                case 5:
                    System.out.println("1. Borrar Player");
                    System.out.println("2. Borrar Games");
                    System.out.println("3. Borrar Compras");
                    int borrar = sca.nextInt();
                    Borrar.BorrarElegir(conexion, borrar);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        sca.close();
    }

}
