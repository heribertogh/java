package modelo.dao;

import java.util.List;

import entidades.Cliente;
import entidades.Libro;

public interface LibrosDaoLocal {
	public Cliente obtenerCliente(String usu, String pwd);
	public List<Libro> obtenerLibros();
}
