package modelo;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Usuario;

@Stateless
@LocalBean
public class GestionUsuarios implements GestionUsuariosLocal {

	@PersistenceContext(unitName="04_agenda_ejbs_JPA")
	EntityManager em;

	@Override
	public Usuario LeerUsuario(String usuario, String password) {
		/*
    	String sql="select u from Usuario u where u.usuario = ?1 and u.passwd = ?2";
    	System.out.println("sql................: " + sql);
    	TypedQuery<Usuario> qr = em.createQuery(sql, Usuario.class);
    	qr.setParameter(1, usuario);
    	qr.setParameter(2, password);
    	*/
		
		TypedQuery<Usuario> qr = em.createNamedQuery("Usuario.login", Usuario.class);
    	Usuario usu = null;
    	qr.setParameter(1, usuario);
    	qr.setParameter(2, password);
    	List<Usuario> lu = qr.getResultList();
    	
    	if (lu.size() > 0) {
    		usu = lu.get(0);
    	}
        return usu;
	}

}
