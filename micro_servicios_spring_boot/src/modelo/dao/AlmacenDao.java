package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidades.Producto;
import entidades.Seccione;

public class AlmacenDao implements AlmacenDaoLocal {

	private EntityManager em;
	
	public AlmacenDao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_almacen");
		em = factory.createEntityManager();			
	}

	@Override
	public List<Seccione> recuperarSecciones() {
		System.out.println("Estoy en recupear secciones");
		String jpql = "SELECT s FROM Seccione s";
		
		TypedQuery<Seccione> tq = em.createQuery(jpql, Seccione.class);
	    List<Seccione> lct = tq.getResultList();
		
		return lct;
	}

	@Override
	public void insertarProducto(Producto pro) {
		System.out.println("Estoy en alta producto:" + pro.getNombre() + "." + pro.getPrecio());
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(pro);
		et.commit();;		

		
	}

}
