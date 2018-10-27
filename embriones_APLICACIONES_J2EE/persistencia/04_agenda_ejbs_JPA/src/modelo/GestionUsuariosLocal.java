package modelo;

import javax.ejb.Local;

import entidades.Usuario;

@Local
public interface GestionUsuariosLocal {
	Usuario LeerUsuario(String usuario, String password);
}
