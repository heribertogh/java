package modelo.service;

import java.util.List;

import javax.ejb.Local;

import entidades.Contacto;

@Local
public interface GestionAgendaServiceLocal {
	void agregarContacto(String nombre, String email, int telefono);
	void agregarContacto(Contacto ct);
	List<Contacto> obtenerContactos();
	void eliminarContactos(int idContacto);
	Contacto obtenerContacto(String email);
	boolean autenticarUsuario(String user, String pwd);
}
