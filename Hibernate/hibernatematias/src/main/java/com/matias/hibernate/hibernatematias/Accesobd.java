package com.matias.hibernate.hibernatematias;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Accesobd {
    private SessionFactory sf;
    private Session sesion;
    private Transaction transaction;
    protected void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sf = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    public void abrir() throws Exception {
        setUp();
        sesion=sf.openSession();
        transaction = sesion.beginTransaction();
    }
    public void cerrar(){
        try {
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
        sf.close();
    }

	public void guardar(Object cosa) {
		sesion=sf.openSession();
        transaction = sesion.beginTransaction();
        int id = (int) sesion.save(cosa);
        transaction.commit();
        System.out.println(id);
        sesion.close();
    }
    
	public Object get(Class cosa, int id) {
		return sesion.get(cosa, id);
	}
	
	public void actualizar(Object cosa) {
		sesion=sf.openSession();
        transaction = sesion.beginTransaction();
		sesion.update(cosa);
		transaction.commit();
        System.out.println("Actualizado");
        sesion.close();
	}
	
	public void borrar(Object cosa) {
		sesion=sf.openSession();
        transaction = sesion.beginTransaction();
		sesion.delete(cosa);
		transaction.commit();
        sesion.close();
	}
}
