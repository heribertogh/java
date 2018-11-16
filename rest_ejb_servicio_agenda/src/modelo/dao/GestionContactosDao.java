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

	@PersistenceContext(unitName="17_servicio_rest_agenda_ejb")
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
		System.out.println("Estoy en buscar contacto:" + email);
		TypedQuery<Contacto> tq = em.createNamedQuery("Contacto.findByEmail", Contacto.class);
	    tq.setParameter("nom", email);
		Contacto ct = null;
		try {
			ct = tq.getSingleResult();
		} catch (Exception ex) {
			System.out.println("da error por no encontrar el email:" + email);
		}		
		
		return ct;
	}

	@Override
	public void actualizarContacto(Contacto ct) {
		em.merge(ct);		
	}

}

