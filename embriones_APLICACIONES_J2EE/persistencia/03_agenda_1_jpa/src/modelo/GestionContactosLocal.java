package modelo;

import java.util.List;

import javax.ejb.Local;

import entidades.Contacto;

@Local
public interface GestionContactosLocal {
	void agregar(int telefono, String nombre, String email);

	void agregar(Contacto con);

	boolean eliminarContacto(int idContacto);

	Contacto buscarPorId(int idContacto);

}
