package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import entidades.Contacto;

@Repository(value="g_agendaDao")
public class GestionAgendaDao implements GestionAgendaDaoLocal {

	@PersistenceContext(unitName="unidadSpringPU")
	EntityManager em;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void altaPersona(Contacto ct) {
			em.persist(ct);		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void eliminarPorIdContacto(Contacto ct) {
		System.out.println("estoy antes remove:::" + ct.getIdContacto());
		
		Contacto con = em.find(Contacto.class, ct.getIdContacto());
		em.remove(con);
		System.out.println("estoy despues remove:" + con.getIdContacto());
	}

	@Override
	public List<Contacto> listarContactosPorNombre(String nombre) {
        String jpql = "select c from Contacto c where c.nombre = ?1";
        TypedQuery<Contacto> qr = em.createQuery(jpql, Contacto.class);
        qr.setParameter(1, nombre);
        List<Contacto> lista = qr.getResultList();
        return lista;		
	}
	
	@Override
	public List<Contacto> recuperarContactos() {
        String jpql = "select c from Contacto c";
        TypedQuery<Contacto> qr = em.createQuery(jpql, Contacto.class);
        List<Contacto> lista = qr.getResultList();
        return lista;		
	}
	

}




