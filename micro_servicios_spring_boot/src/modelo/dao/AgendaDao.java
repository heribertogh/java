package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import entidades.Contacto;

@Repository(value="ag_dao22")
public class AgendaDao implements AgendaDaoLocal {

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

}
