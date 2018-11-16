package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import entidades.Hotele;

@Repository(value="ho_dao")
public class HotelesDao implements HotelesDaoLocal {

	private EntityManager em;
	
	public HotelesDao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_viajes");
		em = factory.createEntityManager();
	}

	@Override
	public List<Hotele> obtenerHoteles(byte disponible) {
			//System.out.println("Estoy recuperando hoteles");
			String jpql = "SELECT h FROM Hotele h where h.disponible = :dis";
			
			TypedQuery<Hotele> tq = em.createQuery(jpql, Hotele.class);
		    tq.setParameter("dis", disponible);
		    List<Hotele> hoteles = tq.getResultList();
			
			return hoteles;
	}

}
