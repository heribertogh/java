package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import entidades.Reserva;

@Repository(value="rs_dao")
public class ReservasDao implements ReservasDaoLocal {

	private EntityManager em;
	
	public ReservasDao() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("P_viajes");
		em = factory.createEntityManager();			
	}

	@Override
	public int agregarReserva(Reserva rs) {
		//System.out.println("Estoy en alta reserva:" + rs.getDni() + "." + rs.getNombre() + "." + rs.getHotel());
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(rs);
		et.commit();		

		//System.out.println ("el codigo es:" + rs.getIdReserva());
		return rs.getIdReserva();
	}

	@Override
	public boolean cancelarReserva(int idReserva) {
		Reserva rs = em.find(Reserva.class, idReserva);
		if (rs == null) {
			return false;
		}
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.remove(rs);
		et.commit();		

		return true;
	}

}
