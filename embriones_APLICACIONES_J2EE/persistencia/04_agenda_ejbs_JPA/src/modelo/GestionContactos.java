package modelo;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Contacto;

@Stateless
@LocalBean
public class GestionContactos implements GestionContactosLocal {

	@PersistenceContext(unitName="04_agenda_ejbs_JPA")
	EntityManager em;
	
	@Override
	public void agregar(int telefono, String nombre, String email){
		Contacto ct = new Contacto(email, nombre, telefono);
		em.persist(ct);
	}
	
	@Override
	public void agregar(Contacto ct){
		em.persist(ct);
    } 

	@Override
	public void eliminarContacto(String email){
		Contacto ct = this.buscarPorEmail(email);
		
		if (ct != null) {
			em.remove(ct);
		}	

		/*
		String jpql = "delete from Contacto c where c.email=?1";
		Query qr = em.createQuery(jpql);
		qr.setParameter(1, email);
		qr.executeUpdate();
		*/
	
	}

    @Override
	public void eliminarContacto(int idContacto){
		Contacto ct = this.buscarPorId(idContacto);
		
		if (ct != null) {
			em.remove(ct);
		}	
	}
    
    @Override
	public List<Contacto> recuperarTodos(){  
        String jpql = "select c from Contacto c";
        TypedQuery<Contacto> qr = em.createQuery(jpql, Contacto.class);

        List<Contacto> lista = qr.getResultList();
        return lista;
    }
    
    
    /* el metodo anterior dara error sino encuentra ningun contacto es posible que no */
    /* el metodo posterior dara un error sino encuenta ningun contacto o encuentra mas de uno */
    
    
    @Override
	public Contacto buscarPorEmail(String email) {
    	//String sql="select c from Contacto c where c.email = '" + email + "'";
    	
    	System.out.println("encontrados......:" + this.contarRepeticionesEmail(email));
    	
    	String sql="select c from Contacto c where c.email = ?1";
    	System.out.println("sql................: " + sql);

    	TypedQuery<Contacto> qr = em.createQuery(sql, Contacto.class);
    	qr.setParameter(1,email);
    	
    	Contacto ct = qr.getSingleResult();
        return ct;
    	
    }

	@Override
	public Contacto buscarPorId(int idContacto) {
		return em.find(Contacto.class, idContacto);
	}

	@Override
	public long contarRepeticionesEmail(String email) {
		String jpql = "select count(c) from Contacto c where c.email=?1";
		Query qr = em.createQuery(jpql);
		qr.setParameter(1, email);
		Object ob = qr.getResultList().get(0);
		long valor = (Long) ob;
		return valor;
		
	}

	@Override
	public void actualizarEmail(String nuevoEmail, int tel) {
		String jpql = "update Contacto c set c.email = :em where c.telefono = :te";
		Query qr = em.createQuery(jpql);
		qr.setParameter("em", nuevoEmail);
		qr.setParameter("te", tel);
		int res = qr.executeUpdate();
		System.out.println("el numero de unidades afectadas es.....:" + res );
	}

    
}


