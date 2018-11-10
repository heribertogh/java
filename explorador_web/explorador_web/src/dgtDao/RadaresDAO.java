package dgtDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import entidadesDGT.Radares;

public class RadaresDAO {
	private EntityManager em;
	
	public RadaresDAO() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("datosDGT");
		em = factory.createEntityManager();
	}

	public void InsertarRadar(Radares rd) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(rd);
		et.commit();;		
	}
}
