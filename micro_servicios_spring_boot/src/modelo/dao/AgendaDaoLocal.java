package modelo.dao;

import java.util.List;

import entidades.Contacto;

public interface AgendaDaoLocal {

	List<Contacto> buscarContacto(String email);

}