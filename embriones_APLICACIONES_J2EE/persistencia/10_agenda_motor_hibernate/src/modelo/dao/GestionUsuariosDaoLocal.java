package modelo.dao;

import javax.ejb.Local;

import entidades.Usuario;

@Local
public interface GestionUsuariosDaoLocal {
	Usuario buscaUsuario(String usuario, String pwd);
	
}
