package modelo.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Contacto;

/**
 * Session Bean implementation class GestionContactosDao
 */
@Stateless
@LocalBean
public class GestionContactosDao implements GestionContactosDaoLocal {

	@PersistenceContext(unitName="10_agenda_motor_hibernate")
	EntityManager em;
	
	@Override
	public void altaContacto(Contacto ct) {
		em.persist(ct);
	}

	@Override
	public Contacto recuperarContactoPorClave(int idContacto) {
		return em.find(Contacto.class, idContacto);
	}

	@Override
	public void eliminarContacto(Contacto ct) {
		em.remove(ct);
	}

	@Override
	public List<Contacto> recuperarContactos() {
		TypedQuery<Contacto> tq = em.createNamedQuery("Contacto.findAll", Contacto.class); 
		return tq.getResultList();
	}

	@Override
	public Contacto buscarContacto(String email) {
		TypedQuery<Contacto> tq = em.createNamedQuery("Contacto.findByEmail", Contacto.class);
	    tq.setParameter("nom", email);
	    List<Contacto> lc = tq.getResultList();

	    System.out.println("Estoy aqui");
	    if (lc == null) System.out.println("la lista recuperada es null");
	    if (lc.size() == 0) {
	    	System.out.println("la lista recuperada es de size 0------------------------------");
	    	return null;
	    } else {
		    for (Contacto ct : lc) {
		    	System.out.println("Contacto:" + ct.getEmail() + " nombre:" + ct.getNombre());
		    }
			return lc.get(0);	    	
	    }
	}

	@Override
	public void actualizarContacto(Contacto ct) {
		em.merge(ct);		
	}

}

