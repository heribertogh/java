package modelo;

import java.util.List;

import javax.ejb.Local;

import entidades.Contacto;

@Local
public interface GestionContactosLocal {
	void agregar(int telefono, String nombre, String email);

	void agregar(Contacto con);

	void eliminarContacto(String email);

	void eliminarContacto(int idContacto);

	List<Contacto> recuperarTodos();

	Contacto buscarPorEmail(String email);

	Contacto buscarPorId(int idContacto);
	
	long contarRepeticionesEmail(String email);

	void actualizarEmail (String nuevoEmail, int tel);
}
