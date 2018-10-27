package modelo.dao;

import java.util.List;

import javax.ejb.Local;

import entidades.Contacto;

@Local
public interface GestionContactosDaoLocal {
	void altaContacto(Contacto ct);
	Contacto recuperarContactoPorClave(int idContacto);
	void eliminarContacto(Contacto ct);
	List<Contacto> recuperarContactos();
	Contacto buscarContacto(String email);
	void actualizarContacto(Contacto ct);
}
