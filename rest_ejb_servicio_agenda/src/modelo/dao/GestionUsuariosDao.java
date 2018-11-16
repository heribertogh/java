package modelo.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entidades.Usuario;

/**
 * Session Bean implementation class GestionUsuariosDao
 */
@Stateless
@LocalBean
public class GestionUsuariosDao implements GestionUsuariosDaoLocal {

	@PersistenceContext(unitName="17_servicio_rest_agenda_ejb")
	EntityManager em;

	@Override
	public Usuario buscaUsuario(String usuario, String pwd) {
		TypedQuery<Usuario> qr = em.createNamedQuery("Usuario.login", Usuario.class);
    	Usuario usu = null;
    	qr.setParameter(1, usuario);
    	qr.setParameter(2, pwd);
    	List<Usuario> lu = qr.getResultList();
  
    	if (lu.size() > 0) {
    		usu = lu.get(0);
    	}
        return usu;
		
	}

 
}
