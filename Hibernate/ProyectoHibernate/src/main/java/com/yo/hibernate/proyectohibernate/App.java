package com.yo.hibernate.proyectohibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
    	
    	SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();   
    	
    	Session session = sf.openSession();
    	
    	Player player = new Player();
    	
    	player.setIdPlayer(1);
    	player.setNick("Mateo");
    	player.setEmail("Mateo@gmail.com");
    	player.setPassword("12345");
    	
    	session.beginTransaction().begin();
    	
    	session.save(player);
    	
    	session.getTransaction().commit();
    	
    	session.close();
    	sf.close();
    }
}
