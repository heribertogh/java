package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import entidades.Vuelo;

@Repository(value="vl_dao")
public class VuelosDao implements VuelosDaoLocal {

	private EntityManager em;
	
	public VuelosDao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_viajes");
		em = factory.createEntityManager();
	}

	@Override
	public List<Vuelo> recuperarVuelos() {
		//System.out.println("Estoy recuperando vuelos");
		String jpql = "SELECT v FROM Vuelo v where v.plazas > 0";
		
		TypedQuery<Vuelo> tq = em.createQuery(jpql, Vuelo.class);
	    List<Vuelo> vuelos = tq.getResultList();
		
		return vuelos;
	}

	@Override
	public boolean cancelarPlazas(int vuelo, int plazas) {
		Vuelo vl = em.find(Vuelo.class, vuelo);
		if (vl == null) {
			return false;
		}
		vl.setPlazas(vl.getPlazas() + plazas);
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(vl);
		et.commit();		
		return true;
	}

	@Override
	public boolean reservarPlazas(int vuelo, int plazas) {
		Vuelo vl = em.find(Vuelo.class, vuelo);
		if (vl == null || vl.getPlazas() < plazas) {
			return false;
		}
		vl.setPlazas(vl.getPlazas() - plazas);
		
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(vl);
		et.commit();		
		
		return true;
	}

}

