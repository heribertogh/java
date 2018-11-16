package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidades.Cliente;
import entidades.Contacto;
import entidades.Libro;

public class LibrosDao implements LibrosDaoLocal {

	private EntityManager em;

	public LibrosDao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_libros");
		em = factory.createEntityManager();
	}

	@Override
	public Cliente obtenerCliente(String usu, String pwd) {
		System.out.println("Estoy en obtener cliente:" + usu + "." + pwd);
		String jpql = "SELECT c FROM Cliente c where c.usuario =:usu and c.password=:pwd";
		
		TypedQuery<Cliente> tq = em.createQuery(jpql, Cliente.class);
	    tq.setParameter("usu", usu);
	    tq.setParameter("pwd", pwd);
	    
	    Cliente cli = null;
	    
		try {
			cli = tq.getSingleResult();
		} catch (Exception ex) {
			System.out.println("da error por no encontrar el usuario/password:" + usu + "." + pwd);
		}		
	    
	    return cli;

	}

	@Override
	public List<Libro> obtenerLibros() {
		System.out.println("Estoy en obtener libros");
		String jpql = "SELECT l FROM Libro l";
		
		TypedQuery<Libro> tq = em.createQuery(jpql, Libro.class);
				
		return tq.getResultList();
	}

}



/*

private EntityManager em;

public AgendaDao() {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_agenda");
	em = factory.createEntityManager();
}

//@PersistenceContext(unitName="24_ejercicio_micro_servicios")
//EntityManager em;

@Override
public List<Contacto> buscarContacto(String email) {
	System.out.println("Estoy en buscar contacto:" + email);
	String jpql = "SELECT c FROM Contacto c where c.email=:nom";
	
	TypedQuery<Contacto> tq = em.createQuery(jpql, Contacto.class);
    tq.setParameter("nom", email);
    List<Contacto> lct = tq.getResultList();
	
	return lct;
}
*/