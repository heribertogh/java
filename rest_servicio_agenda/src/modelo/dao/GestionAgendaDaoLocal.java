package modelo.dao;

import java.util.List;

import entidades.Contacto;

public interface GestionAgendaDaoLocal {
	void altaPersona(Contacto ct);
	void eliminarPorIdContacto(Contacto ct);
	List<Contacto> listarContactosPorNombre(String nombre);
	List<Contacto> recuperarContactos();

}