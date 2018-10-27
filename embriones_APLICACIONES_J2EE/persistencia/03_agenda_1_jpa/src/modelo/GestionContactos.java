package modelo;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entidades.Contacto;


@Stateless
@LocalBean
public class GestionContactos implements GestionContactosLocal {

	@PersistenceContext(unitName="03_agenda_1_jpa")
	EntityManager em;
	
	@Override
	public void agregar(int telefono, String nombre, String email) {
		Contacto ct = new Contacto(email, nombre, telefono);
		em.persist(ct);
		
	}

	@Override
	public void agregar(Contacto ct) {
		em.persist(ct);
		
	}


	@Override
	public boolean eliminarContacto(int idContacto) {
		Contacto ct = this.buscarPorId(idContacto);
		boolean res = false;
		
		if (ct != null) {
			em.remove(ct);
			res = true;
		}
		
		return res;
	}

	@Override
	public Contacto buscarPorId(int idContacto) {
		return em.find(Contacto.class, idContacto);
	}


    
}


