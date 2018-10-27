package modelo;

import javax.ejb.Local;

import beans.Usuario;

@Local
public interface GestionUsuariosLocal {
	Usuario LeerUsuario(String usuario, String password);
}
