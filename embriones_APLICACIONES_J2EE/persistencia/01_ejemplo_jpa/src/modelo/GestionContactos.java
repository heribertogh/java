package modelo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entidades.Contacto;

public class GestionContactos {
	private EntityManager em;
	
	public GestionContactos() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("01_ejemplo_jpa");
		em = factory.createEntityManager();
	}
		
	public void altaContacto(String nombre, String email, int telefono ) {
		Contacto ct = new Contacto(email, nombre, telefono);
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(ct);
		et.commit();;		
	}
	
	public Contacto recuperarContacto(int idContacto) {
		return em.find(Contacto.class, idContacto);
	}
	
	public void eliminarContacto(int idContacto) {
		Contacto ct = this.recuperarContacto(idContacto);
		if (ct != null) {
			EntityTransaction et = em.getTransaction();
			et.begin();
			em.remove(ct);
			et.commit();
		}
	}
	
	public void actualizaRcontacto(Contacto ct) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.merge(ct);
		et.commit();
	}
	
}




