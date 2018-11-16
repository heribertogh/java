package modelo.service;

import java.util.List;

import entidades.Contacto;

public interface GestionAgendaServiceLocal {	
	public void altaPersona(Contacto ct);
	public List<Contacto> eliminarPorIdContacto(int idContacto);
	public List<Contacto> listarContactosPorNombre(String nombre);
}
