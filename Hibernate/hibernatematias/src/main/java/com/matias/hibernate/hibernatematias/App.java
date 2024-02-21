package com.matias.hibernate.hibernatematias;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.processing.SQL;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class App 
{
	private static Accesobd instancia;

    public static void main(String[] args) throws Exception {
        instancia=new Accesobd();
        instancia.abrir();
        Scanner sca = new Scanner(System.in);
        
        System.out.println("1. Insertar");
        System.out.println("2. Leer");
        System.out.println("3. Actualizar");
        System.out.println("4. Borrar");
        
        int opcion = sca.nextInt();
        
        switch(opcion) {
		case 1:
			System.out.println("1. Player");
			System.out.println("2. Game");
			System.out.println("3. Compras");
			opcion = sca.nextInt();
			insertar(opcion);
			break;
		case 2:
			leer();
			break;
		case 3:
			actualizar();
			break;
		case 4:
			borrar();
			break;
		default:
			break;
        }
    }

	private static void insertar(int opcion) throws Exception {
		Scanner sca = new Scanner(System.in);
        switch(opcion) {
        case 1:
        	System.out.println("Introduce el nick del jugador");
        	String nick = sca.next();
        	sca.nextLine();
        	System.out.println("Introduce el email del jugador");
        	String email = sca.next();
        	sca.nextLine();
        	System.out.println("Introduce la contraseña del jugador");
        	String password = sca.next();
        	sca.nextLine();
            Player player = new Player(nick, email, password);
            instancia.guardar(player);
            break;
        case 2:
			System.out.println("Introduce el nombre del juego");
			String nombre = sca.next();
			sca.nextLine();
			System.out.println("Introduce el tiempo jugado (en formato HH:mm:ss):");
		    String timeString = sca.nextLine();

		     // Convertir el String a un objeto Time
		     Time tiempoJugado = null;
		     try {
		    	 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		    	 java.util.Date date = sdf.parse(timeString);
		    	 tiempoJugado = new Time(date.getTime());
		     } catch (ParseException e) {
		    	 e.printStackTrace();
		     }
			Games game = new Games(nombre, tiempoJugado);
			instancia.guardar(game);
			break;
			
		case 3:
			System.out.println("Introduce el id del jugador");
			int idJugador = sca.nextInt();
			System.out.println("Introduce el id del juego");
			int idJuego = sca.nextInt();
			System.out.println("Introduce el precio");
			double precio = sca.nextDouble();
			System.out.println("Introduce la cosa");
			String cosa = sca.next();
			System.out.println("Introduce la fecha de la compra (en formato dd/MM/yyyy):");
			String fechaString = sca.nextLine();
			// Convertir el String a un objeto Date
			Date fechaCompra = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date date = sdf.parse(fechaString);
				fechaCompra = new Date(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Player oplayer = (Player) instancia.get(Player.class, idJugador);
			Games ogame = (Games) instancia.get(Games.class, idJuego);
			Compras compras = new Compras(oplayer, ogame, cosa, precio, fechaCompra);
			instancia.guardar(compras);
			break;
		        	
        }
	}
	
	private static void leer() {
		Scanner sca = new Scanner(System.in);
        System.out.println("1. Player");
        System.out.println("2. Game");
        System.out.println("3. Compras");
        int opcion = sca.nextInt();
        switch(opcion) {
        case 1:
            System.out.println("Introduce el id del jugador");
            int idJugador = sca.nextInt();
            Player player = (Player) instancia.get(Player.class, idJugador);
            System.out.println("Nick: " + player.getNick());
            System.out.println("Email: " + player.getEmail());
            System.out.println("Contraseña: " + player.getPassword());
            break;
        case 2:
            System.out.println("Introduce el id del juego");
            int idJuego = sca.nextInt();
            Games game = (Games) instancia.get(Games.class, idJuego);
            System.out.println("Nombre: " + game.getNombre());
            System.out.println("Tiempo jugado: " + game.getTiempoJugado());
            break;
        case 3:
            System.out.println("Introduce el id de la compra");
            int idCompra = sca.nextInt();
            Compras compras = (Compras) instancia.get(Compras.class, idCompra);
            System.out.println("Jugador: " + compras.getPlayer().getNick());
            System.out.println("Juego: " + compras.getGames().getNombre());
            System.out.println("Cosa: " + compras.getCosa());
            System.out.println("Precio: " + compras.getPrecio());
            System.out.println("Fecha de la compra: " + compras.getFechaCompra());
            break;
        }
    }
	
	private static void actualizar() {
        Scanner sca = new Scanner(System.in);
        System.out.println("1. Player");
        System.out.println("2. Game");
        System.out.println("3. Compras");
        int opcion = sca.nextInt();
        switch(opcion) {
        case 1:
            System.out.println("Introduce el id del jugador");
            int idJugador = sca.nextInt();
            Player player = (Player) instancia.get(Player.class, idJugador);
            System.out.println("Introduce el nuevo nick");
            player.setNick(sca.next());
            System.out.println("Introduce el nuevo email");
            player.setEmail(sca.next());
            System.out.println("Introduce la nueva contraseña");
            player.setPassword(sca.next());
            instancia.actualizar(player);
            break;
        case 2:
            System.out.println("Introduce el id del juego");
            int idJuego = sca.nextInt();
            Games game = (Games) instancia.get(Games.class, idJuego);
            System.out.println("Introduce el nuevo nombre");
            game.setNombre(sca.next());
            System.out.println("Introduce el nuevo tiempo jugado (en formato HH:mm:ss):");
            String timeString = sca.nextLine();

            // Convertir el String a un objeto Time
            Time tiempoJugado = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date date = (Date) sdf.parse(timeString);
                tiempoJugado = new Time(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            game.setTiempoJugado(tiempoJugado);
            instancia.actualizar(game);
            break;
        case 3:
            System.out.println("Introduce el id de la compra");
            int idCompra = sca.nextInt();
            Compras compras = (Compras) instancia.get(Compras.class, idCompra);
            System.out.println("Introduce el nuevo id del jugador");
            int idJugador1 = sca.nextInt();
            System.out.println("Introduce el nuevo id del juego");
            int idJuego1 = sca.nextInt();
            System.out.println("Introduce el nuevo precio");
            double precio = sca.nextDouble();
            System.out.println("Introduce la nueva cosa");
            String cosa = sca.next();
            System.out.println("Introduce la nueva fecha de la compra (en formato dd/MM/yyyy):");
            String fechaString = sca.nextLine();
            // Convertir
            Date fechaCompra = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date date = sdf.parse(fechaString);
                fechaCompra = new Date(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Player oplayer = (Player) instancia.get(Player.class, idJugador1);
            
            Games ogame = (Games) instancia.get(Games.class, idJuego1);
            compras.setPlayer(oplayer);
            compras.setGames(ogame);
            compras.setCosa(cosa);
            compras.setPrecio(precio);
            compras.setFechaCompra(fechaCompra);
            instancia.actualizar(compras);
            break;
        }
	}
	
	private static void borrar() {
		Scanner sca = new Scanner(System.in);
		System.out.println("1. Player");
		System.out.println("2. Game");
		System.out.println("3. Compras");
		int opcion = sca.nextInt();
		switch (opcion) {
		case 1:
			System.out.println("Introduce el id del jugador");
			int idJugador = sca.nextInt();
			Player player = (Player) instancia.get(Player.class, idJugador);
			instancia.borrar(player);
			break;
		case 2:
			System.out.println("Introduce el id del juego");
			int idJuego = sca.nextInt();
			Games game = (Games) instancia.get(Games.class, idJuego);
			instancia.borrar(game);
			break;
		case 3:
			System.out.println("Introduce el id de la compra");
			int idCompra = sca.nextInt();
			Compras compras = (Compras) instancia.get(Compras.class, idCompra);
			instancia.borrar(compras);
			break;
		}
	}

}
