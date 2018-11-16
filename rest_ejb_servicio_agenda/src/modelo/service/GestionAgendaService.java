package modelo.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entidades.Contacto;
import entidades.Usuario;
import modelo.dao.GestionContactosDaoLocal;
import modelo.dao.GestionUsuariosDaoLocal;

/**
 * Session Bean implementation class GestionAgendaService
 */
@Stateless
@LocalBean
public class GestionAgendaService implements GestionAgendaServiceLocal {

	@EJB
	GestionContactosDaoLocal gdao;
	@EJB
	GestionUsuariosDaoLocal gudao;
	
	@Override
	public void agregarContacto(String nombre, String email, int telefono) {
		Contacto ct = gdao.buscarContacto(email);
		if (ct == null) {
			ct = new Contacto(email, nombre, telefono);
			gdao.altaContacto(ct);
		}
	}

	@Override
	public void agregarContacto(Contacto ct) {
		this.agregarContacto(ct.getNombre(), ct.getEmail(), ct.getTelefono());
	}

	@Override
	public List<Contacto> obtenerContactos() {
		return gdao.recuperarContactos();
	}

	@Override
	public void eliminarContactos(int idContacto) {
		Contacto ct = gdao.recuperarContactoPorClave(idContacto);
		if (ct != null) {
			gdao.eliminarContacto(ct);
		}
	}

	@Override
	public Contacto obtenerContacto(String email) {
		return gdao.buscarContacto(email);
	}

	@Override
	public boolean autenticarUsuario(String user, String pwd) {
		Usuario usu = gudao.buscaUsuario(user, pwd);
		if ( usu == null) {
			return false;
		} else {
			return true;
		}

	}
}
